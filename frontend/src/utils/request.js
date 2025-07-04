import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, 
  timeout: 50000
})

// 请求拦截器
request.interceptors.request.use(config => {

  // 获取token
  const admin = JSON.parse(localStorage.getItem('admin') || '{}')
  const token = admin.token

  //校验token
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token
  }

  return config
  
}, error => {
  return Promise.reject(error)
})

request.interceptors.response.use(
  response => {
    // 兼容后端返回格式
    if (response.data && response.data.success === false) {
      ElMessage.error(response.data.message || '请求失败')
      return Promise.reject(response.data)
    }
    return response
  },
  error => {
    if (error.response && error.response.status === 401) {

      ElMessage.error('登录已过期或无权限，请重新登录')
      localStorage.removeItem('admin')

      // 跳转到登录页
      window.location.href = '/admin/login'
    } else {
      ElMessage.error(error.response?.data?.message || error.message || '网络异常')
    }
    return Promise.reject(error)
  }
)

export default request 