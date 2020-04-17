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
    component: () => import('../views/backstage/Login')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../views/backstage/Home')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/backstage/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/backstage/Register')
  },
  {
    path: '/management/tag',
    name: 'Tag',
    component: () => import('../views/backstage/Tag')
  }, {
    path: '/management/comment',
    name: 'Comment',
    component: () => import('../views/backstage/Comment')
  },
  {
    path: '/management/category',
    name: 'Category',
    component: () => import('../views/backstage/Category')
  },
  {
    path: '/management/post',
    name: 'post',
    component: () => import('../views/backstage/Post')
  },
  {
    path: '/management/postlist',
    name: 'postlist',
    component: () => import('../views/backstage/PostList')
  }, {
    path: '/management/attachment',
    name: 'attachment',
    component: () => import('../views/backstage/Attachment')
  }, {
    path: '/management/personal',
    name: 'Personal',
    component: () => import('../views/backstage/Personal')
  }, {
    path: '/site',
    name: 'Site',
    component: () => import('../views/backstage/Site')
  }, {
    path: '/blog/viewpoint',
    name: 'ViewPointPage',
    component: () => import('../views/frontdesk/ViewPoint')
  }, {
    path: '/blog/site',
    name: 'SitePage',
    component: () => import('../views/frontdesk/SitePage')
  }, {
    path: '/blog/post',
    name: 'PostPage',
    component: () => import('../views/frontdesk/PostPage')
  }, {
    path: '/blog/index',
    name: 'IndexPage',
    component: () => import('../views/frontdesk/IndexPage')
  }, {
    path: '/blog/userpost',
    name: 'UserPostPage',
    component: () => import('../views/frontdesk/UserPostPage')
  }, {
    path: '/blog/tag',
    name: 'TagPage',
    component: () => import('../views/frontdesk/TagPage')
  }, {
    path: '/blog/cate',
    name: 'CatePage',
    component: () => import('../views/frontdesk/CatePage')
  }, {
    path: '/blog/query',
    name: 'QueryPage',
    component: () => import('../views/frontdesk/QueryPage')
  }, {
    path: '/blog/postlist',
    name: 'SitePostList',
    component: () => import('../views/frontdesk/SitePostList')
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
