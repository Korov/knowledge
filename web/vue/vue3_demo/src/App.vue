<template>
  <div class="common-layout">
    <el-container>
      <el-header class="my-header">
        <MyMenu :menuData="menuData"></MyMenu>
      </el-header>
      <el-main class="my-main">Main</el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import MyMenu from './components/nav/MyMenu'

let menuData = [{
  id: "1",
  title: 'nav1',
  subNav: [{
    id: "11",
    title: 'subNav1'
  }, {
    id: "12",
    title: 'subNav2',
    subNav: [{title: 'subNav21'}
    ]
  }]
}, {
  id: "2",
  title: 'nav2',
  subNav: [{
    id: "21",
    title: 'subNav1'
  }, {
    id: "22",
    title: 'subNav2'
  }]
}]

</script>

<style>
.my-header {
  background: goldenrod;
}

.my-main {
  background: darkcyan;
}
</style>
