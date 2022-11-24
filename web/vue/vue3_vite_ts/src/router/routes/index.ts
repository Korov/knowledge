import type { RouteRecordRaw } from 'vue-router'

/**
 * 路由配置
 * @description 所有路由都在这里集中管理
 */
const routes: RouteRecordRaw[] = [
  /**
   * 首页
   */
  {
    path: '/',
    name: 'home',
    redirect: '/charts/pie'
  },
  /**
   * 子路由示例
   */
  {
    path: '/charts/pie',
    name: 'demoPie',
    component: () => import('@/components/charts/DemoPie.vue')
  },
  {
    path: '/charts/demo',
    name: 'demoChart',
    component: () => import('@/components/charts/DemoChart.vue')
  },
]

export default routes
