import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import BootstrapVue3 from 'bootstrap-vue-3'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'


const app = createApp(App)
app
    .use(router)
    .use(BootstrapVue3)
    .mount('#app')

app.config.globalProperties.$axios = axios;  //전역변수로 설정 컴포넌트에서 this.$axios 호출할 수 있음
app.config.globalProperties.$homeserviceAPIUrl = 'http://localhost:8080' //homeservice api server

