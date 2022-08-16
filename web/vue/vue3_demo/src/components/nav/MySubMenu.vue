<template>
  <div>
    <el-menu-item v-for="item in menuData" :key="item.id" :index="item.id">
      <template #title>{{ item.title }}</template>
      <el-sub-menu v-for="subItem in item.subNav" :key="subItem.id" :index="subItem.id">
        <template #title>{{ item.title }}</template>
      </el-sub-menu>
    </el-menu-item>
  </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';

@Options({
  components: {},
})

export default class MySubMenu extends Vue {
  demoClick = (key: any) => {
    let existingPropertyNames =
        Reflect.getPrototypeOf(key) || [];
    console.log(`click key:${existingPropertyNames}`)
  }

}
</script>

<style scoped>

</style>