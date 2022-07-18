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

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
              row-key="id"
              default-expand-all
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80px"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column prop="pagePath" label="页面路径"></el-table-column>
      <el-table-column label="图标" align="center">
        <template slot-scope="scope">
          <i :class="scope.row.icon" style="font-size: 18px;"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column label="操作" width="320" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleAddChild(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单 <i class="el-icon-plus"></i></el-button>
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

<!--    <div style="padding: 10px 0">-->
<!--      <el-pagination-->
<!--          @size-change="handleSizeChange"-->
<!--          @current-change="handleCurrentChange"-->
<!--          :current-page="pageNum"-->
<!--          :page-sizes="[5, 8, 10]"-->
<!--          :page-size="pageSize"-->
<!--          layout="total, sizes, prev, pager, next, jumper"-->
<!--          :total="total">-->
<!--      </el-pagination>-->
<!--    </div>-->

    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
<!--          <el-input v-model="form.icon" autocomplete="off"></el-input>-->
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value" />{{ item.name }}
            </el-option>
          </el-select>
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
      form: {},
      headerBg: 'headerBg',
      options: {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      // 请求分页查询数据
      this.request.get("/menu",{
        params: {
          name: this.name,
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.data
      })
    },
    save(){
      this.request.post("/menu/",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)   //[{},{},{}] => [1,2,3,4]
      this.request.post("/menu/del/batch",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    handleDelete(id){
      this.request.delete("/menu/"+id).then(res =>{
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
      //请求图标的数据
      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })
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
    handleAddChild(pid){
      this.dialogFormVisible = true
      this.form = {}
      if(pid){
        this.form.pid = pid
      }
    }
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee!important;
}
</style>