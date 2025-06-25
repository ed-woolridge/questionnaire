<template>
  <AdminLayout>
    <div class="export-title">数据导出</div>
    <el-button type="primary" class="export-btn">导出全部数据</el-button>
  </AdminLayout>
</template>

<script setup>
import AdminLayout from '@/components/AdminLayout.vue'
import { ref, onMounted } from 'vue'
import { getQuestionnaireList, exportQuestionnaire } from '@/api'
import { ElMessage } from 'element-plus'

const list = ref([])
const selectedId = ref()
onMounted(async () => {
  const { data } = await getQuestionnaireList()
  list.value = data
})

async function exportExcel() {
  const { data } = await exportQuestionnaire(selectedId.value)
  const blob = new Blob([data], { type: 'application/vnd.ms-excel' })
  const url = window.URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = '问卷数据.xlsx'
  a.click()
  window.URL.revokeObjectURL(url)
  ElMessage.success('导出成功')
}
</script>

<style scoped>
.export-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 18px;
}
.export-btn {
  width: 100%;
  font-size: 18px;
  height: 44px;
  border-radius: 22px;
  letter-spacing: 2px;
}
</style> 