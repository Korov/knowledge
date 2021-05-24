<template>
  <div id="app">
    <p>
      <router-link to="/trend">Trend</router-link>
    </p>
    <p>
      <router-link to="/line">Line</router-link>
    </p>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>

</style>
