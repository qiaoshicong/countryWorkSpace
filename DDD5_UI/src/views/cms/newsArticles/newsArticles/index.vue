<template>
  <div class="ele-body">
    <a-card :bordered="false">
      <vxe-grid ref='newsArticlesGrid' v-bind="gridOptions" class="my-grid66">
        <!--搜索表单 -->
        <template #form>
          <a-form
            :model="where"
            :label-col="{md: {span: 6}, sm: {span: 24}}"
            :wrapper-col="{md: {span: 16}, sm: {span: 20}}">
            <a-row>


              <a-col :md="8">
                <a-form-item label="新闻标题:" name="title">
                  <a-input
                    v-model:value="where.title"
                    :maxlength="255"
                    placeholder="请输入新闻标题"
                    allow-clear
                    class="ele-fluid"
                  />
                </a-form-item>
              </a-col>

              <a-col v-if="searchExpand" :lg="6" :md="12" :sm="24"
                     :xs="24">
                <a-form-item label="作者姓名:" name="publisherNameId">
                  <m-entity-select
                    v-model:value="where.publisherNameId"
                    placeholder="请选择作者姓名"
                    module="publisher"
                    entity="Publisher"
                    class="ele-fluid"
                  />
                </a-form-item>
              </a-col>

              <a-col :md="6">
                <a-form-item label="新闻内容:" name="context">
                  <a-input
                    v-model:value="where.context"
                    :maxlength="255"
                    placeholder="请输入新闻内容"
                    allow-clear
                    class="ele-fluid"
                  />
                </a-form-item>
              </a-col>

              <a-col v-if="searchExpand" :lg="6" :md="12" :sm="24"
                     :xs="24">
                <a-form-item label="新闻类别:" name="categoryId">
                  <m-entity-select
                    v-model:value="where.categoryId"
                    placeholder="请输入新闻类别"
                    module="NewsCategory"
                    entity="NewsCategory"
                    class="ele-fluid"
                    />
                </a-form-item>
              </a-col>


              <a-col :lg="6" :md="12" :sm="24" :xs="24">
                <a-form-item class="ele-text-right" :wrapper-col="{span: 24}">
                  <a-space>
                    <a-button type="primary" @click="reload">查询</a-button>
                    <a-button @click="reset">重置</a-button>
                    <a @click="searchExpand = !searchExpand">
                      <span v-if="searchExpand">收起 <up-outlined class="ele-text-small"/></span>
                      <span v-else>展开 <down-outlined class="ele-text-small"/></span>
                    </a>
                  </a-space>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </template>
        <!--自定义按钮-->
        <template #bottom>
          <div>
            <br/>
            <a-alert message="Informational Notes" type="info"
                     description="Detailed description and advices about successful copywriting."
                     show-icon/>
            <br/>
          </div>

        </template>

        <template #operate="{ row }">
          <a-space>
            <a @click="editEvent(row)">修改</a>
            <a-divider type="vertical"/>
            <a class="ele-text-warning" @click="viewEvent(row)">查看</a>
            <a-divider type="vertical"/>
            <a-popconfirm
              title="确定要删除此记录吗？"
              @confirm="deleteEvent(row)">
              <a class="ele-text-danger">删除</a>
            </a-popconfirm>
          </a-space>
        </template>

<!--        渲染图片    -->
          <template #img="{ row }">
            <a-space>
<!--              <a-tag color="orange"><UserOutlined /> {{ row.thumbnail }}</a-tag>-->
              <img :src= "row.thumbnail" style="width: 60px" />
            </a-space>
          </template>


        <template #ycs>
          <a-space>
            <a-button type="primary" @click="addEvent()">
              <template #icon>
                <plus-outlined/>
              </template>
              <span>新增新闻</span>
            </a-button>

          </a-space>
        </template>



      </vxe-grid>

      <!--编辑弹窗-->
      <vxe-modal v-model="newsArticlesListApp.editModalShowing" ref="newsArticlesEditModal"
                 :title="gridOptions.showEditTitle"
                 id="newsArticlesEditModal"
                 width="800" height="400" min-width="460" min-height="320" :lock-view="false" :mask="false"
                 show-footer show-zoom resize remember storage transfer dblclickZoom
                 :destroy-on-close="true"
      >
        <template #default>
          <div id="newsArticlesEditModalContent">
          </div>
        </template>
        <template #footer>
          <div id="newsArticlesEditModalFooter">
          </div>
          <teleport to="#newsArticlesEditModalContent">
            <newsArticles-edit ref="newsArticles-edit"/>
          </teleport>
        </template>
      </vxe-modal>

      <!--查看弹窗-->
      <vxe-modal v-model="newsArticlesListApp.viewModalShowing" :title="gridOptions.showDetailsTitle"
                 id="newsArticlesViewModal"
                 width="600"
                 min-width="460"
                 min-height="320"
                 :destroy-on-close="true"
                 :lock-view="false" :mask="false"
                 footer
                 show-footer show-zoom resize remember storage transfer dblclickZoom>
        <template #default>
          <newsArticles-detail ref="newsArticles-detail"/>
        </template>
      </vxe-modal>
    </a-card>
  </div>
