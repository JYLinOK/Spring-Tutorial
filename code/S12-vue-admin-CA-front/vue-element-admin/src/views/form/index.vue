<template>
  <div class="app-container">
    <h3>申请成为-本CA所授权 Connector </h3>
    请按照下面格式，输入申请信息:
    <br>
    <br>
    <br>
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="组织名称">
        <el-input v-model="form.idName" prefix-icon="el-icon-user-solid"
          placeholder="请输入申请-企业-机构-单位-组织-自然人的名称 => 将作为默认用户名" />
      </el-form-item>

      <el-form-item label="初始密码">
        <el-input type="password" prefix-icon="el-icon-lock" v-model="form.idPW" placeholder="请输出初始密码 => 将作为默认用户密码" />
      </el-form-item>

      <el-form-item label="实体类别">
        <el-select v-model="form.caClass" style="width:100%" placeholder="请输入Connector-CA使用实体的类别属性">
          <el-option label="民企" value="mingqi" />
          <el-option label="国企" value="guoqi" />
          <el-option label="事业单位" value="shiyedanwei" />
          <el-option label="机关部门" value="jiguanbumen" />
          <el-option label="科研机构" value="keyanjigou" />
          <el-option label="金融机构" value="jirongjigou" />
          <el-option label="社会团体" value="shehuituanti" />
          <el-option label="个体商户" value="getishanghu" />
          <el-option label="自然人" value="ziranren" />
        </el-select>
      </el-form-item>

      <el-form-item label="申请使用时间">
        <el-col :span="11">
          <el-date-picker v-model="form.date1" type="date" placeholder="请输入CA证书正式生效的时间-以北京时间为准" style="width: 100%;" />
        </el-col>
        <el-col :span="2" class="line">-至-</el-col>
        <!-- <el-col :span="11">
          <el-time-picker v-model="form.date2" type="fixed-time" placeholder="Pick a time" style="width: 100%;" />
        </el-col> -->
        <el-col :span="11">
          <el-date-picker v-model="form.date2" type="date" placeholder="请输入CA证书过期失效的时间-以北京时间为准" style="width: 100%;" />
        </el-col>
      </el-form-item>

      <el-form-item label="附加申请说明">
        <el-input v-model="form.addDoc" :rows="3" type="textarea" />
      </el-form-item>

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
import axios from 'axios'

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

    const validatePW = (rule, value, callback) => {
      if (value.length <= 1) {
        callback(new Error('The password of connector can not be less than 2 digits'))
      } else {
        callback()
      }
    }

    const validateClass = (rule, value, callback) => {
      if (value.length <= 1) {
        callback(new Error('The class of connector can not be null'))
      } else {
        callback()
      }
    }

    const validateDate1 = (rule, value, callback) => {
      if (value.length <= 0) {
        callback(new Error('The start date of connector can not be null'))
      } else {
        callback()
      }
    }

    const validateDate2 = (rule, value, callback) => {
      if (value.length <= 0) {
        callback(new Error('The end date of connector can not be null'))
      } else {
        callback()
      }
    }

    const validateAddDoc = (rule, value, callback) => {
      if (value.length <= 0) {
        callback(new Error('The added document of connector can not be null'))
      } else {
        callback()
      }
    }

    return {
      form: {
        idName: '',
        idPW: '',
        caClass: '',
        date1: 0,
        date2: 0,
        // type: [],
        addDoc: ''
      },
      rules: {
        idName: [{ required: true, trigger: 'blur', validator: validateName }],
        idPW: [{ required: true, trigger: 'blur', validator: validatePW }],
        caClass: [{ required: true, trigger: 'blur', validator: validateClass }],
        date1: [{ required: true, trigger: 'blur', validator: validateDate1 }],
        date2: [{ required: true, trigger: 'blur', validator: validateDate2 }],
        addDoc: [{ required: true, trigger: 'blur', validator: validateAddDoc }],
      },
    }
  },
  methods: {
    async onSubmit() {
      var CACert = this.$getCookie("CACert");
      console.log("form CACert = " + CACert)
      // console.log("form document.cookie = " + document.cookie)

      // console.log("this.form.date1 = " + this.form.date1)
      // console.log("this.form.date2 = " + this.form.date2)
      // console.log("this.form.date2.getTime() = " + this.form.date2.getTime())

      let url = '/api/backConnector/registerConnector'
      let data = new FormData();
      data.append('idName', this.form.idName);
      data.append('idPW', this.form.idPW);
      data.append('caClass', this.form.caClass);
      data.append('ldate1', this.form.date1.getTime());
      data.append('ldate2', this.form.date2.getTime());
      data.append('addDoc', this.form.addDoc);
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
          message: '组织机构名已存在',
          type: 'error',
          center: true
        })
      } else {
        this.$message({
          duration: 5000,
          message: '注册成功！',
          type: 'success',
          center: true
        })
      }
    },

    // async onTest() {
    //   var CACert = this.$getCookie("CACert");
    //   let url = '/api/backConnector/findAllConnector'
    //   let data = new FormData();
    //   data.append('cacert', CACert);
    //   let config = {
    //     headers: {
    //       'Content-Type': 'multipart/form-data'
    //     }
    //   }

    //   await axios.post(url, data, config).then((response) => {
    //     // console.log("response.data = " + response.data)
    //     // console.log("response.data[1].idName = " + response.data[1].idName)

    //     for (let index in response.data) {
    //       console.log("data" + index + " = " + response.data[index]);
    //     }


    //   }).catch(function (err) {
    //     console.log("err = " + err)
    //   });

    // },

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
</style>
