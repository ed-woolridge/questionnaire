<template>
  <div class="fill-bg">
    <el-card class="fill-card">
      <!-- 问卷内容区域 -->
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
              <el-radio value="A">A.是</el-radio>
              <el-radio value="B">B.否（如选"否"，请终止调查）</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="2. 您本次游览的地点，离您日常居住（生活）以及工作（上学）的社区（村庄），距离是否在10公里以上" required>
            <el-radio-group v-model="form.q2" class="radio-group-single">
              <el-radio value="A">A.是</el-radio>
              <el-radio value="B">B.否（如选"否"，请终止调查）</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="3. 您本次出游过程中，是否已经在我市其他县（区、市）或景区（点）等区域参与了本项调查" required>
            <el-radio-group v-model="form.q3" class="radio-group-single">
              <el-radio value="A">A.是（如选"是"，请终止调查）</el-radio>
              <el-radio value="B">B.否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-alert v-if="shouldTerminate" type="warning" title="您不符合本次调查条件，感谢您的参与！" show-icon style="margin-bottom: 20px;" />

          <template v-else>
            <div class="section-title">调查部分</div>
            <el-form-item label="1. 您目前的常住地是_______省（自治区、直辖市）________市（州、盟）______县（区、市、旗）（常住地是指连续居住生活 6 个月以上的地区）" required>
              <el-input v-model="form.address" placeholder="省/市/县（区/旗）" style="width: 130px;" />
            </el-form-item>
            <el-form-item label="2. 您的性别是：" required>
              <el-radio-group v-model="form.gender" class="radio-group-single">
                <el-radio v-for="opt in options.gender" :key="opt.id" :value="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="3. 您的年龄：" required>
              <el-radio-group v-model="form.age" class="radio-group-single">
                <el-radio v-for="opt in options.age" :key="opt.id" :value="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="4. 您日常居住生活的地方是：" required>
              <el-radio-group v-model="form.residence" class="radio-group-single">
                <el-radio v-for="opt in options.residence" :key="opt.id" :value="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="5. 您本次出游，主要目的是（单选）：" required>
              <el-radio-group v-model="form.purpose" class="radio-group-single">
                <el-radio
                  v-for="opt in options.purpose"
                  :key="opt.id"
                  :value="opt.id"
                  :style="opt.showInput ? 'display: flex; align-items: center;' : ''"
                >
                  {{ opt.content }}
                  <template v-if="opt.showInput && form.purpose === opt.id">
                    <el-input
                      v-model="form.purposeOther"
                      placeholder="请注明"
                      style="width: 180px; margin-left: 8px;"
                      size="small"
                    />
                  </template>
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="6. 您本次出游，是否住宿过夜：" required>
              <el-radio-group v-model="form.stayOvernight" class="radio-group-single">
                <el-radio v-for="opt in options.stayOvernight" :key="opt.id" :value="opt.id">
                  {{ opt.content }}
                </el-radio>
              </el-radio-group>
            </el-form-item>

            <!--只有选项为showInput为true时才会出现-->
            <template v-if="form.stayOvernight === stayOvernightShowInputId">
              <el-form-item label="如您出游期间住宿过夜，在我市共住___夜" required>
                <el-input-number v-model="form.nights" style="margin-top: 10px;width: 180px;" :min="1" controls-position="right">
                  <template #prefix>
                    <span>住宿</span>
                  </template>
                  <template #suffix>
                    <span>夜</span>
                  </template>
                </el-input-number>
              </el-form-item>
              <el-form-item label="主要选择的住宿设施（住宿时间最长）是（单选）" required>
                <el-radio-group v-model="form.accommodation" class="radio-group-single">
                  <el-radio v-for="opt in options.accommodation" :key="opt.id" :value="opt.id">
                  {{ opt.content }}
                </el-radio>
                </el-radio-group>
              </el-form-item>
            </template>

            <el-form-item label="7. 您本次出游，在我市游览的旅游景区（景点）类型有（可多选）：" required>
              <el-checkbox-group v-model="form.scenicTypes" class="checkbox-group-single">
                <el-checkbox v-for="opt in options.scenicTypes" :key="opt.id" :value="opt.id">
                  {{ opt.content }}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>

            <el-form-item label="8. 您本次出游，来我市游览的方式是（单选）：" required>
              <el-radio-group v-model="form.travelMode" class="radio-group-single">
                <el-radio
                  v-for="opt in options.travelMode"
                  :key="opt.id"
                  :value="opt.id"
                  :style="opt.showInput ? 'display: flex; align-items: center;' : ''"
                >
                  {{ opt.content }}
                  <template v-if="opt.showInput && form.travelMode === opt.id">
                    <el-input
                      v-model="form.travelModeOther"
                      placeholder="请注明"
                      style="width: 180px; margin-left: 8px;"
                      size="small"
                    />
                  </template>
                </el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="9. 您本次出游，来我市游览的交通出行方式是（可多选）：" required>
              <el-checkbox-group v-model="form.transport" class="checkbox-group-single">
                <el-checkbox v-for="opt in options.transport" :key="opt.id" :value="opt.id">
                  {{ opt.content }}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>

            <el-form-item label="10.您计划在本市游览___天，今天是您来本市的第___天。" required>
              <el-space direction="vertical">
                <el-input-number v-model="form.planDays" style="margin-top: 10px;width: 180px;" :min="1" controls-position="right">
                  <template #prefix>
                    <span>计划游览</span>
                  </template>
                  <template #suffix>
                    <span>天</span>
                  </template>
                </el-input-number>
              <el-input-number v-model="form.currentDay" style="width: 180px;" :min="1" controls-position="right">
                <template #prefix>
                  <span>当前是第</span>
                </template>
                <template #suffix>
                  <span>天</span>
                </template>
              </el-input-number>
              </el-space>
            </el-form-item>

            <el-form-item label="（1）您本人本次出游，交给旅行社或由单位支付的费用是:___元（如未发生此项费用支出，本项目请填'0'）。" required>
              <el-input-number v-model="form.agencyFee" style="width: 200px;margin-top: 10px;" :min="0" controls-position="right">
                <template #prefix>
                  <span>支出费用</span>
                </template>
                <template #suffix>
                  <span>元</span>
                </template>
              </el-input-number>
            </el-form-item>

            <el-form-item label="（2）您本人本次出游期间支出的交通费___元。（如此项费用已含在交给旅行社或由单位支付的费用中，本项目请填'0'）" required>
              <el-input-number v-model="form.transportFee" style="width: 200px;margin-top: 10px;" :min="0" controls-position="right">
                <template #prefix>
                  <span>交通费</span>
                </template>
                <template #suffix>
                  <span>元</span>
                </template>
              </el-input-number>
            </el-form-item>

            <el-form-item label="其中:">
              <el-space direction="vertical">
                <el-input-number v-model="form.feePlane" style="width: 200px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>A.飞机</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeTrain" style="width: 200px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>B.火车</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeBus" style="width: 200px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>C.长途汽车</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeShip" style="width: 200px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>D.轮船</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeCar" style="width: 200px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>E.自驾/租车</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>
              </el-space>
            </el-form-item>

            <el-form-item label="（3）除上述（1）（2）两项费用之外，截至此时，您本人在我市游览期间支付的费用是:___元。" required>
              <el-input-number v-model="form.otherFee" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                <template #prefix>
                  <span>支出的费用为</span>
                </template>
                <template #suffix>
                  <span>元</span>
                </template>
              </el-input-number>
            </el-form-item>

            <el-form-item label="其中：">
              <el-space direction="vertical">
                <el-input-number v-model="form.feeFood" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>A.餐饮</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeHotel" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>B.住宿</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeTicket" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>C.门票</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeShopping" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>D.购物</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeCulture" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>E.购买文创产品</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeArt" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>F.文化艺术/娱乐</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeMedical" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>G.医疗康养</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeCityTrans" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>H.市内交通</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>

                <el-input-number v-model="form.feeOther" style="width: 230px;margin-top: 10px;" :min="0" controls-position="right">
                  <template #prefix>
                    <span>I.其他</span>
                  </template>
                  <template #suffix>
                    <span>元</span>
                  </template>
                </el-input-number>
              </el-space>
            </el-form-item>

            <el-form-item label="11. 您本次出游，在我省（自治区、直辖市）停留期间，共游览了___个市？">
              <el-input-number v-model="form.cityCount" style="margin-top: 10px;width: 230px;" :min="0" controls-position="right" @input="handleCityCountChange">
                <template #prefix>
                  <span>游览城市数量：</span>
                </template>
                <template #suffix>
                  <span>个</span>
                </template>
              </el-input-number>
            </el-form-item>

            <el-form-item label="分别是">
              <div class="input-row align-bottom">
                <div class="input-col" v-for="(city, idx) in form.cityList" :key="idx">
                  <el-input v-model="form.cityList[idx]" :placeholder="`市${idx+1}`" style="width: 180px;" />
                </div>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" @click="submit" class="submit-btn">提交问卷</el-button>
            </el-form-item>
          </template>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
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
   options.value.gender = await getOptions(1)//性别
   options.value.age = await getOptions(2)//年龄
   options.value.residence = await getOptions(3)//居住地
   options.value.purpose = await getOptions(5)//出行目的
   options.value.stayOvernight = await getOptions(7)//是否过夜
   options.value.accommodation = await getOptions(9)//住宿类型
   options.value.scenicTypes = await getOptions(10)//景区类型
   options.value.travelMode = await getOptions(11)//游览方式
   options.value.transport = await getOptions(13)//交通工具
 })

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

