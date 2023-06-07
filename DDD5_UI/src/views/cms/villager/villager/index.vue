<template>
  <div class="ele-body">
    <a-card :bordered="false">
      <vxe-grid ref='villagerGrid' v-bind="gridOptions" class="my-grid66">
        <!--搜索表单 -->
        <template #form>
          <a-form
            :model="where"
            :label-col="{md: {span: 4}, sm: {span: 24}}"
            :wrapper-col="{md: {span: 20}, sm: {span: 24}}">
            <a-row>

              <a-col :lg="6" :md="12" :sm="24"
                     :xs="240">
                <a-form-item label="姓名:" name="name">
                  <a-input
                    v-model:value="where.name"
                    :maxlength="255"
                    placeholder="请输入姓名"
                    allow-clear
                    class="ele-fluid"
                  />
                </a-form-item>
              </a-col>

              <a-col :lg="6" :md="12" :sm="24"
                     :xs="24">
                <a-form-item label="年龄:" name="age">
                  <a-input-number
                    v-model:value="where.age"
                    placeholder="请输入年龄"
                    :min="0"
                    :step="1"
                    class="ele-fluid">
                  </a-input-number>
                </a-form-item>
              </a-col>

              <a-col :lg="6" :md="12" :sm="24"
                     :xs="24">
                <a-form-item label="性别:" name="gender">
                  <a-input
                    v-model:value="where.gender"
                    :maxlength="255"
                    placeholder="请输入性别"
                    allow-clear
                    class="ele-fluid"
                  />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :lg="6" :md="12" :sm="24"
                     :xs="24">
                <a-form-item label="手机号:" name="phoneNumber">
                  <a-input
                    v-model:value="where.phoneNumber"
                    :maxlength="255"
                    placeholder="请输入手机号"
                    allow-clear
                    class="ele-fluid"
                  />
                </a-form-item>
              </a-col>
              <a-col :lg="6" :md="12" :sm="24"
                     :xs="24">
                <a-form-item label="地址:" name="address">
                  <a-input
                    v-model:value="where.address"
                    :maxlength="255"
                    placeholder="请输入地址"
                    allow-clear
                    class="ele-fluid"
                  />
                </a-form-item>
              </a-col>

              <a-col :lg="6" :md="12" :sm="24" :xs="24">
                <a-form-item class="ele-text-right" :wrapper-col="{span: 24}">
                  <a-space>
                    <a-button type="primary" @click="reload">查询</a-button>
                    <a-button @click="reset">重置</a-button>
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
            <a-alert message="易村行" type="info"
                     description="与时俱进，共创新价值，共建新农村."
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

        <template #ycs>
          <a-space>
            <a-button type="primary" @click="addEvent()">
              <template #icon>
                <plus-outlined/>
              </template>
              <span>新建弹窗</span>
            </a-button>
          </a-space>
        </template>
      </vxe-grid>

      <!--编辑弹窗-->
      <vxe-modal v-model="villagerListApp.editModalShowing" ref="villagerEditModal"
                 :title="gridOptions.showEditTitle"
                 id="villagerEditModal"
                 width="880" height="400" min-width="460" min-height="320" :lock-view="false" :mask="false"
                 show-footer show-zoom resize remember storage transfer dblclickZoom
                 :destroy-on-close="true"
      >
        <template #default>
          <div id="villagerEditModalContent">
          </div>
        </template>
        <template #footer>
          <div id="villagerEditModalFooter">
          </div>
          <teleport to="#villagerEditModalContent">
            <villager-edit ref="villager-edit"/>
          </teleport>
        </template>
      </vxe-modal>

      <!--查看弹窗-->
      <vxe-modal v-model="villagerListApp.viewModalShowing" :title="gridOptions.showDetailsTitle"
                 id="villagerViewModal"
                 width="600"
                 min-width="460"
                 min-height="320"
                 :destroy-on-close="true"
                 :lock-view="false" :mask="false"
                 footer
                 show-footer show-zoom resize remember storage transfer dblclickZoom>
        <template #default>
          <villager-detail ref="villager-detail"/>
        </template>
      </vxe-modal>
    </a-card>
  </div>
