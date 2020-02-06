import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import './plugins/element.js'

Vue.use(ElementUI)

var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8089/api'

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