const purposeShowInputId = computed(() => {
  // 找到 showInput 为 true 的选项的 id
  const opt = options.value.purpose.find(item => item.showInput);
  return opt ? opt.id : null;
});

const stayOvernightShowInputId = computed(() => {
  const opt = options.value.stayOvernight.find(item => item.showInput);
  return opt ? opt.id : null;
});

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


const scrollToFirstInvalid = () => {
  nextTick(() => {
    const formItems = document.querySelectorAll('.el-form-item')
    for (let i = 0; i < formItems.length; i++) {
      const item = formItems[i]
      const label = item.querySelector('.el-form-item__label')
      const input = item.querySelector('input, textarea, .el-radio-group, .el-checkbox-group')
      if (input) {
        if (input.tagName === 'INPUT' || input.tagName === 'TEXTAREA') {
          if (!input.value) {
            item.scrollIntoView({ behavior: 'smooth', block: 'center' })
            input.focus()
            ElMessageBox.alert('请填写：' + (label?.innerText || '必填项'), '必填项未填写', { type: 'warning' })
            break
          }
        } else if (input.classList.contains('el-radio-group')) {
          const checked = item.querySelector('input[type=radio]:checked')
          if (!checked) {
            item.scrollIntoView({ behavior: 'smooth', block: 'center' })
            input.focus()
            ElMessageBox.alert('请填写：' + (label?.innerText || '必填项'), '必填项未填写', { type: 'warning' })
            break
          }
        } else if (input.classList.contains('el-checkbox-group')) {
          const checked = item.querySelector('input[type=checkbox]:checked')
          if (!checked) {
            item.scrollIntoView({ behavior: 'smooth', block: 'center' })
            input.focus()
            ElMessageBox.alert('请填写：' + (label?.innerText || '必填项'), '必填项未填写', { type: 'warning' })
            break
          }
        }
      }
    }
  })
}

