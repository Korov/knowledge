<template>
  <v-chart :option="option_pipe" class="chart"/>
</template>

<script>
import {use} from "echarts/core";
import {CanvasRenderer} from "echarts/renderers";
import {PieChart} from "echarts/charts";
import {LegendComponent, TitleComponent, TooltipComponent} from "echarts/components";
import VChart, {THEME_KEY} from "vue-echarts";
import {defineComponent, ref} from "vue";

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent
]);

function getLegend() {
  return ["Direct", "Email", "Ad Networks", "Video Ads", "Search Engines"]
}

function getSeries() {
  return [
    {
      name: "Traffic Sources",
      type: "pie",
      radius: "55%",
      center: ["50%", "60%"],
      data: [
        {value: 335, name: "Direct"},
        {value: 310, name: "Email"},
        {value: 234, name: "Ad Networks"},
        {value: 135, name: "Video Ads"},
        {value: 1548, name: "Search Engines"}
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: "rgba(0, 0, 0, 0.5)"
        }
      }
    }
  ]
}

export default defineComponent({
  name: "Trend",
  components: {
    VChart
  },
  provide: {
    [THEME_KEY]: "dark"
  },
  setup: () => {
    const option_pipe = ref({
      title: {
        text: "Traffic Sources",
        left: "center"
      },
      tooltip: {
        trigger: "item",
        formatter: "{a} <br/>{b} : {c} ({d}%)"
      },
      legend: {
        orient: "vertical",
        left: "left",
        data: getLegend()
      },
      series: getSeries()
    });

    return {option_pipe};
  }
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.chart {
  height: 400px;
}
</style>
