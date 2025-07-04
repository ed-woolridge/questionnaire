import { createRouter, createWebHistory } from 'vue-router'
import QuestionnaireFill from '@/views/User/QuestionnaireFill.vue'
import Success from '@/views/User/Success.vue'
import Login from '@/views/Login.vue'
import QuestionnaireList from '@/views/Admin/QuestionnaireList.vue'
import AnswerSheetList from '@/views/Admin/AnswerSheetList.vue'
import QuestionnaireStats from '@/views/Admin/QuestionnaireStats.vue'
import QuestionManage from '@/views/Admin/QuestionManage.vue'
import NotFound from '@/views/NotFound.vue'
import Unauthorized from '../views/Unauthorized.vue'

const routes = [
  { path: '/', redirect: '/fill' },
  { path: '/admin/login', component: Login },
  { path: '/fill', component: QuestionnaireFill },
  { path: '/success', component: Success },
  { path: '/unauthorized', component: Unauthorized},

  // 管理员相关路由
  {
    path: '/admin',
    children: [
      { path: 'questionnaires', component: QuestionnaireList },
      { path: 'answer-sheets', component: AnswerSheetList },
      { path: 'stats', component: QuestionnaireStats },
      { path: 'questions', component: QuestionManage },
    ]
  },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 