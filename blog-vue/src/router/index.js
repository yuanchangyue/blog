import Vue from 'vue'
import VueRouter from 'vue-router'
import Register from '../views/Register'
import Login from '../views/Login'
import Home from '../views/Home'
import Category from '../views/Category'
import Tag from '../views/Tag'
import Post from '../views/Post'
import PostList from '../views/PostList'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
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
  },
  {
    path: '/category',
    name: 'Category',
    component: Category
  },
  {
    path: '/post',
    name: 'post',
    component: Post
  },
  {
    path: '/postlist',
    name: 'postlist',
    component: PostList
  }
]

const router = new VueRouter({
  routes
})

export default router
