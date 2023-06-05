
<template>
  <div class="bigbody">
    <div class="ele-body">
      <a-card :bordered="false" class="card">
        <div class="content">
          <a-form
            ref="form"
            :model="form"
            :rules="rules"
            :label-col="{md: {span: 6}, sm: {span: 24}}"
            :wrapper-col="{md: {span: 18}, sm: {span: 24}}"
            class="form">

            <a-row :gutter="1">
              <a-col :lg="12" :md="12" :sm="24" :xs="24">
                <a-form-item label="用户名:" name="username">
                  <a-input
                    placeholder="请输入5-20位用户名，必须包括数字和字母"
                    v-model:value="form.username"
                    :maxlength="250"
                  />
                </a-form-item>
              </a-col>
              <a-col :lg="12" :md="12" :sm="24" :xs="24">
                <a-form-item label="姓名:" name="name">
                  <a-input
                    v-model:value="form.name"
                    placeholder="请输入姓名"
                    :maxlength="255"
                  />
                </a-form-item>
              </a-col>
              <a-col :lg="12" :md="12" :sm="24" :xs="24">
                <a-form-item label="年龄:" name="age" :rules="{ required: false }">
                  <a-input-number
                    v-model:value="form.age"
                    placeholder="请输入年龄"
                    :min="0"
                    :step="1"
                  ></a-input-number>
                </a-form-item>
              </a-col>
              <a-col :lg="6" :md="12" :sm="24" :xs="24">
                <a-form-item label="性别:" name="gender" :rules="{ required: false }">
                  <a-radio-group v-model:value="form.gender">
                    <a-radio-button value="男">男</a-radio-button>
                    <a-radio-button value="女">女</a-radio-button>
                  </a-radio-group>
                </a-form-item>
              </a-col>
              <a-col :lg="12" :md="12" :sm="24" :xs="24">
                <a-form-item label="电话:" name="phoneNumber">
                  <a-input
                    v-model:value="form.phoneNumber"
                    placeholder="请输入联系电话"
                    :maxlength="255"
                  />
                </a-form-item>
              </a-col>
              <a-col :lg="12" :md="12" :sm="24" :xs="24">
                <a-form-item label="地址:" name="address">
                  <a-input
                    v-model:value="form.address"
                    placeholder="请输入地址"
                    :maxlength="255"
                  />
                </a-form-item>
              </a-col>
              <a-col :lg="12" :md="12" :sm="24" :xs="24">
                <a-form-item label="注册密码:" name="password">
                  <a-input-password
                    v-model:value="form.password"
                    placeholder="请输入5-20位密码，必须包括数字和字母"
                    :maxlength="255"
                  />
                </a-form-item>
              </a-col>

              <a-col :lg="12" :md="12" :sm="24" :xs="24">
                <a-form-item label="确认密码:" name="password2">
                  <a-input-password
                    v-model:value="form.password2"
                    placeholder="请再次输入注册密码"
                    :maxlength="255"
                  />
                </a-form-item>
              </a-col>
                            <a-col :md="24" :sm="24" :xs="24">
                <a-form-item :wrapper-col="{md: {offset: 6}}" style="margin-bottom: -20px">
                  <div class="buttons">
                    <a-space size="middle" class="btn">
                      <a-button @click="toReset" type="primary" size="large" class="clearbt">
                        重置信息
                      </a-button>

                      <a-button
                        type="primary"
                        @click="onSubmit()"
                        :loading="loading"
                        size="large"
                        class="submitbt">提交信息
                      </a-button>
                    </a-space>
                  </div>

                </a-form-item>
              </a-col>

            </a-row>

          </a-form>
        </div>
      </a-card>
    </div>
    <div class="login-copyright">cqnu 软件工程 2021级 易村行</div>
  </div>

</template>

<script>

import {VXETable} from "vxe-table";
import {VillagerService} from "@/views/cms/villager/villager/villagerService";
import {UserService} from "@/views/login/UserService";