const submit = async () => {
  if (!isFormValid.value) {
    ElMessage.warning('请完整填写所有必填项');
    scrollToFirstInvalid();
    return;
  }
  if (shouldTerminate.value) {
    ElMessage.warning('您不符合本次调查条件，感谢您的参与！')
    return
  }

  // 组装所有问题答案（从地址开始，ID从1递增）
  const allAnswers = [
    { questionId: 1, answer: form.value.address },
    { questionId: 2, answer: form.value.gender },
    { questionId: 3, answer: form.value.age },
    { questionId: 4, answer: form.value.residence },
    { questionId: 5, answer: form.value.purpose },
    { questionId: 6, answer: form.value.purposeOther },
    { questionId: 7, answer: form.value.stayOvernight },
    { questionId: 8, answer: form.value.nights },
    { questionId: 9, answer: form.value.accommodation },
    { questionId: 10, answer: form.value.scenicTypes },
    { questionId: 11, answer: form.value.travelMode },
    { questionId: 12, answer: form.value.travelModeOther },
    { questionId: 13, answer: form.value.transport },
    { questionId: 14, answer: form.value.planDays },
    { questionId: 15, answer: form.value.currentDay },
    { questionId: 16, answer: form.value.agencyFee },
    { questionId: 17, answer: form.value.transportFee },
    { questionId: 18, answer: form.value.feePlane },
    { questionId: 19, answer: form.value.feeTrain },
    { questionId: 20, answer: form.value.feeBus },
    { questionId: 21, answer: form.value.feeShip },
    { questionId: 22, answer: form.value.feeCar },
    { questionId: 23, answer: form.value.otherFee },
    { questionId: 24, answer: form.value.feeFood },
    { questionId: 25, answer: form.value.feeHotel },
    { questionId: 26, answer: form.value.feeTicket },
    { questionId: 27, answer: form.value.feeShopping },
    { questionId: 28, answer: form.value.feeCulture },
    { questionId: 29, answer: form.value.feeArt },
    { questionId: 30, answer: form.value.feeMedical },
    { questionId: 31, answer: form.value.feeCityTrans },
    { questionId: 32, answer: form.value.feeOther },
    { questionId: 33, answer: form.value.cityCount },
    { questionId: 34, answer: form.value.cityList.filter(city => city.trim() !== '').join('，') }
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

const handleNumberInput = (field) => (val) => {
  let filtered = String(val).replace(/[^\d.]/g, '');
  const parts = filtered.split('.');
  if (parts.length > 2) {
    filtered = parts[0] + '.' + parts.slice(1).join('');
  }
  if (filtered !== String(val)) {
    ElMessage.warning('只能输入数字');
  }
  form.value[field] = filtered;
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
.section-title {
  font-size: 18px;
  font-weight: bold;
  margin: 24px 0 12px 0;
  color: #409eff;
}

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
