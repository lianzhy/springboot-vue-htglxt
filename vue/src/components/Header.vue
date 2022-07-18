<template>
  <div style="line-height: 60px;display: flex">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
        <el-breadcrumb-item :to="'/'" >首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>

      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 150px;cursor: pointer;text-align: right;">
      <div style="display: inline-block">
        <img :src="user.avatarUrl" alt="" style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
        <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
        <el-dropdown-item style="font-size: 14px;padding: 5px 0;">
          <router-link to="/person" style="text-decoration: none">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "MyHeader",
  data(){
    return{

    }
  },
  watch: {  //监听路由变化
    currentPathName(newVal,oldVal){
      console.log(newVal)
    }
  },
  computed: {
    currentPathName(){
      return this.$store.state.currentPathName;
    }
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      localStorage.removeItem("menus")
      this.$message.success("退出成功!")
    }
  },
  props: {
    collapseBtnClass: String,
    collapse: {
      type: Function
    },
    user: Object
  }
}
</script>

<style scoped>

</style>