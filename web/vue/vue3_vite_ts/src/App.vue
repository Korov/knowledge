<template>
  <div class="common-layout">
    <el-container>
      <el-header class="my-header">
        <MyMenu :menuData="menuData"></MyMenu>
      </el-header>
      <el-row>
        <el-button type="primary" @click="changeNav">Change Nav</el-button>
      </el-row>
      <el-main class="my-main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts">
import MyMenu from '@/components/nav/MyMenu.vue'
import { computed, defineComponent, provide, ref } from 'vue'
import httpCommon from '@/http-common'
import MenuData from '@/my-interface'

export default defineComponent({
  components: {
    MyMenu,
  },
  setup() {
    let menuData = ref<MenuData[]>([
      {
        id: '1',
        title: 'nav1',
        subNav: [
          {
            id: '11',
            title: 'subNav1',
            path: '/charts/pie',
          },
        ],
      },
      {
        id: '2',
        title: 'nav2',
        subNav: [
          {
            id: '21',
            title: 'subNav1',
            path: '/charts/demo',
          },
        ],
      },
    ])

    const changeNav = () => {
      console.log(`nav title:${menuData.value[0].title}`)
      menuData.value[0].title = 'changed nav'
      menuData.value[0].id = '31'
      console.log(`nav title:${menuData.value[0].title}`)
      httpCommon.get('/hello?name=korov').then((response: { data: any }) => {
        console.log(`axiox test:${response.data}`)
      })
    }
    provide(
      'menuData',
      computed(() => menuData.value)
    )
    return {
      menuData,
      changeNav,
    }
  },
})
</script>

<style>
.my-header {
  background: goldenrod;
}

.my-main {
  background: aliceblue;
}
</style>
