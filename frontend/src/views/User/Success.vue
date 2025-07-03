<template>
  <div class="success-bg">
    <div class="success-overlay"></div>
    <el-card class="success-card">
      <div class="success-illustration">
        <svg width="160" height="100" viewBox="0 0 180 120" fill="none" xmlns="http://www.w3.org/2000/svg">
          <ellipse cx="90" cy="100" rx="70" ry="12" fill="#e0e7ef"/>
          <circle cx="60" cy="60" r="32" fill="#f8fafc" stroke="#67c23a" stroke-width="3"/>
          <circle cx="120" cy="60" r="32" fill="#f8fafc" stroke="#67c23a" stroke-width="3"/>
          <ellipse cx="60" cy="60" rx="8" ry="12" fill="#67c23a" fill-opacity="0.15"/>
          <ellipse cx="120" cy="60" rx="8" ry="12" fill="#67c23a" fill-opacity="0.15"/>
          <rect x="80" y="80" width="20" height="8" rx="4" fill="#67c23a" fill-opacity="0.12"/>
        </svg>
      </div>
      <div class="success-icon fade-in">
        <el-icon size="80" color="#67c23a">
          <CircleCheckFilled />
        </el-icon>
      </div>
      <div class="success-title fade-in">提交成功</div>
      <div class="success-desc">
        感谢您的参与，祝您旅途愉快！
      </div>
      <div class="success-actions">
        <el-button type="primary" size="large" @click="goHome">
          返回首页
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { CircleCheckFilled } from '@element-plus/icons-vue'
const router = useRouter()
const goHome = () => router.push('/')
</script>

<style scoped>
.success-bg {
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
.success-overlay {
  position: absolute;
  top: 0; left: 0; right: 0; bottom: 0;
  width: 100vw; height: 100vh;
  background: linear-gradient(120deg, rgba(103,194,58,0.08) 0%, rgba(64,158,255,0.10) 100%);
  z-index: 1;
  pointer-events: none;
}
.success-card {
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
.success-illustration {
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
.success-icon {
  margin-bottom: 24px;
}
.success-title {
  font-size: 28px;
  font-weight: bold;
  color: #67c23a;
  margin-bottom: 16px;
}
.success-desc {
  font-size: 16px;
  color: #666;
  margin-bottom: 32px;
  line-height: 1.5;
}
.success-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}
.success-actions .el-button {
  min-width: 120px;
  border-radius: 8px;
}
@media (max-width: 600px) {
  .success-card {
    width: 98vw;
    padding: 24px 6vw 18px 6vw;
  }
  .success-illustration svg {
    width: 100px;
    height: 60px;
  }
}
</style> 