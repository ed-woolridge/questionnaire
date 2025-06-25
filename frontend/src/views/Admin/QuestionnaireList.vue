<template>
  <AdminLayout>
    <div class="list-title">问卷管理</div>
    <el-button type="primary" class="add-btn" @click="goEdit()">新建问卷</el-button>
    <el-table :data="tableData" border class="list-table" style="margin-top: 18px;">
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
</script>

<style scoped>
.list-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 18px;
}
.add-btn {
  margin-bottom: 12px;
}
.list-table {
  font-size: 16px;
}
</style> 