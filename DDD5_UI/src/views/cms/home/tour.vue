<template>
  <h1 class="hdtitle">乡村旅游</h1>
  <div calss='box'>
    <!-- 首页上半部分 -->
    <!-- 标题下图片 -->
    <div class="head-image">
      <div>
        <el-image src="../assets/img/xcly.jpg"></el-image>
      </div>
    </div>
    <!-- 通知公告导航条 -->
    <el-row type='flex' class='row-bg underline marb10' justify='space-between'>
      <span class='color'><b>通知公告</b></span>
      <span @click="goMore('通知公告')" class='liPointer'> 更多<i class='el-icon-d-arrow-right'></i> </span>
    </el-row>
    <!-- 通知公告新闻列表 -->
    <div style='max-height:382px;overflow:hidden;'>
      <ul>
        <li class='lieBiao liPointer' :key='item.eid' v-for='item in noticeList.notice'>
          <div class='time'>
            {{ item.createDatetime }}
            <!--            {{ item.title }}-->
          </div>
          <div class='title' @click='toNoticeMsg(item.eid)'>
            {{ item.title }}
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>

import {defineComponent, onMounted, reactive} from "vue";
import {NewsArticlesService} from "@/views/cms/newsArticles/newsArticles/newsArticlesService";
import router from "@/router";

export default defineComponent({

  setup(){
    let noticeList =reactive({
      notice:[]
    })
    onMounted(() => {
      const conditions = { categoryId:4}
      const queryParams = {}
      queryParams.currentPage = 1
      queryParams.pageSize = 6
      queryParams.filters = conditions;
      NewsArticlesService.findNewsArticless(queryParams).then( res =>{
        // debugger
        noticeList.notice = res.data.datas
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



    // const queryParams = {}
    // queryParams.currentPage = 1
    // queryParams.pageSize = 6
    // NewsArticlesService.findNewsArticless(queryParams).then( res =>{
    //   // debugger
    //   noticeList.values = res.data.datas
    // }).catch(error => {
    //   console.log(error)
    // })
    return{
      noticeList,
      toNoticeMsg
    }
  }
})
</script>

<style scoped>
.hdtitle{
  font-size: 35px;
  color: #f39408;
  text-align: center;
}
.head-image{
  height: 452px;
  width: 1200px;
  position: relative;
  left: 420px;
  background-image: url(~@/assets/img/xcly.jpg);
}
</style>
