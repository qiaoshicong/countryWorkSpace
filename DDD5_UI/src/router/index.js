/**
 * 路由配置
 */
import {createRouter, createWebHistory,createWebHashHistory} from 'vue-router';
import store from "@/store";
import setting from '@/config/setting';

import NProgress from 'nprogress';
import city from '@/views/cms/home/city'
import help from '@/views/cms/home/help'
import tour from '@/views/cms/home/tour'
import education from '@/views/cms/home/education'
import First from '@/views/cms/home/First'
import news from '@/views/cms/home/news'
import welcome from '@/views/cms/home/welcome'
// 静态路由
const routes = [
  {
    path: '/',redirect:'/index/welcome'

  },
  {
    path: '/login',
    component: () => import('@/views/login/login'),
    meta: {title: '登录'}
  },
  {
    path: '/register',
    component:()=>import('@/views/login/register'),
    meta: {title: '注册'}
  },
  {
    path: '/forget',
    component: () => import('@/views/login/forget'),
    meta: {title: '忘记密码'}
  },
  {
    path: '/decache',
    component: () => import('@/views/login/decache'),
    meta: {title: '忘记密码'}
  },

  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/exception/404')
  },
  {
    path: '/news',
    component:() => import('@/views/cms/home/news')

  },
  {
    path: '/index',
    component: () => import('@/views/cms/home/index'),
    children:[{
      path: 'city',
      component:city
    },{
      path: 'help',
      component:help
    },{
      path: 'education',
      component:education
    },{
      path: 'tour',
      component:tour
    },{
      path: 'first',
      component: First
    },{
      path: 'news',
      component: news
    },{
      path: 'welcome',
      component: welcome
    }
    ]
  }
];

const router = createRouter({
  routes,
  // history: createWebHistory()
  history: process.env.IS_ELECTRON ? createWebHashHistory() : createWebHistory(),
});

// 路由守卫
router.beforeEach((to, from, next) => {
  NProgress.start();
  updateTitle(to);

  if(to.path === '/decache')
  {
    store.dispatch('user/setUser').then(() => {
    });
    store.dispatch('user/setToken').then(() => {
    });
    next();  // 在无需登录的白名单内
  }
  else if (setting.takeToken()) {
    //判断是否已经注册动态路由
    if (!store.state.user.menus) {
      // debugger
      // 获取动态路由
      const  user = setting.takeUser();
      if(user)
      {
        store.dispatch('user/getMenus',user)
        next({...to, replace: true});
      }
      else
      {
        store.dispatch('user/getUser').then(() => {
          store.dispatch('user/getMenus',user)
          next({...to, replace: true});
        })
      }
    } else {
      next();
    }
  } else if (setting.whiteList.indexOf(to.path) !== -1) {
    next();  // 在无需登录的白名单内
  } else {
    // 未登录跳转登录页面
    next({path: '/login', query: to.path === '/' ? {} : {from: to.path}});
  }
});

router.afterEach(() => {
  setTimeout(() => {
    NProgress.done(true);
  }, 300);
});

export default router;

/**
 * 更新浏览器标题
 * @param route 路由
 */
function updateTitle(route) {
  let names = [];
  if (route && route.meta && route.meta.title) {
    names.push(route.meta.title);
  }
  const appName = process.env.VUE_APP_NAME;
  if (appName) {
    names.push(appName);
  }
  document.title = names.join(' - ');
}
