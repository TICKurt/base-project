<template>
  <div class="error-page">
    <div class="error-code">404</div>
    <div class="error-desc">抱歉，你访问的页面不存在</div>
    <el-button type="primary" @click="goHome">返回首页</el-button>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}
</script>

<style lang="scss" scoped>
.error-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f5f7fa;
  
  .error-code {
    font-size: 120px;
    font-weight: bold;
    color: #409eff;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
  }
  
  .error-desc {
    font-size: 24px;
    color: #606266;
    margin-bottom: 30px;
  }
}
</style> 