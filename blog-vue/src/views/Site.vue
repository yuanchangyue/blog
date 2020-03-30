<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp">
      <el-card>
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="关键字">
            <el-input v-model="query.keyWords" placeholder="关键字"></el-input>
          </el-form-item>
          <el-form-item label="类型">
              <el-select v-model="query.cateId" placeholder="类型">
              <el-option value="">全部</el-option>
              <!--<el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>-->
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button type="primary" icon="el-icon-search" @click="submit">查询</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      <el-table
        v-loading="loading"
        ref="multipleTable"
        :data="siteData"
        tooltip-effect="dark"
        style="width: 100%">
        <el-table-column
          label="序号"
          type="index"
          align="center"
          width="50">
        </el-table-column>
        <el-table-column
          width="120"
          label="图标"
          align="center"
          prop="name">
          <template slot-scope="scope">
            <el-avatar shape="square" :size="50" :src="scope.row.pic"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column
          label="站点名称"
          width="120"
          prop="name">
        </el-table-column>
        <el-table-column
          label="简介"
          prop="brief"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          label="RRS"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link type="primary" :href="scope.row.rssUrl" v-text="scope.row.rssUrl"></el-link>
          </template>
        </el-table-column>
        <el-table-column
          label="文章数量"
          width="80"
          prop="articleNum">
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
            <el-link type="primary" >删除</el-link>
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

import TopNav from '../components/TopNav'
import BreadCrumb from '../components/BreadCrumb'
import moment from 'moment'

export default {
  name: 'Site',
  components: { TopNav, BreadCrumb },
  data: function () {
    return {
      bread: {
        firstBread: '看点',
        secondBread: '站点仓库'
      },
      query: {
        keyWords: '',
        cateId: ''
      },
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0,
      loading: true,
      siteData: []
    }
  },
  methods: {
    getlist () {
      this.$axios.post('/site', this.query).then(value => {
        if (value.data.code === 200) {
          console.info(value.data)
          this.setPageValue(value.data)
        }
      })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    handleCurrentChange (val) {
      console.info('handleCurrentChange' + val)
      console.info('handleCurrentChange' + this.currentPage)
      if (val) {
        this.currentPage = val
      }
      this.$axios.post('/site?pageIndex=' + this.currentPage + '&pageSize=' + this.pageSize, this.query).then(value => {
        this.setPageValue(value.data)
      })
    },
    handleSizeChange (val) {
      console.info('handleSizeChange->' + val)
      this.pageSize = val
      this.handleCurrentChange()
    },
    setPageValue (value) {
      this.siteData = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
      this.loading = false
    },
    submit () {
      this.$axios.post('/site?pageIndex=' + this.currentPage + '&pageSize=' + this.pageSize, this.query).then(value => {
        this.setPageValue(value.data)
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
