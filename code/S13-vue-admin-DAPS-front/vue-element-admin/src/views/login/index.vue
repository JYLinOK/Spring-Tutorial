<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" autocomplete="on"
      label-position="left">

      <div class="title-container">
        <h3 class="title">DAPS-001</h3>
        <h5 class="title">开发者:林进威 设计项目: DS数据空间 </h5>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input ref="username" v-model="loginForm.username" placeholder="Username" name="username" type="text"
          tabindex="1" autocomplete="on" />
      </el-form-item>

      <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType"
            placeholder="Password" name="password" tabindex="2" autocomplete="on" @keyup.native="checkCapslock"
            @blur="capsTooltip = false" @keyup.enter.native="handleLogin" />
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
      </el-tooltip>

      <!-- <el-form-item prop="cacert">
        <span class="svg-container">
          <svg-icon icon-class="guide" />
        </span>
        <el-input ref="cacert" v-model="loginForm.cacert" placeholder="CA-Certification" name="cacert" type="text"
          tabindex="1" autocomplete="on" />
      </el-form-item> -->

      <br>

      <div>
        <b class="noteFont">CA-Certification</b>
        <br>
        <br>
        <UploadJson ref='UploadJson' v-model="loginForm.cacert" />
      </div>

      <br>
      <br>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
        @click.native.prevent="handleLogin">Login</el-button>

      <div style="position:relative">
        <!-- <div class="tips">
          <span>Username : admin</span>
          <span>Password : any</span>
        </div> -->
        <!-- <div class="tips">
          <span style="margin-right:18px;">Username : editor</span>
          <span>Password : any</span>
        </div> -->

        <el-button class="thirdparty-button2" type="primary" @click="showDialog = true">
          更多登录方式
        </el-button>
      </div>

      <br>
      <br>

    </el-form>

    <el-dialog title="Or connect with" :visible.sync="showDialog">
      Can not be simulated on local, so please combine you own business simulation! ! !
      <br>
      <br>
      <br>
      <social-sign />
    </el-dialog>

  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
// import Upload from '@/components/Upload/SingleImage3'
import UploadJson from '@/components/UploadJson/index.vue'
import SocialSign from './components/SocialSignin'
import axios from 'axios'

