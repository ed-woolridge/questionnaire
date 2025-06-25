import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, 
  timeout: 50000
})

// 请求拦截器
request.interceptors.request.use(config => {
  // 可在此处统一加token等
  
  return config
}, error => {
  return Promise.reject(error)
})

// 响应拦截器
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
    ElMessage.error(error.response?.data?.message || error.message || '网络异常')
    return Promise.reject(error)
  }
)

export default request 