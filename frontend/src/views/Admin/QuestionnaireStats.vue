<template>
    <!--统计分析页-->
    <AdminLayout>
        <div class="stats-container">
            <el-card class="stats-card">
                <div class="stats-header">
                    <h2>统计分析</h2>
                    <el-form :inline="true" :model="searchForm" class="stats-form">
                        <el-form-item label="问卷选择" style="width: 260px;">
                            <el-select v-model="searchForm.questionnaireId" placeholder="请选择问卷" clearable @change="fetchStats">
                                <el-option v-for="q in questionnaireList" :key="q.id" :label="q.title" :value="q.id" />
                            </el-select>
                        </el-form-item>
                    </el-form>
                </div>
                <div class="stats-cards">
                    <el-card class="stat-card">
                        <div class="stat-content">
                            <div class="stat-number">{{ stats.totalCount }}</div>
                            <div class="stat-label">总提交数</div>
                        </div>
                    </el-card>
                    <el-card class="stat-card">
                        <div class="stat-content">
                            <div class="stat-number">{{ stats.todayCount }}</div>
                            <div class="stat-label">今日提交</div>
                        </div>
                    </el-card>
                    <el-card class="stat-card">
                        <div class="stat-content">
                            <div class="stat-number">{{ stats.completedCount }}</div>
                            <div class="stat-label">已完成</div>
                        </div>
                    </el-card>
                </div>
            </el-card>
            <el-card class="stats-card" style="margin-top: 24px;">
                <h3>选择题选项分布</h3>
                <div v-for="q in optionStats" :key="q.questionId" class="option-stats-block">
                    <div class="question-title">{{ q.questionTitle }}</div>
                    <div class="echart-block">
                        <div :id="'pie-' + q.questionId" class="echart-pie"></div>
                    </div>
                </div>
            </el-card>
        </div>
    </AdminLayout>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick, watch } from 'vue'
import { ElMessage } from 'element-plus'
import AdminLayout from '../../components/AdminLayout.vue';
import { getQuestionnaireList, getAnswerSheetStats, getOptionStats } from '@/api'
import * as echarts from 'echarts'

const questionnaireList = ref([])
const stats = reactive({
    totalCount: 0,
    todayCount: 0,
    completedCount: 0
})
const searchForm = reactive({
    questionnaireId: ''
})
const optionStats = ref([])

const fetchQuestionnaireList = async () => {
    try {
        const res = await getQuestionnaireList()
        if (res.data && res.data.code === '200') {
            questionnaireList.value = res.data.data
        } else {
            questionnaireList.value = []
        }
    } catch (e) {
        ElMessage.error('获取问卷列表失败')
    }
}

const fetchOptionStats = async () => {
    if (!searchForm.questionnaireId) {
        optionStats.value = []
        return
    }
    try {
        const { data } = await getOptionStats(searchForm.questionnaireId)
        if (data.code === '200') {
            optionStats.value = data.data
        }
    } catch (e) {
        ElMessage.error('获取选项分布失败')
    }
}

const fetchStats = async () => {
    try {
        const params = {}
        if (searchForm.questionnaireId) params.questionnaireId = searchForm.questionnaireId
        const { data } = await getAnswerSheetStats(params)
        if (data.code === '200') {
            Object.assign(stats, data.data)
        }
        await fetchOptionStats()
    } catch (e) {
        ElMessage.error('获取统计信息失败')
    }
}

const renderCharts = () => {
    nextTick(() => {
        optionStats.value.forEach(q => {
            const dom = document.getElementById('pie-' + q.questionId)
            if (!dom) return
            let chart = echarts.getInstanceByDom(dom)
            if (!chart) chart = echarts.init(dom)
            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    top: 'middle',
                    formatter: name => {
                        const opt = q.options.find(o => o.content === name)
                        return opt ? `${name}  ${opt.count}次` : name
                    }
                },
                series: [
                    {
                        name: q.questionTitle,
                        type: 'pie',
                        radius: '60%',
                        data: q.options.map(opt => ({
                            value: opt.count,
                            name: opt.content
                        })),
                        label: {
                            formatter: '{b}: {d}%'
                        }
                    }
                ]
            }
            chart.setOption(option)
            chart.resize()
        })
    })
}

watch(optionStats, () => {
    renderCharts()
})

onMounted(async () => {
    await fetchQuestionnaireList()
    await fetchStats()
    renderCharts()
})
</script>

<style scoped>
.stats-container {
    padding: 20px;
}
.stats-card {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding: 24px;
}
.stats-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
}
.stats-form {
    margin-bottom: 0;
}
.stats-cards {
    display: flex;
    gap: 20px;
}
.stat-card {
    flex: 1;
    border: none;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;
}
.stat-card:hover {
    transform: translateY(-2px);
}
.stat-content {
    text-align: center;
    padding: 16px;
}
.stat-number {
    font-size: 28px;
    font-weight: bold;
    color: #409eff;
    margin-bottom: 8px;
}
.stat-label {
    color: #606266;
    font-size: 14px;
}
@media (max-width: 768px) {
    .stats-cards {
        flex-direction: column;
        gap: 12px;
    }
    .stats-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 12px;
    }
}
.option-stats-block {
    margin-bottom: 32px;
}
.question-title {
    font-weight: bold;
    margin-bottom: 8px;
}
.echart-block {
    width: 100%;
    min-height: 320px;
    display: flex;
    align-items: center;
    justify-content: center;
}
.echart-pie {
    width: 400px;
    height: 320px;
}
</style>