import {createWebHistory, createRouter, createWebHashHistory} from 'vue-router'

// 创建https://example.com/#/bar类型的路由
// const hasHistory = createWebHashHistory()
const history = createWebHistory()
const routes = [
    {
        path: '/',
        name: 'home',
        redirect: '/charts/pie'
    },
    {
        path: '/charts/pie',
        name: 'demoPie',
        component: () => import('@/components/charts/DemoPie.vue')
    },
    {
        path: '/charts/demo',
        name: 'demoChart',
        component: () => import('@/components/charts/DemoChart.vue')
    }
]
const router = createRouter({
    history,
    routes
})

export default router