import request from '@/utils/request'
import type { LoginData, LoginResult } from './types'

export function login(data: LoginData) {
  return request<LoginResult>({
    url: '/api/auth/login',
    method: 'post',
    data
  })
}

export function getUserInfo() {
  return request({
    url: '/api/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/auth/logout',
    method: 'post'
  })
} 