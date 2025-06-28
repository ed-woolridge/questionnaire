import request from '@/utils/request'

export const login = (data) => request.post('/api/admin/login', data)
export const getQuestionnaireList = () => request.get('/admin/questionnaire/list')
export const createQuestionnaire = (data) => request.post('/admin/questionnaire/create', data)
export const updateQuestionnaire = (data) => request.put('/admin/questionnaire/update', data)
export const deleteQuestionnaire = (id) => request.delete(`/admin/questionnaire/delete/${id}`)
export const getQuestions = (questionnaireId) => request.get(`/admin/question/list/${questionnaireId}`)
export const createQuestion = (data) => request.post('/admin/question/create', data)
export const updateQuestion = (data) => request.put('/admin/question/update', data)
export const deleteQuestion = (id) => request.delete(`/admin/question/delete/${id}`)
export const getOptions = async (questionId) => {
  const res = await request.get(`/api/option/list/${questionId}`)
  return res.data?.data || []
}
export const createOption = (data) => request.post('/admin/option/create', data)
export const updateOption = (data) => request.put('/admin/option/update', data)
export const deleteOption = (id) => request.delete(`/admin/option/delete/${id}`)
export const exportQuestionnaire = (id) => request.get(`/admin/questionnaire/${id}/export`, { responseType: 'blob' })

export const submitAnswer = (data) => {
  return request({
    url: '/questionnaire/submit',
    method: 'post',
    data
  })
}

// 手机号验证相关接口
export const sendSmsCode = (phone, type) => {
  return request({
    url: '/api/auth/sendCode',
    method: 'post',
    params: { phone, type }
  })
}

export const verifySmsCode = (phone, code, type) => {
  return request({
    url: '/api/auth/verifyCode',
    method: 'post',
    params: { phone, code, type }
  })
}

export const loginByPhone = (phone) => {
  return request({
    url: '/api/auth/loginByPhone',
    method: 'post',
    params: { phone }
  })
}

// 管理员相关接口
export const adminLogin = (data) => {
  return request({
    url: '/api/admin/login',
    method: 'post',
    params: data
  })
}

// 用户提交问卷管理接口
export const getAnswerSheets = (params) => {
  return request({
    url: '/api/admin/answerSheets',
    method: 'get',
    params
  })
}

export const getAnswerSheetDetail = (id) => {
  return request({
    url: `/api/admin/answerSheets/${id}`,
    method: 'get'
  })
}

export const deleteAnswerSheet = (id) => {
  return request({
    url: `/api/admin/answerSheets/${id}`,
    method: 'delete'
  })
}

export const batchDeleteAnswerSheets = (ids) => {
  return request({
    url: '/api/admin/answerSheets/batch',
    method: 'delete',
    data: ids
  })
}

export const getAnswerSheetStats = (params) => {
  return request({
    url: '/api/admin/answerSheets/stats',
    method: 'get',
    params
  })
} 