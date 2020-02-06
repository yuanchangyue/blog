import Vue from 'vue'
import VueRouter from 'vue-router'
import Register from '../views/Register'
import Login from '../views/Login'
import Home from '../views/Home'
import Tag from '../views/Tag'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/tag',
    name: 'Tag',
    component: Tag
  }
]

const router = new VueRouter({
  routes
})

export default router
