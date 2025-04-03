<template>
  <div class="login-container">
    <el-form
      ref="loginFormRef"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">后台管理系统</h3>
      </div>

      <el-form-item prop="username">
        <el-input
          ref="usernameRef"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
          :prefix-icon="User"
        />
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          ref="passwordRef"
          v-model="loginForm.password"
          :type="passwordVisible ? 'text' : 'password'"
          placeholder="密码"
          name="password"
          tabindex="2"
          autocomplete="on"
          :prefix-icon="Lock"
          :suffix-icon="passwordVisible ? View : Hide"
          @click-suffix-icon="passwordVisible = !passwordVisible"
          @keyup.enter="handleLogin"
        />
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click="handleLogin"
      >
        登录
      </el-button>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, View, Hide } from '@element-plus/icons-vue'
import type { FormInstance } from 'element-plus'
import { useUserStore } from '@/store/modules/user'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref<FormInstance>()
const loading = ref(false)
const passwordVisible = ref(false)

const loginForm = reactive({
  username: 'admin',
  password: '123456'
})

const loginRules = {
  username: [{ required: true, trigger: 'blur', message: '请输入用户名' }],
  password: [{ required: true, trigger: 'blur', message: '请输入密码' }]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return

  try {
    loading.value = true
    await loginFormRef.value.validate()
    await userStore.login(loginForm)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error: any) {
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  width: 100%;
  background-color: #2d3a4b;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  .login-form {
    width: 400px;
    max-width: 100%;
    padding: 35px;
    border-radius: 6px;
    background: #fff;
    
    .title-container {
      position: relative;
      .title {
        font-size: 26px;
        color: #333;
        margin: 0 auto 30px;
        text-align: center;
        font-weight: bold;
      }
    }

    :deep(.el-input) {
      height: 48px;
      .el-input__wrapper {
        padding: 0 15px;
        background: #fff;
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        
        input {
          height: 46px;
          color: #333;
          
          &::placeholder {
            color: #999;
          }
        }
      }
    }

    .el-form-item {
      margin-bottom: 20px;
    }
  }
}
</style> 