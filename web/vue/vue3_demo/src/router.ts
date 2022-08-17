import {createWebHistory, createRouter} from 'vue-router'

const history = createWebHistory()
const routes = [
    {
        path: '/',
        name: 'home',
        redirect: '/nav'
    },
    {
        // 页面逻辑
        path: '/nav',
        name: 'nav',
        component: () => import('@/App.vue')
    },
    {
        // 页面逻辑
        path: '/charts/demo',
        name: 'demoChart',
        component: () => import('@/components/charts/DemoChart.vue')
    }
]
const router = createRouter({
    history, // 路由模式
    routes
})

export default router