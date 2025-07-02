// 检查是否已登录
export const isLoggedIn = () => {
  const admin = localStorage.getItem('admin')
  if (!admin) return false
  
  try {
    const adminData = JSON.parse(admin)
    return adminData && adminData.id
  } catch (error) {
    return false
  }
}

// 获取管理员信息
export const getAdminInfo = () => {
  const admin = localStorage.getItem('admin')
  if (!admin) return null
  
  try {
    return JSON.parse(admin)
  } catch (error) {
    return null
  }
}

// 清除登录信息
export const clearAuth = () => {
  localStorage.removeItem('admin')
}

// 设置登录信息
export const setAuth = (adminData) => {
  localStorage.setItem('admin', JSON.stringify(adminData))
}