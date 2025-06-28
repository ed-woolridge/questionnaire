<template>
  <div class="fill-bg">
    <el-card class="fill-card">
      <!-- 手机号验证区域 - 暂时注释掉，后期接入API时还原 -->
      <!--
      <div v-if="!isVerified" class="verify-section">
        <div class="verify-title">手机号验证</div>
        <div class="verify-desc">请先验证手机号，验证通过后才能填写问卷</div>
        
        <el-form :model="verifyForm" label-width="auto" class="verify-form">
          <el-form-item label="手机号">
            <el-input 
              v-model="verifyForm.phone" 
              placeholder="请输入手机号" 
              maxlength="11"
              :disabled="verifyForm.isSending"
            />
          </el-form-item>
          
          <el-form-item label="验证码">
            <div class="code-input-group">
              <el-input 
                v-model="verifyForm.code" 
                placeholder="请输入验证码" 
                maxlength="6"
                :disabled="verifyForm.isSending"
              />
              <el-button 
                type="primary" 
                :disabled="!verifyForm.phone || verifyForm.phone.length !== 11 || verifyForm.isSending"
                @click="sendCode"
                class="send-code-btn"
              >
                {{ verifyForm.countdown > 0 ? `${verifyForm.countdown}s` : '发送验证码' }}
              </el-button>
            </div>
          </el-form-item>
          
          <el-form-item>
            <el-button 
              type="primary" 
              @click="verifyCode" 
              :disabled="!verifyForm.phone || !verifyForm.code || verifyForm.isVerifying"
              class="verify-btn"
            >
              {{ verifyForm.isVerifying ? '验证中...' : '验证' }}
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      -->

      <!-- 问卷内容区域 -->
      <!-- <div v-else> -->
      <div>
        <div class="title">地方接待国内游客抽样调查问卷</div>
        <div class="desc">
          <div>表号：文旅统调5表</div>
          <div>制定机关：文化和旅游部</div>
          <div>批准机关：国家统计局</div>
          <div>有效期：2027年2月</div>
          <div style="margin: 8px 0;">欢迎您来我市游览！请您帮助填写这份调查问卷，在符合您情况的项目内填写或在□内用"√"表示。</div>
        </div>
        <el-form :model="form" label-width="auto" label-position="top" class="fill-form">

          <!-- 筛选题 -->
          <div class="section-title">筛选题</div>
          <el-form-item label="1. 您本次出游的时间，是否在6小时以上" required>
            <el-radio-group v-model="form.q1" class="radio-group-single">
              <el-radio label="A">A.是</el-radio>
              <el-radio label="B">B.否（如选"否"，请终止调查）</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="2. 您本次游览的地点，离您日常居住（生活）以及工作（上学）的社区（村庄），距离是否在10公里以上" required>
            <el-radio-group v-model="form.q2" class="radio-group-single">
              <el-radio label="A">A.是</el-radio>
              <el-radio label="B">B.否（如选"否"，请终止调查）</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="3. 您本次出游过程中，是否已经在我市其他县（区、市）或景区（点）等区域参与了本项调查" required>
            <el-radio-group v-model="form.q3" class="radio-group-single">
              <el-radio label="A">A.是（如选"是"，请终止调查）</el-radio>
              <el-radio label="B">B.否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-alert v-if="shouldTerminate" type="warning" title="您不符合本次调查条件，感谢您的参与！" show-icon style="margin-bottom: 20px;" />

          <template v-else>
            <div class="section-title">调查部分</div>
            <el-form-item label="1. 您目前的常住地是_______省（自治区、直辖市）________市（州、盟）______县（区、市、旗）（常住地是指连续居住生活 6 个月以上的地区）" required>
              <el-input v-model="form.address" placeholder="省/市/县（区/旗）" style="width: 200px;" />
            </el-form-item>
            <el-form-item label="2. 您的性别是：" required>
              <el-radio-group v-model="form.gender" class="radio-group-single">
                <el-radio v-for="opt in options.gender" :key="opt.id" :label="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="3. 您的年龄：" required>
              <el-radio-group v-model="form.age" class="radio-group-single">
                <el-radio v-for="opt in options.age" :key="opt.id" :label="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="4. 您日常居住生活的地方是：" required>
              <el-radio-group v-model="form.residence" class="radio-group-single">
                <el-radio v-for="opt in options.residence" :key="opt.id" :label="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="5. 您本次出游，主要目的是（单选）：" required>
              <el-radio-group v-model="form.purpose" class="radio-group-single">
                <el-radio v-for="opt in options.purpose" :key="opt.id" :label="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>

              <!--其他目的输入框-->
              <div v-if="form.purposeOther" class="input-indent input-inline" style="margin-top: 8px;">
                <span class="input-tip">请填写：</span>
                <el-input
                  v-model="form.purposeOther"
                  placeholder="请注明"
                  style="width: 240px;"
                  size="small"
                />
              </div>
            </el-form-item>
            <el-form-item label="6. 您本次出游，是否住宿过夜：" required>
              <el-radio-group v-model="form.stayOvernight" class="radio-group-single">
                <el-radio label="A">A.是</el-radio>
                <el-radio label="B">B.否（选"否"免填本题后续内容）</el-radio>
              </el-radio-group>
            </el-form-item>

            <!--只有选项为A时才会出现-->
            <template v-if="form.stayOvernight==='A'">
              <el-form-item label="如您出游期间住宿过夜，在我市共住___夜" required>
                <el-input v-model="form.nights" placeholder="请输入夜数" style="width: 120px;" />
              </el-form-item>
              <el-form-item label="主要选择的住宿设施（住宿时间最长）是（单选）" required>
                <el-radio-group v-model="form.accommodation" class="radio-group-single">
                  <el-radio v-for="opt in options.accommodation" :key="opt.id" :label="opt.id">
                  {{ opt.content }}
                </el-radio>
                </el-radio-group>
              </el-form-item>
            </template>

            <el-form-item label="7. 您本次出游，在我市游览的旅游景区（景点）类型有（可多选）：" required>
              <el-checkbox-group v-model="form.scenicTypes" class="checkbox-group-single">
                <el-checkbox v-for="opt in options.scenicTypes" :key="opt.id" :label="opt.id">
                  {{ opt.content }}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>

            <el-form-item label="8. 您本次出游，来我市游览的方式是（单选）：" required>
              <el-radio-group v-model="form.travelMode" class="radio-group-single">
                <el-radio label="A">A.单位（安排）组织</el-radio>
                <el-radio label="B">B.个人、家庭或亲朋结伴自由出行</el-radio>
                <el-radio label="C">C.通过旅行社组团出行</el-radio>
                <el-radio label="D">D.其他___（请注明）</el-radio>
              </el-radio-group>
              <el-input v-if="form.travelMode==='D'" v-model="form.travelModeOther" placeholder="请注明" style="margin-top: 8px; width: 300px;" />
            </el-form-item>

            <el-form-item label="9. 您本次出游，来我市游览的交通出行方式是（可多选）：" required>
              <el-checkbox-group v-model="form.transport" class="checkbox-group-single">
                <el-checkbox v-for="opt in options.transport" :key="opt.id" :label="opt.id">
                  {{ opt.content }}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>

            <div class="section-title">花费情况</div>
            <el-form-item label="您计划在本市游览___天，今天是您来本市的第___天。" required>
              <div class="input-single-row">
                <span class="input-label">计划天数</span>
                <el-input v-model="form.planDays"  placeholder="计划天数" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">第几天</span>
                <el-input v-model="form.currentDay" placeholder="第几天" style="width: 180px;" @input="validateNumber" />
              </div>
            </el-form-item>
            <el-form-item label="（1）您本人本次出游，交给旅行社或由单位支付的费用是:__元（如未发生此项费用支出，本项目请填'0'）。">
              <el-input v-model="form.agencyFee" placeholder="请输入金额" style="width: 180px;" @input="validateNumber" />
            </el-form-item>
            <el-form-item label="（2）您本人本次出游期间支出的交通费__元。（如此项费用已含在交给旅行社或由单位支付的费用中，本项目请填'0'）">
              <el-input v-model="form.transportFee" placeholder="请输入金额" style="width: 180px;" @input="validateNumber" />
            </el-form-item>
            <el-form-item label="其中：">
              <div class="input-single-row">
                <span class="input-label">A.飞机</span>
                <el-input v-model="form.feePlane" placeholder="飞机" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">B.火车</span>
                <el-input v-model="form.feeTrain" placeholder="火车" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">C.长途汽车</span>
                <el-input v-model="form.feeBus" placeholder="长途汽车" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">D.轮船</span>
                <el-input v-model="form.feeShip" placeholder="轮船" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">E.自驾车或租车</span>
                <el-input v-model="form.feeCar" placeholder="自驾/租车" style="width: 180px;" @input="validateNumber" />
              </div>
            </el-form-item>
            <el-form-item label="（3）除上述（1）（2）两项费用之外，截至此时，您本人在我市游览期间支付的费用是:__元。">
              <el-input v-model="form.otherFee" placeholder="请输入金额" style="width: 180px;" @input="validateNumber" />
            </el-form-item>
            <el-form-item label="其中：">
              <div class="input-single-row">
                <span class="input-label">A.餐饮</span>
                <el-input v-model="form.feeFood" placeholder="餐饮" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">B.住宿</span>
                <el-input v-model="form.feeHotel" placeholder="住宿" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">C.门票</span>
                <el-input v-model="form.feeTicket" placeholder="门票" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">D.购物</span>
                <el-input v-model="form.feeShopping" placeholder="购物" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">E.文创产品</span>
                <el-input v-model="form.feeCulture" placeholder="文创产品" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">F.文化艺术/娱乐</span>
                <el-input v-model="form.feeArt" placeholder="文化艺术/娱乐" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">G.医疗康养</span>
                <el-input v-model="form.feeMedical" placeholder="医疗康养" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">H.市内交通</span>
                <el-input v-model="form.feeCityTrans" placeholder="市内交通" style="width: 180px;" @input="validateNumber" />
              </div>
              <div class="input-single-row">
                <span class="input-label">I.其他</span>
                <el-input v-model="form.feeOther" placeholder="其他" style="width: 180px;" @input="validateNumber" />
              </div>
            </el-form-item>
            <el-form-item label="11. 您本次出游，在我省（自治区、直辖市）停留期间，共游览了___个市？">
              <el-input v-model="form.cityCount" placeholder="请输入数量" style="width: 120px;" @input="handleCityCountChange" />
            </el-form-item>
            <el-form-item label="分别是">
              <div class="input-row align-bottom">
                <div class="input-col" v-for="(city, idx) in form.cityList" :key="idx">
                  <el-input v-model="form.cityList[idx]" :placeholder="`市${idx+1}`" style="width: 180px;" />
                </div>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" @click="submit" class="submit-btn" :disabled="!isFormValid">提交问卷</el-button>
            </el-form-item>
          </template>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { submitAnswer, getOptions } from '@/api'
