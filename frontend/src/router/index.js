import { createRouter, createWebHistory } from 'vue-router'
import QuestionnaireFill from '@/views/User/QuestionnaireFill.vue'
import Success from '@/views/User/Success.vue'
import Login from '@/views/Login.vue'
import Dashboard from '@/views/Admin/Dashboard.vue'
import QuestionnaireList from '@/views/Admin/QuestionnaireList.vue'
import QuestionnaireEdit from '@/views/Admin/QuestionnaireEdit.vue'
import Export from '@/views/Admin/Export.vue'

const routes = [
  { path: '/', redirect: '/verifyPhone' },
  { path: '/login', component: Login },
  { path: '/fill', component: QuestionnaireFill },
  { path: '/success', component: Success },
  {
    path: '/admin',
    children: [
      { path: 'questionnaires', component: QuestionnaireList },
      { path: 'questionnaire/edit/:id?', component: QuestionnaireEdit },
      { path: 'export', component: Export }
    ]
  }
]

export default createRouter({
  history: createWebHistory(),
  routes
}) 