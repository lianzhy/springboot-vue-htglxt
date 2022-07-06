import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      {
        path: 'home',
        name: '主页',
        component: () => import('../views/Home.vue')
      },
      {
        path: 'user',
        name: '用户管理',
        component: () => import('../views/User.vue')
      }
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to ,from,next)=>{
  localStorage.setItem("currentPathName",to.name)   //设置当前路由名称
  store.commit("setPath")
  next()
})

export default router