import { useRouter } from 'vue-router'

const router = useRouter()

// 选项数据对象
const options = ref({
  gender: [],
  age: [],
  residence: [],
  purpose: [],
  stayOvernight: [],
  accommodation: [],
  scenicTypes: [],
  travelMode: [],
  transport: []
})

 onMounted(async () => {
   // 查询对应的选项
   options.value.gender = await getOptions(5)//性别
   options.value.age = await getOptions(6)//年龄
   options.value.residence = await getOptions(7)//居住地
   options.value.purpose = await getOptions(8)//出行目的
   options.value.stayOvernight = await getOptions(9)//住宿天数
   options.value.accommodation = await getOptions(12)//
   options.value.scenicTypes = await getOptions(13)
   options.value.travelMode = await getOptions(14)//交通方式
   options.value.transport = await getOptions(16)//交通工具
 })

// 验证状态 - 暂时设为true，跳过验证
const isVerified = ref(true)

// 验证表单 - 暂时注释掉，后期接入API时还原
/*
const verifyForm = ref({
  phone: '',
  code: '',
  isSending: false,
  isVerifying: false,
  countdown: 0
})
*/

// 问卷表单
const form = ref({
  q1: '', q2: '', q3: '',
  address: '',
  gender: '', age: '', residence: '', purpose: '', purposeOther: '',
  stayOvernight: '', nights: '', accommodation: '',
  scenicTypes: [],
  travelMode: '', travelModeOther: '',
  transport: [],
  planDays: '', currentDay: '',
  agencyFee: '', transportFee: '',
  feePlane: '', feeTrain: '', feeBus: '', feeShip: '', feeCar: '',
  otherFee: '', feeFood: '', feeHotel: '', feeTicket: '', feeShopping: '', feeCulture: '',
  feeArt: '', feeMedical: '', feeCityTrans: '', feeOther: '',
  cityCount: '',
  cityList: []
})

