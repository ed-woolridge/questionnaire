<template>
  <div class="dashboard-bg">
    <el-card class="dashboard-card">
      <div class="dashboard-title">后台管理首页</div>
      <div class="dashboard-desc">欢迎使用地方接待国内游客抽样调查问卷管理系统</div>
    </el-card>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
function logout() {
  localStorage.removeItem('admin')
  router.push('/login')
}
</script>

<style scoped>
.dashboard-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #f8fafc 0%, #e0e7ef 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.dashboard-card {
  width: 500px;
  padding: 48px 32px 36px 32px;
  border-radius: 18px;
  box-shadow: 0 2px 16px #0001;
  text-align: center;
}
.dashboard-title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 16px;
}
.dashboard-desc {
  font-size: 16px;
  color: #666;
}
</style> 