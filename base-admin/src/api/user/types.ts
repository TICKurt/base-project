export interface LoginData {
  username: string
  password: string
}

export interface LoginUser {
  userId: number
  username: string
  nickname: string
  userType: number
  permissions: string[]
  roleCodes: string[]
}

export interface LoginResponseData {
  token: string
  user: LoginUser
}

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

export type LoginResult = ApiResponse<LoginResponseData>

export interface UserInfo {
  id: number
  username: string
  name: string
  avatar: string
  roles: string[]
} 