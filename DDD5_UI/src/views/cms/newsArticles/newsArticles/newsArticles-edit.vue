<!-- 新闻文章编辑弹窗 -->
<template>
  <div class="ele-body">
    <a-card :title="newsArticlesModalApp.title" :bordered="false">
      <a-form
        ref="form2"
        :model="newsArticlesModalApp.newsArticles"
        :rules="newsArticlesModalApp.rules"
        :label-col="{md: {span: 7}, sm: {span: 24}}"
        :wrapper-col="{md: {span: 24}, sm: {span: 24}}">
        <a-row :gutter="16">

          <a-col :lg="6" :md="12" :sm="24" :xs="24">
            <a-form-item label="标题:" name="title">
              <a-input
                v-model:value="newsArticlesModalApp.newsArticles.title"
                placeholder="请输入新闻标题"
                :maxlength="255"
                allow-clear
                class="ele-fluid"
              />
            </a-form-item>
          </a-col>

          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="新闻概要:" name="summary">
              <a-input
                v-model:value="newsArticlesModalApp.newsArticles.summary"
                placeholder="请输入新闻概要"
                :maxlength="255"
                allow-clear
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="作者姓名:" name="publisherNameId">
              <m-entity-select
                :default-value="newsArticlesModalApp.newsArticles.publisherNameName"
                v-model:value="newsArticlesModalApp.newsArticles.publisherNameId"
                placeholder="请选择作者姓名"
                module="publisher"
                entity="Publisher"
                class="ele-fluid"
              />
            </a-form-item>
          </a-col>

          <a-col :lg="10" :md="12" :sm="24" :xs="24">
            <a-form-item label="新闻内容:" name="context">
<!--              <vxe-textarea  v-model:value="newsArticlesModalApp.newsArticles.context" placeholder="显示字数统计" maxlength="100" show-word-count></vxe-textarea>-->
              <a-textarea v-model:value="newsArticlesModalApp.newsArticles.context" placeholder="显示字数统计" show-count :maxlength="100" />
              <!--              <a-input-->
<!--                v-model:value="newsArticlesModalApp.newsArticles.context"-->
<!--                placeholder="请输入新闻内容"-->
<!--                :maxlength="255"-->
<!--                allow-clear-->
<!--              />-->
            </a-form-item>
          </a-col>

          <a-col :lg="10" :md="12" :sm="24" :xs="24">
            <a-form-item label="新闻缩略图:" name="thumbnail"  prop="imageUrl">
<!--              sdfghjmh-->
<!--              <a-upload-->
<!--                multiple-->
<!--                :remove="handleRemove"-->
<!--                :file-list="fileList"-->
<!--                :before-upload="beforeUpload"-->
<!--              >-->
<!--                <a-button>-->
<!--                  <upload-outlined></upload-outlined>-->
<!--                  选择文件-->
<!--                </a-button>-->
<!--              </a-upload>-->

              <a-form-item label="上传图片:" :label-col="{span:6}" :wrapper-col="{span:18}" style="color: red;">
                <m-attachment
                  :associate-form-id=" newsArticlesListApp.currentId"
                  associate-form-name="newsArticles"
                  v-on:fileList="fileListFromParent"
                  :operator-type="operatorType"
                  ref="attachment"
                />
<!--                <a-p class="theTip">（只能上传一张图片且大小不超过30M）</a-p>-->
              </a-form-item>
<!---->
<!--              <img v-if="newsArticlesModalApp.isPicture" :src="newsArticlesModalApp.phone_tip_url" class="avatar"-->
<!--                   @click="look">-->
<!--              <a-upload-->
<!--                :beforeUpload="beforeFileUpload"-->
<!--                :defaultFileList="defaultFileList"-->
<!--                :headers="headers"-->
<!--                :multiple="true"-->
<!--                :remove="handleFileRemove"-->
<!--                @change="handleChange"-->
<!--                @preview="handleImagePreview"-->
<!--                :action="uploadUrl"-->
<!--                style="width: 60%"-->

