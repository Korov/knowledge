import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import { createWebHistory, createRouter } from 'vue-router'


const history = createWebHistory()
const router = createRouter({
    history, // 路由模式
    routes: [
      {
        // 页面逻辑
        path: '/nav',
        name: 'nav',
        component: () => import('./components/nav/MyMenu')
      }
    ]
  })

const app = createApp(App)

app.use(ElementPlus, {
    locale: zhCn,
})
// 注入路由
app.use(router)
app.mount('#app')