export default {
  data() {
    //用户名输入规则
    let usernameRule = (rule, value) => {
      if (!value) {
        return Promise.reject('请输入用户名');
      }
      let reg = /^[A-Za-z0-9]+$/;
      if ( !(reg.test(value) ) ) {
        return Promise.reject('用户名格式有误');
      }
      return Promise.resolve();
    };
    //姓名输入规则
    let nameRule = (rule, value) => {
      if (!value) {
        return Promise.reject('请输入姓名');
      }
      let reg = /^[\u4E00-\u9FA5]{2,100}$/;
      if ( !(reg.test(value) ) ) {
        return Promise.reject('姓名格式有误');
      }
      return Promise.resolve();
    };

    //电话输入规则
    let phoneNumberRule = (rule, value) => {
      let reg = /^(13[0-9]{9})|(15[0-9]{9})|(17[0-9]{9})|(18[0-9]{9})|(19[0-9]{9})$/;
      if(value.length != 0) {
        if ( !(reg.test(value) ) ) {
          return Promise.reject('电话格式有误');
        }
      }
      return Promise.resolve();
    };

    //密码输入规则
    let passwordRule = async (rule, value) => {
      if (!value) {
        return Promise.reject('请输入注册密码');
      }
      let reg = /(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{5,20}$/;
      if ( !(reg.test(value) && value.length >= 5 && value.length <= 20) ) {
        return Promise.reject('5-20位密码，必须包括数字和字母');
      }
      return Promise.resolve();
    };

    //确认密码输入规则
    let password2Rule = async (rule, value) => {
      if (!value) {
        return Promise.reject('请再次输入密码');
      }
      let reg = /(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{5,20}$/;
      if ( !(reg.test(value) && value.length >= 5 && value.length <= 20) ) {
        return Promise.reject('5-20位密码，必须包括数字和字母');
      }

      if (value === this.form.password) {
        return Promise.resolve();
      }
      return Promise.reject('两次输入密码不一致');
    };


    return {
      //规则标识flag
      ruleFlag:{
        usernameRuleFlag:false,
      },

      // 表单数据
      form: {
       username:'',
        name:'',
        age:'',
        gender:'',
       phoneNumber:'',
        address:'',
       password:'',
      },

      //其他数据
      demo: {
        timer: null,
        time_count: 60,
        roleId: 10,
        userId: '',
        deleted: 0,
        email_verified: 0,
        state: 0
      },

      // 表单验证规则
      rules: {

        username: [
          {required: true, validator: usernameRule, trigger: 'blur'}
        ],
        name:[
          {required:true,validator:nameRule,trigger:'blur'}
        ],
        phoneNumber: [
          {required: false, validator: phoneNumberRule, trigger: 'blur'}
        ],
        password: [
          {required: true, validator: passwordRule, trigger: 'blur'}
        ],
        password2: [
          {required: true, validator: password2Rule, trigger: 'blur'}
        ],

      }
    }
  },
  methods: {
        /*重置所填信息*/
    toReset() {
      location.reload();
    },

    /*提交信息*/
    onSubmit() {
      if(this.ruleFlag.usernameRuleFlag==true) {
        this.$refs.form.validate().then(() => {

          //检验村民表是否有重复的
          VillagerService.findVillagersWithIdNameByName(this.form.name).then((respp) => {
            if (respp.data != -1) {
              //存入村民表
              VillagerService.saveStudentInfo(this.form).then((resp) => {
                console.log(resp);
                //存入user表
                UserService.userRegister(this.demo.deleted, this.form.email, this.demo.email_verified, this.form.studentId, this.form.password, this.demo.state, this.form.username).then((re) => {
                  console.log(re);
                  UserService.getUserEid(this.form.studentId).then((res) => {

                    //存入user_role表
                    UserService.insertBatchSingle(res.data, this.demo.roleId).then((ree) => {
                      console.log(ree);
                      VXETable.modal.message({content: '注册成功！', status: 'success'});

                      setTimeout(() => {
                        this.$router.push('/login');
                      }, 1000);

                    })
                      .catch(error => {
                        VXETable.modal.message({content: `加载数据出错之存入用户角色表1，原因是：${error.message}`, status: 'error'});
                      })

                  })
                    .catch(error => {
                      VXETable.modal.message({content: `加载数据出错点击后学号重复，原因是：${error.message}`, status: 'error'});
                    })
                })
                  .catch(error => {
                    VXETable.modal.message({content: `加载数据出错之存入用户表，原因是：${error.message}`, status: 'error'});
                  })
              })
                .catch(error => {
                  VXETable.modal.message({content: `加载数据出错之存入学生表，原因是：${error.message}`, status: 'error'});
                })
            } else {
              //修改村民表
              VillagerService.updateStudentInfoMy( this.form.name, this.form.address,
                this.form.age, this.form.gender, this.form.phoneNumber).then((ress) => {
                console.log(ress);

                //存入user表
                UserService.userRegister(this.demo.deleted, this.form.email, this.demo.email_verified, this.form.password,this.form.phoneNumber, this.demo.state, this.form.username).then((re) => {
                  console.log(re);

                  UserService.getUserEid(this.form.username).then((resss) => {

                    //存入user_role表
                    UserService.insertBatchSingle(resss.data, this.demo.roleId).then((ree) => {
                      console.log(ree);
                      VXETable.modal.message({content: '注册成功！', status: 'success'});

                      setTimeout(function () {
                        this.$router.push('/login');
                      }, 1000);

                    })
                      .catch(error => {
                        VXETable.modal.message({content: `加载数据出错之存入用户角色表2，原因是：${error.message}`, status: 'error'});
                      })

                  })
                    .catch(error => {
                      VXETable.modal.message({content: `加载数据出错点击后学号重复，原因是：${error.message}`, status: 'error'});
                    })
                })
                  .catch(error => {
                    VXETable.modal.message({content: `加载数据出错之存入用户表，原因是：${error.message}`, status: 'error'});
                  })
              })
                .catch(error => {
                  VXETable.modal.message({content: `加载数据出错之修改学生表，原因是：${error.message}`, status: 'error'});
                })
            }

          })
            .catch(error => {
              VXETable.modal.message({content: `加载数据出错，原因是：${error.message}`, status: 'error'});
            })

        }).catch(() => {
          //VXETable.modal.message({content: `填写内容不全，请检查`, status: 'error'});
        });
      }
      else {
        VXETable.modal.message({content: `填写内容有误，请检查`, status: 'error'});
      }

    }

  }
}


</script>

<style scoped>
/*背景*/
.bigbody{
  background-image: url("~@/assets/bg.jpg");
  background-repeat: no-repeat;
  background-size: cover;
}
.ele-body {
  padding: 48px 16px 0 16px;
  position: relative;
  box-sizing: border-box;
  min-height: 100vh;
}
.card {
  width: 680px;
  height: 580px;
  margin: auto;
  padding: 0px;
  border-radius: 15px;
  background: rgba(255, 255, 255, 0.95);
}
.content {
  margin-top: -10px;
}

.form {
  margin-top: 25px;
  margin-left: -20px;
}

.buttons {
  margin-top: 10px;
  margin-left: 0px;
}
.btn {
  margin-top: -40px;
  margin-left: 20px;
  width: 80px; /* 宽度 */
  height: 40px; /* 高度 */
}
.submitbt {
  width: 110px;
  border-radius: 8px;
  position: absolute;
  margin-left: 170px;
  margin-top: -35px;
}
.clearbt {
  width: 110px;
  border-radius: 8px;
  position: absolute;
  margin-left: 30px;
  margin-top: -35px;
}

/* 底部版权 */
.login-copyright {
  color: #666972;
  text-align: center;

}

</style>
