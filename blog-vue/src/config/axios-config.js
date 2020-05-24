import axios from 'axios'
import { Message, Notification } from 'element-ui'
import router from '../router'

// axios.defaults.headers.post['Content-Type'] = 'application/json'
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
  if (response.data.code === 200 || response.data.code === undefined) {
    return response
  }
  if (response.data.code === '300') {
    router.push('/login')
  } else if (response.data.code === 1001) {
    Notification.error(response.data.msg)
    return Promise.reject(response)
  } else if (response.data.code !== 200) {
    Message.error(response.data.msg)
    return Promise.reject(response)
  }
  return response
}, function (err) {
  if (err && err.response) {
    switch (err.response.status) {
      case 400: err.message = '请求错误(400)'; break
      case 401: err.message = '未授权，请重新登录(401)'; break
      case 403: err.message = '拒绝访问(403)'; break
      case 404: err.message = '请求出错(404)'; break
      case 408: err.message = '请求超时(408)'; break
      case 500: err.message = '服务器错误(500)'; break
      case 501: err.message = '服务未实现(501)'; break
      case 502: err.message = '网络错误(502)'; break
      case 503: err.message = '服务不可用(503)'; break
      case 504: err.message = '网络超时(504)'; break
      case 505: err.message = 'HTTP版本不受支持(505)'; break
      default: err.message = `连接出错(${err.response.status})!`
    }
  } else {
    err.message = '连接服务器失败!'
  }
  return Promise.reject(err)
})

export default axios
