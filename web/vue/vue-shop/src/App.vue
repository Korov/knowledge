<template>
  <div id="app">
    <el-button type="info" @click="test111">退出</el-button>
   <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',

  methods: {
    async test111() {
      const { data: resultVo } = await this.$http.get('/menus')
      if (resultVo.code !== 1) return this.$message.error(resultVo.description)
    }
  }
}
</script>

<style>
</style>
