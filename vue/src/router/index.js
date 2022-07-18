import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//刷星页面会重置路由
export const setRouters = () =>{
  const storeMenus = localStorage.getItem("menus");
  if(storeMenus){
    //拼接动态路由
    const manageRoute = { path: '/',name: 'Manage', component: () => import('../views/Manage.vue'), redirect: "/home", children: [] }
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if(item.path){  //当且仅当path不为空的时候采取设置路由
        let itemMenu = { path: item.path.replace("/",""), name: item.name, component: () => import('../views/'+ item.pagePath +'.vue')}
        manageRoute.children.push(itemMenu)
      }else if(item.children.length){
        item.children.forEach(item => {
          if(item.path){
            let itemMenu = { path: item.path.replace("/",""), name: item.name, component: () => import('../views/'+ item.pagePath +'.vue')}
            manageRoute.children.push(itemMenu)
          }
        })
      }
    })
    let person = { path: 'person', name: '个人信息', component: () => import('../views/Person.vue')}
    manageRoute.children.push(person)

    //获取当前路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if(!currentRouteNames.includes('Manage')){
      // 动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }
  }
}

//刷新重新获取路由
setRouters()

//路由守卫
router.beforeEach((to ,from,next)=>{
  localStorage.setItem("currentPathName",to.name)   //设置当前路由名称
  store.commit("setPath")

  //未找到路由的情况
  if(!to.matched.length){
    const storeMenus = localStorage.getItem("menus")
    if(storeMenus){
      next("/404")
    } else{
      //跳回登录页面
      next("/login")
    }
  }
  next()
})

export default router