const shouldTerminate = computed(() =>
  form.value.q1 === 'B' || form.value.q2 === 'B' || form.value.q3 === 'A'
)

// 发送验证码 - 暂时注释掉，后期接入API时还原
/*
const sendCode = async () => {
  if (!verifyForm.value.phone || verifyForm.value.phone.length !== 11) {
    ElMessage.warning('请输入正确的手机号')
    return
  }
  
  verifyForm.value.isSending = true
  try {
    const { data } = await sendSmsCode(verifyForm.value.phone, 'QUESTIONNAIRE')
    if (data.code === '200') {
      ElMessage.success('验证码发送成功')
      startCountdown()
    } else {
      ElMessage.error(data.message || '发送失败')
    }
  } catch (error) {
    ElMessage.error('发送失败，请重试')
  } finally {
    verifyForm.value.isSending = false
  }
}
*/

// 验证验证码 - 暂时注释掉，后期接入API时还原
/*
const verifyCode = async () => {
  if (!verifyForm.value.phone || !verifyForm.value.code) {
    ElMessage.warning('请输入手机号和验证码')
    return
  }
  
  verifyForm.value.isVerifying = true
  try {
    const { data } = await verifySmsCode(verifyForm.value.phone, verifyForm.value.code, 'QUESTIONNAIRE')
    if (data.code === '200') {
      ElMessage.success('验证成功')
      isVerified.value = true
      // 保存验证状态到本地存储
      localStorage.setItem('questionnaire_verified', 'true')
      localStorage.setItem('questionnaire_phone', verifyForm.value.phone)
    } else {
      ElMessage.error(data.message || '验证失败')
    }
  } catch (error) {
    ElMessage.error('验证失败，请重试')
  } finally {
    verifyForm.value.isVerifying = false
  }
}
*/

