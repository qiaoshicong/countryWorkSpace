<template>
  <div :class="['login-wrapper', ['', 'login-form-right', 'login-form-left'][direction]]">
    <a-form
      ref="loginForm"
      :model="form"
      :rules="rules"
      layout="vertical"
      class="login-form ele-bg-white">
      <h4>用户登录</h4>
      <a-form-item name="username">
        <a-input
          placeholder="请输入登录账号"
          v-model:value="form.username"
          @keyup.enter="doSubmit()"
          allow-clear
          size="large">
          <template #prefix>
            <user-outlined/>
          </template>
        </a-input>
      </a-form-item>
      <a-form-item name="password">
        <a-input-password
          placeholder="请输入登录密码"
          v-model:value="form.password"
          @keyup.enter="doSubmit()"
          size="large">
          <template #prefix>
            <lock-outlined/>
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item name="code" v-show="rules.code.required">
        <div class="login-input-group">
          <a-input
            placeholder="请输入验证码"
            v-model:value="form.code"
            @keyup.enter="doSubmit()"
            allow-clear
            size="large">
            <template #prefix>
              <safety-certificate-outlined/>
            </template>
          </a-input>
          <a-button class="login-captcha">
            <img
              v-if="captcha"
              :src="captcha"
              alt=""
              @click="changeCode"/>
          </a-button>
        </div>
      </a-form-item>
      <a-form-item>
        <a-checkbox
          v-model:checked="form.remember">记住密码
        </a-checkbox>
        <router-link
          to="/forget"
          class="ele-pull-right"
          style="line-height: 22px;">忘记密码
        </router-link>
      </a-form-item>
      <a-form-item>
        <a-button
          block
          size="large"
          type="primary"
          :loading="loading"
          @keyup.enter="doSubmit()"
          @click="doSubmit">
          {{ loading ? '登录中' : '登录' }}
        </a-button>
        <a-button
          block
          size="large"
          type="primary"
          class="registerButton">
          <router-link
            to="/register"
            style="line-height: 22px;">注册
          </router-link>
        </a-button>
      </a-form-item>
      <div class="ele-text-center" style="padding-bottom: 32px;">
        <qq-outlined class="login-oauth-icon" style="background: #3492ed;"/>
        <wechat-outlined class="login-oauth-icon" style="background: #4daf29;"/>
        <weibo-outlined class="login-oauth-icon" style="background: #CF1900;"/>
      </div>
    </a-form>
    <div class="login-copyright">cqnu 软件工程 2021级 易村行</div>
  </div>
</template>

<script>
import {
  UserOutlined,
  LockOutlined,
  SafetyCertificateOutlined,
  QqOutlined,
  WechatOutlined,
  WeiboOutlined
} from '@ant-design/icons-vue';
import store from '@/store';


