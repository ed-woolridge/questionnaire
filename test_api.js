// 简单的API测试脚本
const axios = require('axios');

const baseURL = 'http://localhost:8080';

async function testQuestionnaireAPI() {
  try {
    console.log('测试问卷列表API...');
    const response = await axios.get(`${baseURL}/admin/questionnaire/list`);
    console.log('问卷列表响应:', response.data);
    
    if (response.data && response.data.code === '200') {
      console.log('✅ 问卷列表API测试成功');
      console.log('问卷数量:', response.data.data.length);
    } else {
      console.log('❌ 问卷列表API测试失败');
    }
  } catch (error) {
    console.error('❌ 问卷列表API测试失败:', error.message);
  }
}

async function testQuestionAPI() {
  try {
    console.log('\n测试问题列表API...');
    const response = await axios.get(`${baseURL}/admin/question/list/1`);
    console.log('问题列表响应:', response.data);
    
    if (response.data && response.data.code === '200') {
      console.log('✅ 问题列表API测试成功');
      console.log('问题数量:', response.data.data.length);
    } else {
      console.log('❌ 问题列表API测试失败');
    }
  } catch (error) {
    console.error('❌ 问题列表API测试失败:', error.message);
  }
}

async function runTests() {
  console.log('开始API测试...\n');
  await testQuestionnaireAPI();
  await testQuestionAPI();
  console.log('\nAPI测试完成');
}

runTests(); 