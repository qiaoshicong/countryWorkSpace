<template>
    <div class="ele-body">
        <a-card :bordered="false">
            <vxe-grid ref='newsCommentGrid' v-bind="gridOptions" class="my-grid66">
                <!--搜索表单 -->
                <template #form>
                    <a-form
                            :model="where"
                            :label-col="{md: {span: 4}, sm: {span: 24}}"
                            :wrapper-col="{md: {span: 20}, sm: {span: 24}}">
                        <a-row>


                                        <a-col  :lg="6" :md="12" :sm="24"
                                                             :xs="24">
                                            <a-form-item label="内容:" name="content">
                                                                                                    <a-input
                                                            v-model:value="where.content"
                                                            :maxlength="255"
                                                            placeholder="请输入内容"
                                                            allow-clear
                                                            class="ele-fluid"
                                                    />
                                                                                            </a-form-item>
                                        </a-col>

                                        <a-col  :lg="12" :md="12" :sm="24"
                                                             :xs="24">
                                            <a-form-item label="评论的文章id:" name="articleId">
                                                                                                    <a-input-number
                                                            v-model:value="where.articleId"
                                                            placeholder="请输入评论的文章id"
                                                            :min="0"
                                                            :step="1"
                                                            class="ele-fluid">
                                                    </a-input-number>
                                                                                            </a-form-item>
                                        </a-col>

                                        <a-col  :lg="12" :md="12" :sm="24"
                                                             :xs="24">
                                            <a-form-item label="创建者的id:" name="createIdId">
                                                                                                    <m-entity-select
                                                            v-model:value="where.createIdId"
                                                            placeholder="请选择创建者的id"
                                                            module="newsComment"
                                                            entity="User"
                                                            class="ele-fluid"
                                                    />
                                                                                            </a-form-item>
                                        </a-col>

                                        <a-col  v-if="searchExpand"  :lg="6" :md="12" :sm="24"
                                                             :xs="24">
                                            <a-form-item label="父评论id:" name="parentId">
                                                                                                    <a-input-number
                                                            v-model:value="where.parentId"
                                                            placeholder="请输入父评论id"
                                                            :min="0"
                                                            :step="1"
                                                            class="ele-fluid">
                                                    </a-input-number>
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
            <vxe-modal v-model="newsCommentListApp.editModalShowing" ref="newsCommentEditModal"
                       :title="gridOptions.showEditTitle"
                       id="newsCommentEditModal"
                       width="800" height="400" min-width="460" min-height="320" :lock-view="false" :mask="false"
                       show-footer show-zoom resize remember storage transfer dblclickZoom
                       :destroy-on-close="true"
            >
                <template #default>
                    <div id="newsCommentEditModalContent">
                    </div>
                </template>
                <template #footer>
                    <div id="newsCommentEditModalFooter">
                    </div>
                    <teleport to="#newsCommentEditModalContent">
                        <newsComment-edit ref="newsComment-edit"/>
                    </teleport>
                </template>
            </vxe-modal>

            <!--查看弹窗-->
            <vxe-modal v-model="newsCommentListApp.viewModalShowing" :title="gridOptions.showDetailsTitle"
                       id="newsCommentViewModal"
                       width="600"
                       min-width="460"
                       min-height="320"
                       :destroy-on-close="true"
                       :lock-view="false" :mask="false"
                       footer
                       show-footer show-zoom resize remember storage transfer dblclickZoom>
                <template #default>
                    <newsComment-detail ref="newsComment-detail"/>
                </template>
            </vxe-modal>
        </a-card>
    </div>
</template>

