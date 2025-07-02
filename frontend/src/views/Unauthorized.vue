<template>
  <div class="unauth-bg">
    <div class="unauth-overlay"></div>
    <el-card class="unauth-card">
      <div class="unauth-illustration">
        <svg width="160" height="100" viewBox="0 0 180 120" fill="none" xmlns="http://www.w3.org/2000/svg">
          <ellipse cx="90" cy="100" rx="70" ry="12" fill="#e0e7ef"/>
          <circle cx="60" cy="60" r="32" fill="#f8fafc" stroke="#409eff" stroke-width="3"/>
          <circle cx="120" cy="60" r="32" fill="#f8fafc" stroke="#f56c6c" stroke-width="3"/>
          <ellipse cx="60" cy="60" rx="8" ry="12" fill="#409eff" fill-opacity="0.15"/>
          <ellipse cx="120" cy="60" rx="8" ry="12" fill="#f56c6c" fill-opacity="0.15"/>
          <rect x="80" y="80" width="20" height="8" rx="4" fill="#409eff" fill-opacity="0.12"/>
        </svg>
      </div>
      <div class="unauth-icon fade-in">
        <el-icon size="80" color="#f56c6c">
          <Lock />
        </el-icon>
      </div>
      <div class="unauth-title fade-in">访问被拒绝</div>
      <div class="unauth-desc">
        您没有权限访问此页面，请先登录管理员账户
      </div>
      <div class="unauth-actions">
        <el-button type="primary" size="large" @click="goToLogin">
          前往登录
        </el-button>
        <el-button size="large" @click="goBack">
          返回上页
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { Lock } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goToLogin = () => {
  router.push('/admin/login')
}

const goBack = () => {
  router.go(-1)
}
</script>

<style scoped>
.unauth-bg {
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
.unauth-overlay {
  position: absolute;
  top: 0; left: 0; right: 0; bottom: 0;
  width: 100vw; height: 100vh;
  background: linear-gradient(120deg, rgba(64,158,255,0.08) 0%, rgba(245,108,108,0.10) 100%);
  z-index: 1;
  pointer-events: none;
}
.unauth-card {
  width: 480px;
  padding: 48px 32px 36px 32px;
  border-radius: 18px;
  box-shadow: 0 2px 32px #0002;
  text-align: center;
  position: relative;
  z-index: 2;
  background: rgba(255,255,255,0.95);
  backdrop-filter: blur(2px);
}
.unauth-illustration {
  margin-bottom: 8px;
  animation: float 2.5s ease-in-out infinite alternate;
}
@keyframes float {
  0% { transform: translateY(0); }
  100% { transform: translateY(-10px); }
}
.fade-in {
  animation: fadeIn 1.2s;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: translateY(0); }
}
.unauth-icon {
  margin-bottom: 24px;
}
.unauth-title {
  font-size: 28px;
  font-weight: bold;
  color: #f56c6c;
  margin-bottom: 16px;
}
.unauth-desc {
  font-size: 16px;
  color: #666;
  margin-bottom: 32px;
  line-height: 1.5;
}
.unauth-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}
.unauth-actions .el-button {
  min-width: 120px;
  border-radius: 8px;
}
@media (max-width: 600px) {
  .unauth-card {
    width: 98vw;
    padding: 24px 6vw 18px 6vw;
  }
  .unauth-illustration svg {
    width: 100px;
    height: 60px;
  }
}
</style>
