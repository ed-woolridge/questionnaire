<template>
  <AdminLayout>
    <div class="question-manage-container">
      <el-card class="main-card">
        <div class="page-header">
          <h2>问题管理</h2>
          <el-form :inline="true" :model="searchForm" class="search-form">
            <el-form-item label="问卷选择">
              <el-select v-model="searchForm.questionnaireId" placeholder="请选择问卷" @change="fetchQuestions">
                <el-option v-for="q in questionnaireList" :key="q.id" :label="q.title" :value="q.id" />
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <el-table :data="questionList" v-loading="loading" style="margin-bottom: 24px;">
          <el-table-column prop="content" label="题干" />
          <el-table-column prop="type" label="题型">
            <template #default="scope">
              <el-tag>{{ typeText(scope.row.type) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="选项" v-if="questionList.some(q => q.type === 'SINGLE_CHOICE' || q.type === 'MULTIPLE_CHOICE')">
            <template #default="scope">
              <div v-if="scope.row.type === 'SINGLE_CHOICE' || scope.row.type === 'MULTIPLE_CHOICE'">
                <div v-for="opt in scope.row.options" :key="opt.id" class="option-item">
                  {{ opt.content }}
                  <el-button type="text" size="small" @click="deleteOption(scope.row.id, opt.id)">删除</el-button>
                </div>
                <el-input v-model="newOption[scope.row.id]" size="small" placeholder="添加新选项" style="width: 180px; margin-top: 4px;" @keyup.enter="addOption(scope.row.id)" />
                <el-button type="primary" size="small" @click="addOption(scope.row.id)">添加</el-button>
              </div>
              <div v-else>--</div>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button type="danger" size="small" @click="deleteQuestion(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-divider />
        <el-form :model="form" label-width="80px" class="add-form">
          <el-form-item label="题干">
            <el-input v-model="form.content" placeholder="请输入题干" />
          </el-form-item>
          <el-form-item label="题型">
            <el-select v-model="form.type" placeholder="请选择题型">
              <el-option label="单选题" value="SINGLE_CHOICE" />
              <el-option label="多选题" value="MULTIPLE_CHOICE" />
              <el-option label="填空题" value="TEXT" />
              <el-option label="数字题" value="NUMBER" />
            </el-select>
          </el-form-item>
          <el-form-item label="选项" v-if="form.type === 'SINGLE_CHOICE' || form.type === 'MULTIPLE_CHOICE'">
            <el-input v-model="newAddOption" placeholder="输入选项内容，回车添加" @keyup.enter="addNewOption" style="width: 220px;" />
            <el-button type="primary" @click="addNewOption">添加</el-button>
            <div class="option-list">
              <el-tag v-for="(opt, idx) in form.options" :key="idx" closable @close="removeNewOption(idx)">{{ opt }}</el-tag>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addQuestion">添加问题</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </AdminLayout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import AdminLayout from '../../components/AdminLayout.vue'
import { getQuestionnaireList, getQuestions, createQuestionWithOptions, deleteQuestion as deleteQuestionApi, getOptions, createOption, deleteOption as deleteOptionApi } from '@/api'

const questionnaireList = ref([])
const questionList = ref([])
const loading = ref(false)
const searchForm = reactive({ questionnaireId: '' })
const form = reactive({ content: '', type: '', options: [] })
const newAddOption = ref('')
const newOption = reactive({})

const fetchQuestionnaireList = async () => {
  try {
    const { data } = await getQuestionnaireList()
    questionnaireList.value = data
  } catch (e) {
    ElMessage.error('获取问卷列表失败')
  }
}

const fetchQuestions = async () => {
  if (!searchForm.questionnaireId) return
  loading.value = true
  try {
    const { data } = await getQuestions(searchForm.questionnaireId)
    // 获取每题选项
    for (const q of data) {
      if (q.type === 'SINGLE_CHOICE' || q.type === 'MULTIPLE_CHOICE') {
        q.options = await getOptions(q.id)
      } else {
        q.options = []
      }
    }
    questionList.value = data
  } catch (e) {
    ElMessage.error('获取问题失败')
  } finally {
    loading.value = false
  }
}

const addQuestion = async () => {
  if (!searchForm.questionnaireId) {
    ElMessage.warning('请先选择问卷')
    return
  }
  if (!form.content || !form.type) {
    ElMessage.warning('请填写题干和题型')
    return
  }
  const payload = {
    questionnaireId: searchForm.questionnaireId,
    content: form.content,
    type: form.type,
    options: form.type === 'SINGLE_CHOICE' || form.type === 'MULTIPLE_CHOICE' ? form.options : undefined
  }
  try {
    await createQuestionWithOptions(payload)
    ElMessage.success('添加成功')
    form.content = ''
    form.type = ''
    form.options = []
    fetchQuestions()
  } catch (e) {
    ElMessage.error('添加失败')
  }
}

const deleteQuestion = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该问题吗？', '提示', { type: 'warning' })
    await deleteQuestionApi(id)
    ElMessage.success('删除成功')
    fetchQuestions()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('删除失败')
  }
}

const addNewOption = () => {
  if (!newAddOption.value) return
  form.options.push(newAddOption.value)
  newAddOption.value = ''
}
const removeNewOption = (idx) => {
  form.options.splice(idx, 1)
}

const addOption = async (questionId) => {
  if (!newOption[questionId]) return
  try {
    await createOption({ questionId, content: newOption[questionId] })
    ElMessage.success('添加选项成功')
    newOption[questionId] = ''
    fetchQuestions()
  } catch (e) {
    ElMessage.error('添加选项失败')
  }
}
const deleteOption = async (questionId, optionId) => {
  try {
    await deleteOptionApi(optionId)
    ElMessage.success('删除选项成功')
    fetchQuestions()
  } catch (e) {
    ElMessage.error('删除选项失败')
  }
}

const typeText = (type) => {
  switch (type) {
    case 'SINGLE_CHOICE': return '单选题'
    case 'MULTIPLE_CHOICE': return '多选题'
    case 'TEXT': return '填空题'
    case 'NUMBER': return '数字题'
    default: return type
  }
}

onMounted(async () => {
  await fetchQuestionnaireList()
})
</script>

<style scoped>
.question-manage-container {
  padding: 20px;
}
.main-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 24px;
}
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}
.search-form {
  margin-bottom: 0;
}
.option-item {
  display: flex;
  align-items: center;
  margin-bottom: 4px;
}
.option-list {
  margin-top: 8px;
}
.add-form {
  margin-top: 32px;
}
</style> 