<script>
    import {defineComponent, reactive, ref, toRefs, provide} from 'vue'//, Ref
    import {VXETable} from 'vxe-table'//, VxeGridInstance, VxeGridProps
    import {NewsCommentService} from "@/views/cms/newsComment/newsComment/newsCommentService";
    import NewsCommentEdit
        from "@/views/cms/newsComment/newsComment/newsComment-edit";
    import NewsCommentDetail
        from "@/views/cms/newsComment/newsComment/newsComment-detail";
    import {
        PlusOutlined,


    } from '@ant-design/icons-vue';


                                                        import MEntitySelect from "@/components/MEntity/entitySelect";

    export default defineComponent({
        components: {
                NewsCommentEdit,
                NewsCommentDetail,

            PlusOutlined,
                                                                                        MEntitySelect,
                    },
        setup() {
             const newsCommentGrid = ref({})// as VxeGridInstance
            // 搜索表单是否展开
            let searchExpand = ref(false)
            let proxyInfo = reactive({})
            const where = ref({})
            const eid = ref({})
            const startIndex = ref({})
            const newsCommentListApp = reactive({
                editModalShowing: false,
                viewModalShowing: false,
                editModalForEdit: false,
                addModalForEdit: false,
              tableRefresh:function (){
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
                id: 'newsCommentsGrid',
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
                printConfig: {
                    columns: [
                        {field: 'name'},
                        {field: 'email'},
                        {field: 'nickname'},
                        {field: 'age'},
                        {field: 'amount'}
                    ]
                },

                customConfig: {
                    storage: true
                },
                /*过滤器配置*/
                filterConfig: {
                    remote: true
                },
                /*分页配置*/
                pagerConfig: {
                    pageSize: 10,
                    pageSizes: [5, 10, 15, 20, 50]
                },
                /*工具栏配置*/
                toolbarConfig: {
                    slots: {buttons: 'ycs'},

                },
                /*请求代理配置*/
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
                    /*请求*/
                    ajax: {
                        /*页条件查询*/
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
                            const $grid = newsCommentGrid.value
                                $grid.queryParams = queryParams
                            return NewsCommentService.findNewsComments(queryParams).catch(error => {
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
                                field: 'content',
                                title: '内容',
                                sortable: true
                            },
                                                                                                {
                                field: 'articleId',
                                title: '评论的文章id',
                                sortable: true
                            },
                                                                                                {
                                field: 'createIdId',
                                title: '创建者的id',
                                sortable: true
                            },
                                                                                                {
                                field: 'parentId',
                                title: '父评论id',
                                sortable: true
                            },
                                                                                                                                                                                                                                                {title: '操作', width: 250, slots: {default: 'operate'}}
                ],


                /*复选框配置*/
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
            })//as VxeGridProps
            provide('newsCommentListApp', newsCommentListApp)
            provide('gridOptions', gridOptions)

            /*新建事件*/
            const addEvent = () => {
                    newsCommentListApp.editModalShowing = true
                    newsCommentListApp.currentId = null;
                    newsCommentListApp.addModalForEdit = true;
                gridOptions.showEditTitle = "新建";
            }


            /*编辑事件*/
            const editEvent = (row) => {
                    newsCommentListApp.editModalShowing = true
                    newsCommentListApp.currentId = row.eid;
                    newsCommentListApp.editModalForEdit = true;
                gridOptions.showEditTitle = "编辑";
            }

            /*编辑关闭事件*/
            const editEventClose = () => {
                console.log(gridOptions.formData);
                VXETable.modal.message({content: '提交成功', status: 'success'})
            }

            /*查看事件*/
            const viewEvent = (row) => {
                    newsCommentListApp.currentId = row.eid;
                    newsCommentListApp.viewModalShowing = true
                gridOptions.showDetailsTitle = '查看';
            }

            /*删除事件*/
            const deleteEvent = (row) => {
                    NewsCommentService.deleteNewsComment(row.eid).then(() => {
                    VXETable.modal.message({content: '操作成功', status: 'success'})
                }).catch(error => {
                    VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
                })
            }
            /*条件查询提交*/
            const reload = () => {
                const $grid = newsCommentGrid.value
                    $grid.commitProxy('query')
            }
            /*重置搜索*/
            const reset = () => {
                where.value = {}
                const $grid = newsCommentGrid.value
                    $grid.commitProxy('query')//
            }

            //这里需要返回才能调用
            return {
                eid,
                newsCommentGrid,
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
                    newsCommentListApp,
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
