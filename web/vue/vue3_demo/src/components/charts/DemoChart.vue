<template>
  <div id="main" style='height: 500px'>grgrrg</div>
</template>

<script>
import * as echarts from 'echarts'

//初始化
var myChart = echarts.init(document.getElementById('main'))
//设置参数
myChart.setOption({
  xAxis:{},
  yAxis:{},
  series:[]
})

export default {
  name: "DemoChart"
}
</script>

<style scoped>

</style>