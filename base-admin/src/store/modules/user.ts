import { defineStore } from 'pinia'
import { login as loginApi } from '@/api/user'
import { setToken, removeToken } from '@/utils/auth'
import { LoginData } from '@/api/user/types'

interface UserInfo {
  userId: number
  username: string
  nickname: string
  userType: number
  permissions: string[]
  roleCodes: string[]
}

export const useUserStore = defineStore('user', {
  state: () => {
    return {
      token: '',
      userInfo: null as UserInfo | null,
      roles: [] as string[],
      permissions: [] as string[]
    }
  },
  actions: {
    async login(loginData: LoginData) {
      try {
        const response = await loginApi(loginData)
        if (response.code === 200 && response.data) {
          const { token, user } = response.data
          this.token = token
          this.userInfo = user
          this.roles = user.roleCodes || []
          this.permissions = user.permissions || []
          setToken(token)
        } else {
          throw new Error(response.message || '登录失败')
        }
      } catch (error) {
        removeToken()
        throw error
      }
    },
    
    logout() {
      this.token = ''
      this.userInfo = null
      this.roles = []
      this.permissions = []
      removeToken()
    },

    resetToken() {
      this.token = ''
      this.userInfo = null
      this.roles = []
      this.permissions = []
      removeToken()
    }
  }
}) 