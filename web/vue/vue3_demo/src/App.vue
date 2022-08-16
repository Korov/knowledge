<template>
  <div class="common-layout">
    <el-container>
      <el-header class="my-header">
        <MyMenu/>
      </el-header>
      <el-main class="my-main">Main</el-main>
    </el-container>
  </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import MyMenu from "@/components/nav/MyMenu.vue";

@Options({
  components: {MyMenu},
})
// 变量通过声明属性的方式定义在这里
export default class App extends Vue {
}
</script>

<style>
.my-header {
  background: goldenrod;
}

.my-main {
  background: darkcyan;
}
</style>
