<template>
  <div class="home-container">
    <div class="home-text">name: {{ name }}</div>
    <br>
    <br>
    <div class="bigText">欢迎访问 CA-001</div>
    <br>
    <div class="bigTextDown">机构编号: CA-001  作者: 林进威</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      name: 'Home',
    }
  },

  computed: {
    ...mapGetters([
    ])
  }
}
</script>

<style lang="scss" scoped>
.home {
  &-container {
    // margin: 30px;
    background: url("~@/assets/images/p1.jpeg") no-repeat;
    -webkit-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
    position: absolute;
    width: 100%;
    height: 100%;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
    color: white;
    margin: 10px;
  }
}

.bigText {
  font-size: 66px;
  margin: auto 0;
  width: 100%;
  text-align: center;
  color: white;
}

.bigTextDown {
  font-size: 26px;
  margin: auto 0;
  width: 100%;
  text-align: center;
  color: white;
}

</style>
