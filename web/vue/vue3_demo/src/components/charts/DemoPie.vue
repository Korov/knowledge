<template>
    <div ref="myChart" :style="{ width: '300px', height: '300px' }"></div>
</template>

<script lang="ts">
import * as echarts from 'echarts'
import { ref, onMounted } from "vue";

export default {
    setup() {
        const myChart = ref<HTMLElement>()
        const myCharts = ref<any>()
        onMounted(() => {
            // 绘制图表
            myCharts.value = echarts.init(myChart.value!)
            myCharts.value.setOption({
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '40',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            { value: 1048, name: 'Search Engine' },
                            { value: 735, name: 'Direct' },
                            { value: 580, name: 'Email' },
                            { value: 484, name: 'Union Ads' },
                            { value: 300, name: 'Video Ads' }
                        ]
                    }
                ]
            });
        })
        return {
            myChart
        }
    }
}
</script>

<style>
</style>