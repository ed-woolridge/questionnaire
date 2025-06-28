<template>
  <AdminLayout>
    <div class="questionnaire-list">
      <el-card class="main-card">
        <div class="page-header">
          <h2>问卷管理</h2>
          <div class="header-actions">
            <el-button type="primary" class="add-btn" @click="goEdit()">新建问卷</el-button>
            <el-button type="success" class="export-btn" @click="exportAllAnswers">导出全部答卷</el-button>
          </div>
        </div>
        <el-card class="table-card">
          <template #header>
            <div class="card-header">
              <span>问卷列表</span>
              <span class="table-info">共 {{ tableData.length }} 条记录</span>
            </div>
          </template>
          <el-table :data="tableData" border class="list-table">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="title" label="问卷标题" />
            <el-table-column prop="status" label="状态" />
            <el-table-column prop="createdAt" label="创建时间" />
            <el-table-column label="操作" width="160">
              <template #default="scope">
                <el-button size="small" type="primary" @click="goEdit(scope.row.id)">编辑</el-button>
                <el-button size="small" type="danger">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-card>
    </div>
  </AdminLayout>
</template>

<script setup>
import AdminLayout from '@/components/AdminLayout.vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const tableData = [
  { id: 1, title: '2024年游客问卷', status: '启用', createdAt: '2024-06-01' },
  { id: 2, title: '2023年游客问卷', status: '停用', createdAt: '2023-06-01' }
]
function goEdit(id) {
  router.push(`/admin/questionnaire/edit/${id || ''}`)
}
function exportAllAnswers() {
  window.open('/api/admin/answers/export')
}
</script>

<style scoped>
.questionnaire-list {
  padding: 20px;
}
.main-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #ebeef5;
}
.page-header h2 {
  margin: 0;
  color: #303133;
  font-size: 24px;
  font-weight: 600;
}
.header-actions {
  display: flex;
  gap: 12px;
}
.table-card {
  margin-bottom: 20px;
  border: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  color: #303133;
}
.table-info {
  font-size: 14px;
  color: #909399;
  font-weight: normal;
}
.list-table {
  font-size: 16px;
}
.add-btn, .export-btn {
  margin-bottom: 0;
}
</style> 