<template>
  <div class="admin-layout">
    <el-container style="min-height: 100vh;">
      <el-aside :width="collapsed ? '64px' : '210px'" class="sidebar" :class="{ collapsed }">
        <div class="sidebar-inner">
          <div class="logo" v-if="!collapsed">问卷后台</div>
          <el-menu :default-active="$route.path" router class="menu" :collapse="collapsed">
            <el-menu-item index="/admin/questionnaires">
              <el-icon><Document /></el-icon>
              <template #title>问卷管理</template>
            </el-menu-item>

            <el-menu-item index="/admin/answer-sheets">
              <el-icon><List /></el-icon>
              <template #title>提交管理</template>
            </el-menu-item>

            <el-menu-item index="/admin/stats">
              <el-icon><Data /></el-icon>
              <template #title>统计分析</template>
            </el-menu-item>

            <el-menu-item index="/admin/questions">
              <el-icon><Document /></el-icon>
              <template #title>问题管理</template>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>
      <el-container>
        <el-header class="header">
          <div class="header-left">
            <el-icon class="collapse-btn" @click="collapsed = !collapsed">
              <Expand v-if="collapsed" />
              <Fold v-else />
            </el-icon>
            <div class="logo-header">问卷后台</div>
          </div>
          <div class="header-title">地方接待国内游客抽样调查问卷管理系统</div>
          <div class="user-info">
            <el-icon style="margin-right:6px;"><UserFilled /></el-icon>
            <span class="username">{{ username }}</span>
            <el-button type="text" @click="logout">退出登录</el-button>
          </div>
        </el-header>
        <el-main class="main">
          <slot />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Document, Download, Fold, Expand, UserFilled, List } from '@element-plus/icons-vue'
const router = useRouter()
const collapsed = ref(false)
const username = computed(() => {
  const admin = localStorage.getItem('admin')
  if (!admin) return '未登录'
  try {
    const obj = JSON.parse(admin)
    return obj.username || obj.name || '管理员'
  } catch {
    return '管理员'
  }
})
function logout() {
  localStorage.removeItem('admin')
  router.push('/admin/login')
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
  background: linear-gradient(135deg, #f8fafc 0%, #e0e7ef 100%);
}
.sidebar {
  background: #fff;
  box-shadow: 2px 0 8px #0001;
  min-height: 100vh;
  padding-top: 0;
  position: relative;
  transition: width 0.3s cubic-bezier(.4,0,.2,1);
  display: flex;
  flex-direction: column;
  align-items: center;
}
.sidebar-inner {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.logo {
  font-size: 22px;
  font-weight: bold;
  color: #409eff;
  text-align: center;
  margin: 24px 0 16px 0;
  letter-spacing: 2px;
  transition: font-size 0.3s, margin 0.3s;
}
.sidebar.collapsed .logo {
  font-size: 16px;
  margin: 12px 0 8px 0;
}
.menu {
  border-right: none;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  box-shadow: 0 2px 8px #0001;
  height: 64px;
}
.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}
.logo-header {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
  letter-spacing: 2px;
  margin-left: 4px;
}
.collapse-btn {
  font-size: 22px;
  color: #409eff;
  cursor: pointer;
  transition: color 0.2s;
}
.collapse-btn:hover {
  color: #66b1ff;
}
.header-title {
  font-size: 18px;
  font-weight: 500;
  color: #333;
}
.user-info {
  display: flex;
  align-items: center;
  font-size: 15px;
  color: #666;
}
.username {
  margin-right: 16px;
  font-weight: 500;
}
.main {
  /* 上右下左 */
  padding: 2px 2px 2px 2px;
  background: transparent;
  min-height: 600px;
}
</style> 