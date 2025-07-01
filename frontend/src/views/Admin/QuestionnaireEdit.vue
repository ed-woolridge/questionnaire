<template>
  <AdminLayout>
    <el-card class="main-card">
      <div class="edit-title">问卷编辑</div>
      <el-form :model="form" label-width="auto" label-position="top" class="edit-form">
        <el-form-item label="问卷标题">
          <el-input v-model="form.title" placeholder="请输入问卷标题" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" active-text="启用" inactive-text="停用" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="save-btn" @click="save">保存</el-button>
          <el-button @click="$router.back()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </AdminLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { createQuestionnaire, updateQuestionnaire, getQuestionnaireList } from '@/api'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import AdminLayout from '@/components/AdminLayout.vue'

const route = useRoute()
const router = useRouter()
const isEdit = !!route.params.id
const form = ref({ title: '', description: '', status: true })

onMounted(async () => {
  if (isEdit) {
    const { data } = await getQuestionnaireList()
    const q = data.find(q => q.id == route.params.id)
    if (q) Object.assign(form.value, q)
  }
})

async function save() {
  if (isEdit) {
    await updateQuestionnaire(form.value)
    ElMessage.success('更新成功')
  } else {
    await createQuestionnaire(form.value)
    ElMessage.success('创建成功')
  }
  router.push('/admin/questionnaires')
}
</script>

<style scoped>
.main-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.edit-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 18px;
}
.edit-form {
  margin-top: 10px;
}
.save-btn {
  width: 100%;
  font-size: 18px;
  height: 44px;
  border-radius: 22px;
  letter-spacing: 2px;
}
</style> 