// 倒计时 - 暂时注释掉，后期接入API时还原
/*
const startCountdown = () => {
  verifyForm.value.countdown = 60
  const timer = setInterval(() => {
    verifyForm.value.countdown--
    if (verifyForm.value.countdown <= 0) {
      clearInterval(timer)
    }
  }, 1000)
}
*/

// 检查是否已验证 - 暂时注释掉，后期接入API时还原
/*
onMounted(() => {
  const verified = localStorage.getItem('questionnaire_verified')
  const phone = localStorage.getItem('questionnaire_phone')
  if (verified === 'true' && phone) {
    isVerified.value = true
    verifyForm.value.phone = phone
  }
})
*/

const isFormValid = computed(() => {
  if (!form.value.q1 || !form.value.q2 || !form.value.q3) return false;
  if (shouldTerminate.value) return false;
  if (!form.value.address || !form.value.gender || !form.value.age || !form.value.residence) return false;
  if (!form.value.purpose) return false;
  if (!form.value.stayOvernight) return false;
  if (form.value.stayOvernight === 'A' && (!form.value.nights || !form.value.accommodation)) return false;
  if (!form.value.scenicTypes.length) return false;
  if (!form.value.travelMode) return false;
  if (form.value.travelMode === 'D' && !form.value.travelModeOther) return false;
  if (!form.value.transport.length) return false;
  if (!form.value.planDays || !form.value.currentDay) return false;
  return true;
});

