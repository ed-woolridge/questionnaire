import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs' // 引入中文语言包


const app = createApp(App)
app.use(router)
app.use(createPinia())
app.use(ElementPlus, {
  locale: zhCn, // 设置中文
})
app.mount('#app') 