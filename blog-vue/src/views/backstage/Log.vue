<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp">
      <el-card>
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="用户">
            <el-input v-model="query.username" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button type="primary" icon="el-icon-search" @click="getlist">查询</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      <el-table
        v-loading="loading"
        ref="multipleTable"
        :data="logData"
        tooltip-effect="dark"
        style="width: 100%">
        <el-table-column
          label="ID"
          prop="id"
          align="center"
          width="50">
        </el-table-column>
        <el-table-column
          width="120"
          label="操作人"
          align="center"
          prop="username">
        </el-table-column>
        <el-table-column
          label="操作"
          width="120"
          prop="operation">
        </el-table-column>
        <el-table-column
          label="方法"
          prop="method"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="参数"
          prop="params"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="IP地址"
          prop="ipAddress">
        </el-table-column>
        <el-table-column
          width="200"
          label="创建时间"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-text="dateFormat(scope.row.createTime)"></span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
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
        :page-sizes="[8,16,24]"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :total="pageTotal">
      </el-pagination>
      </el-card>
    </div>
  </div>
</template>

<script>

import TopNav from '../../components/TopNav'
import BreadCrumb from '../../components/BreadCrumb'
import moment from 'moment'

export default {
  name: 'Site',
  components: { TopNav, BreadCrumb },
  data: function () {
    return {
      bread: {
        firstBread: '系统',
        secondBread: '日志'
      },
      query: {
        username: ''
      },
      pageTotal: 0,
      currentPage: 0,
      pageSize: 8,
      loading: true,
      logData: [],
      cateData: []
    }
  },
  methods: {
    getlist () {
      this.$axios.get('/system/logs?pageIndex=' + this.currentPage + '&pageSize=' + this.pageSize + '&username=' + this.query.username).then(value => {
        if (value.data.code === 200) {
          this.setPageValue(value.data)
        }
      })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    handleCurrentChange (val) {
      if (val) {
        this.currentPage = val
      }
      this.getlist()
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    },
    setPageValue (value) {
      this.logData = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
      this.loading = false
    },
    remove (logId) {
      this.$axios.delete('/system/logs/' + logId).then(_ => {
        this.$notify.success('删除成功')
        this.getlist()
      })
    }
  },
  created () {
    this.getlist()
  }
}
</script>

<style scoped>
</style>
