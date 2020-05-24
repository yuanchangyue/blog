<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="container" style="margin-bottom: 40px;">
      <el-row :gutter="30" v-loading="loading">
        <el-col :span="8" v-for="user in userList" :key="user.userDTO.id">
          <el-card class="user-card">
            <div class="user-img">
              <el-avatar class="personal-avatar" :size="80"
                         v-if="user.userDTO.avatar!==null || user.userDTO.avatar!==''"
                         :src="handlerUrl(user.userDTO.avatar)"/>
              <el-avatar class="personal-avatar" :size="80" v-else :src="bgImg" @click="dialogVisible = true"/>
            </div>
            <div class="user-info">
              <h2 style="color: #409EFF ;font-size: 28px" v-text="user.userDTO.username"></h2>
              <p style="color: #303133" v-text="'邮箱：'+user.userDTO.email"></p>
              <p style="color: #303133;font-size: 14px"
                 v-text="'简介：'+user.userDTO.description"></p>
              <div class="roles">
                <span>当前角色：{{ user.role.name }}</span>
              </div>
              <el-divider content-position="left">拥有的菜单</el-divider>
              <div class="menu-info">
                <el-tag v-for="item in user.menuVos"
                        :key="item.name"
                        :id="item.id"
                        style="margin: 5px;">
                  {{ item.name }}
                </el-tag>
              </div>
              <div v-if="user.role.id === 2">
                <el-divider/>
                <el-button-group >
                  <el-button @click="updateRole(user.userDTO.id)" size="small" type="primary">升级
                  </el-button>
<!--                  <el-button size="small" type="danger" >注销</el-button>-->
                </el-button-group>
              </div>
            </div>
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
  name: 'usermanage',
  components: { TopNav, BreadCrumb },
  data: function () {
    return {
      bread: {
        firstBread: '系统',
        secondBread: '用户管理'
      },
      bgImg: require('../../assets/login-bg.jpg'),
      pageTotal: 0,
      currentPage: 0,
      pageSize: 10,
      loading: true,
      userList: [],
      username: ''
    }
  },
  methods: {
    getUserList () {
      this.$axios.get('/system/userlist?pageIndex=' + this.currentPage + '&pageSize=' + this.pageSize + '&username=' + this.username).then(value => {
        this.setPageValue(value)
      })
    },
    setPageValue (value) {
      this.userList = value.data.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
      this.loading = false
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    },
    updateRole (id) {
      this.$axios.put('/system/changeuserrole/' + id).then(_ => {
        if (_.data.data) {
          this.$notify.success('升级成功')
          this.getUserList()
        }
      })
    }
  },
  created () {
    this.getUserList()
  }
}
</script>

<style scoped>
  .user-card .user-info .roles{
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .user-card {
    text-align: center;
    margin-top: 2rem;
    box-sizing: border-box;
  }
</style>
