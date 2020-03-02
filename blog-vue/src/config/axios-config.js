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
  if (response.data.code === undefined || response.data.code === 200) {
    return response
  }
  if (response.data.code === '300') {
    console.info('not login -> ' + response.data.data + response.data.code)
    Message.error({
      message: response.data.data
    })
    router.push('/login')
  } else if (response.data.code !== 200) {
    console.info('error--->' + response.data.code)
    Message.error({
      message: response.data.data
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