<!--              >-->
<!--                <a-button>-->
<!--                  <a-icon type="upload"/>-->
<!--                  上传-->
<!--                </a-button>-->
<!--              </a-upload>-->
<!--              <a-upload-->
<!--                v-model:file-list="fileList"-->
<!--                action="https://www.mocky.io/v2/5cc8019d300000980a055e76"-->
<!--                list-type="picture-card"-->
<!--                @preview="handlePreview"-->
<!--              >-->
<!--                <div v-if="fileList.length < 8">-->
<!--                  <plus-outlined />-->
<!--                  <div style="margin-top: 8px">Upload</div>-->
<!--                </div>-->
<!--              </a-upload>-->
<!--              <img v-if="appraisingModalApp.isPicture" :src="appraisingModalApp.phone_tip_url" class="avatar"-->
<!--                   @click="look">-->
<!--              <a-upload-->
<!--                name="file"-->
<!--                :multiple="false"-->
<!--                :action="uploadUrl"-->
<!--                :headers="headers"-->
<!--                :limit="1"-->
<!--                @change="handleChangeFile"-->
<!--              >-->
<!--                <vxe-button status="primary" icon="vxe-icon&#45;&#45;plus">{{ appraisingModalApp.phone_tip }}</vxe-button>-->
<!--              </a-upload>-->
<!--              <a-upload-->
<!--                class="avatar-uploader"-->
<!--                action="http://localhost:8081/upload/img"-->
<!--                :show-file-list="false"-->
<!--                :on-success="handleAvatarSuccess"-->
<!--                :before-upload="beforeAvatarUpload">-->
<!--                <img v-if="imageUrl!=''" :src="imageUrl" class="avatar">-->
<!--                <i v-else class="el-icon-plus avatar-uploader-icon">上传图片</i>-->
<!--              </a-upload>-->
<!--              <img v-if="newsArticlesModalApp.isPicture" :src="newsArticlesModalApp.phone_tip_url" class="avatar"-->
<!--                   @click="look">-->
<!--              <a-upload-->
<!--                name="file"-->
<!--                :multiple="false"-->
<!--                :action="uploadUrl"-->
<!--                :headers="headers"-->
<!--                :limit="1"-->
<!--                @change="handleChangeFile"-->
<!--              >-->
<!--                <vxe-button status="primary" icon="vxe-icon&#45;&#45;plus">{{ newsArticlesModalApp.phone_tip }}</vxe-button>-->
<!--              <a-input-->
<!--                v-model:value="newsArticlesModalApp.newsArticles.thumbnail"-->
<!--                placeholder="请输入新闻缩略图"-->
<!--                :maxlength="255"-->
<!--                allow-clear-->
<!--                              />-->
<!--              </a-upload>-->
        </a-form-item>
          </a-col>
        </a-row>

<!--          <a-row style="margin-bottom: 20px">-->
<!--            <a-col :span="4">-->
<!--              <div class="example-side">-->
<!--                demo2:图片上传-->
<!--              </div>-->
<!--            </a-col>-->
<!--            <a-col :span="8">-->
<!--              <div class="example-side">-->
<!--                &lt;!&ndash;-&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;demo2-&#45;&#45;start&ndash;&gt;-->
<!--                <a-upload-->
<!--                  :beforeUpload="beforeImageUpload"-->
<!--                  :defaultFileList="defaultImageList"-->
<!--                  :headers="headers"-->
<!--                  :remove="handleImageRemove"-->
<!--                  @change="handleImageChange"-->
<!--                  @preview="handleImagePreview"-->
<!--                  action="/api/storage/upload"-->
<!--                  listType="picture-card"-->
<!--                  style="width: 60%"-->
<!--                >-->
<!--                  <div v-if="defaultImageList.length < 2">-->
<!--                    <a-icon :type="imageLoading ? 'loading' : 'plus'"/>-->
<!--                    <div class="ant-upload-text">上传</div>-->
<!--                  </div>-->
<!--                </a-upload>-->
<!--                <a-modal :footer="null" :visible="previewVisible" @cancel="handleImageCancel">-->
<!--                  <img :src="previewImage" alt="example" style="width: 100%"/>-->
<!--                </a-modal>-->
<!--                &lt;!&ndash;-&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;demo2-&#45;&#45;end&ndash;&gt;-->
<!--              </div>-->
<!--            </a-col>-->
<!--            <a-col :span="12" class="example-side-row">-->
<!--              {{ defaultImageList }}-->
<!--            </a-col>-->
<!--          </a-row>-->

      <a-row>

          <a-col :md="12" :sm="24" :xs="24">
            <a-form-item :wrapper-col="{md: {offset: 6}}" style="margin-bottom: -20px">
              <!--class="ele-pull-right"-->
              <div v-if="newsArticlesModalApp.display">
                <teleport to="#newsArticlesEditModalFooter">
                  <div>
                    <a-space size="middle">
                      <a-button @click="newsArticlesListApp.editModalShowing=false">取消
                      </a-button>
                      <a-button
                        type="primary"
                        @click="onSubmit(newsArticlesModalApp.newsArticles)"
                        :loading="loading">提交
                      </a-button>
                      <!--<a-button v-if="!eid"
                                type="dashed"
                                @click="continueSubmit"
                                :loading="loading">继续提交
                      </a-button>-->
                    </a-space>
                  </div>
                </teleport>
              </div>
              <div v-if="!newsArticlesModalApp.display">
                <a-space size="middle">
                  <a-button @click="onBack()">返回列表</a-button>
                  <a-button
                    type="primary"
                    @click="onSubmit(newsArticlesModalApp.newsArticles)"
                    :loading="loading">提交
                  </a-button>
                  <!--<a-button v-if="!eid"
                            type="dashed"
                            @click="continueSubmit"
                            :loading="loading">继续提交
                  </a-button>-->
                </a-space>
              </div>

            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-card>


