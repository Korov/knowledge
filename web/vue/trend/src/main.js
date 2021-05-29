import { createApp } from 'vue'
import App from './App.vue'
import ECharts from "vue-echarts"
import { use } from "echarts/core"
import {router} from "@/router";
import axios from 'axios'
axios.defaults.baseURL = 'http://korov-linux.org:8180/v1'

import {
    CanvasRenderer
} from 'echarts/renderers'
import {
    BarChart, LinesChart
} from 'echarts/charts'
import {
    GridComponent,
    TooltipComponent
} from 'echarts/components'

use([
    CanvasRenderer,
    BarChart,
    LinesChart,
    GridComponent,
    TooltipComponent
])

createApp(App).use(router, axios).component('v-chart', ECharts).mount('#app')
