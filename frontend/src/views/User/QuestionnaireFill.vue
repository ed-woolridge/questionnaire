<template>
  <div class="fill-bg">
    <el-card class="fill-card">
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
        <el-form-item label="1. 您本次出游的时间，是否在6小时以上">
          <el-radio-group v-model="form.q1">
            <el-radio label="A">是</el-radio>
            <el-radio label="B">否（如选"否"，请终止调查）</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="2. 您本次游览的地点，离您日常居住（生活）以及工作（上学）的社区（村庄），距离是否在10公里以上">
          <el-radio-group v-model="form.q2">
            <el-radio label="A">是</el-radio>
            <el-radio label="B">否（如选"否"，请终止调查）</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="3. 您本次出游过程中，是否已经在我市其他县（区、市）或景区（点）等区域参与了本项调查">
          <el-radio-group v-model="form.q3">
            <el-radio label="A">是（如选"是"，请终止调查）</el-radio>
            <el-radio label="B">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-alert v-if="shouldTerminate" type="warning" title="您不符合本次调查条件，感谢您的参与！" show-icon style="margin-bottom: 20px;" />

        <template v-else>
          <div class="section-title">调查部分</div>
          <el-form-item label="1. 您目前的常住地是_______省（自治区、直辖市）________市（州、盟）______县（区、市、旗）（常住地是指连续居住生活 6 个月以上的地区）">
            <div class="input-row">
              <el-input v-model="form.province" placeholder="省/自治区/直辖市" style="width: 180px;" />
              <el-input v-model="form.city" placeholder="市/州/盟" style="width: 180px; margin-left: 12px;" />
              <el-input v-model="form.district" placeholder="县/区/旗" style="width: 180px; margin-left: 12px;" />
            </div>
          </el-form-item>
          <el-form-item label="2. 您的性别是：">
            <el-radio-group v-model="form.gender">
              <el-radio label="A">男</el-radio>
              <el-radio label="B">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="3. 您的年龄：">
            <el-radio-group v-model="form.age">
              <el-radio label="A">未满18岁</el-radio>
              <el-radio label="B">18-24岁</el-radio>
              <el-radio label="C">25-35岁</el-radio>
              <el-radio label="D">36-45岁</el-radio>
              <el-radio label="E">46-60岁</el-radio>
              <el-radio label="F">60岁以上</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="4. 您日常居住生活的地方是：">
            <el-radio-group v-model="form.residence">
              <el-radio label="A">城镇</el-radio>
              <el-radio label="B">乡村</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="5. 您本次出游，主要目的是（单选）：">
            <el-radio-group v-model="form.purpose">
              <el-radio label="A">观光/休闲</el-radio>
              <el-radio label="B">探亲访友</el-radio>
              <el-radio label="C">购物</el-radio>
              <el-radio label="D">文化艺术欣赏/娱乐</el-radio>
              <el-radio label="E">会议/培训/展览</el-radio>
              <el-radio label="F">商务活动</el-radio>
              <el-radio label="G">研学/体育/科技交流</el-radio>
              <el-radio label="H">医疗康养</el-radio>
              <el-radio label="I">宗教活动</el-radio>
              <el-radio label="J">其他</el-radio>
            </el-radio-group>
            <el-input v-if="form.purpose==='J'" v-model="form.purposeOther" placeholder="请注明" style="margin-top: 8px; width: 300px;" />
          </el-form-item>
          <el-form-item label="6. 您本次出游，是否住宿过夜：">
            <el-radio-group v-model="form.stayOvernight">
              <el-radio label="A">是</el-radio>
              <el-radio label="B">否（选"否"免填本题后续内容）</el-radio>
            </el-radio-group>
          </el-form-item>
          <template v-if="form.stayOvernight==='A'">
            <el-form-item label="如您出游期间住宿过夜，在我市共住___夜">
              <el-input-number v-model="form.nights" :min="1" style="width: 120px;" />
            </el-form-item>
            <el-form-item label="主要选择的住宿设施（住宿时间最长）是（单选）">
              <el-radio-group v-model="form.accommodation">
                <el-radio label="A">住在酒店设施</el-radio>
                <el-radio label="B">住在宾馆、民宿客栈、家庭旅馆、疗养院、游船等经营性住宿设施</el-radio>
                <el-radio label="C">住在亲友家、自有休闲度假居所（房屋）</el-radio>
                <el-radio label="D">住在自驾房车、露营地等其他住宿设施</el-radio>
              </el-radio-group>
            </el-form-item>
          </template>
          <el-form-item label="7. 您本次出游，在我市游览的旅游景区（景点）类型有（可多选）：">
            <el-checkbox-group v-model="form.scenicTypes">
              <el-checkbox label="A">A级旅游景区（文化和旅游部门评定的5A级、4A级、3A级、2A级等旅游景区）</el-checkbox>
              <el-checkbox label="B">文化场馆（博物馆、图书馆、美术馆、影院剧院等文化场馆）</el-checkbox>
              <el-checkbox label="C">音乐节、演唱会、展览及节庆活动等相关区域</el-checkbox>
              <el-checkbox label="D">城市公园、游乐园（主题公园）、绿道等休闲娱乐类区域</el-checkbox>
              <el-checkbox label="E">商场、步行街、集市等购物消费类区域</el-checkbox>
              <el-checkbox label="F">革命活动地、战役发生地、纪念馆、爱国主义教育示范基地等红色旅游类区域</el-checkbox>
              <el-checkbox label="G">传统村落、农（牧、渔）家乐、农业田园、牧场、渔场等乡村旅游区域</el-checkbox>
              <el-checkbox label="I">以上各类型景区（景点）都未游览</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="8. 您本次出游，来我市游览的方式是（单选）：">
            <el-radio-group v-model="form.travelMode">
              <el-radio label="A">单位（安排）组织</el-radio>
              <el-radio label="B">个人、家庭或亲朋结伴自由出行</el-radio>
              <el-radio label="C">通过旅行社组团出行</el-radio>
              <el-radio label="D">其他</el-radio>
            </el-radio-group>
            <el-input v-if="form.travelMode==='D'" v-model="form.travelModeOther" placeholder="请注明" style="margin-top: 8px; width: 300px;" />
          </el-form-item>
          <el-form-item label="9. 您本次出游，来我市游览的交通出行方式是（可多选）：">
            <el-checkbox-group v-model="form.transport">
              <el-checkbox label="A">乘坐（飞机、火车、轮船、汽车等）公共交通工具出行</el-checkbox>
              <el-checkbox label="B">自驾车出行</el-checkbox>
              <el-checkbox label="C">租车自驾出行</el-checkbox>
              <el-checkbox label="D">骑行、徒步等其他出行方式</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <div class="section-title">花费情况</div>
          <el-form-item label="您计划在本市游览___天，今天是您来本市的第___天。">
            <div class="input-row">
              <el-input-number v-model="form.planDays" :min="1" style="width: 120px;" placeholder="计划天数" />
              <el-input-number v-model="form.currentDay" :min="1" style="width: 120px; margin-left: 12px;" placeholder="第几天" />
            </div>
          </el-form-item>
          <el-form-item label="（1）您本人本次出游，交给旅行社或由单位支付的费用是:__元（如未发生此项费用支出，本项目请填'0'）。">
            <el-input-number v-model="form.agencyFee" :min="0" style="width: 180px;" />
          </el-form-item>
          <el-form-item label="（2）您本人本次出游期间支出的交通费__元。（如此项费用已含在交给旅行社或由单位支付的费用中，本项目请填'0'）">
            <el-input-number v-model="form.transportFee" :min="0" style="width: 180px;" />
          </el-form-item>
          <el-form-item label="其中：A.飞机__元、B.火车__元、C.长途汽车__元、D.轮船__元、E.自驾车或租车的花费总额__元（自驾车或租车自驾的游客填写）">
            <div class="input-row align-bottom">
              <div class="input-col">
                <el-input-number v-model="form.feePlane" :min="0" style="width: 120px;" placeholder="飞机" />
                <div class="input-label">A.飞机</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeTrain" :min="0" style="width: 120px;" placeholder="火车" />
                <div class="input-label">B.火车</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeBus" :min="0" style="width: 120px;" placeholder="长途汽车" />
                <div class="input-label">C.长途汽车</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeShip" :min="0" style="width: 120px;" placeholder="轮船" />
                <div class="input-label">D.轮船</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeCar" :min="0" style="width: 120px;" placeholder="自驾/租车" />
                <div class="input-label">E.自驾/租车</div>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="（3）除上述（1）（2）两项费用之外，截至此时，您本人在我市游览期间支付的费用是:__元。">
            <el-input-number v-model="form.otherFee" :min="0" style="width: 180px;" />
          </el-form-item>
          <el-form-item label="其中：A.餐饮__元 B.住宿__元 C.旅游景区（景点）门票（套票）__元 D.购物__元 其中：购买文创产品__元 E.文化艺术欣赏／娱乐__元 F.医疗康养__元 G.市内交通__元 H. 其他__元">
            <div class="input-row align-bottom">
              <div class="input-col">
                <el-input-number v-model="form.feeFood" :min="0" style="width: 120px;" placeholder="餐饮" />
                <div class="input-label">A.餐饮</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeHotel" :min="0" style="width: 120px;" placeholder="住宿" />
                <div class="input-label">B.住宿</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeTicket" :min="0" style="width: 120px;" placeholder="门票" />
                <div class="input-label">C.门票</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeShopping" :min="0" style="width: 120px;" placeholder="购物" />
                <div class="input-label">D.购物</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeCulture" :min="0" style="width: 120px;" placeholder="文创产品" />
                <div class="input-label">文创产品</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeArt" :min="0" style="width: 120px;" placeholder="文化艺术/娱乐" />
                <div class="input-label">E.文化艺术</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeMedical" :min="0" style="width: 120px;" placeholder="医疗康养" />
                <div class="input-label">F.医疗康养</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeCityTrans" :min="0" style="width: 120px;" placeholder="市内交通" />
                <div class="input-label">G.市内交通</div>
              </div>
              <div class="input-col">
                <el-input-number v-model="form.feeOther" :min="0" style="width: 120px;" placeholder="其他" />
                <div class="input-label">H.其他</div>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="11. 您本次出游，在我省（自治区、直辖市）停留期间，共游览了___个市？">
            <el-input-number v-model="form.cityCount" :min="1" style="width: 120px;" />
          </el-form-item>
          <el-form-item label="分别是">
            <div class="input-row align-bottom">
              <div class="input-col">
                <el-input v-model="form.city1" placeholder="市1" style="width: 180px;" />
              </div>
              <div class="input-col">
                <el-input v-model="form.city2" placeholder="市2" style="width: 180px;" />
              </div>
              <div class="input-col">
                <el-input v-model="form.city3" placeholder="市3" style="width: 180px;" />
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit" class="submit-btn">提交</el-button>
          </el-form-item>
        </template>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { submitAnswer } from '@/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = ref({
  q1: '', q2: '', q3: '',
  province: '', city: '', district: '',
  gender: '', age: '', residence: '', purpose: '', purposeOther: '',
  stayOvernight: '', nights: 1, accommodation: '',
  scenicTypes: [],
  travelMode: '', travelModeOther: '',
  transport: [],
  planDays: 1, currentDay: 1,
  agencyFee: 0, transportFee: 0,
  feePlane: 0, feeTrain: 0, feeBus: 0, feeShip: 0, feeCar: 0,
  otherFee: 0, feeFood: 0, feeHotel: 0, feeTicket: 0, feeShopping: 0, feeCulture: 0,
  feeArt: 0, feeMedical: 0, feeCityTrans: 0, feeOther: 0,
  cityCount: 1, city1: '', city2: '', city3: ''
})

