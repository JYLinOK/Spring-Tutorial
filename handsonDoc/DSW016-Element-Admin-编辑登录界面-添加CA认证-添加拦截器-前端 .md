# DSW016-Element-Admin-编辑登录界面-添加CA认证-添加拦截器-前端 

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code/S11-vue-admin-jwt-login](../code/S11-vue-admin-jwt-login/)

继DSW15:

---

## SpringBoot后台程序

Code: [../code/S11_mongotemplate_JWT_JSON_Login](../code/S11_mongotemplate_JWT_JSON_Login/)

## src\views\login\index.vue

```js
<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" autocomplete="on"
      label-position="left">

      <div class="title-container">
        <h3 class="title">Connector</h3>
        <h5 class="title">开发者:林进威 测试项目: DS数据空间 </h5>
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
      let url = 'api/backuser/login'

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
        // console.log("response.data = " + response.data);
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
        console.log('\n\n=================================')
        console.log('validCacert2 re = ' + re)

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
$bg: #2d3a4b;
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
```

## src\utils\validate.js

```js
import axios from 'axios'


export function delayMsAsync(ms) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve('done');
    }, ms);
  });
}

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['Jinwei', 'admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}


/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
  if (typeof str === 'string' || str instanceof String) {
    return true
  }
  return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}
```

## src\components\UploadJson\index.vue

```js
<template>
  <div class="jsonBox">
    <el-form-item prop="cacert">
      <span class="svg-container">
        <svg-icon icon-class="guide" />
      </span>
      <el-input ref="cacert" v-model="uploadForm.jsonData" class="noteFontJson" placeholder="直接填写-复制或者上传" name="cacert"
        type="text" tabindex="1" autocomplete="on" />
    </el-form-item>

    <input ref="json-upload-input"  class="json-upload-input" type="file" accept=".json" @change="handleClick">
    <div class="drop" @drop="handleDrop" @dragover="handleDragover" @dragenter="handleDragover">
      拖拽或点击上传
      <el-button :loading="loading" style="margin-left:16px;" size="mini" type="primary" @click="handleUpload">
        上传
      </el-button>
    </div>
  </div>
</template>

<script>
// import { ElMessage } from 'element-plus'

export default {
  props: {
    beforeUpload: Function, // eslint-disable-line
    onSuccess: Function// eslint-disable-line
  },
  data() {
    return {
      uploadForm: {
        jsonData: ''
      },
      loading: false,
      excelData: {
        header: null,
        results: null
      }
    }
  },
  methods: {
    generateData({ header, results }) {
      this.excelData.header = header
      this.excelData.results = results
      this.onSuccess && this.onSuccess(this.excelData)
    },
    handleDrop(e) {
      e.stopPropagation()
      e.preventDefault()
      if (this.loading) return
      const files = e.dataTransfer.files
      if (files.length !== 1) {
        this.$message.error('只能上传一个文件')
        return
      }
      const rawFile = files[0] // only use files[0]

      if (!this.isJson(rawFile)) {
        this.$message.error('请上传json文件')
        return false
      }
      this.upload(rawFile)
      e.stopPropagation()
      e.preventDefault()
    },
    handleDragover(e) {
      e.stopPropagation()
      e.preventDefault()
      e.dataTransfer.dropEffect = 'copy'
    },
    handleUpload() {
      this.$refs['json-upload-input'].click()
    },
    handleClick(e) {
      const files = e.target.files
      const rawFile = files[0] // only use files[0]
      if (!rawFile) return
      this.upload(rawFile)
    },
    upload(rawFile) {
      this.$refs['json-upload-input'].value = null // fix can't select the same excel

      if (!this.beforeUpload) {

        this.$message({
          duration: 3000,
          message: 'CA证书输入成功! 点击 Login-验证登录！',
          type: 'success'
        })

        this.readerData(rawFile)
        return
      }
      const before = this.beforeUpload(rawFile)
      if (before) {
        this.readerData(rawFile)
      }
    },
    readerData(rawFile) {
      this.loading = true
      return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.onload = e => {
          // console.log('reader.onload = e => ========================')

          // 获取读取到的文件内容+解析JSON文件
          const contents = reader.result
          // console.log('contents = ' + contents)
          // console.log('contents.byteLength = ' + contents.byteLength)
          const jsonData = String.fromCharCode.apply(null, new Uint8Array(contents))
          // console.log('jsonData = ' + jsonData)
          // console.log('typeof jsonData = ' + typeof jsonData);
          this.uploadForm.jsonData = jsonData

          // ========================
          // 继续处理读取数据



          // ========================

          this.loading = false
          resolve()
        }
        reader.readAsArrayBuffer(rawFile)
      })
    },
    isJson(file) {
      // return /\.(json|text|csv)$/.test(file.name)
      return /\.(json)$/.test(file.name)
    }
  }
}
</script>

<style scoped>
.noteFontJson {
  color: rgb(10, 2, 2);
  /* background-color: rgb(0, 20, 56); */
}

.jsonBox {
  color: aqua;
  /* background-color: rgb(233, 229, 226); */
  background-color: rgba(192, 220, 223, 0.6);
}

.json-upload-input {
  display: none;
  z-index: -9999;
}

.drop {
  border: 2px dashed #bbb;
  width: 100%;
  height: 160px;
  line-height: 160px;
  margin: 0 auto;
  font-size: 24px;
  border-radius: 5px;
  text-align: center;
  color: #05160ccc;
  position: relative;
}
</style>
```


