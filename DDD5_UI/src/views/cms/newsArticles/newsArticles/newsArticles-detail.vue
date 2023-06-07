<!-- 新闻文章详情弹窗 -->
<template>
  <a-form
    ref="form3"
    class="ele-form-detail"
    :model="newsArticlesModalApp.newsArticles"
    :label-col="{md: {span: 2}, sm: {span: 4}, xs: {span: 6}}"
    :wrapper-col="{md: {span: 22}, sm: {span: 20}, xs: {span: 18}}">
    <a-descriptions :title="newsArticlesModalApp.title" bordered>
      <a-descriptions-item label="名称">
        {{ newsArticlesModalApp.newsArticles.name }}
      </a-descriptions-item>
      <a-descriptions-item label="新闻标题">
        {{ newsArticlesModalApp.newsArticles.title }}
      </a-descriptions-item>
      <a-descriptions-item label="新闻概要">
        {{ newsArticlesModalApp.newsArticles.summary }}
      </a-descriptions-item>
      <a-descriptions-item label="新闻内容">
        {{ newsArticlesModalApp.newsArticles.context }}
      </a-descriptions-item>
      <a-descriptions-item label="新闻类别id">
        {{ newsArticlesModalApp.newsArticles.categoryId }}
      </a-descriptions-item>
      <a-descriptions-item label="新闻缩略图">
        {{ newsArticlesModalApp.newsArticles.thumbnail }}
      </a-descriptions-item>
<!--      <a-descriptions-item label="评论数">-->
<!--        {{ newsArticlesModalApp.newsArticles.comment }}-->
<!--      </a-descriptions-item>-->

      <a-descriptions-item label="新闻类型">
        {{ newsArticlesModalApp.newsArticles.type }}
      </a-descriptions-item>


      <a-descriptions-item label="作者姓名">
        {{ newsArticlesModalApp.newsArticles.publisherNameId }}
      </a-descriptions-item>

      <a-descriptions-item label="创建时间">
        {{ newsArticlesModalApp.newsArticles.createDatetime }}
      </a-descriptions-item>
      <a-descriptions-item label="附件" :span="2">
        <m-attachment
          :associate-form-id="newsArticlesListApp.currentId"
          associate-form-name="newsArticles"
          operator-type="view"
        />
      </a-descriptions-item>
    </a-descriptions>
  </a-form>
</template>

<script>
import {defineComponent, reactive, onMounted, inject} from "vue";
import {useRoute} from "vue-router"
import {NewsArticlesService} from "@/views/cms/newsArticles/newsArticles/newsArticlesService";

export default defineComponent({
  setup() {
    const route = useRoute();
    let routeId = route.params.id;
    let newsArticlesListApp = inject('newsArticlesListApp', reactive({}));
    const newsArticlesModalApp = reactive({id: null, newsArticles: {}, display: false});

    onMounted(() => {
      newsArticlesModalApp.newsArticles = {};
      if (routeId) {
        newsArticlesModalApp.id = routeId;
      } else if (newsArticlesListApp.viewModalShowing) {
        newsArticlesModalApp.id = newsArticlesListApp.currentId;
        newsArticlesModalApp.display = true;
      }
      if (newsArticlesModalApp.id) {
        getInfo(newsArticlesModalApp.id);
      }
    })

    const getInfo = (id) => {
      NewsArticlesService.findNewsArticles(id).then((res) => {
        newsArticlesModalApp.newsArticles = res.data;
        newsArticlesModalApp.title = "查看 ";
      })
    }
    return {
      newsArticlesModalApp,
      routeId,
      getInfo,
      newsArticlesListApp,
    }
  }
})
</script>

<style scoped>
</style>

