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
