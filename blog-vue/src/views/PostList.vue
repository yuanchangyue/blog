<template>
    <div class="all-warp">
      <TopNav/>
      <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
      <div class="content-warp">
        <el-row :gutter="20">
          <el-card>
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="关键字">
                <el-input v-model="form.keyWords" placeholder="关键字"></el-input>
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="form.status" placeholder="文章状态">
                  <el-option value="">全部</el-option>
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-form-item>
              <el-form-item label="文章分类">
                <el-select v-model="form.categoryId" placeholder="文章分类">
                  <el-option value="">全部</el-option>
                  <el-option v-for="item in categoryData"
                             :key="item.id"
                             :label="item.name"
                             :value="item.id"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button-group>
                  <el-button icon="el-icon-search" @click="submit">查询</el-button>
                  <el-button type="danger" icon="el-icon-delete" @click="deletePost">批量删除</el-button>
                  <el-button type="primary" @click="toAdd" >添加<i class="el-icon-right el-icon--right"></i></el-button>
                </el-button-group>
              </el-form-item>
            </el-form>
            <el-table
              v-loading="loading"
              ref="multipleTable"
              :data="tableData"
              tooltip-effect="dark"
              style="width: 100%"
              @selection-change="handleSelectionChange">
              <el-table-column
                prop="id"
                type="selection"
                width="80">
              </el-table-column>
              <el-table-column
                label="标题"
                width="120">
                <template slot-scope="scope">
                  <span v-if="scope.row.status===2" v-bind:style="titleColor">{{scope.row.title}}</span>
                  <span v-else>{{scope.row.title}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="状态"
                width="120">
                <template slot-scope="scope">
                  <div>
                    <el-badge is-dot class="item" v-bind:type="status(scope.row.status)" >
                    {{ scope.row.status===0 ? '已发布': scope.row.status===1 ? '草稿' : scope.row.status===2 ? '回收站' :
                    scope.row.status===3? '加密': '无状态' }}
                    </el-badge>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="分类"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-tag v-for="item in scope.row.categories"
                          :key="item.id"
                          :id="item.id"
                          style="margin-left: 5px">
                    {{ item.name }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column
                prop="address"
                label="标签"
                show-overflow-tooltip>
                <template slot-scope="scope">
                <el-tag v-for="item in scope.row.tags"
                        :key="item.id"
                        :id="item.id"
                        style="margin-left: 5px">
                  {{ item.name }}
                </el-tag>
                </template>
              </el-table-column>
              <el-table-column
                prop="address"
                label="评价"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="visits"
                label="访问"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-badge v-bind:value="scope.row.visits" class="item" type="primary" ></el-badge>
                </template>
              </el-table-column>
              <el-table-column
                label="发布时间"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-text="dateFormat(scope.row.editTime)"></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="address"
                label="操作">
                <template slot-scope="scope">
                  <el-link type="primary" @click="updatePost(scope.row.id)">编辑</el-link>
                  <el-divider v-if="scope.row.status!==2" direction="vertical"></el-divider>
                  <el-link v-if="scope.row.status!==2" type="primary" @click="putIntoTrash(scope.row.id)">回收站</el-link>
                  <el-divider direction="vertical"></el-divider>
                  <el-link type="primary" @click="deletePost(scope.row.id)">删除</el-link>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              align="right"
              style="margin: 10px 0;"
              background
              @current-change="handleCurrentChange"
              @size-change="handleSizeChange"
              layout="total, sizes,prev, pager, next"
              :page-sizes="[5,8,12]"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              :total="pageTotal">
            </el-pagination>
          </el-card>
        </el-row>
      </div>
    </div>
</template>

<script>

import TopNav from '../components/TopNav'
import BreadCrumb from '../components/BreadCrumb'
import moment from 'moment'

export default {
  name: 'PostList',
  components: { TopNav, BreadCrumb },
  data: function () {
    return {
      bread: {
        firstBread: '文章',
        secondBread: '文章列表'
      },
      tableData: [],
      multipleDelete: [],
      form: {
        keyWords: '',
        status: '',
        categoryId: ''
      },
      categoryData: [],
      tagData: [],
      options: [{
        value: '0',
        label: '已发布'
      }, {
        value: '1',
        label: '草稿箱'
      }, {
        value: '2',
        label: '回收站'
      }, {
        value: '3',
        label: '加密'
      }],
      loading: true,
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0,
      titleColor: 'color:#e1e1e1',
      statusCode: 2
    }
  },
  methods: {
    showPostList () {
      this.$axios.get('/post').then(value => {
        this.setPageValue(value)
      }).catch(_ => {
      })
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
      this.$axios.get('/post', { params: page }).then(value => {
        this.setPageValue(value)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    },
    showTagList () {
      this.$axios.get('/tag/list').then(value => {
        console.info(value.data)
        this.tagData = value.data.data
      }).catch(_ => {
      })
    },
    showCategory () {
      this.$axios.get('/category/list').then(value => {
        console.info(value.data)
        this.categoryData = value.data.data
      }).catch(_ => {
      })
    },
    status (s) {
      switch (s) {
        case 0:
          return 'success'
        case 1:
          return 'warning'
        case 2:
          return 'info'
        case 3:
          return 'danger'
      }
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    submit () {
      this.$axios.post('/post/query', this.form).then(value => {
        console.info(value.data)
        this.tableData = value.data.list
      }).catch(_ => {})
    },
    deletePost (id) {
      if (id !== null) {
        this.multipleDelete.push(id)
      }
      if (this.multipleDelete.length === 0) {
        this.$notify('没有选择对象')
        return
      }
      this.$confirm('此操作不可逆，确定删除吗？').then(_ => {
        this.$axios.delete('/post', {
          params: {
            multipleDelete: this.multipleDelete
          },
          paramsSerializer: params => {
            return this.$qs.stringify(params, { indices: false })
          }
        }).then(_ => {
          this.multipleDelete = []
          if (_.data.code === 200) {
            this.$notify.success('文章已经移至删除')
            this.showPostList()
          }
        }).catch(_ => {
          this.$notify.error('文章删除失败')
        })
      })
    },
    handleSelectionChange (val) {
      this.multipleDelete = []
      for (let n = 0; val.length > n; n++) { this.multipleDelete.push(val[n].id) }
    },
    toAdd () {
      this.$router.push('/post')
    },
    updatePost (id) {
      this.$router.push({ name: 'post', params: { postId: id } })
    },
    putIntoTrash (id) {
      var data = { postId: id, status: 2 }
      this.$axios.post('/post/status', this.$qs.stringify(data)).then(_ => {
        this.$notify.success('移至回收站')
        this.showPostList()
      })
    }
  },
  mounted () {
    this.showPostList()
    this.showTagList()
    this.showCategory()
  }
}
</script>

<style scoped>
  .item{
    margin-top: 10px;
    margin-right: 40px;
  }
</style>
