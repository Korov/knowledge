import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import router from './router'


const app = createApp(App)

app.use(ElementPlus, {
    locale: zhCn,
})
// 注入路由
app.use(router)
app.mount('#app')
