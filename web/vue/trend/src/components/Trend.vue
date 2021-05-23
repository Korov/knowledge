<template>
  <div class="trend">
    <h1>Trend</h1>
    <TrendChart :datasets="trendData" :grid="gridData" :labels="labelsData" :min="minNumber"
                interactive="true" @mousemove="onMouseMove">
    </TrendChart>
  </div>
</template>

<script>

export default {
  name: 'Trend',
  data() {
    return {
      trendData: [
        {
          data: [10, 50, 20, 100, 40, 60, 80],
          smooth: true,
          fill: true
        },
        {
          data: [40, 50, 60, 500, 400, 600, 800],
          smooth: true,
          fill: true
        }
      ],
      gridData: {
        verticalLines: true,
        horizontalLines: true
      },
      labelsData: {
        xLabels: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
        yLabels: 5
      },
      minNumber: 0,
    }
  },
  methods: {
    onMouseMove(params) {
      this.tooltipData = params || null;
    }
  },
  props: {
    msg: String
  }
}
</script>
