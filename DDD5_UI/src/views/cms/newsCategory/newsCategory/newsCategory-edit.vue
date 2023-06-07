
<!-- 新闻类别编辑弹窗 -->
<template>
    <div class="ele-body">
        <a-card :title="newsCategoryModalApp.title" :bordered="false">
            <a-form
                    ref="form2"
                    :model="newsCategoryModalApp.newsCategory"
                    :rules="newsCategoryModalApp.rules"
                    :label-col="{md: {span: 7}, sm: {span: 24}}"
                    :wrapper-col="{md: {span: 17}, sm: {span: 24}}">
                <a-row :gutter="16">

                                <a-col :lg="10" :md="12" :sm="24" :xs="24">
                                    <a-form-item label="新闻类别:" name="name">
                                                                                    <a-input
                                                    v-model:value="newsCategoryModalApp.newsCategory.name"
                                                    placeholder="请输入新闻类别"
                                                    :maxlength="255"
                                                    allow-clear
                                            />
                                                                            </a-form-item>
                                </a-col>
                               <a-col :lg="10" :md="12" :sm="24" :xs="24">
                                   <a-form-item label="备注:" name="remark">
                                       <a-input
                                            v-model:value="newsCategoryModalApp.newsCategory.remark"
                                             placeholder="请输入备注"
                                             :maxlength="255"
                                              allow-clear
                                       />
                                 </a-form-item>
                            </a-col>
                            <a-col :lg="10" :md="12" :sm="24" :xs="24">
                                  <a-form-item label="状态:" name="status">
                                      <a-input
                                       v-model:value="newsCategoryModalApp.newsCategory.status"
                                       placeholder="请输入状态"
                                       :maxlength="255"
                                       allow-clear
                                       />
                                  </a-form-item>
                            </a-col>

                    <a-col :md="12" :sm="24" :xs="24">
                        <a-form-item :wrapper-col="{md: {offset: 6}}" style="margin-bottom: -20px">
                                                     <div v-if="newsCategoryModalApp.display">
                                <teleport to="#newsCategoryEditModalFooter">
                                    <div>
                                        <a-space size="middle">
                                            <a-button @click="newsCategoryListApp.editModalShowing=false">取消
                                            </a-button>
                                            <a-button
                                                    type="primary"
                                                    @click="onSubmit(newsCategoryModalApp.newsCategory)"
                                                    :loading="loading">提交
                                            </a-button>

                                        </a-space>
                                    </div>
                                </teleport>
                            </div>
                            <div v-if="!newsCategoryModalApp.display">
                                <a-space size="middle">
                                    <a-button @click="onBack()"></a-button>
                                    <a-button
                                            type="primary"
                                            @click="onSubmit(newsCategoryModalApp.newsCategory)"
                                            :loading="loading">提交
                                    </a-button>

                                </a-space>
                            </div>

                        </a-form-item>
                    </a-col>
                </a-row>
            </a-form>
        </a-card>

    </div>

</template>

<script>
    import {defineComponent, inject, reactive, onMounted} from 'vue'
    import {useRoute, useRouter} from "vue-router"
    import {NewsCategoryService} from "@/views/cms/newsCategory/newsCategory/newsCategoryService";
    import {VXETable} from 'vxe-table'
    import {useStore} from "vuex";
    import regions from 'ele-admin-pro/packages/regions.js';



    export default defineComponent({
        components: {
                                                                                },
        setup() {
            const cityData = regions
            const route = useRoute();
            const router = useRouter();
            const store = useStore();
            let routeId = route.params.id;
            let newsCategoryListApp = inject('newsCategoryListApp', reactive({}));
            const newsCategoryModalApp = reactive({
                id: null,
                    newsCategory: {},
                isEdit: false,
                display: false
            });
                newsCategoryModalApp.rules = {
                                                                                                                                                                                                                                                                                        }

            onMounted(() => {
                //清理数据
                    newsCategoryModalApp.newsCategory = reactive({});
                if (routeId) {//路由
                        newsCategoryModalApp.id = routeId;
                        newsCategoryModalApp.display = false;
                    findNewsCategory(newsCategoryModalApp.id);
                } else if (newsCategoryListApp.editModalForEdit) {//修改弹窗
                        newsCategoryModalApp.id = newsCategoryListApp.currentId;
                        newsCategoryModalApp.display = true;
                } else if (!newsCategoryListApp.editModalForEdit && newsCategoryListApp.addModalForEdit) {//新增弹窗
                        newsCategoryModalApp.display = true;
                } else {
                        newsCategoryModalApp.title = "新增新闻类别";
                }
                if (newsCategoryModalApp.id) findNewsCategory(newsCategoryModalApp.id);
            })

            const findNewsCategory = (id) => {
                    NewsCategoryService.findNewsCategoryForView(id).then((res) => {
                        newsCategoryModalApp.newsCategory = res.data;
                                                                                                                                                                                                                                                                                                                                                                newsCategoryModalApp.title = "编辑学生 " + newsCategoryModalApp.newsCategory.name;
                }).catch(error => {
                    VXETable.modal.message({content: `查找新闻类别出错，原因是：${error.message}`, status: 'error'});
                })
            }
          //提交
            const onSubmit = (data) => {
                // debugger;
                //当为数组时用逗号连接
                                                                                                                                                                                                                                                                                            if (data.eid) {
                    //修改
                        NewsCategoryService.updateNewsCategory(data).then((res) => {
                        console.log(res);
                        VXETable.modal.message({content: '操作成功', status: 'success'});
                        setTimeout(onBack, 3000);
                    }).catch(error => {
                        VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
                    })
                } else {
                    //新增
                        NewsCategoryService.saveNewsCategory(data).then((res) => {
                        console.log(res);
                        VXETable.modal.message({content: '操作成功', status: 'success'});
                        setTimeout(onBack, 3000);
                    }).catch(error => {
                        VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
                    })
                }
            }


                      const onBack = () => {
                router.push(`/cms/newsCategory/newsCategory`);
                store.dispatch('user/tabRemove', route.fullPath);

            }
                                                                                                                                                                                                                            return {
                cityData,
                routeId,
                    newsCategoryListApp,
                    newsCategoryModalApp,
                onSubmit,
                onBack,
                                                                                                                                                                                                                                                                                        }

        },
    })
</script>

<style scoped>

</style>
