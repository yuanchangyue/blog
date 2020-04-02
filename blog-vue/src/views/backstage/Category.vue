<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp">
      <el-row :gutter="20">
        <el-col :md="12" :sm="24">
          <el-card class="box-card">
            <div slot="header">
              <span>{{ title }}</span>
            </div>
            <el-form>
              <el-form-item label="名称:">
                <el-input size="small" v-model="form.name" placeholder="请输入类别名称"/>
                <div class="prompt-form">* 名称不能空,长度不能超过20</div>
              </el-form-item>
              <el-form-item label="别名:">
                <el-input size="small" type="text" v-model="form.slugName" placeholder="请输入类别别名"/>
                <div class="prompt-form">* 别名不能空,长度不能超过20,不能重复</div>
              </el-form-item>
              <el-form-item label="父级类别">
                <el-select v-model="form.parentId" clearable style="width: 100%;" size="small" placeholder="请选择类别">
                  <el-option
                    v-for="item in parentCategoryDate"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
                <div class="prompt-form">* 选择父级类别(一级分类), 一级分类默认不选</div>
              </el-form-item>
              <el-form-item label="描述">
                <el-input type="textarea" v-model="form.description" placeholder="请输入描述内容"/>
                <div class="prompt-form">* 关于类别描述</div>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="small" @click="createAndUpdate">{{button}}</el-button>
                <el-button v-if="!isInsert" size="small" @click="returnInsert()">返回新增</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
        <el-col :md="12" :sm="24">
          <el-card class="box-card">
            <div slot="header">
              <span>全部分类</span>
            </div>
            <div>
              <el-table v-loading="loading"
                        :data="tableData"
                        style="width: 100%">
                <el-table-column
                  prop="name"
                  label="名称"
                  width="120">
                </el-table-column>
                <el-table-column
                  prop="slugName"
                  label="别名"
                  width="120">
                </el-table-column>
                <el-table-column
                  prop="description"
                  label="描述">
                </el-table-column>
                <el-table-column
                  label="操作">
                  <template slot-scope="scope">
                    <div>
                      <el-link type="primary" @click="updateCategory(scope.$index)">编辑</el-link>&nbsp;
                      <el-link type="primary" @click="handleDelete(scope.$index)">删除</el-link>
                    </div>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <el-pagination
              @current-change="handleCurrentChange"
              @size-change="handleSizeChange"
              layout="total, sizes,prev, pager, next"
              :page-sizes="[5,8,12]"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              :total="pageTotal">
            </el-pagination>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import TopNav from '../../components/TopNav'
import BreadCrumb from '../../components/BreadCrumb'

export default {
  name: 'Category',
  components: { TopNav, BreadCrumb },
  data () {
    return {
      bread: {
        firstBread: '文章',
        secondBread: '类别'
      },
      title: '新增类别',
      button: '立即创建',
      categories: [],
      isInsert: true,
      currentCategoryId: '',
      form: {
        name: '',
        slugName: '',
        description: '',
        parentId: ''
      },
      parentCategoryDate: [],
      tableData: [],
      loading: true,
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0
    }
  },
  mounted () {
    this.showList()
    this.showParentCategory()
  },
  methods: {
    showList () {
      this.$axios.get('/category').then(value => {
        this.setPageValue(value)
      }).catch(_ => {})
    },
    setPageValue (value) {
      this.loading = false
      this.tableData = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
    },
    handleCurrentChange (val) {
      var page = { pageIndex: val, pageSize: this.pageSize }
      this.$axios.get('/category', { params: page }).then(value => {
        this.setPageValue(value)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    },
    createAndUpdate () {
      var formData = {
        name: this.form.name,
        slugName: this.form.slugName,
        description: this.form.description,
        parentId: this.form.parentId
      }
      if (this.isInsert) {
        this.$axios.post('/category', formData).then(_ => {
          this.$notify.success('类别添加成功')
          this.showList()
        }).catch(_ => {})
      } else {
        this.$axios.put('/category/' + this.currentCategoryId, formData).then(_ => {
          this.$notify.success('修改标签成功!')
          this.showList()
        }).catch(_ => {})
      }
    },
    handleDelete (index) {
      var id = this.tableData[index].id
      this.$confirm('此操作永久删除该类别以及使用该类别的文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('/category/' + id).then(() => {
          this.$notify.success('删除成功')
          this.tableData.splice(index, 1)
        }).catch(() => {
          this.showMessage('info', '已取消删除')
        })
      }).catch(() => {
        this.showMessage('info', '已取消删除')
      })
    },
    updateCategory (index) {
      var id = this.tableData[index].id
      this.$axios.get('/category/' + id).then(value => {
        this.title = '修改类别'
        this.button = '更新'
        this.currentCategoryId = id
        this.isInsert = false
        this.form.name = value.data.name
        this.form.slugName = value.data.slugName
        this.form.description = value.data.description
        this.form.parentId = value.data.parentId === 0 ? '' : value.data.parentId
      }).catch(_ => {})
    },
    returnInsert () {
      this.title = '新增类别'
      this.button = '立即创建'
      this.form.name = ''
      this.form.slugName = ''
      this.form.name = ''
      this.form.slugName = ''
      this.form.description = ''
      this.form.parentId = ''
      this.isInsert = true
    },
    showParentCategory () {
      this.$axios.get('/category/parent').then(value => {
        this.parentCategoryDate = value.data.data
      })
    }
  }
}
</script>

<style scoped>
  .el-col, .el-pagination {
    margin: 10px 0;
  }
</style>
