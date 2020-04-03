<template>
  <div>
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp" style="height: 100%;padding: 30px 80px;">
      <el-row :gutter="20">
        <el-card>
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="状态">
              <el-select v-model="comment.status" placeholder="审批状态">
                <el-option value="">全部</el-option>
                <el-option label="已经审批" value="1"/>
                <el-option label="未审批" value="0"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button-group>
                <el-button icon="el-icon-search" @click="submit">查询</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="batchPass()">批量通过</el-button>
                <el-button type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
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
              label="id"
              prop="id"
              width="120">
            </el-table-column>
            <el-table-column
              label="状态"
              width="120">
              <template slot-scope="scope">
                <div style="padding: 5px;">
                  <el-badge is-dot :type="scope.row.status===1?'success':'danger'" class="item"
                            style="margin-right:10px;">{{scope.row.status===1?'通过审核':'暂未审核'}}
                  </el-badge>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="content"
              label="评价"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="postVO.title"
              label="评价文章"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="userDTO.username"
              label="评价用户"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="评价时间"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-text="dateFormat(scope.row.createTime)"></span>
              </template>
            </el-table-column>
            <el-table-column
              prop="ipAddress"
              label="IP地址"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作">
              <template slot-scope="scope">
                <el-link type="primary" v-if="scope.row.status!==1" @click="changeStatus(scope.row.id)">通过</el-link>
                <el-divider direction="vertical" v-if="scope.row.status!==1"></el-divider>
                <el-link type="primary" @click="remove(scope.row.id)">删除</el-link>
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
import TopNav from '../../components/TopNav'
import BreadCrumb from '../../components/BreadCrumb'
import moment from 'moment'
export default {
  name: '',
  components: { TopNav, BreadCrumb },
  data () {
    return {
      bread: {
        firstBread: '评论',
        secondBread: '评论管理'
      },
      tableData: [],
      multiple: [],
      loading: true,
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0,
      pageIndex: 0,
      status: '',
      comment: {
        id: '',
        postId: '',
        content: '',
        userId: '',
        parentId: 0,
        status: ''
      },
      userData: JSON.parse(localStorage.getItem('user'))
    }
  },
  methods: {
    batchPass: function () {
      if (this.multiple.length > 0) {
        this.$axios.post('/comments/batchchangestatus',
          this.$qs.stringify({ multiple: this.multiple }, { indices: false })).then(_ => {
          this.$notify.success(_.data.data)
          this.getCommentList()
        })
      }
    },
    batchRemove: function () {
      if (this.multiple.length > 0) {
        this.$axios.delete('/comments/batchremove',
          { params: { multiple: this.multiple },
            paramsSerializer: params => {
              return this.$qs.stringify(params, { indices: false })
            }
          }).then(_ => {
          this.$notify.success(_.data.data)
          this.getCommentList()
        })
      }
    },
    submit () {
      this.getCommentList()
    },
    changeStatus (id) {
      this.comment.id = id
      this.comment.status = 1
      this.$axios.post('/comments/changestatus', this.comment).then(_ => {
        this.$notify.success('审核通过')
        this.getCommentList()
        this.comment.status = ''
      })
    },
    remove (id) {
      this.$confirm('确定删除吗？').then(_ => {
        this.$axios.delete('/comments/' + id).then(_ => {
          this.$notify.success(_.data.data)
          this.getCommentList()
        })
      }).catch(_ => {
        this.$notify.error('已经取消删除')
      })
    },
    getCommentList () {
      this.comment.userId = this.userData.id
      this.$axios.post('/comments/list', this.comment).then(_ => {
        this.setPageValue(_.data)
        console.info(_.data)
      })
    },
    setPageValue (value) {
      this.loading = false
      this.tableData = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    handleSelectionChange (val) {
      this.multiple = []
      for (let n = 0; val.length > n; n++) { this.multiple.push(val[n].id) }
    },
    handleCurrentChange (val) {
      if (val) {
        this.currentPage = val
      }
      this.$axios.post('/comments/list?pageIndex=' + this.currentPage + '&pageSize=' + this.pageSize, this.comment).then(_ => {
        this.setPageValue(_.data)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    }
  },
  created () {
    this.getCommentList()
  }
}
</script>

<style scoped>
</style>