</template>

<script>
import {defineComponent, reactive, ref, toRefs, provide} from 'vue'//, Ref
import {VXETable} from 'vxe-table'//, VxeGridInstance, VxeGridProps
// import XEAjax from 'xe-ajax'
import {NewsArticlesService} from "@/views/cms/newsArticles/newsArticles/newsArticlesService";
import NewsArticlesEdit
  from "@/views/cms/newsArticles/newsArticles/newsArticles-edit";
import NewsArticlesDetail
  from "@/views/cms/newsArticles/newsArticles/newsArticles-detail";
import {
  PlusOutlined,
  DownOutlined,
  UpOutlined,
} from '@ant-design/icons-vue';
// import {useRouter} from "vue-router";

import MEntitySelect from "@/components/MEntity/entitySelect";

export default defineComponent({
  components: {
    NewsArticlesEdit,
    NewsArticlesDetail,
    DownOutlined,
    PlusOutlined,
    UpOutlined,
    MEntitySelect,
  },
  setup() {
    // const router = useRouter();
    const newsArticlesGrid = ref({})// as VxeGridInstance
    // 搜索表单是否展开
    let searchExpand = ref(false)
    let proxyInfo = reactive({})
    const where = ref({})
    const eid = ref({})
    const startIndex = ref({})
    const newsArticlesListApp = reactive({
      editModalShowing: false,
      viewModalShowing: false,
      editModalForEdit: false,
      addModalForEdit: false,
      tableRefresh: function () {
        reload()
      }
    })
    const gridOptions = reactive({
      border: true,
      resizable: true,
      showHeaderOverflow: true,
      showOverflow: true,
      highlightHoverRow: true,
      keepSource: true,
      id: 'newsArticlessGrid',
      // maxHeight: "100%",//表格高度
      height: 800,
      rowId: 'eid',
      selectRow: null,
      showEdit: false,
      showDetails: false,
      showEditTitle: '新建',
      showDetailsTitle: '查看',
      formData: {},//自定义的
      detailData: [],
      startIndex: startIndex,
      // printConfig: {
      //   columns: [
      //     {field: 'name'},
      //     {field: 'email'},
      //     {field: 'nickname'},
      //     {field: 'age'},
      //     {field: 'amount'}
      //   ]
      // },
      /*TODO:排序配置 */
      sortConfig: {
        trigger: 'cell',
        remote: true,
        multiple: true,//多字段排序，默认单排序
        showIcon: true,
        // iconAsc: "fa fa-arrow-up fa-1x",
        // iconDesc: "fa fa-arrow-down fa-1x",
      },
      customConfig: {
        storage: true
      },
      /*TODO:过滤器配置*/
      filterConfig: {
        remote: true
      },
      /*TODO:分页配置*/
      pagerConfig: {
        pageSize: 10,
        pageSizes: [5, 10, 15, 20, 50]
      },
      /*TODO:工具栏配置*/
      toolbarConfig: {
        slots: {buttons: 'ycs'},
        refresh: true,
        // import: true,
        // export: true,
        // print: true,
        zoom: true,
        custom: true
      },
      /*TODO:请求代理配置*/
      proxyConfig: {
        seq: true, // 启用动态序号代理，每一页的序号会根据当前页数变化
        sort: true, // 启用排序代理，当点击排序时会自动触发 query 行为
        filter: true, // 启用筛选代理，当点击筛选时会自动触发 query 行为
        form: false, // 启用表单代理，当点击表单提交按钮时会自动触发 reload 行为
        // 对应响应结果 { result: [], page: { total: 100 } }
        props: {
          result: 'data.datas', // 配置响应结果列表字段
          total: 'data.totalCount', // 配置响应结果总页数字段
          message: 'data.msg'
        },
        /*TODO:请求*/
        ajax: {
          /*TODO:分页条件查询*/
          query: ({page, sorts, filters}) => {  // 接收 Promise

            const conditions = {...where.value}

            // 处理筛选条件
            filters.forEach(({property, values}) => {
              conditions[property] = values.join('$|$')
            })
            const queryParams = {}
            queryParams.currentPage = page.currentPage
            queryParams.pageSize = page.pageSize
            startIndex.value = (queryParams.currentPage - 1) * queryParams.pageSize + 1
            // // 处理筛选条件
            queryParams.filters = conditions;
            // // 处理排序条件
            queryParams.orderBys = sorts;
            //
            const $grid = newsArticlesGrid.value
            $grid.queryParams = queryParams
            return NewsArticlesService.findNewsArticless(queryParams).catch(error => {
              VXETable.modal.message({content: `加载数据出错，原因是：${error.message}`, status: 'error'});

            })
          },
        }
      },

      /*数据表列*/
      columns: [
        /*多选checkbox 单选seq*/
        {type: 'seq', title: '#', width: 40},
        {type: 'checkbox', field: 'eid', title: 'ID', width: 100, sortable: true},
        {
          field: 'title',
          title: '新闻标题',
          sortable: true
        },
        {
          field: 'publisherNameName',
          title: '作者姓名',
          sortable: true
        },
        {
          field: 'context',
          title: '新闻内容',
          sortable: true
        },
        {
          field: 'categoryName',
          title: '新闻类别id',
          sortable: true
        },
        {
          field: 'thumbnail',
          title: '新闻缩略图',
          sortable: true,
          slots: {default: 'img'},
        },
        // {
        //   field: 'createDateTime',
        //   title: '发布时间',
        //   sortable: true
        // },
        // {
        //   field: 'comment',
        //   title: '评论数',
        //   sortable: true
        // },
        // {
        //   field: 'type',
        //   title: '新闻类型',
        //   sortable: true
        // },
        // {
        //   field: 'isTop',
        //   title: '是否置顶',
        //   sortable: true
        // },

        {title: '操作', width: 250, slots: {default: 'operate'}}
      ],

      //
      // editConfig: {
      //   trigger: 'click',
      //   mode: 'row',
      //   showStatus: true
      // }
    })//as VxeGridProps
    provide('newsArticlesListApp', newsArticlesListApp)
    provide('gridOptions', gridOptions)

    /*TODO:新建事件*/
    const addEvent = () => {
      newsArticlesListApp.editModalShowing = true
      newsArticlesListApp.currentId = null;
      newsArticlesListApp.addModalForEdit = true;
      gridOptions.showEditTitle = "新建";
    }
    //
    // /*TODO:新建独立页*/
    // const addEvent2 = () => {
    //   router.push(`/cms/newsArticles/newsArticles/newsArticles-add`);
    // }
    /*TODO:编辑事件*/
    const editEvent = (row) => {
      newsArticlesListApp.editModalShowing = true
      newsArticlesListApp.currentId = row.eid;
      newsArticlesListApp.editModalForEdit = true;
      gridOptions.showEditTitle = "编辑";
    }
    // /*TODO:编辑独立页*/
    // const editEvent2 = (eid) => {
    //   router.push(`/cms/newsArticles/newsArticles/newsArticles-edit/${eid}`);
    // }

    /*TODO:编辑关闭事件*/
    const editEventClose = () => {
      console.log(gridOptions.formData);
      VXETable.modal.message({content: '提交成功', status: 'success'})
    }

    /*TODO:查看事件*/
    const viewEvent = (row) => {
      newsArticlesListApp.currentId = row.eid;
      newsArticlesListApp.viewModalShowing = true
      gridOptions.showDetailsTitle = '查看';
    }

    /*TODO:删除事件*/
    const deleteEvent = (row) => {
      NewsArticlesService.deleteNewsArticles(row.eid).then(() => {
        VXETable.modal.message({content: '操作成功', status: 'success'})
        setTimeout(reload,1000)
      }).catch(error => {
        VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
      })
    }

    // /*TODO:批量删除*/
    // const batchDeleteEvent = () => {
    //   VXETable.modal.message({content: '点了批量删除', status: 'success'})
    // }
    /*TODO:条件查询提交*/
    const reload = () => {
      const $grid = newsArticlesGrid.value
      $grid.commitProxy('query')//TODO:执行代理方法
    }
    /*TODO:重置搜索*/
    const reset = () => {
      where.value = {}
      const $grid = newsArticlesGrid.value
      $grid.commitProxy('query')//TODO:执行代理方法
    }

    //TODO:这里需要返回才能调用
    return {
      eid,
      newsArticlesGrid,
      // editModal,
      gridOptions,
      searchExpand,
      addEvent,
      editEvent,
      viewEvent,
      editEventClose,
      deleteEvent,
      // batchDeleteEvent,
      // editEvent2,
      // addEvent2,
      where,
      reload,
      reset,
      newsArticlesListApp,
      ...toRefs(proxyInfo)
    }
  }
})
</script>

<style scoped>
.my-grid66 .alert-message {
  height: 40px;
  display: flex;
  align-items: center;
  margin: 10px 0;
  border-radius: 4px;
  background-color: #e6f7ff;
  border: 1px solid #91d5ff;
}

.my-grid66 .alert-message-icon {
  width: 30px;
  text-align: center;
  color: #409eff;
  margin-right: 8px;
}

.my-grid66 .alert-message-content {
  flex-grow: 1;
  padding-right: 20px;
}

.my-grid66 .page-left {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  z-index: 10;
}

.my-grid66 .select-count {
  display: inline-block;
  vertical-align: middle;
}

.my-grid66 .my-input {
  width: 100%;
}

.my-grid66 .first-col {
  position: relative;
  height: 20px;
}

.my-grid66 .first-col:before {
  content: "";
  position: absolute;
  left: -15px;
  top: 10px;
  width: 110px;
  height: 1px;
  transform: rotate(28deg);
  background-color: #e8eaec;
}

.my-grid66 .first-col .first-col-top {
  position: absolute;
  right: 4px;
  top: -10px;
}

.my-grid66 .first-col .first-col-bottom {
  position: absolute;
  left: 4px;
  bottom: -10px;
}
</style>
