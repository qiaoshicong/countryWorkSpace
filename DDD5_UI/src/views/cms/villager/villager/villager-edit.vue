<!-- 村民信息编辑弹窗 -->
<template>
  <div class="ele-body">
    <a-card :title="villagerModalApp.title" :bordered="false">
      <a-form
        ref="form2"
        :model="villagerModalApp.villager"
        :rules="villagerModalApp.rules"
        :label-col="{md: {span: 7}, sm: {span: 24}}"
        :wrapper-col="{md: {span: 17}, sm: {span: 24}}">
        <a-row :gutter="16">

          <a-col :lg="6" :md="12" :sm="24" :xs="24">
            <a-form-item label="姓名:" name="name">
              <a-input
                v-model:value="villagerModalApp.villager.name"
                placeholder="请输入姓名"
                :maxlength="255"
                allow-clear
              />
            </a-form-item>
          </a-col>

          <a-col :lg="6" :md="12" :sm="24" :xs="24">
            <a-form-item label="年龄:" name="age" :rules="{ required: false }">
              <a-input-number
                v-model:value="villagerModalApp.villager.age"
                placeholder="请输入年龄"
                :min="0"
                :step="1"
                class="ele-fluid"
              ></a-input-number>
            </a-form-item>
          </a-col>

          <a-col :lg="6" :md="12" :sm="24" :xs="24">
            <a-form-item label="性别:" name="gender" :rules="{ required: false }">
              <a-radio-group v-model:value="villagerModalApp.villager.gender">
                <a-radio-button value="男">男</a-radio-button>
                <a-radio-button value="女">女</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :lg="6" :md="12" :sm="24" :xs="24">
            <a-form-item label="手机号:" name="phoneNumber" :rules="{ required: false, pattern: /^1[3-9]\d{9}$/,message:'手机格式不正确' }">
              <a-input
                v-model:value="villagerModalApp.villager.phoneNumber"
                placeholder="请输入手机号"
                :maxlength="255"
                allow-clear
              />
            </a-form-item>
          </a-col>

          <a-col :lg="6" :md="12" :sm="24" :xs="24">
            <a-form-item label="地址:" name="address">
              <a-input
                v-model:value="villagerModalApp.villager.address"
                placeholder="请输入地址"
                :maxlength="255"
                allow-clear
              />
            </a-form-item>
          </a-col>

          <a-col :md="12" :sm="24" :xs="24">
            <a-form-item :wrapper-col="{md: {offset: 6}}" style="margin-bottom: -20px">
              <div v-if="villagerModalApp.display">
                <teleport to="#villagerEditModalFooter">
                  <div>
                    <a-space size="middle">
                      <a-button @click="villagerListApp.editModalShowing=false">取消
                      </a-button>
                      <a-button
                        type="primary"
                        @click="onSubmit(villagerModalApp.villager)"
                        :loading="loading">提交
                      </a-button>
                    </a-space>
                  </div>
                </teleport>
              </div>
              <div v-if="!villagerModalApp.display">
                <a-space size="middle">
                  <a-button @click="onBack()">返回列表</a-button>
                  <a-button
                    type="primary"
                    @click="onSubmit(villagerModalApp.villager)"
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
import {VillagerService} from "@/views/cms/villager/villager/villagerService";
import {VXETable} from 'vxe-table'
import {useStore} from "vuex";
import regions from 'ele-admin-pro/packages/regions.js';


export default defineComponent({
  components: {},
  setup() {
    // 省市区数据
    const cityData = regions
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    let routeId = route.params.id;
    let villagerListApp = inject('villagerListApp', reactive({}));
    const villagerModalApp = reactive({
      id: null,
      villager: {},
      isEdit: false,
      display: false
    });
    villagerModalApp.rules = {
      age: [
        {
          required: true,
          type: 'number',
          message: '请输入年龄',
          trigger: 'blur'
        }
      ],
      gender: [
        {
          required: false,
          type: 'string',
          message: '请输入性别',
          trigger: 'blur'
        }
      ],
      phoneNumber: [
        {
          required: false,
          type: 'string',
          message: '请输入手机号',
          trigger: 'blur'
        }
      ],
      address: [
        {
          required: false,
          type: 'string',
          message: '请输入地址',
          trigger: 'blur'
        }
      ],
    }

    onMounted(() => {
      //清理数据
      villagerModalApp.villager = reactive({});
      if (routeId) {//路由
        villagerModalApp.id = routeId;
        villagerModalApp.display = false;
        findVillager(villagerModalApp.id);
      } else if (villagerListApp.editModalForEdit) {//修改弹窗
        villagerModalApp.id = villagerListApp.currentId;
        villagerModalApp.display = true;
      } else if (!villagerListApp.editModalForEdit && villagerListApp.addModalForEdit) {//新增弹窗
        villagerModalApp.display = true;
      } else {
        villagerModalApp.title = "新增学生";
      }
      if (villagerModalApp.id) findVillager(villagerModalApp.id);
    })

    const findVillager = (id) => {
      VillagerService.findVillagerForView(id).then((res) => {
        villagerModalApp.villager = res.data;
        villagerModalApp.title = "编辑学生 " + villagerModalApp.villager.name;
      }).catch(error => {
        VXETable.modal.message({content: `查找学生出错，原因是：${error.message}`, status: 'error'});
      })
    }
    const onSubmit = (data) => {
      //当为数组时用逗号连接
      if (data.eid) {
        //修改
        VillagerService.updateVillager(data).then((res) => {
          console.log(res);
          VXETable.modal.message({content: '操作成功', status: 'success'});
          setTimeout(onBack, 3000);
        }).catch(error => {
          VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
        })
      } else {
        //新增
        VillagerService.saveVillager(data).then((res) => {
          console.log(res);
          VXETable.modal.message({content: '操作成功', status: 'success'});
          setTimeout(onBack, 3000);
        }).catch(error => {
          VXETable.modal.message({content: `系统错误，原因是：${error.message}`, status: 'error'});
        })
      }
    }
    const continueSubmit = () => {
      VXETable.modal.message({content: 'error 提示', status: 'error'});
    }

    /*TODO:返回列表*/
    const onBack = () => {
      router.push(`/cms/villager/villager`);
      villagerListApp.editModalShowing=false;
      villagerListApp.tableRefresh();
      store.dispatch('user/tabRemove', route.fullPath);

    }
    return {
      cityData,
      routeId,
      villagerListApp,
      villagerModalApp,
      onSubmit,
      continueSubmit,
      onBack,
    }

  },
})
</script>

<style scoped>

</style>
