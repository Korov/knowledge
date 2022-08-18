import { createWebHistory, createRouter } from 'vue-router'

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