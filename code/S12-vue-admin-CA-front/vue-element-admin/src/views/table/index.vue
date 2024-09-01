<template>
  <div class="app-container">
    <h3>本CA所授权所有 Connector </h3>
    实时更新：
    <br>
    <br>
    <el-button @click="refreshF" type="primary">刷新</el-button>
    <br>
    <br>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" width="180">
      </el-table-column>
      <el-table-column prop="idName" label="idName" width="180">
      </el-table-column>
      <el-table-column prop="idPW" label="idPW">
      </el-table-column>
      <el-table-column prop="caClass" label="caClass">
      </el-table-column>
      <el-table-column prop="date1" label="date1">
      </el-table-column>
      <el-table-column prop="date2" label="date2">
      </el-table-column>
      <el-table-column prop="addDoc" label="addDoc">
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      listLoading: true,
      tableData: []

    }
  },
  created() {
    this.refreshF()
  },
  methods: {
    async refreshF() {
      var CACert = this.$getCookie("CACert");
      let url = '/api/backConnector/findAllConnector'
      let data = new FormData();
      data.append('cacert', CACert);
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }

      await axios.post(url, data, config).then((response) => {
        // console.log("response.data = " + response.data)

        for (let index in response.data) {
          console.log("data" + index + " = " + response.data[index]);
        }
        // this.tableData = response.data

        // const objList = response.data.map(obj => {
        //   // 你可以根据需求选择哪些键值对保留在字典中
        //   return {
        //     id: obj.id,
        //     idName: obj.idName,
        //     idPW: obj.idPW,
        //     caClass: obj.caClass,
        //     addDoc: obj.addDoc,
        //     date1: obj.date1,
        //     date2: obj.date2
        //   };
        // });

        // this.tableData = objList
        this.tableData = response.data

        console.log("objList = " + objList)

        // console.log("this.tableData = " + this.tableData)
        // console.log("typeof this.tableData = " + typeof this.tableData)
        // console.log("typeof response.data = " + typeof response.data)
        // console.log("this.tableData[0] = " + this.tableData[0].date)
        // console.log("response.data[0] = " + response.data[0].idName)


      }).catch(function (err) {
        console.log("err = " + err)
      });

    },
  }
}
</script>
