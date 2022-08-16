<template>
  <div class="common-layout">
    <el-container>
      <el-header class="my-header">
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect">
          <el-menu-item index="1">Processing Center</el-menu-item>
          <el-sub-menu index="2">
            <template #title>Workspace</template>
            <el-menu-item index="2-1">item one</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
            <el-sub-menu index="2-4">
              <template #title>item four</template>
              <el-menu-item index="2-4-1">item one</el-menu-item>
              <el-menu-item index="2-4-2">item two</el-menu-item>
              <el-menu-item index="2-4-3">item three</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-menu-item index="3" disabled>Info</el-menu-item>
          <el-menu-item index="4">Orders</el-menu-item>
        </el-menu>
      </el-header>
      <el-main class="my-main">Main</el-main>
    </el-container>
  </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';

@Options({
  components: {},
})
// 变量通过声明属性的方式定义在这里
export default class App extends Vue {
  navs = [{
    title: 'nav1',
    subNav: [{
      title: 'subNav1'
    }, {
      title: 'subNav2',
      subNav: [{title: 'subNav21'}
      ]
    }]
  }, {
    title: 'nav2',
    subNav: [{
      title: 'subNav1'
    }, {
      title: 'subNav2'
    }]
  }]
  activeIndex = "2-4-2"
  handleSelect = (key: string, keyPath: string[]) => {
    this.activeIndex = key
    console.log(`key:${key}, path:${keyPath}`)
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.my-header {
  /*background: goldenrod;*/
}

.my-main {
  background: darkcyan;
}
</style>
