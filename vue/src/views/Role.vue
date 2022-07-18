<template>
  <div>

    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
<!--      <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>-->
<!--      <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>-->
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          @confirm="delBatch"
          confirm-button-text='确定'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
      >
        <el-button type="danger" slot="reference" class="ml-5">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
<!--      <el-upload-->
<!--          action="http://localhost:8080/user/import" style="display: inline-block" class="mr-5"-->
<!--          :show-file-list="false"-->
<!--          :accept="'.xlsx'"-->
<!--          :on-success="handleExcelImportSuccess">-->
<!--        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--      </el-upload>-->

<!--      <el-button type="primary" @click="exp">导出 <i class="el-icon-top"></i></el-button>-->
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80px"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="flag" label="唯一标识"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="selectMenu(scope.row.id)" class="ml-5">分配菜单 <i class="el-icon-menu"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              @confirm="handleDelete(scope.row.id)"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
          >
            <el-button type="danger" slot="reference" class="ml-5">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 8, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="菜单分配" :visible.sync="menuDialogVis" width="30%">
      <el-tree
          :data="menuData"
          :props="props"
          node-key="id"
          ref="tree"
          :default-checked-keys="checks"
          :default-expanded-keys="expends"
          show-checkbox>
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span><i :class="data.icon"/> {{ data.name }}</span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVis = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      dialogFormVisible: false,
      multipleSelection: [],
      menuDialogVis: false,
      form: {},
      headerBg: 'headerBg',
      menuData: [],
      props: {
        label: 'name',
      },
      expends: [],
      checks: [],
      roleId: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      // 请求分页查询数据
      this.request.get("/role/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save(){
      this.request.post("/role/",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    saveRoleMenu(){
      console.log(this.$refs.tree.getCheckedKeys())
      this.request.post("/role/roleMenu/" + this.roleId,this.$refs.tree.getCheckedKeys()).then(res => {
        if(res.code === '200'){
          this.$message.success("绑定成功！")
          this.menuDialogVis = false
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)   //[{},{},{}] => [1,2,3,4]
      this.request.post("/role/del/batch",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    handleDelete(id){
      this.request.delete("/role/"+id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    handleEdit(row){
      this.form = Object.assign({},row)
      this.dialogFormVisible = true
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    reset(){
      this.name = "",
      this.load()
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:8080/role/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("文件导入成功！")
      this.load()
    },
    selectMenu(roleId){
      this.menuDialogVis = true;
      this.roleId = roleId
      //请求菜单数据
      this.request.get("/menu").then(res => {
        this.menuData = res.data

        //把后台返回的菜单数据id给数组
        this.expends = this.menuData.map(v => v.id)
      })

      this.request.get("/role/roleMenu/"+roleId).then(res => {
        this.checks = res.data
      })
    },
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee!important;
}
</style>