const shouldTerminate = computed(() =>
  form.value.q1 === 'B' || form.value.q2 === 'B' || form.value.q3 === 'A'
)

const submit = async () => {
  if (shouldTerminate.value) {
    ElMessage.warning('您不符合本次调查条件，感谢您的参与！')
    return
  }
  // 组装所有问题答案
  const allAnswers = [
    { questionId: 1, answer: form.value.q1 },
    { questionId: 2, answer: form.value.q2 },
    { questionId: 3, answer: form.value.q3 },
    { questionId: 4, answer: form.value.province },
    { questionId: 5, answer: form.value.city },
    { questionId: 6, answer: form.value.district },
    { questionId: 7, answer: form.value.gender },
    { questionId: 8, answer: form.value.age },
    { questionId: 9, answer: form.value.residence },
    { questionId: 10, answer: form.value.purpose },
    { questionId: 11, answer: form.value.purposeOther },
    { questionId: 12, answer: form.value.stayOvernight },
    { questionId: 13, answer: form.value.nights },
    { questionId: 14, answer: form.value.accommodation },
    { questionId: 15, answer: form.value.scenicTypes },
    { questionId: 16, answer: form.value.travelMode },
    { questionId: 17, answer: form.value.travelModeOther },
    { questionId: 18, answer: form.value.transport },
    { questionId: 19, answer: form.value.planDays },
    { questionId: 20, answer: form.value.currentDay },
    { questionId: 21, answer: form.value.agencyFee },
    { questionId: 22, answer: form.value.transportFee },
    { questionId: 23, answer: form.value.feePlane },
    { questionId: 24, answer: form.value.feeTrain },
    { questionId: 25, answer: form.value.feeBus },
    { questionId: 26, answer: form.value.feeShip },
    { questionId: 27, answer: form.value.feeCar },
    { questionId: 28, answer: form.value.otherFee },
    { questionId: 29, answer: form.value.feeFood },
    { questionId: 30, answer: form.value.feeHotel },
    { questionId: 31, answer: form.value.feeTicket },
    { questionId: 32, answer: form.value.feeShopping },
    { questionId: 33, answer: form.value.feeCulture },
    { questionId: 34, answer: form.value.feeArt },
    { questionId: 35, answer: form.value.feeMedical },
    { questionId: 36, answer: form.value.feeCityTrans },
    { questionId: 37, answer: form.value.feeOther },
    { questionId: 38, answer: form.value.cityCount },
    { questionId: 39, answer: form.value.city1 },
    { questionId: 40, answer: form.value.city2 },
    { questionId: 41, answer: form.value.city3 }
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
  flex-wrap: wrap;
  gap: 0;
  align-items: flex-end;
}
.input-row.align-bottom {
  align-items: flex-end;
}
.input-col {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 12px;
}
.input-label {
  font-size: 14px;
  color: #888;
  margin-top: 4px;
  text-align: center;
}
:deep(.el-form-item__label) {
  white-space: normal !important;
  font-size: 16px;
  line-height: 1.5;
  font-weight: 500;
}
</style>