const submit = async () => {
  if (!isFormValid.value) {
    ElMessage.warning('请完整填写所有必填项');
    return;
  }
  if (shouldTerminate.value) {
    ElMessage.warning('您不符合本次调查条件，感谢您的参与！')
    return
  }
  // 组装所有问题答案
  const allAnswers = [
    { questionId: 1, answer: form.value.q1 },
    { questionId: 2, answer: form.value.q2 },
    { questionId: 3, answer: form.value.q3 },
    { questionId: 4, answer: form.value.address },
    { questionId: 5, answer: form.value.gender },
    { questionId: 6, answer: form.value.age },
    { questionId: 7, answer: form.value.residence },
    { questionId: 8, answer: form.value.purpose },
    { questionId: 9, answer: form.value.purposeOther },
    { questionId: 10, answer: form.value.stayOvernight },
    { questionId: 11, answer: form.value.nights },
    { questionId: 12, answer: form.value.accommodation },
    { questionId: 13, answer: form.value.scenicTypes },
    { questionId: 14, answer: form.value.travelMode },
    { questionId: 15, answer: form.value.travelModeOther },
    { questionId: 16, answer: form.value.transport },
    { questionId: 17, answer: form.value.planDays },
    { questionId: 18, answer: form.value.currentDay },
    { questionId: 19, answer: form.value.agencyFee },
    { questionId: 20, answer: form.value.transportFee },
    { questionId: 21, answer: form.value.feePlane },
    { questionId: 22, answer: form.value.feeTrain },
    { questionId: 23, answer: form.value.feeBus },
    { questionId: 24, answer: form.value.feeShip },
    { questionId: 25, answer: form.value.feeCar },
    { questionId: 26, answer: form.value.otherFee },
    { questionId: 27, answer: form.value.feeFood },
    { questionId: 28, answer: form.value.feeHotel },
    { questionId: 29, answer: form.value.feeTicket },
    { questionId: 30, answer: form.value.feeShopping },
    { questionId: 31, answer: form.value.feeCulture },
    { questionId: 32, answer: form.value.feeArt },
    { questionId: 33, answer: form.value.feeMedical },
    { questionId: 34, answer: form.value.feeCityTrans },
    { questionId: 35, answer: form.value.feeOther },
    { questionId: 36, answer: form.value.cityCount },
    { questionId: 37, answer: form.value.cityList }
  ];
  // 过滤未填写的答案
  const answers = allAnswers.filter(item => {
    if (item.answer === null || item.answer === undefined) return false;
    if (typeof item.answer === 'string' && item.answer.trim() === '') return false;
    if (Array.isArray(item.answer) && item.answer.length === 0) return false;
    return true;
  });
  const payload = {
    questionnaireId: form.value.questionnaireId || 1,
    answers
  };
  await submitAnswer(payload)
  ElMessage.success('提交成功！')
  router.push('/success')
}

