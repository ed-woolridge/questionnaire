import request from '@/utils/request'

export const login = (data) => request.post('/admin/login', data)
export const getQuestionnaireList = () => request.get('/admin/questionnaire/list')
export const createQuestionnaire = (data) => request.post('/admin/questionnaire/create', data)
export const updateQuestionnaire = (data) => request.put('/admin/questionnaire/update', data)
export const deleteQuestionnaire = (id) => request.delete(`/admin/questionnaire/delete/${id}`)
export const getQuestions = (questionnaireId) => request.get(`/admin/question/list/${questionnaireId}`)
export const createQuestion = (data) => request.post('/admin/question/create', data)
export const updateQuestion = (data) => request.put('/admin/question/update', data)
export const deleteQuestion = (id) => request.delete(`/admin/question/delete/${id}`)
export const getOptions = (questionId) => request.get(`/admin/option/list/${questionId}`)
export const createOption = (data) => request.post('/admin/option/create', data)
export const updateOption = (data) => request.put('/admin/option/update', data)
export const deleteOption = (id) => request.delete(`/admin/option/delete/${id}`)
export const exportQuestionnaire = (id) => request.get(`/admin/questionnaire/${id}/export`, { responseType: 'blob' })

export const submitAnswer = (data) => {
  return request({
    url: '/questionnaire/submit',
    method: 'post',
    data: data
  })
}

export const sendCode = (phone, type) => request.post('/auth/sendCode', { phone, type })
export const verifyCode = (phone, code, type) => request.post('/auth/verifyCode', { phone, code, type }) 