<template>
  <div class="app-container">
    <h3>Connector 上传-DAT</h3>
    请按照下面格式，输入申请信息:
    <br>
    <br>
    <br>
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="CA名称">
        <el-input v-model="form.idName" prefix-icon="el-icon-user-solid"
          placeholder="请输入申请-企业-机构-单位-组织-自然人的名称 => 将作为默认用户名" />
      </el-form-item>


      <span>
        <el-button style="margin-right: 20px;" type="primary" @click="addDAT">添加 DAT</el-button>
        DAT Key:
        <el-input v-model="keyDAT" style="width: 266px; margin-right: 20px;" placeholder="key of DAT" />
        DAT Value:
        <el-input v-model="valueDAT" style="width: 266px; margin-right: 20px;" placeholder="value of DAT" />
      </span>

      <br>
      <br>

      <div class="DAT" v-for="(item, index) in form.addedDAT">
        DAT-{{ index }}: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; keyDAT = {{ item.key }}
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; valueDAT = {{ item.value }}
      </div>


      <br>
      <br>
      <br>

      <el-form-item style="margin: auto 0; width: 100%; text-align: center;">
        <el-button style="margin-left: -60px;" type="primary" @click="onSubmit">提交</el-button>
        <el-button style="margin-left: 60px;" @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import request from '@/utils/request';
import axios from 'axios';


export default {
  data() {
    const validateName = (rule, value, callback) => {
      console.log('username value = ' + value)
      if (value.length < 2) {
        callback(new Error('The idName of connector can not be less than 2 digits'))
      } else {
        callback()
      }
    }

    return {
      keyDAT: '',
      valueDAT: '',
      form: {
        idName: '',
        addedDAT: [
        ]
      },
      rules: {
        idName: [{ required: true, trigger: 'blur', validator: validateName }],
      },
    }
  },
  methods: {
    async onSubmit() {
      var CACert = this.$getCookie("CACert");
      console.log("form CACert = " + CACert)
      // console.log("form document.cookie = " + document.cookie)

      console.log("form this.form.addedDAT = " + this.form.addedDAT)
      console.log("form typeof this.form.addedDAT = " + typeof this.form.addedDAT)

      // console.log("this.form.date1 = " + this.form.date1)
      // console.log("this.form.date2 = " + this.form.date2)
      // console.log("this.form.date2.getTime() = " + this.form.date2.getTime())

      let url = '/api/backConnectorDAT/registerConnectorDAPS'
      let data = new FormData();
      data.append('idName', this.form.idName);
      // data.append('DAT', this.form.addedDAT);
      data.append('DAT', JSON.stringify(this.form.addedDAT));
      data.append('cacert', CACert);
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }

      var re = false;
      await axios.post(url, data, config).then((response) => {
        console.log("response.data = " + response.data)
        re = true;
      }).catch(function (err) {
        console.log("err = " + err)
      });
      if (!re) {
        this.$message({
          duration: 2000,
          message: '提交错误',
          type: 'error',
          center: true
        })
      } else {
        this.$message({
          duration: 5000,
          message: '提交成功！',
          type: 'success',
          center: true
        })
      }
    },

    addDAT() {
      this.$message({
        duration: 1000,
        message: 'Add DAT',
        type: 'success'
      })

      this.form.addedDAT.push(
        {
          "key": this.keyDAT,
          "value": this.valueDAT
        }
      )
    },

    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}

.DAT {
  width: 100%;
  height: 40px;
  padding: 10px;
  background: rgba(200, 229, 230, 0.79);
  margin-top: 10px;
}
</style>
