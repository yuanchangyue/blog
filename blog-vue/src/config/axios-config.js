import axios from 'axios'
import { Message } from 'element-ui'
import router from '../router'

axios.defaults.headers.post['Content-Type'] = 'application/json'
axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest'
axios.defaults.changeOrigin = true
axios.defaults.withCredentials = true
axios.defaults.timeout = 60000
axios.defaults.baseURL = 'http://localhost:8089/api'

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
  return config
}, function (error) {
  Message.error({
    message: '错误的传参'
  })
  return Promise.reject(error)
})

axios.interceptors.response.use(function (response) {
  console.info(response.data)
  if (response.data.code === undefined) {
    return response
  }
  if (response.data.code === 300) {
    router.push({ path: '/login' })
  } else if (response.data.code !== 200) {
    Message.error({
      message: response
    })
    return Promise.reject(response)
  }
  return response
}, function (error) {
  console.log(error)
  Message.error({
    message: '网络异常'
  })
  return Promise.reject(error)
})

export default axios
