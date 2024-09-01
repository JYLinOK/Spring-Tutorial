<template>
  <div class="app-container">
    <h3>DAPS 所有 Connector 对应 DAT</h3>
    实时更新：
    <br>
    <br>
    <el-button @click="refreshF" type="primary">刷新</el-button>
    <br>
    <br>

    <!-- <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" width="180">
      </el-table-column>
      <el-table-column prop="idName" label="idName" width="180">
      </el-table-column>
      <el-table-column prop="DAT" label="DAT">
      </el-table-column>
    </el-table> -->

    <div class="CADAT" v-for="(item1, key1) in tableData">
      <div v-for="(item2, key2) in item1">
        <div v-if="key2 != 'dat'">
          {{ key2 }} = {{ item2 }}
          <br>
        </div>
        <div v-if="key2 == 'dat'">
          <!-- dat: {{ key2 }} = {{ item2 }} -->
          <br>
           DAT:
           <br>
          <div v-for="(item3, key3) in item2">
            {{ key3 }}: key = {{ item3.key }}
            {{ key3 }}: value = {{ item3.value }}
          </div>
          <br>
        </div>
      </div>
    </div>


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
      let url = '/api/backConnectorDAT/findAllConnectorDAT'
      let data = new FormData();
      data.append('cacert', CACert);
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }

      await axios.post(url, data, config).then((response) => {
        // console.log("response.data = " + response.data)

        var datObj = response.data
        Object.keys(datObj).forEach(key => {
          this.tableData.push(
            {
              "id": key,
              "idName": datObj[key].idName,
              "dat": JSON.parse(datObj[key].dat),
            }
          )

          console.log("datObj[key].dat = " + datObj[key].dat);
          console.log("key = " + key)
          console.log(datObj[key]);
        });


        // console.log("this.tableData = " + this.tableData)
        // this.tableData = response.data
        // console.log(this.tableData[0].idName);
        // console.log("this.tableData = " + this.tableData)
        // console.log("typeof this.tableData = " + typeof this.tableData)
        // console.log("response.data[0] = " + response.data[0].idName)


      }).catch(function (err) {
        console.log("err = " + err)
      });

    },
  }
}
</script>

<style scoped>
.CADAT {
  width: 100%;
  /* height: 80px; */
  height: auto;
  padding: 10px;
  background: rgba(200, 229, 230, 0.79);
  margin-top: 10px;
}
</style>
