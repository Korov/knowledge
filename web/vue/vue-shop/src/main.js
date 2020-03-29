import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
// 导入字体库
import './assets/font/iconfont.css'
// 导入全局样式表
import './assets/css/global.css'

import axios from 'axios'
axios.defaults.baseURL = 'http://localhost:8080/service/user'
axios.interceptors.request.use(config => {
  config.headers.Authorization = 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.' +
    'eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJ6aGFuZ3NhbiIsInNjb3BlIjpbIlJPTEVfQURN' +
    'SU4iLCJST0xFX1VTRVIiLCJST0xFX0FQSSJdLCJleHAiOjE1ODU0OTg1NTMsImF1dGhvcml0aWV' +
    'zIjpbInAxIiwicDIiLCJwMyJdLCJqdGkiOiIwZjJhN2NkZC1jN2NmLTRkYTMtODA5MS0wN2VjMW' +
    'M1OTAzMTUiLCJjbGllbnRfaWQiOiJjMSJ9.2qXi-zg9LXFpsLXlx1qFuEq-Y9vwKoetGg2kOkKDz0w'
  // config.headers.Access
  return config
})
Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
