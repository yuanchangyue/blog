import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

// const routerPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push (location) {
//   return routerPush.call(this, location).catch(error => error)
// }

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('../views/Login')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../views/Home')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  },
  {
    path: '/tag',
    name: 'Tag',
    component: () => import('../views/Tag')
  },
  {
    path: '/category',
    name: 'Category',
    component: () => import('../views/Category')
  },
  {
    path: '/post',
    name: 'post',
    component: () => import('../views/Post')
  },
  {
    path: '/postlist',
    name: 'postlist',
    component: () => import('../views/PostList')
  }, {
    path: '/attachment',
    name: 'attachment',
    component: () => import('../views/Attachment')
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

// let newRouter = []
// router.beforeEach((to, from, next) => {
//   if (to.path !== '/login') {
//     var routerItem = JSON.parse(localStorage.getItem('router'))
//     if (routerItem) {
//       if (!newRouter) {
//         routerItem.forEach(e => {
//           if (e.component != null && e.url != null) {
//             var value = e.component
//             var item = {
//               path: e.url,
//               name: Math.floor(Math.random() * 1000),
//               component: function component (resolve) {
//                 require([value], resolve)
//               }
//             }
//           }
//           newRouter.push(item)
//         })
//         alert(newRouter)
//         router.addRoutes(newRouter)
//         next({ ...to, replace: true })
//       } else {
//         next()
//       }
//     } else {
//       next('/')
//     }
//   } else {
//     next()
//   }
// })

// {
//   path: '/tag',
//     name: 'Tag',
//   component: () => import('../views/Tag')
// },
// {
//   path: '/category',
//     name: 'Category',
//   component: () => import('../views/Category')
// },
// {
//   path: '/post',
//     name: 'post',
//   component: () => import('../views/Post')
// },
// {
//   path: '/postlist',
//     name: 'postlist',
//   component: () => import('../views/PostList')
// }, {
//   path: '/attachment',
//     name: 'attachment',
//     component: () => import('../views/Attachment')
// }

export default router
