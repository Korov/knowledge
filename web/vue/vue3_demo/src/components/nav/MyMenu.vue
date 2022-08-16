<template>
  <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect">
    <el-sub-menu v-for="item in menuData" :key="item.id" :index="item.id">
      <template #title>{{ item.title }}</template>
      <el-menu-item v-for="subItem in item.subNav" :key="subItem.id" :index="subItem.id">
        <template #title>{{ item.title }}</template>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import MySubMenu from "@/components/nav/MySubMenu.vue";

@Options({
  components: {MySubMenu},
})

// 变量通过声明属性的方式定义在这里
export default class MyMenu extends Vue {
  menuData = [{
    id: 1,
    title: 'nav1',
    subNav: [{
      id: 11,
      title: 'subNav1'
    }, {
      id: 12,
      title: 'subNav2',
      subNav: [{title: 'subNav21'}
      ]
    }]
  }, {
    id: 2,
    title: 'nav2',
    subNav: [{
      id: 21,
      title: 'subNav1'
    }, {
      id: 22,
      title: 'subNav2'
    }]
  }]
  activeIndex = "1"
  handleSelect = (key: string, keyPath: string[]) => {
    this.activeIndex = key
    console.log(`key:${key}, path:${keyPath}`)
  }
}
</script>

<style>
</style>
