<template>
  <div class="login-bg">
    <el-card class="login-card">
      <div class="login-title">管理员登录</div>
      <el-form :model="form" class="login-form" label-position="top">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" placeholder="请输入密码" size="large" show-password />
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

const login = async () => {
  const { data } = await apiLogin(form.value)
  if (data.code === '200') {
    localStorage.setItem('admin', JSON.stringify(data.data))
    ElMessage.success('登录成功')
    router.push('/admin/questionnaires')
  } else {
    ElMessage.error(data.message)
  }
}
</script>

<style scoped>
.login-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #f8fafc 0%, #e0e7ef 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-card {
  width: 400px;
  padding: 48px 32px 36px 32px;
  border-radius: 18px;
  box-shadow: 0 2px 16px #0001;
}
.login-title {
  font-size: 26px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 24px;
}
.login-form {
  margin-top: 10px;
}
.login-btn {
  width: 100%;
  font-size: 18px;
  height: 44px;
  border-radius: 22px;
  letter-spacing: 2px;
}
</style> 