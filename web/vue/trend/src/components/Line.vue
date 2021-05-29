<template>
  <v-chart :option="option_line" class="chart"/>
  <button @click="getData">test</button>
</template>

<script>
import {use} from "echarts/core";
import {CanvasRenderer} from "echarts/renderers";
import {LineChart} from "echarts/charts";
import {DataZoomComponent, GridComponent, TitleComponent, ToolboxComponent, TooltipComponent} from 'echarts/components';
import VChart, {THEME_KEY} from "vue-echarts";
import {defineComponent} from "vue";
import axios from "axios";

use([TitleComponent, ToolboxComponent, TooltipComponent, GridComponent, DataZoomComponent, LineChart, CanvasRenderer]);

function getLegend() {
  return ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']
}

function getCategory() {
  return ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
}


var series = [
  {
    name: "邮件营销",
    type: "line",
    data: [120, 132, 101, 134, 90, 230, 210]
  },
  {
    name: '联盟广告',
    type: 'line',
    data: [220, 182, 191, 234, 290, 330, 310]
  },
  {
    name: '视频广告',
    type: 'line',
    data: [150, 232, 201, 154, 190, 330, 410]
  },
  {
    name: '直接访问',
    type: 'line',
    data: [320, 332, 301, 334, 390, 330, 320]
  },
  {
    name: '搜索引擎',
    type: 'line',
    data: [820, 932, 901, 934, 1290, 1330, 1320]
  }
]

export default defineComponent({
  name: "Line",
  components: {
    VChart
  },
  provide: {
    [THEME_KEY]: "dark"
  },
  data() {
    return {
      option_line: {
        title: {
          text: "Traffic Sources",
          left: "center"
        },
        toolbox: {
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
          }
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          orient: "vertical",
          left: "left",
          data: getLegend()
        },
        xAxis: {
          type: 'category',
          name: '日期',
          data: getCategory()
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '100%']
        },
        dataZoom: [{
          type: 'inside',
          start: 0,
          end: 100
        }, {
          start: 0,
          end: 100
        }],
        series: series
      }
    };
  },
  methods: {
    getData() {
      axios.get("/get/name/time").then((response) => {
        series = response.data.datas.map(item => {
          return {
            name: item.Name,
            type: 'line',
            data: item.Count
          }
        })
        this.option_line.series = series;
        this.option_line.xAxis.data = response.data.timepoint
      })
    }
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.chart {
  height: 400px;
}
</style>
