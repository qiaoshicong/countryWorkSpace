<template>
  <div class='box'>
    <div class='header'>
      <h2>{{ newsObject.title }}</h2>
      <p>发布日期：{{ newsObject.createDatetime }}  </p>
    </div>
    <hr />
    <div v-html='newsObject.context' class='ql-editor'></div>
  </div>
</template>

<script>
import {defineComponent, onMounted, reactive} from "vue";
import {NewsArticlesService} from "@/views/cms/newsArticles/newsArticles/newsArticlesService";
import {useRouter} from "vue-router";


export default defineComponent({

  setup(){
    const newsObject= reactive({
      context:'',
      createDatetime:'',
      title:''
    })
    onMounted(() => {

      const router = useRouter()
      const data = router.currentRoute.value.query.id
      NewsArticlesService.findNewsArticles(data).then(res => {
        console.log(res)
        newsObject.context = res.data.context
        newsObject.createDatetime = res.data.createDatetime
        newsObject.title = res.data.title
      })
    })
    return{
onMounted,
    newsObject
    }
  }
})
</script>

<style scoped>

.box{
  background: #f6f0e8;
  width: 850px;
  margin: 5px auto;
  padding: 10px 25px 15px ;
}
</style>
