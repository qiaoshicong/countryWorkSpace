<template>
<!--<h1 class="hdtitle">home</h1>-->
  <div calss='box'>
    <!-- 首页上半部分 -->
    <!-- 标题下图片 -->
    <div class="head-image">
      <div>
        <el-image src="../assets/img/home.jpeg"></el-image>
      </div>
    </div>
    <!-- 通知公告导航条 -->
    <el-row type='flex' class='row-bg underline marb10' justify='space-between'>
      <span class='color'><b>通知公告</b></span>
      <span @click="goMore('通知公告')" class='liPointer'> 更多<i class='el-icon-d-arrow-right'></i> </span>
    </el-row>
    <!-- 通知公告新闻列表 -->
    <div class="homenews">
      <ul>
        <li class='lieBiao liPointer' :key='item.eid' v-for='item in noticeList.notice'>
          <div class='time'>
            {{ item.createDatetime }}
<!--            {{ item.title }}-->
          </div>
          <img :src=" item.thumbnail" alt='' style='width:50px; height:90%' referrerPolicy="no-referrer"/>
          <div class='title' @click='toNoticeMsg(item.eid)'>
            {{ item.title }}
          </div>
        </li>
      </ul>
    </div>
    <a-row>
      <a-col :span="24">
      </a-col>
    </a-row>
    <!-- 首页下半部分 -->
    <el-row type='flex' class='row-bg' justify='space-between'>
      <work class='work' />
      <school class='school' />
      <other class='other' />
    </el-row>
  </div>
</template>

<script>

import {defineComponent, onMounted, reactive} from "vue";
import {NewsArticlesService} from "@/views/cms/newsArticles/newsArticles/newsArticlesService";
import router from "@/router";

export default defineComponent({
  methods: {
  },
  setup(){
    let noticeList =reactive({
      notice:[]
    })
    let imgList =reactive({
      img:[]
    })
    onMounted(() => {
      const queryParams = {}
      queryParams.currentPage = 1
      queryParams.pageSize = 6

      NewsArticlesService.findAll().then( res=>{
        noticeList.notice = res.data;
        console.log("notice"+res.data)
      }).catch(error => {
        console.log(error)
      })
    })

    const toNoticeMsg = (id) => {
      router.push({
        path: '/index/news',
        query: {
         id:id
        }

         })}
    return{
      noticeList,
      toNoticeMsg,
      imgList
    }
  }
})
</script>

<style  lang='less' scoped>

.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 160px;
  line-height: 160px;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}
.underline {
  border-bottom: 1px solid rgb(1, 72, 153);
}

.lieBiao {
  width: 100%;
  height: 54px;
  background: rgb(241, 241, 241);
  display: flex;
  justify-content: space-between;
  overflow: hidden;
  margin-bottom: 10px;
}

.time {
  width: 100px;
  padding: 17px 0px;
  display: flex;
  flex-direction: column;
  background-color: rgb(1, 72, 153);
  color: white;
  text-align: center;
}
.homenews{
  padding: 5px;
  margin: 0 auto;
}
.title {
  width: 500px;
  height: 50px;
  overflow: hidden;
  line-height: 50px;
// background-color: rgb(179, 19, 19);
  padding: 0 0 0 10px;
}
.head-image{
  float: left;
  height: 400px;
  width: 1200px;
  position: relative;
  left: 250px;
  margin-bottom:10px;
  background-image: url(~@/assets/img/home.jpeg);
}
.hdtitle{
  font-size: 35px;
  color: #f39408;
  text-align: center;
}
.news{
  float: left;
  margin-left: 85px;
}
.row-bg underline marb10{
  width: 500px;
  height: 30px;
}
</style>