export default {
  name: 'Login',
  components: { SocialSign, UploadJson },
  data() {
    const validateUsername = (rule, value, callback) => {
      console.log('username value = ' + value)
      if (!validUsername(value)) {
        this.bool_validateUsername = false
        callback(new Error('Please enter the correct user name'))
      } else {
        this.bool_validateUsername = true
        console.log('this.bool_validateUsername = ' + this.bool_validateUsername)
        callback()
      }
    }

    // 添加-认证CA证书-初步认证
    const validateCacert = (rule, value, callback) => {
      // 父组件直接获取子组件的值
      this.jsonStr = this.$refs.UploadJson.uploadForm.jsonData
      // console.log('\n\nca this.jsonStr = ' + this.jsonStr)
      // console.log('ca value = ' + value)
      this.loginForm.cacert = this.jsonStr
      // console.log('ca this.loginForm.cacert = ' + this.loginForm.cacert)

      if (this.loginForm.cacert < 6) {
        this.bool_validateCacert = false
        callback(new Error('The ca cert can not be less than 6 digits'))
      } else {
        this.bool_validateCacert = true
        callback()
      }
    }

    const validatePassword = (rule, value, callback) => {
      if (value.length < 2) {
        this.bool_validatePassword = false
        callback(new Error('The password can not be less than 2 digits'))
      } else {
        this.bool_validatePassword = true
        callback()
      }
    }

    return {
      loginForm: {
        username: 'Jinwei',
        cacert: 'CA',
        password: 'pw0',
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        cacert: [{ required: true, trigger: 'change', validator: validateCacert }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      showDialog: false,
      redirect: undefined,
      otherQuery: {},
      bool_validateUsername: false,
      bool_validatePassword: false,
      bool_validateCacert: false,
      jsonStr: '',
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  created() {
    // window.addEventListener('storage', this.afterQRScan)
  },
  mounted() {
    if (this.loginForm.username === '') {
      this.$refs.username.focus()
    } else if (this.loginForm.password === '') {
      this.$refs.password.focus()
    }
  },
  destroyed() {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
    async validCacert2(str, userName, userPW) {
      let url = 'api/backUser/loginUser'

      let data = new FormData();
      data.append('idName', userName);
      data.append('idPW', userPW);
      data.append('cacert', str);
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }

      // let code = 200
      let re = false
      await axios.post(url, data, config).then((response) => {
        console.log("response.data = " + response.data);
        // console.log("response.status = " + response.status);
        re = true
      }).catch(function (err) {
        console.log("err = " + err)
        re = false
        // console.log("1 re = " + re)
      });
      console.log("2 re = " + re)
      return re
    },
    checkCapslock(e) {
      const { key } = e
      this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.validCacert2(this.jsonStr, this.loginForm.username, this.loginForm.password).then((re) => {
        console.log('\n\n======================================================')
        // console.log('validCacert2 re = ' + re)
        // console.log('validCacert2 this.jsonStr = ' + this.jsonStr)
        // console.log('typeof validCacert2 this.jsonStr = ' + typeof this.jsonStr)
        // console.log('login JSON.parse(this.jsonStr).cacert = ' + JSON.parse(this.jsonStr).cacert )

        // JS原生写入 Cookie
        var d = new Date();
        var days = 10;
        d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000)); // days天后过期
        var expires = "expires=" + d.toUTCString();
        document.cookie = "CACert=" + JSON.stringify(JSON.parse(this.jsonStr))  + "; " + expires + ";" ;
        console.log("login document.cookie = " + document.cookie)

        // CA证书认证通过后-运行代码
        this.$refs.loginForm.validate(valid => {
          console.log('valid = ' + valid)
          console.log('this.bool_validateUsername = ' + this.bool_validateUsername)
          console.log('this.bool_validateCacert = ' + this.bool_validateCacert)
          console.log('this.bool_validatePassword = ' + this.bool_validatePassword)

          if (valid && this.bool_validateUsername && this.bool_validateCacert && this.bool_validatePassword && re) {
            this.loading = true
            console.log('logging ===>')

            // 使用store存储数据
            this.$store.dispatch('user/login', this.loginForm)
              .then(() => {
                // 正式启动后的运行代码
                this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
                this.loading = false
              })
              .catch(() => {
                this.loading = false
              })
            this.$message({
              duration: 5000,
              message: '登录成功！',
              type: 'success',
              center: true
            })
          } else {
            this.$message({
              duration: 2000,
              message: '输出信息错误',
              type: 'error',
              center: true
            })
            return false
          }
        })

      }).catch(() => {
        console.log('登录错误2')
        this.loading = false
      })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
    // afterQRScan() {
    //   if (e.key === 'x-admin-oauth-code') {
    //     const code = getQueryObject(e.newValue)
    //     const codeMap = {
    //       wechat: 'code',
    //       tencent: 'code'
    //     }
    //     const type = codeMap[this.auth_type]
    //     const codeName = code[type]
    //     if (codeName) {
    //       this.$store.dispatch('LoginByThirdparty', codeName).then(() => {
    //         this.$router.push({ path: this.redirect || '/' })
    //       })
    //     } else {
    //       alert('第三方登录失败')
    //     }
    //   }
    // }
  }
}
</script>

<style lang="scss">
// 自定义

.noteFont {
  color: white;
}
</style>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

.noteFont {
  color: white;
}

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
// $bg: #226888;
$bg: #393363;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
    width: auto;
    margin: auto 0;
  }

  .thirdparty-button2 {
    width: 100%;
    margin: auto 0;
    // margin-left: 20%;
    background-color: #c6d4ca38;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }
}
</style>
