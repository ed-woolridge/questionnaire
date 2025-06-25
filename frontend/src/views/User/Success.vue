<template>
  <div class="success-bg">
    <el-card class="success-card">
      <div class="icon-box">
        <el-icon size="60" color="#67c23a"><CircleCheckFilled /></el-icon>
      </div>
      <div class="success-title">提交成功</div>
      <div class="success-desc">感谢您的参与，祝您旅途愉快！</div>
      <el-button type="primary" class="back-btn" @click="goHome">返回首页</el-button>
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
  background: linear-gradient(135deg, #f8fafc 0%, #e0e7ef 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.success-card {
  width: 420px;
  padding: 48px 32px 36px 32px;
  border-radius: 18px;
  box-shadow: 0 2px 16px #0001;
  text-align: center;
}
.icon-box {
  margin-bottom: 18px;
}
.success-title {
  font-size: 28px;
  font-weight: bold;
  color: #67c23a;
  margin-bottom: 10px;
}
.success-desc {
  font-size: 16px;
  color: #666;
  margin-bottom: 28px;
}
.back-btn {
  width: 100%;
  font-size: 18px;
  height: 44px;
  border-radius: 22px;
  letter-spacing: 2px;
}
</style> 