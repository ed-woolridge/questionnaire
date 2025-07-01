<template>
  <AdminLayout>
    <div class="detail-container">
      <div class="detail-header">
        <h2>问卷提交详情</h2>
        <el-button @click="$router.back()">返回</el-button>
      </div>

      <el-card class="detail-card" v-loading="loading">
        <template #header>
          <div class="card-header">
            <span>基本信息</span>
          </div>
        </template>
        
        <el-descriptions :column="2" border>
          <el-descriptions-item label="提交ID">{{ detail.id }}</el-descriptions-item>
          <el-descriptions-item label="问卷标题">{{ detail.questionnaireTitle }}</el-descriptions-item>
          <el-descriptions-item label="用户手机">{{ detail.userPhone }}</el-descriptions-item>
          <el-descriptions-item label="提交状态">
            <el-tag :type="getStatusType(detail.status)">
              {{ getStatusText(detail.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="提交时间">{{ detail.submitTime }}</el-descriptions-item>
          <el-descriptions-item label="完成时间">{{ detail.completeTime || '-' }}</el-descriptions-item>
        </el-descriptions>
      </el-card>

      <el-card class="answers-card">
        <template #header>
          <div class="card-header">
            <span>答案详情</span>
          </div>
        </template>
        
        <div v-if="answers.length === 0" class="no-data">
          暂无答案数据
        </div>
        
        <div v-else class="answers-list">
          <div v-for="answer in answers" :key="answer.sortOrder" class="answer-item">
            <div class="question-title">
              {{ answer.questionTitle }}
            </div>
            <div class="answer-content">
              <el-tag type="info" size="small">答案：</el-tag>
              <span class="answer-text">{{ answer.answerContent || '无答案' }}</span>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </AdminLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import AdminLayout from '@/components/AdminLayout.vue'
import { getAnswerSheetDetail } from '@/api'

const route = useRoute()
const loading = ref(false)
const detail = ref({})
const answers = ref([])

onMounted(async () => {
  await loadDetail()
})

async function loadDetail() {
  try {
    loading.value = true
    const { data } = await getAnswerSheetDetail(route.params.id)
    detail.value = data.answerSheet || {}
    answers.value = (data.answers || []).filter(a => a.sortOrder > 3)
  } catch (error) {
    ElMessage.error('加载详情失败')
  } finally {
    loading.value = false
  }
}

function getStatusType(status) {
  switch (status) {
    case 'COMPLETED':
      return 'success'
    case 'IN_PROGRESS':
      return 'warning'
    default:
      return 'info'
  }
}

function getStatusText(status) {
  switch (status) {
    case 'COMPLETED':
      return '已完成'
    case 'IN_PROGRESS':
      return '进行中'
    default:
      return '未知'
  }
}
</script>

<style scoped>
.detail-container {
  padding: 20px;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.detail-header h2 {
  margin: 0;
  color: #303133;
}

.detail-card {
  margin-bottom: 24px;
}

.answers-card {
  margin-bottom: 24px;
}

.card-header {
  font-weight: bold;
  color: #303133;
}

.no-data {
  text-align: center;
  color: #909399;
  padding: 40px 0;
}

.answers-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.answer-item {
  border: 1px solid #ebeef5;
  border-radius: 8px;
  padding: 16px;
  background: #fafafa;
}

.question-title {
  font-weight: bold;
  color: #303133;
  margin-bottom: 12px;
  font-size: 16px;
}

.question-number {
  color: #409eff;
  margin-right: 8px;
}

.answer-content {
  display: flex;
  align-items: center;
  gap: 8px;
}

.answer-text {
  color: #606266;
  font-size: 14px;
}
</style> 