</div>


</template>

<script >
import {defineComponent, inject, reactive, onMounted,ref} from 'vue'
import {useRoute, useRouter} from "vue-router"
import {NewsArticlesService} from "@/views/cms/newsArticles/newsArticles/newsArticlesService";
import uploadAttachmentService from "@/components/MFileUpload/attachmentService.js";
import {VXETable} from 'vxe-table'
import {useStore} from "vuex";
import regions from 'ele-admin-pro/packages/regions.js';

// import {toRaw} from '@vue/reactivity'
import MEntitySelect from "@/components/MEntity/entitySelect";
// import {message} from "ant-design-vue";

export default defineComponent({
  components: {
    MEntitySelect,
  },
  setup() {
    // 省市区数据
    const cityData = regions
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    let routeId = route.params.id;
    let newsArticlesListApp = inject('newsArticlesListApp', reactive({}));

    const newsArticlesModalApp = reactive({
      id: null,
      newsArticles: {},
      isEdit: false,
      display: false,
      isPicture: false,
      phone_tip_url: '',

    });
    newsArticlesModalApp.rules = {
      title: [
        {
          required: true,
          type: 'string',
          message: '请输入新闻标题',
          trigger: 'blur'
        }
      ],
      summary: [
        {
          required: false,
          type: 'string',
          message: '请输入新闻概要',
          trigger: 'blur'
        }
      ],
      context: [
        {
          required: false,
          type: 'string',
          message: '请输入新闻内容',
          trigger: 'blur'
        }
      ],
      categoryId: [
        {
          required: false,
          type: 'number',
          message: '请输入新闻类别id',
          trigger: 'blur'
        }
      ],
      thumbnail: [
        {
          required: false,
          type: 'string',
          message: '请输入新闻缩略图',
          trigger: 'blur'
        }
      ],
      comment: [
        {
          required: false,
          type: 'number',
          message: '请输入评论数',
          trigger: 'blur'
        }
      ],
      view: [
        {
          required: false,
          type: 'number',
          message: '请输入浏览数',
          trigger: 'blur'
        }
      ],
      thumb: [
        {
          required: false,
          type: 'number',
          message: '请输入点赞数',
          trigger: 'blur'
        }
      ],
      collect: [
        {
          required: false,
          type: 'number',
          message: '请输入收藏数',
          trigger: 'blur'
        }
      ],
      type: [
        {
          required: false,
          type: 'number',
          message: '请输入新闻类型',
          trigger: 'blur'
        }
      ],
      isTop: [
        {
          required: false,
          type: 'number',
          message: '请输入是否置顶',
          trigger: 'blur'
        }
      ],
      publisherNameName: [
        {
          required: false,
          type: 'string',
          message: '请输入作者姓名',
          trigger: 'blur'
        }
      ],
      publisherNameId: [
        {
          required: false,
          type: 'number',
          message: '请输入作者姓名',
          trigger: 'blur'
        }
      ],
    }

    onMounted(() => {
      //清理数据
      newsArticlesModalApp.newsArticles = reactive({});
      if (routeId) {//路由
        newsArticlesModalApp.id = routeId;
        newsArticlesModalApp.display = false;
        findNewsArticles(newsArticlesModalApp.id);
      } else if (newsArticlesListApp.editModalForEdit) {//修改弹窗
        newsArticlesModalApp.id = newsArticlesListApp.currentId;
        newsArticlesModalApp.display = true;
      } else if (!newsArticlesListApp.editModalForEdit && newsArticlesListApp.addModalForEdit) {//新增弹窗
        newsArticlesModalApp.display = true;
      } else {
        newsArticlesModalApp.title = "新增学生";
      }
      if (newsArticlesModalApp.id) findNewsArticles(newsArticlesModalApp.id);
    })

    const findNewsArticles = (id) => {
      NewsArticlesService.findNewsArticlesForView(id).then((res) => {
        newsArticlesModalApp.newsArticles = res.data;
        //TODO:为编辑准备.
        newsArticlesModalApp.title = "编辑学生 " + newsArticlesModalApp.newsArticles.name;
      }).catch(error => {
        VXETable.modal.message({content: `查找学生出错，原因是：${error.message}`, status: 'error'});
      })
    }


    /*TODO:提交 新增&编辑*/
    const onSubmit = (data) => {
      // debugger;
      //当为数组时用逗号连接

      if (data.eid) {
        //保存文件
        saveFile(data.eid)
        //修改
        NewsArticlesService.updateNewsArticles(data).then((res) => {
          console.log(res);
          VXETable.modal.message({content: '操作成功', status: 'success'});
          setTimeout(onBack, 1000);
        }).catch(error => {
          VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
        })
      } else {
        //新增
        NewsArticlesService.saveNewsArticles(data).then((res) => {
          console.log(res);
          saveFile(res.data.eid)
          VXETable.modal.message({content: '操作成功', status: 'success'});
          setTimeout(onBack, 1000);
        }).catch(error => {
          VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
        })

      }

    }

    /*TODO:继续提交*/
    const continueSubmit = () => {
      VXETable.modal.message({content: 'error 提示', status: 'error'});
    }

    /*TODO:返回列表*/
    const onBack = () => {
      router.push(`/cms/newsArticles/newsArticles`);
      newsArticlesListApp.editModalShowing = false;
      newsArticlesListApp.tableRefresh();
      store.dispatch('user/tabRemove', route.fullPath);

    }


    //文件上传--------------
    let operatorType = ref(); //操作类型（查看或者编辑）
    // const attachment = ref(null);
    let fileList = reactive([]);
    //获取子组件传过来的fileList
    let fileListFromParent = (files) => {
      // childValue就是子组件传过来的值
      fileList.values = files;
    }

    //点击上传
    const saveFile = (entityEid) => {
      console.log("图片上传")
      if (fileList.values.length !== 0) {
        let formData = new FormData();
        //表单名--对应实体表名
        let formDto = {associateFormId: entityEid, associateFormName: "newsArticles"};
        var fileUploadDto = JSON.stringify(formDto)
        formData.append("fileUploadDto", new Blob([fileUploadDto], {type: "application/json"}));
        fileList.values.forEach(file => {
          formData.append('files', file)
        });
        uploadAttachmentService.saveFile(formData).then(() => {
          fileList = [];
          VXETable.modal.message({content: '图片上传成功', status: 'success'});
          //更改上传组件的状态为编辑状态
          operatorType = "edit";
        }).catch((error) => {
          VXETable.modal.message({content: '图片上传失败' + `系统错误，原因是：${error.message}`, status: 'error'});
        });

      }

    }


    return {
      cityData,
      routeId,
      newsArticlesListApp,
      newsArticlesModalApp,
      onSubmit,
      continueSubmit,
      onBack,
      // handleAvatarSuccess,
      // beforeAvatarUpload,
      // handleChange,
      // headers: {
      //   authorization: 'authorization-text',
      // },

      // look,
      // beforeUpload,
      // handleRemove,
      fileList,
      saveFile,
      operatorType,
      fileListFromParent,

    }

  },
})
</script>

<style scoped>
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
