<template>
  <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect">
    <el-sub-menu v-for="item in props.menuData" :key="item.id" :index="item.id">
      <template #title>{{ item.title }}</template>
      <el-menu-item v-for="subItem in item.subNav" :key="subItem.id" :index="subItem.id">
        <template #title>{{ item.title }}</template>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup lang="ts">
import {defineProps} from "vue";

let props = defineProps({
  menuData: {}
})
let activeIndex = "11"

function handleSelect(key: string, keyPath: string[]) {
  activeIndex = key
  console.log(`key:${key}, path:${keyPath}`)
}
</script>

<style>
</style>
