import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import { getToken } from '@/utils/auth'

// 创建 axios 实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 50000,
  headers: { 'Content-Type': 'application/json;charset=utf-8' }
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    const token = getToken()
    if (token && config.headers) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const { code, msg, data } = response.data
    if (code === 200) {
      return data
    }
    // 处理业务错误
    ElMessage.error(msg || '系统出错')
    return Promise.reject(new Error(msg || '系统出错'))
  },
  (error) => {
    // 处理 HTTP 网络错误
    let message = ''
    const status = error.response?.status
    switch (status) {
      case 401:
        message = '未授权，请登录'
        // TODO: 处理登出逻辑
        break
      case 403:
        message = '拒绝访问'
        break
      case 404:
        message = '请求地址错误'
        break
      case 500:
        message = '服务器故障'
        break
      default:
        message = '网络连接故障'
    }
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

// 导出封装的请求方法
export default function request<T = any>(config: AxiosRequestConfig): Promise<T> {
  return service(config) as unknown as Promise<T>
} 