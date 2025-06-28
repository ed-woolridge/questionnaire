<template>
  <AdminLayout>
    <div class="answer-sheet-list">
      <el-card class="main-card">
        <div class="page-header">
          <h2>用户提交问卷管理</h2>
          <div class="header-actions">
            <el-button type="danger" @click="batchDelete" :disabled="selectedIds.length === 0">
              批量删除
            </el-button>
          </div>
        </div>

        <!-- 统计卡片 -->
        <div class="stats-cards">
          <el-card class="stat-card">
            <div class="stat-content">
              <div class="stat-number">{{ stats.totalCount }}</div>
              <div class="stat-label">总提交数</div>
            </div>
          </el-card>
          <el-card class="stat-card">
            <div class="stat-content">
              <div class="stat-number">{{ stats.todayCount }}</div>
              <div class="stat-label">今日提交</div>
            </div>
          </el-card>
          <el-card class="stat-card">
            <div class="stat-content">
              <div class="stat-number">{{ stats.completedCount }}</div>
              <div class="stat-label">已完成</div>
            </div>
          </el-card>·
        </div>

        <!-- 搜索区域 -->
        <el-card class="search-card">
          <template #header>
            <div class="card-header">
              <span>搜索筛选</span>
            </div>
          </template>
          <el-form :model="searchForm" inline>
            <el-form-item label="问卷ID">
              <el-input v-model="searchForm.questionnaireId" placeholder="请输入问卷ID" clearable />
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
                <el-option label="填写中" value="IN_PROGRESS" />
                <el-option label="已完成" value="COMPLETED" />
                <el-option label="已终止" value="TERMINATED" />
              </el-select>
            </el-form-item>
            <el-form-item label="提交时间">
              <el-date-picker
                v-model="searchForm.dateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="search">搜索</el-button>
              <el-button @click="resetSearch">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <!-- 数据表格 -->
        <el-card class="table-card">
          <template #header>
            <div class="card-header">
              <span>问卷列表</span>
              <span class="table-info">共 {{ pagination.total }} 条记录</span>
            </div>
          </template>
          <el-table
            :data="tableData"
            v-loading="loading"
            @selection-change="handleSelectionChange"
            stripe
          >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="questionnaireId" label="问卷ID" width="100" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">
                  {{ getStatusText(row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="submitTime" label="提交时间" width="180">
              <template #default="{ row }">
                {{ formatDate(row.submitTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="duration" label="填写时长(分钟)" width="120" />
            <el-table-column prop="surveyorName" label="调查员" width="120" />
            <el-table-column prop="surveyLocation" label="调查地点" />
            <el-table-column label="操作" width="200" fixed="right">
              <template #default="{ row }">
                <el-button type="primary" size="small" @click="viewDetail(row.id)">
                  查看详情
                </el-button>
                <el-button type="danger" size="small" @click="deleteItem(row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页 -->
          <div class="pagination-wrapper">
            <el-pagination
              v-model:current-page="pagination.page"
              v-model:page-size="pagination.size"
              :page-sizes="[10, 20, 50, 100]"
              :total="pagination.total"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-card>
      </el-card>

      <!-- 详情对话框 -->
      <el-dialog
        v-model="detailVisible"
        title="问卷详情"
        width="80%"
        :before-close="handleCloseDetail"
      >
        <div v-if="detailData" class="detail-content">
          <div class="detail-section">
            <h3>基本信息</h3>
            <el-descriptions :column="3" border>
              <el-descriptions-item label="答卷ID">{{ detailData.answerSheet.id }}</el-descriptions-item>
              <el-descriptions-item label="问卷ID">{{ detailData.answerSheet.questionnaireId }}</el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag :type="getStatusType(detailData.answerSheet.status)">
                  {{ getStatusText(detailData.answerSheet.status) }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="提交时间">{{ formatDate(detailData.answerSheet.submitTime) }}</el-descriptions-item>
              <el-descriptions-item label="填写时长">{{ detailData.answerSheet.duration }}分钟</el-descriptions-item>
              <el-descriptions-item label="调查员">{{ detailData.answerSheet.surveyorName || '-' }}</el-descriptions-item>
              <el-descriptions-item label="调查地点" :span="3">
                {{ detailData.answerSheet.surveyProvince }} {{ detailData.answerSheet.surveyCity }} {{ detailData.answerSheet.surveyDistrict }}
                {{ detailData.answerSheet.surveyLocation }}
              </el-descriptions-item>
            </el-descriptions>
          </div>

          <div class="detail-section">
            <h3>答案详情</h3>
            <div v-for="(item, index) in detailData.answers" :key="index" class="answer-item">
              <div class="question-title">
                <strong>{{ index + 1 }}. {{ item.questionTitle }}</strong>
              </div>
              <div class="answer-content">
                <span class="answer-label">答案：</span>
                <span class="answer-value">
                  {{ item.answerContent }}
                </span>
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>
  </AdminLayout>
</template>

<script setup>
import AdminLayout from '@/components/AdminLayout.vue'
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAnswerSheets, getAnswerSheetDetail, deleteAnswerSheet, batchDeleteAnswerSheets, getAnswerSheetStats } from '@/api'

// 响应式数据
const loading = ref(false)
const tableData = ref([])
const selectedIds = ref([])
const detailVisible = ref(false)
const detailData = ref(null)

// 搜索表单
const searchForm = reactive({
  questionnaireId: '',
  status: '',
  dateRange: []
})

// 分页
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

// 统计信息
const stats = reactive({
  totalCount: 0,
  todayCount: 0,
  completedCount: 0
})

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      size: pagination.size,
      ...searchForm
    }
    
    if (searchForm.dateRange && searchForm.dateRange.length === 2) {
      params.startTime = searchForm.dateRange[0]
      params.endTime = searchForm.dateRange[1]
    }
    
    const { data } = await getAnswerSheets(params)
    if (data.code === '200') {
      tableData.value = data.data.records
      pagination.total = data.data.total
    }
  } catch (error) {
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

// 获取统计信息
const fetchStats = async () => {
  try {
    const { data } = await getAnswerSheetStats({
      questionnaireId: searchForm.questionnaireId
    })
    if (data.code === '200') {
      Object.assign(stats, data.data)
    }
  } catch (error) {
    console.error('获取统计信息失败', error)
  }
}

// 搜索
const search = () => {
  pagination.page = 1
  fetchData()
  fetchStats()
}

// 重置搜索
const resetSearch = () => {
  Object.assign(searchForm, {
    questionnaireId: '',
    status: '',
    dateRange: []
  })
  search()
}

// 分页处理
const handleSizeChange = (size) => {
  pagination.size = size
  pagination.page = 1
  fetchData()
}

const handleCurrentChange = (page) => {
  pagination.page = page
  fetchData()
}

// 选择处理
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 查看详情
const viewDetail = async (id) => {
  try {
    const { data } = await getAnswerSheetDetail(id)
    if (data.code === '200') {
      detailData.value = data.data
      detailVisible.value = true
    }
  } catch (error) {
    ElMessage.error('获取详情失败')
  }
}

// 删除单个
const deleteItem = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这条记录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const { data } = await deleteAnswerSheet(id)
    if (data.code === '200') {
      ElMessage.success('删除成功')
      fetchData()
      fetchStats()
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 批量删除
const batchDelete = async () => {
  try {
    await ElMessageBox.confirm(`确定要删除选中的 ${selectedIds.value.length} 条记录吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const { data } = await batchDeleteAnswerSheets(selectedIds.value)
    if (data.code === '200') {
      ElMessage.success('批量删除成功')
      selectedIds.value = []
      fetchData()
      fetchStats()
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量删除失败')
    }
  }
}

// 关闭详情对话框
const handleCloseDetail = () => {
  detailVisible.value = false
  detailData.value = null
}

// 工具函数
const getStatusType = (status) => {
  const statusMap = {
    'IN_PROGRESS': 'warning',
    'COMPLETED': 'success',
    'TERMINATED': 'danger'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'IN_PROGRESS': '填写中',
    'COMPLETED': '已完成',
    'TERMINATED': '已终止'
  }
  return statusMap[status] || status
}

const formatDate = (date) => {
  if (!date) return '-'
  return new Date(date).toLocaleString()
}

const formatAnswer = (answer, question) => {
  if (answer.textAnswer) {
    return answer.textAnswer
  }
  if (answer.numberAnswer) {
    return answer.numberAnswer.toString()
  }
  if (answer.optionId) {
    return `选项ID: ${answer.optionId}`
  }
  return '-'
}

// 初始化
onMounted(() => {
  fetchData()
  fetchStats()
})
</script>

<style scoped>
.answer-sheet-list {
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

.stats-cards {
  display: flex;
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  flex: 1;
  border: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
}

.stat-content {
  text-align: center;
  padding: 16px;
}

.stat-number {
  font-size: 28px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 8px;
}

.stat-label {
  color: #606266;
  font-size: 14px;
}

.search-card {
  margin-bottom: 24px;
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

.table-card {
  margin-bottom: 20px;
  border: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  padding-top: 16px;
  border-top: 1px solid #ebeef5;
}

.detail-content {
  max-height: 600px;
  overflow-y: auto;
}

.detail-section {
  margin-bottom: 30px;
}

.detail-section h3 {
  margin-bottom: 15px;
  color: #303133;
  border-bottom: 2px solid #409eff;
  padding-bottom: 8px;
}

.answer-item {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  background-color: #fafafa;
}

.question-title {
  margin-bottom: 10px;
  color: #303133;
}

.answer-content {
  display: flex;
  align-items: flex-start;
}

.answer-label {
  font-weight: bold;
  color: #606266;
  margin-right: 10px;
  min-width: 40px;
}

.answer-value {
  color: #303133;
  flex: 1;
  word-break: break-all;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .stats-cards {
    flex-direction: column;
    gap: 12px;
  }
  
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .header-actions {
    width: 100%;
    display: flex;
    justify-content: flex-end;
  }
}
</style> 