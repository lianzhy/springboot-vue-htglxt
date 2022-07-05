<template>
  <el-menu :default-openeds="['1','3']" style="min-height: 100%;overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           @open="handleOpen"
           @close="handleClose"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../assets/logo.png" style="width: 20px; position:relative; top: 5px;margin-right: 5px">
      <b style="color: white" v-show="logoTextShow">后台管理系统</b>
    </div>
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-message"></i>
        <span slot="title">导航一</span>
      </template>
      <el-menu-item-group>
        <template slot="title">分组一</template>
        <el-menu-item index="1-1">选项1</el-menu-item>
        <el-menu-item index="1-2">选项2</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="分组2">
        <el-menu-item index="1-3">选项3</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="1-4">
        <template slot="title">选项4</template>
        <el-menu-item index="1-4-1">选项4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-menu"></i>
        <span slot="title">导航二</span>
      </template>
      <el-menu-item-group>
        <template slot="title">分组一</template>
        <el-menu-item index="2-1">选项1</el-menu-item>
        <el-menu-item index="2-2">选项2</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="分组2">
        <el-menu-item index="2-3">选项3</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="2-4">
        <template slot="title">选项4</template>
        <el-menu-item index="2-4-1">选项4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-submenu index="3">
      <template slot="title"><i class="el-icon-setting"></i>
        <span slot="title">导航三</span>
      </template>
      <el-submenu index="3-4">
        <template slot="title">选项4</template>
        <el-menu-item index="3-4-1">选项4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      index: ['1','3']
    }
  },
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log("open")
      // console.log(keyPath[0]);
      // console.log(this.index.indexOf(keyPath[0]))
      if(this.index.indexOf(keyPath[0])===-1){
        this.index.push(keyPath[0])
      }
      // console.log(this.index)
    },
    handleClose(key, keyPath) {
      // console.log("close")
      //console.log(keyPath[0]);
      // console.log(this.index.indexOf(keyPath[0]))
      if(this.index.indexOf(keyPath[0]) !== -1){
        this.index.splice(this.index.indexOf(keyPath[0]),1)
      }
      console.log(this.index.length)
      if(this.index.length === 0 || (this.index.includes('3') && this.index.length === 1)){
        this.$emit('collapse');
        this.index = ['1','3']
      }

    }
  }
}
</script>

<style scoped>

</style>