<template>
  <div class="app-wrapper">
    <div class="main-container">
      <router-view />
    </div>
  </div>
</template>

<script lang="ts" setup>
// 布局组件逻辑
</script>

<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  height: 100%;
  width: 100%;
}

.main-container {
  min-height: 100%;
  transition: margin-left 0.28s;
  position: relative;
}
</style> 