## .env.development

```js
# just a flag
ENV = 'development'

# base api
VUE_APP_BASE_API = '/dev-api'

# backend-api
BACKEND_API = 'http://localhost:8081'
```


## .env.production

```js
# just a flag
ENV = 'production'

# base api
VUE_APP_BASE_API = '/prod-api'

# backend-api
BACKEND_API = 'http://localhost:8081'
```


## vue.config.js

```js
'use strict'
const path = require('path')
const defaultSettings = require('./src/settings.js')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const name = defaultSettings.title || 'vue Element Admin' // page title
const port = process.env.port || process.env.npm_config_port || 9527 // dev port

module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: process.env.NODE_ENV === 'development',
  productionSourceMap: false,
  devServer: {
    port: port,
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    before: require('./mock/mock-server.js'),
    proxy: {
      // 设置后端API-跨域
      '/api': {
        // target: 'http://localhost:8081',  // 跨域真实请求后端-URl地址
        target: process.env.BACKEND_API,  // 跨域真实请求后端-URl地址
        ws: true,
        changeOrigin: true,  // 设置允许跨域
        // pathRewrite: {  // 替换-通配 /api 的替换成对应字符
        // //  重写路径=> http://localhost:8080/api/xxx = http://localhost:8081/xxx
        '^/api': ''  // 当你的接口中没有/api字眼时，用这种方式，替换成空''
        // //  '^/api': '/api'   //当接口中有/api时，用这种方式
        // }
      }
    }

  },
  configureWebpack: {
    name: name,
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  },
  chainWebpack(config) {
    config.plugin('preload').tap(() => [
      {
        rel: 'preload',
        fileBlacklist: [/\.map$/, /hot-update\.js$/, /runtime\..*\.js$/],
        include: 'initial'
      }
    ])

    config.plugins.delete('prefetch')
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()

    config
      .when(process.env.NODE_ENV !== 'development',
        config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
              inline: /runtime\..*\.js$/
            }])
            .end()
          config
            .optimization.splitChunks({
              chunks: 'all',
              cacheGroups: {
                libs: {
                  name: 'chunk-libs',
                  test: /[\\/]node_modules[\\/]/,
                  priority: 10,
                  chunks: 'initial' // only package third parties that are initially dependent
                },
                elementUI: {
                  name: 'chunk-elementUI', // split elementUI into a single package
                  priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
                  test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
                },
                commons: {
                  name: 'chunk-commons',
                  test: resolve('src/components'), // can customize your rules
                  minChunks: 3, //  minimum common number
                  priority: 5,
                  reuseExistingChunk: true
                }
              }
            })
          config.optimization.runtimeChunk('single')
        }
      )
  }
}
```








