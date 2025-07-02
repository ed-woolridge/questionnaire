<template>
  <AdminLayout>
    <div class="questionnaire-list">
      <el-card class="main-card">
        <div class="page-header">
          <h2>问卷管理</h2>
          <div class="header-actions">
            <el-button type="primary" class="add-btn" @click="openEditDialog()">新建问卷</el-button>
            <el-button type="success" class="export-btn" @click="exportAllAnswers">导出全部问卷</el-button>
          </div>
        </div>
        <el-card class="table-card">
          <template #header>
            <div class="card-header">
              <span>问卷列表</span>
              <span class="table-info">共 {{ tableData.length }} 条记录</span>
            </div>
          </template>
          
          <!-- 加载状态 -->
          <div v-if="loading" class="loading-container">
            <el-skeleton :rows="5" animated />
          </div>
          
          <!-- 数据表格 -->
          <el-table v-else :data="tableData" border class="list-table" v-loading="loading">
            <el-table-column prop="id" align="center" label="ID" width="80" />
            <el-table-column prop="title" align="center" label="问卷标题" />
            <el-table-column prop="type" align="center" label="问卷类型" width="120">
              <template #default="scope">
                <el-tag v-if="scope.row.type === 'TOURISM_SURVEY'" type="success">旅游调查</el-tag>
                <el-tag v-else type="info">{{ scope.row.type }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" align="center" label="状态" width="100">
              <template #default="scope">
                <el-tag v-if="scope.row.status === 'DRAFT'" type="info">草稿</el-tag>
                <el-tag v-else-if="scope.row.status === 'PUBLISHED'" type="success">已发布</el-tag>
                <el-tag v-else-if="scope.row.status === 'CLOSED'" type="danger">已关闭</el-tag>
                <el-tag v-else type="warning">{{ scope.row.status }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" align="center" label="创建时间" width="180">
              <template #default="scope">
                {{ formatDate(scope.row.createTime) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="200">
              <template #default="scope">
                <el-button size="small" type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          
          <!-- 空状态 -->
          <el-empty v-if="!loading && tableData.length === 0" description="暂无问卷数据" />
        </el-card>
      </el-card>

      <!-- 弹窗表单 -->
      <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑问卷' : '新建问卷'" width="800px" style="text-align: center;" @close="resetForm">
        <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
          <el-form-item label="问卷标题" prop="title">
            <el-input v-model="form.title" />
          </el-form-item>
          <el-form-item label="问卷描述" prop="description">
            <el-input v-model="form.description" type="textarea" />
          </el-form-item>
          <el-form-item label="问卷类型" prop="type">
            <el-select v-model="form.type" placeholder="请选择类型">
              <el-option label="旅游调查" value="TOURISM_SURVEY" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择状态">
              <el-option label="草稿" value="DRAFT" />
              <el-option label="已发布" value="PUBLISHED" />
              <el-option label="已关闭" value="CLOSED" />
            </el-select>
          </el-form-item>
          <el-form-item label="有效期" required>
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="YYYY-MM-DD HH:mm:ss"
              style="width: 100%;"
            />
          </el-form-item>
          <el-form-item label="创建人ID" prop="createBy">
            <el-input v-model="form.createBy" />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="onSubmit">保存</el-button>
        </template>
      </el-dialog>
    </div>
  </AdminLayout>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import AdminLayout from '@/components/AdminLayout.vue'
import { getQuestionnaireList, deleteQuestionnaire, createQuestionnaire, updateQuestionnaire } from '@/api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)

const form = reactive({
  id: '',
  title: '',
  description: '',
  type: '',
  status: '',
  validStartTime: '',
  validEndTime: '',
  createBy: ''
})

const dateRange = ref([])

const rules = {
  title: [{ required: true, message: '请输入问卷标题', trigger: 'blur' }],
  type: [{ required: true, message: '请选择问卷类型', trigger: 'change' }],
  status: [{ required: true, message: '请选择问卷状态', trigger: 'change' }]
}

// 获取当前管理员ID（假设 localStorage 里有 admin）
function getCurrentAdminId() {
  try {
    const admin = JSON.parse(localStorage.getItem('admin'))
    return admin?.id || ''
  } catch {
    return ''
  }
}

// 打开弹窗（新建/编辑）
function openEditDialog(row) {
  if (row) {
    isEdit.value = true
    Object.assign(form, row)
    dateRange.value = [form.validStartTime, form.validEndTime]
    // 回显创建人ID
    form.createBy = row.createBy || ''
  } else {
    isEdit.value = false
    resetForm()
    // 新建时自动填充创建人ID
    form.createBy = getCurrentAdminId()
  }
  dialogVisible.value = true
}

// 重置表单
function resetForm() {
  form.id = ''
  form.title = ''
  form.description = ''
  form.type = ''
  form.status = ''
  form.validStartTime = ''
  form.validEndTime = ''
  form.createBy = ''
  dateRange.value = []
  formRef.value && formRef.value.clearValidate()
}

// 保存
function onSubmit() {
  formRef.value.validate(async (valid) => {
    if (!valid) return
    if (dateRange.value.length === 2) {
      form.validStartTime = dateRange.value[0]
      form.validEndTime = dateRange.value[1]
    }
    try {
      if (isEdit.value) {
        await updateQuestionnaire(form)
        ElMessage.success('更新成功')
      } else {
        await createQuestionnaire(form)
        ElMessage.success('创建成功')
      }
      dialogVisible.value = false
      fetchQuestionnaireList()
    } catch (e) {
      ElMessage.error('保存失败')
    }
  })
}

// 获取问卷列表加载页面时触发
const fetchQuestionnaireList = async () => {
  loading.value = true
  try {
    const response = await getQuestionnaireList()
    if (response.data && response.data.code === '200') {
      tableData.value = response.data.data || []
      console.log('赋值后的 tableData:', tableData.value)
    } else {
      ElMessage.error(response.data?.message || '获取问卷列表失败')
    }
  } catch (error) {
    console.error('获取问卷列表失败:', error)
    ElMessage.error('获取问卷列表失败，请检查网络连接')
  } finally {
    loading.value = false
  }
}

// 删除问卷
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除问卷"${row.title}"吗？删除后无法恢复。`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    const response = await deleteQuestionnaire(row.id)
    if (response.data && response.data.code === '200') {
      ElMessage.success('删除成功')
      await fetchQuestionnaireList() // 重新获取列表
    } else {
      ElMessage.error(response.data?.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除问卷失败:', error)
      ElMessage.error('删除失败，请重试')
    }
  }
}

// 导出全部问卷
const exportAllAnswers = () => {
  window.open('/api/admin/answers/export')
}

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 页面加载时获取数据
onMounted(() => {
  fetchQuestionnaireList()
})

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
.loading-container {
  padding: 20px;
}
</style> 