// 数字验证函数
const validateNumber = (event) => {
  const value = event.target.value;
  // 只允许数字和小数点
  const filtered = value.replace(/[^\d.]/g, '');
  // 确保只有一个小数点
  const parts = filtered.split('.');
  let validValue;
  if (parts.length > 2) {
    validValue = parts[0] + '.' + parts.slice(1).join('');
  } else {
    validValue = filtered;
  }
  if (value !== validValue) {
    ElMessage.warning('只能输入数字');
  }
  event.target.value = validValue;
};

const handleCityCountChange = (e) => {
  let val = e && e.target ? e.target.value : form.value.cityCount
  val = parseInt(val) || 0
  if (val < 0) val = 0
  // 自动调整cityList长度
  if (val > form.value.cityList.length) {
    for (let i = form.value.cityList.length; i < val; i++) {
      form.value.cityList.push('')
    }
  } else if (val < form.value.cityList.length) {
    form.value.cityList.splice(val)
  }
}
</script>

<style scoped>
.fill-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #f8fafc 0%, #e0e7ef 100%);
  padding: 32px 0 48px 0;
}
.fill-card {
  max-width: 900px;
  margin: 0 auto;
  margin-top: 24px;
  box-shadow: 0 2px 16px #0001;
  border-radius: 18px;
  padding: 32px 48px 32px 48px;
}

/* 验证区域样式 - 暂时注释掉，后期接入API时还原 */
/*
.verify-section {
  text-align: center;
  padding: 40px 0;
}
.verify-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 12px;
  color: #409eff;
}
.verify-desc {
  font-size: 16px;
  color: #666;
  margin-bottom: 32px;
}
.verify-form {
  max-width: 400px;
  margin: 0 auto;
}
.code-input-group {
  display: flex;
  gap: 12px;
}
.send-code-btn {
  width: 120px;
  flex-shrink: 0;
}
.verify-btn {
  width: 100%;
  font-size: 18px;
  height: 44px;
  border-radius: 22px;
  letter-spacing: 2px;
}
*/

.title {
  font-size: 26px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
}
.desc {
  font-size: 15px;
  color: #666;
  text-align: center;
  margin-bottom: 18px;
}
.section-title {
  font-size: 18px;
  font-weight: bold;
  margin: 24px 0 12px 0;
  color: #409eff;
}
.el-form-item {
  margin-bottom: 22px;
}
.submit-btn {
  width: 100%;
  font-size: 20px;
  height: 48px;
  border-radius: 24px;
  letter-spacing: 2px;
}
.input-row {
  display: flex;
  flex-direction: column;
  gap: 0;
  align-items: flex-end;
}
.input-row.align-bottom {
  align-items: flex-end;
}
.input-row-multi {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  align-items: flex-end;
}
.input-col {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 12px;
  margin-bottom: 12px;
}
.input-label {
  font-size: 14px;
  color: #888;
  margin-top: 4px;
  text-align: center;
}

/* 单行显示选项样式 */
.radio-group-single {
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}


.checkbox-group-single {
  margin-top: 10px;
  display: flex;
  /* 每个选项单独占一行 */
  flex-direction: column;
  gap: 5px;
}

:deep(.el-form-item__label) {
  white-space: normal !important;
  font-size: 16px;
  line-height: 1.5;
  font-weight: 500;
}

:deep(.el-radio) {
  margin-right: 0 !important;
  margin-bottom: 8px;
}

:deep(.el-checkbox) {
  margin-right: 0 !important;
  margin-bottom: 8px;
}

.radio-group-single,
.checkbox-group-single {
  align-items: flex-start;
}

:deep(.el-form-item__content) {
  margin-left: 0 !important;
  padding-left: 0 !important;
}

.input-indent {
  margin-left: 32px;
}

.input-inline {
  display: flex;
  align-items: center;
  gap: 8px;
}
.input-tip {
  color: #888;
  font-size: 14px;
  margin-right: 2px;
}

.input-single-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.input-label {
  width: 100px;
  color: #888;
  font-size: 15px;
  margin-right: 8px;
  text-align: right;
}
</style>
