<template>
  <div class="common-layout">
    <el-container>
      <el-header class="my-header">
        <el-button
            v-for="button in buttons"
            :key="button.text"
            :type="button.type"
            :disabled="button.disabled"
            link
        >{{ button.text }}
        </el-button>
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
  buttons = [
    {type: '', text: 'plain', disabled: false},
    {type: 'primary', text: 'primary', disabled: true},
    {type: 'success', text: 'success', disabled: false},
    {type: 'info', text: 'info', disabled: false},
    {type: 'warning', text: 'warning', disabled: false},
    {type: 'danger', text: 'danger', disabled: false},
  ] as const
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
