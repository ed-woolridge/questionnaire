<template>
  <div class="login-bg">
    <div class="login-overlay"></div>
    <el-card class="login-card">
      <div class="login-illustration">
        <svg width="160" height="100" viewBox="0 0 180 120" fill="none" xmlns="http://www.w3.org/2000/svg">
          <ellipse cx="90" cy="100" rx="70" ry="12" fill="#e0e7ef"/>
          <circle cx="60" cy="60" r="32" fill="#f8fafc" stroke="#409eff" stroke-width="3"/>
          <circle cx="120" cy="60" r="32" fill="#f8fafc" stroke="#f56c6c" stroke-width="3"/>
          <ellipse cx="60" cy="60" rx="8" ry="12" fill="#409eff" fill-opacity="0.15"/>
          <ellipse cx="120" cy="60" rx="8" ry="12" fill="#f56c6c" fill-opacity="0.15"/>
          <rect x="80" y="80" width="20" height="8" rx="4" fill="#409eff" fill-opacity="0.12"/>
        </svg>
      </div>
      <div class="login-title">管理员登录</div>
      <el-form :model="form" :rules="rules" ref="loginFormRef" class="login-form" label-position="top">
        <el-form-item label="用户名" prpo="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-btn" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { login as apiLogin } from '@/api'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = ref({ username: '', password: '' })
const loginFormRef = ref()
const rules = {
  username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
}

//登录表单校验
const login = async () => {
  loginFormRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      const { data } = await apiLogin(form.value)
      if (data.code === '200') {
        localStorage.setItem('admin', JSON.stringify(data.data))
        ElMessage.success('登录成功')
        router.push('/admin/questionnaires')
      } else {
        ElMessage.error(data.message || '登录失败')
      }
    } catch (error) {
      const msg = error.response?.data?.message || error.message || '登录失败，请检查网络连接'
      ElMessage.error(msg)
    }
  })
}
</script>

<style scoped>
.login-bg {
  min-height: 100vh;
  min-width: 100vw;
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0; left: 0;
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  background: linear-gradient(135deg, #e0e7ef 0%, #f8fafc 100%), url('https://cdn.jsdelivr.net/gh/konpa/devicon@master/icons/vuejs/vuejs-original.svg') no-repeat right bottom;
  background-size: cover, 220px;
}
.login-overlay {
  position: absolute;
  top: 0; left: 0; right: 0; bottom: 0;
  width: 100vw; height: 100vh;
  background: linear-gradient(120deg, rgba(64,158,255,0.08) 0%, rgba(245,108,108,0.10) 100%);
  z-index: 1;
  pointer-events: none;
}
.login-card {
  width: 400px;
  padding: 48px 32px 36px 32px;
  border-radius: 18px;
  box-shadow: 0 2px 32px #0002;
  text-align: center;
  position: relative;
  z-index: 2;
  background: rgba(255,255,255,0.95);
  backdrop-filter: blur(2px);
}
.login-illustration {
  margin-bottom: 8px;
  animation: float 2.5s ease-in-out infinite alternate;
}
@keyframes float {
  0% { transform: translateY(0); }
  100% { transform: translateY(-10px); }
}
.login-title {
  font-size: 26px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 24px;
  color: #409eff;
  letter-spacing: 2px;
}
.login-form {
  margin-top: 10px;
}
.login-btn {
  width: 100%;
  margin-top: 10px;
  font-size: 18px;
  height: 44px;
  border-radius: 10px;
  letter-spacing: 2px;
}
@media (max-width: 600px) {
  .login-card {
    width: 98vw;
    padding: 24px 6vw 18px 6vw;
  }
  .login-illustration svg {
    width: 100px;
    height: 60px;
  }
}
</style> 