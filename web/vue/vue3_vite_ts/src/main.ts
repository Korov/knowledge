import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from '@/App.vue'
import router from '@/router'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/dist/index.css'
// 全局样式
import '@less/global.less'

createApp(App)
  .use(ElementPlus, {
    locale: zhCn,
  })
  .use(createPinia()) // 启用 Pinia
  .use(router)
  .mount('#app')
