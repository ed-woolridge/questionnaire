import { createRouter, createWebHistory } from 'vue-router'
import QuestionnaireFill from '@/views/User/QuestionnaireFill.vue'
import Success from '@/views/User/Success.vue'
import Login from '@/views/Login.vue'
import QuestionnaireList from '@/views/Admin/QuestionnaireList.vue'
import QuestionnaireEdit from '@/views/Admin/QuestionnaireEdit.vue'
import AnswerSheetList from '@/views/Admin/AnswerSheetList.vue'
import QuestionnaireStats from '@/views/Admin/QuestionnaireStats.vue'
import QuestionManage from '@/views/Admin/QuestionManage.vue'

const routes = [
  { path: '/', redirect: '/fill' },
  { path: '/admin/login', component: Login },
  { path: '/fill', component: QuestionnaireFill },
  { path: '/success', component: Success },
  {
    path: '/admin',
    children: [
      { path: 'questionnaires', component: QuestionnaireList },
      { path: 'questionnaire/edit/:id?', component: QuestionnaireEdit },
      { path: 'answer-sheets', component: AnswerSheetList },
      { path: 'stats', component: QuestionnaireStats },
      { path: 'questions', component: QuestionManage },
    ]
  }
]

export default createRouter({
  history: createWebHistory(),
  routes
}) 