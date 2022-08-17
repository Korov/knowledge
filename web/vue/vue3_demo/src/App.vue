<template>
  <div class="common-layout">
    <el-container>
      <el-header class="my-header">
        <MyMenu :menuData="menuData"></MyMenu>
      </el-header>
      <el-main class="my-main">
        <el-button type="primary" @click="changeNav">Change Nav</el-button>
        <div ref="myChart" :style="{ width: '300px', height: '300px' }"></div>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts">
import MyMenu from '@/components/nav/MyMenu.vue'
import {computed, provide, ref} from "vue";
import * as echarts from 'echarts'
import {onMounted} from 'vue'

export default {
  components: {
    MyMenu
  },
  setup() {
    const myChart = ref<HTMLElement>();
    const myCharts = ref<any>();
    onMounted(() => {
      // 绘制图表
      myCharts.value = echarts.init(myChart.value!);
      myCharts.value.setOption({
        title: {text: "总用户量"},
        tooltip: {},
        xAxis: {
          data: ["12-3", "12-4", "12-5", "12-6", "12-7", "12-8"],
        },
        yAxis: {},
        series: [
          {
            name: "用户量",
            type: "line",
            data: [5, 20, 36, 10, 10, 20],
          },
        ],
      });
    })

    let menuData = ref([{
      id: "1",
      title: 'nav1',
      subNav: [{
        id: "11",
        title: 'subNav1',
        router: "nav"
      }, {
        id: "12",
        title: 'subNav2',
        router: "/charts/demo",
        subNav: [{
          title: 'subNav21'
        }]
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
    }])

    const changeNav = () => {
      console.log(`nav title:${menuData.value[0].title}`)
      menuData.value[0].title = "changed nav"
      menuData.value[0].id = "31"
      console.log(`nav title:${menuData.value[0].title}`)
    }
    provide(
        "menuData", computed(() => menuData.value)
    )
    return {
      menuData,
      changeNav,
      myChart
    }
  }
}


</script>

<style>
.my-header {
  background: goldenrod;
}

.my-main {
  background: aliceblue;
}
</style>
