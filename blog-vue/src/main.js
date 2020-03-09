import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import './plugins/element.js'
import './assets/common.css'
import qs from 'qs'
import axios from './config/axios-config'

Vue.use(ElementUI)

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.prototype.$qs = qs

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