</template>

<script>
import {defineComponent, reactive, ref, toRefs, provide} from 'vue'//, Ref
import {VXETable} from 'vxe-table'//, VxeGridInstance, VxeGridProps
import {VillagerService} from "@/views/cms/villager/villager/villagerService";
import VillagerEdit
  from "@/views/cms/villager/villager/villager-edit";
import VillagerDetail
  from "@/views/cms/villager/villager/villager-detail";
import {
  PlusOutlined,
} from '@ant-design/icons-vue';


export default defineComponent({
  components: {
    VillagerEdit,
    VillagerDetail,
    PlusOutlined,
  },
  setup() {
    const villagerGrid = ref({})// as VxeGridInstance
    // 搜索表单是否展开
    let searchExpand = ref(false)
    let proxyInfo = reactive({})
    const where = ref({})
    const eid = ref({})
    const startIndex = ref({})
    const villagerListApp = reactive({
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
      id: 'villagersGrid',
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
      customConfig: {
        storage: true
      },
      /*过滤器配置*/
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
            const $grid = villagerGrid.value
            $grid.queryParams = queryParams
            return VillagerService.findVillagers(queryParams).catch(error => {
              VXETable.modal.message({content: `加载数据出错，原因是：${error.message}`, status: 'error'});

            })
          },
        }
      },

      /*TODO:数据表列*/
      columns: [
        /*多选checkbox 单选seq*/
        {type: 'seq', title: '#', width: 40},
        {type: 'checkbox', field: 'eid', title: 'ID', width: 100, sortable: true},
        {
          field: 'name',
          title: '姓名',
          sortable: true
        },
        {
          field: 'age',
          title: '年龄',
          sortable: true
        },
        {
          field: 'gender',
          title: '性别',
          sortable: true
        },
        {
          field: 'phoneNumber',
          title: '手机号',
          sortable: true
        },
        {
          field: 'address',
          title: '地址',
          sortable: true
        },
        {title: '操作', width: 250, slots: {default: 'operate'}}
      ],
      /*TODO:复选框配置*/
      checkboxConfig: {
        labelField: 'eid',
        reserve: false,
        highlight: false,
        range: true
      },
      editConfig: {
        trigger: 'click',
        mode: 'row',
        showStatus: true
      }
    })
    provide('villagerListApp', villagerListApp)
    provide('gridOptions', gridOptions)

    /*新建事件*/
    const addEvent = () => {
      villagerListApp.editModalShowing = true
      villagerListApp.currentId = null;
      villagerListApp.addModalForEdit = true;
      gridOptions.showEditTitle = "新建";
    }

    /*编辑事件*/
    const editEvent = (row) => {
      villagerListApp.editModalShowing = true
      villagerListApp.currentId = row.eid;
      villagerListApp.editModalForEdit = true;
      gridOptions.showEditTitle = "编辑";
    }

    /*编辑关闭事件*/
    const editEventClose = () => {
      console.log(gridOptions.formData);
      VXETable.modal.message({content: '提交成功', status: 'success'})
    }

    /*查看事件*/
    const viewEvent = (row) => {
      villagerListApp.currentId = row.eid;
      villagerListApp.viewModalShowing = true
      gridOptions.showDetailsTitle = '查看';
    }

    /*删除事件*/
    const deleteEvent = (row) => {
      VillagerService.deleteVillager(row.eid).then(() => {
        VXETable.modal.message({content: '操作成功', status: 'success'})
      }).catch(error => {
        VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
      })
    }

    /*条件查询提交*/
    const reload = () => {
      const $grid = villagerGrid.value
      $grid.commitProxy('query')//TODO:执行代理方法
    }
    /*重置搜索*/
    const reset = () => {
      where.value = {}
      const $grid = villagerGrid.value
      $grid.commitProxy('query')//TODO:执行代理方法
    }

    //这里需要返回才能调用
    return {
      eid,
      villagerGrid,
      // editModal,
      gridOptions,
      searchExpand,
      addEvent,
      editEvent,
      viewEvent,
      editEventClose,
      deleteEvent,
      where,
      reload,
      reset,
      villagerListApp,
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
