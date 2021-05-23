import Vue from 'vue'
import VueRouter from 'vue-router'
import Trend from "@/components/Trend";

Vue.use(VueRouter)

const routes = [
    { path: '/', component: Trend }
]

const router = new VueRouter({
    routes
})

export default router