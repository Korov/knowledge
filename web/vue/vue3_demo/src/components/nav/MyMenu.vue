<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" router @select="handleSelect">
    <el-sub-menu v-for="item in menuData" :key="item.id" :index="item.id">
      <template #title>{{ item.title }}</template>
      <el-menu-item v-for="subItem in item.subNav" :key="subItem.id" :index="subItem.id" :route="subItem.path">
        <template #title>{{ subItem.path }}</template>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts">
import { inject, nextTick, provide, ref } from "vue";

export default {
  setup() {
    const menuData = inject('menuData')

    const isRouterAlive = ref(true);
    const reload = () => {
      isRouterAlive.value = false;
      nextTick(() => {
        isRouterAlive.value = true;
      });
    };
    provide("reload", reload);

    let activeIndex = "11"

    function handleSelect(key: string, keyPath: string[]) {
      activeIndex = key
      console.log(`key:${key}, path:${keyPath}`)
    }

    return {
      activeIndex,
      handleSelect,
      menuData
    }
  }
}
</script>

<style>
</style>
