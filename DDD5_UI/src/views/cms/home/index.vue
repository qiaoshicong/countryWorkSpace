<template>
<!--<h2>主页</h2>-->
  <el-container class='home-container'>
    <!-- 主页头部 -->
    <el-header >
      <div class="bannner">

      </div>
      <div>

      </div>
    </el-header>
    <a-row class="banner">
      <a-col :span='4' :key='index' v-for='(item, index) in menuList' :index='item.id.toString()'>
        <a-button type="danger" block @click="handleSelect(item.id)">{{item.name}}</a-button>
<!--        <Button type="danger"  @click="handleSelect(item.id)">{{item.name}}</Button>-->
      </a-col>
    </a-row>
    <el-container>
      <!-- 更变的内容区域 -->
      <el-main class='main'>
        <router-view></router-view>
      </el-main>
    </el-container>

    <el-footer></el-footer>
  </el-container>


</template>

<script>


import {notification} from "ant-design-vue";
import {defineComponent, reactive} from "vue";
import {useRouter} from "vue-router";

export default defineComponent({

  name: "index",
  data(){
    return {
      activeIndex: '1',
      menuList: [
        { name: '首页', id: 1 },
        { name: '城乡规划', id: 2 },
        { name: '帮扶政策', id: 3 },
        { name: '乡村教育', id: 4 },
        { name: '乡村旅游', id: 5 },
        {name:'',id:6}
      ],
      menuList2: [],

    }},
  setup() {
    const router = useRouter();
    const loginbtn = reactive({
      flag:true
    });
    const openNotification = () => {
      notification.open({
        message: 'Notification Title',
        description:
          'I will never close automatically. I will be close automatically. I will never close automatically.',
        duration: 1,
      });
    };

    const formatterTitle = (t) => {
      console.log("t" + t)
      // eslint-disable-next-line no-empty
      switch (t) {
        case '1' :
          return 'news'
        case '2' :
          return 'city'
        case '3' :
          return 'help'
        case '4' :
          return 'education'
        case '5' :
          return 'tour'

      }

    }
    const handleSelect = (key) => {
      if(key===1){ router.push({
        path: '/index/First',
        query: {
          msg: 'index'
        }

      })
      }else if (key===2){
        router.push({
          path: '/index/city',
          query: {
            msg: 'city'
          }

        })
      }else if (key===3){
        router.push({
          path: '/index/help',
          query: {
            msg: 'help'
          }

        })
      }else if (key===4){
        router.push({
          path: '/index/education',
          query: {
            msg: 'education'
          }

        })
      }else if (key===5){
        router.push({
          path: '/index/tour',
          query: {
            msg: 'tour'
          }

        })
      }else if (key===6){
        router.push({
          path: '/index/tour',
          query: {
            msg: 'tour'
          }

        })
      }
      console.log(key)

     }

     const disappear =() =>{
      loginbtn.flag = false
       router.push({
         path: '/index/tour',
         query: {
           msg: 'tour'
         }
       })
     }

// router.push('/'+formatterTitle(key))
//     }

      return {
        openNotification,
        formatterTitle,
        handleSelect,
        disappear,
        loginbtn
      };
    }
  })
</script>

<style  scoped>
.home-container {
  height: 100%;
}

.el-header {
  background-color: rgb(1, 72, 153);
}

.el-header {
  height: 120px !important;
  padding: 0 80px 0 120px;
  overflow: hidden;
  display: flex;
  justify-content: space-between;
  align-items: center;
}


.menu {
  display: flex;
  justify-content: space-between;
}

.el-footer {
  background-color: rgb(85, 81, 82);
}

.main {
  padding: 20px 5px;
}

.el-footer {
  font-size: 15px;
  color: white;
  text-align: center;
  line-height: 60px;
}

.bannner{
  height: 20vh;
  background-image: url("~@/assets/img/乡村1.jpg");
  background-repeat: no-repeat;
  background-size: cover;
}
.banner{
  padding-left: 35vh;
  background-color: #ed8d36;
}
/*.box {*/
/*  width: 1200px;*/
/*  margin: 0 auto;*/
/*}*/
.menu{
  background-color: red;
  text-decoration-color: white;
}
</style>
