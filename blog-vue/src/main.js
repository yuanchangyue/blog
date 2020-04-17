import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import './plugins/element.js'
import './assets/css/common.css'
import qs from 'qs'
import AOS from 'aos'
import 'aos/dist/aos.css'
/* eslint-disable no-unused-vars */
import $ from 'jquery'
// 导入bootstrap
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import axios from './config/axios-config'
import fontawesome from '@fortawesome/fontawesome'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import solid from '@fortawesome/fontawesome-free-solid'
import regular from '@fortawesome/fontawesome-free-regular'
import brands from '@fortawesome/fontawesome-free-brands'
import './assets/js/pinterest_grid'
/* eslint-disable no-unused-vars */
import './assets/js/owl.carousel.js'

AOS.init()

fontawesome.library.add(solid)
fontawesome.library.add(regular)
fontawesome.library.add(brands)

Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.use(ElementUI)

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.prototype.$qs = qs

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
