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
    }
]
const router = createRouter({
    history, // 路由模式
    routes
})

export default router