export default {
  name: 'Login',
  components: {
    UserOutlined,
    LockOutlined,
    SafetyCertificateOutlined,
    QqOutlined,
    WechatOutlined,
    WeiboOutlined
  },
  data() {
    return {
      // 登录框方向, 0居中, 1居右, 2居左
      direction: 0,
      // 加载状态
      loading: false,
      // 表单数据
      form: {
        username: 'admin',
        password: 'admin',
        code: '',
        remember: false
      },
      // 表单验证规则
      rules: {
        username: [
          {required: true, message: '请输入账号', type: 'string', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', type: 'string', trigger: 'blur'}
        ],
        code: [
          {required: false, message: '请输入验证码', type: 'string', trigger: 'blur'}
        ]
      },
      // 验证码base64数据
      captcha: '',
      // 验证码内容, 实际项目去掉
      text: ''
    };
  },
  mounted() {
    if (this.$setting.takeToken()) {
      this.goHome();
    }
    this.changeCode();
  },
  methods: {
    /* 提交 */
    doSubmit() {
      this.$refs.loginForm.validate().then(() => {
        this.loading = true;
        if (this.rules.code.required === true) {
          if (this.form.code.toLowerCase() !== this.text) {
            this.loading = false;
            this.$message.error('验证码错误');
            return;
          }
        }

        let formData = new FormData();
        for (let key in this.form) {
          formData.append(key, this.form[key]);
        }
        this.$http.post('/login', formData).then((res) => {
          // debugger
          this.loading = false;
          if (res.data.code === 0) {
            this.$message.success('登录成功');
            // console.log("usertype===>" + res.data.type);

            this.$store.dispatch('user/setToken', {
              token: res.data.access_token,
              remember: this.form.remember
            }).then(() => {
              //this.goHome();
             console.log( res.data.data().userType);
              this.getUserInfo();
            });
          } else {
            this.loading = false;
            if (res.data.captchaStatus) {
              this.rules.code.required = true;
            }
            this.$message.error(res.data.msg);
          }
        }).catch((error) => {
          if (error.data.captchaStatus) {
            this.rules.code.required = true;
            this.changeCode();
          }
          this.loading = false;
          this.$message.error(error.message);
        });
      }).catch((error) => {
        this.loading = false;
        this.$message.error(error.errorFields[0].errors);
      });
    },
    /* 获取当前用户信息 */
    getUserInfo() {
      console.log(process.env.VUE_APP_BASE_URL)
      console.log(process.env.VUE_SOCKET_BASE_URL)
      // console.log()
      store.dispatch('user/getUser').then(() => {

        this.goHome();

        this.showContent = true;
      })

    },
    /* 跳转到首页 */
    goHome() {
      const query = this.$route.query;
      const path = query && query.from ? query.from : '/cms/HomePage';
      this.$router.push(path).catch(() => {
      });
    },
    /* 更换图形验证码 */
    changeCode() {
      this.form.code ="";
      // 这里演示的验证码是后端返回base64格式的形式, 如果后端地址直接是图片请参考忘记密码页面
      this.$http.get('/file/captcha?username=' + this.form.username).then(res => {
        if (res.data.code === 0) {
          this.captcha = res.data.data;
          // 实际项目后端一般会返回验证码的key而不是直接返回验证码的内容, 登录用key去验证, 你可以根据自己后端接口修改
          this.text = res.data.text;
          // 自动回填验证码, 实际项目去掉这个
          // this.form.code = this.text;
        } else {
          this.$message.error(res.data.msg);
        }
      }).catch(e => {
        this.$message.error(e.message);
      });
    }
  }
}
</script>

<style scoped>
/* 背景 */
.login-wrapper {
  padding: 48px 16px 0 16px;
  position: relative;
  box-sizing: border-box;
  background-image: url("~@/assets/bg.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  min-height: 100vh;
}

.login-wrapper:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, .2);
}

/* 卡片 */
.login-form {
  width: 360px;
  margin: 0 auto;
  max-width: 100%;
  padding: 0 28px;
  box-sizing: border-box;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
  border-radius: 2px;
  position: relative;
  z-index: 2;
}

.login-form-right .login-form {
  margin: 0 15% 0 auto;
}

.login-form-left .login-form {
  margin: 0 auto 0 15%;
}

.login-form h4 {
  padding: 22px 0;
  text-align: center;
}

/* 验证码 */
.login-input-group {
  display: flex;
  align-items: center;
}

.login-input-group :deep(.ant-input-affix-wrapper) {
  flex: 1;
}

.login-input-group .login-captcha {
  width: 102px;
  height: 40px;
  margin-left: 10px;
  padding: 0;
}

.login-input-group .login-captcha > img {
  width: 100%;
  height: 100%;
}

/* 第三方登录图标 */
.login-oauth-icon {
  color: #fff;
  padding: 5px;
  margin: 0 12px;
  font-size: 18px;
  border-radius: 50%;
  cursor: pointer;
}

/* 底部版权 */
.login-copyright {
  color: #eee;
  text-align: center;
  padding: 48px 0 22px 0;
  position: relative;
  z-index: 1;
}

/* 响应式 */
@media screen and (min-height: 640px) {
  .login-wrapper {
    padding-top: 0;
  }

  .login-form {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translateX(-50%);
    margin-top: -230px;
  }

  .login-form-right .login-form,
  .login-form-left .login-form {
    left: auto;
    right: 15%;
    transform: translateX(0);
    margin: -230px auto auto auto;
  }

  .login-form-left .login-form {
    right: auto;
    left: 15%;
  }

  .login-copyright {
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
  }
}

@media screen and (max-width: 768px) {
  .login-form-right .login-form,
  .login-form-left .login-form {
    left: 50%;
    right: auto;
    margin-left: 0;
    margin-right: auto;
    transform: translateX(-50%);
  }
}
</style>
