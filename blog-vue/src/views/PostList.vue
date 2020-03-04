<template>
    <div class="all-warp">
      <TopNav/>
      <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
      <div class="content-warp">
        <el-row :gutter="20">
          <el-card>
            <el-table
              ref="multipleTable"
              :data="tableData"
              tooltip-effect="dark"
              style="width: 100%">
              <el-table-column
                prop="id"
                type="selection"
                width="80">
              </el-table-column>
              <el-table-column
                prop="title"
                label="标题"
                width="120">
              </el-table-column>
              <el-table-column
                prop="status"
                label="状态"
                width="120">
              </el-table-column>
              <el-table-column
                prop=""
                label="分类"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="address"
                label="标签"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="address"
                label="评价"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="address"
                label="访问"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="address"
                label="发布时间"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="address"
                label="操作"
                show-overflow-tooltip>
              </el-table-column>
            </el-table>
          </el-card>
        </el-row>
      </div>
    </div>
</template>

<script>

import TopNav from '../components/TopNav'
import BreadCrumb from '../components/BreadCrumb'

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
      multipleSelection: []
    }
  },
  methods: {
    showPostList () {
      this.$axios('/post').then(value => {
        this.tableData = value.data.list
      }).catch(reason => {
      })
    }
  }
}
</script>
