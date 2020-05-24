<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp">
      <el-row :gutter="30">
        <el-col :span="10" style="position: relative">
          <el-card :body-style="{ padding: '0px' }">
            <div class="personal-box" @click="dialogVisible = true">
              <el-image class="personal-bg" :src="bgImg" fit="cover" alt=""/>
              <el-image class="personal-avatar" v-if="userData.avatar!==null" :size="80" :src="handlerUrl(userData.avatar)" />
              <el-image class="personal-avatar" v-else :size="80" :src="bgImg" @click="dialogVisible = true"/>
            </div>
            <div class="personal-info">
              <span style="color: #409EFF ;font-size: 28px" v-text="userData.username"></span>
              <span style="color: #303133" v-text="userData.email"></span>
              <span style="color: #303133;font-size: 14px"
                    v-text="'简介：'+userData.description"></span>
            </div>
            <el-divider content-position="left">拥有的标签</el-divider>
            <div class="tags-list">
              <el-tag v-for="item in user.tags"
                      :key="item.name"
                      :id="item.id"
                      style="margin-right: 10px;">
                {{ item.name }}
              </el-tag>
              <el-link href="/management/tag" type="primary">查看全部标签<i class="el-icon-right el-icon--right"></i></el-link>
            </div>
            <el-divider content-position="left">拥有的分类</el-divider>
            <div class="cate_list">
              <el-table v-loading="loading"
                        :data="user.categories"
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
              </el-table>
              <el-link href="/management/category" type="primary">查看全部分类<i class="el-icon-right el-icon--right"></i></el-link>
            </div>
          </el-card>
        </el-col>
        <el-col :span="14">
          <el-tabs type="border-card">
            <el-tab-pane label="基本资料">
              <div class="personal-basic">
                <el-form ref="userForm" :model="userForm" size="small" :rules="rules" >
                  <el-form-item label="用户名称" prop="username">
                    <el-input prefix-icon="el-icon-user-solid" v-model="userForm.username"/>
                  </el-form-item>
                  <el-form-item label="用户昵称" prop="nickname">
                    <el-input prefix-icon="el-icon-user-solid" v-model="userForm.nickname"/>
                  </el-form-item>
                  <el-form-item label="用户邮箱" prop="email">
                    <el-input prefix-icon="el-icon-message" v-model="userForm.email"/>
                  </el-form-item>
                  <el-form-item label="简介" prop="description">
                    <el-input prefix-icon="el-icon-message" v-model="userForm.description"/>
                  </el-form-item>
                </el-form>
                <el-button type="primary" @click="updateUser('userForm')">更新</el-button>
              </div>
            </el-tab-pane>
            <el-tab-pane label="修改密码">
              <el-form ref="userPasswordForm" :rules="rule2" :model="userPasswordForm" size="small">
                <el-form-item label="旧密码" prop="oldPassword">
                  <el-input type="password" show-password prefix-icon="el-icon-lock"
                            v-model="userPasswordForm.oldPassword" placeholder="请输入旧密码"/>
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword">
                  <el-input type="password" show-password prefix-icon="el-icon-lock"
                            v-model="userPasswordForm.newPassword" placeholder="请输入新密码"/>
                </el-form-item>
                <el-form-item label="再一次新密码">
                  <el-input type="password" show-password prefix-icon="el-icon-lock"
                            v-model="userPasswordForm.againPassword" placeholder="请再一次输入新密码"/>
                </el-form-item>
              </el-form>
              <el-button type="primary" @click="updatePassword('userPasswordForm')">修改密码</el-button>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </div>
    <el-dialog
      title="修改头像"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose">
      <el-row>
      <el-col style="margin: 5px" :span="5" v-for="item in user.attachmentList" :key="item.id">
        <transition name="el-fade-in">
          <el-card>
            <el-image :src="handlerUrl(item.path)" @click="saveAvatar(item.path)" fit="cover" :lazy="true" style="width: 100%; height: 200px"/>
          </el-card>
        </transition>
      </el-col>
      </el-row>
      <el-pagination
        style="margin: 10px 0;"
        background
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
        layout="total, sizes,prev, pager, next"
        :page-sizes="[8,12]"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :total="pageTotal">
      </el-pagination>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">保 存</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>

import TopNav from '../../components/TopNav'
import BreadCrumb from '../../components/BreadCrumb'
import qs from 'qs'

export default {
  name: 'Personal',
  components: { TopNav, BreadCrumb },
  data: function () {
    return {
      bgImg: require('../../assets/login-bg.jpg'),
      bread: {
        firstBread: '个人中心',
        secondBread: '个人资料'
      },
      userData: '',
      user: {
        categories: [],
        tags: [],
        attachmentList: []
      },
      userForm: {
        username: '',
        nickname: '',
        email: '',
        description: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入介绍', trigger: 'blur' },
          { max: 1032, message: '长度不能超过1023个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]
      },
      rule2: {
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        newAgainPassword: [
          { required: true, message: '请第二次输入密码', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ]
      },
      userPasswordForm: {
        userId: '',
        oldPassword: '',
        newPassword: '',
        newAgainPassword: ''
      },
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0,
      loading: true,
      dialogVisible: false
    }
  },
  methods: {
    showUser () {
      this.$axios.get('/user').then(value => {
        this.userData = value.data.data
        this.userPasswordForm.userId = value.data.data.id
        this.userForm.username = value.data.data.username
        this.userForm.nickname = value.data.data.nickname
        this.userForm.email = value.data.data.email
        this.userForm.description = value.data.data.description
      })
    },
    showTag () {
      this.$axios.get('/tag').then(res => {
        this.user.tags = res.data
      }).catch(_ => {})
    },
    showCate () {
      this.$axios.get('/category').then(value => {
        this.user.categories = value.data.list
        this.loading = false
      }).catch(_ => {})
    },
    showAttachment () {
      this.$axios.get('/attachment').then(value => {
        this.setPageValue(value)
      })
    },
    setPageValue (value) {
      this.user.attachmentList = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
    },
    updateUser (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.put('/user', this.userForm).then(_ => {
            this.$notify.success('用户信息更新成功')
            this.showUser()
          })
        } else {
          this.$message.error('请输入有效的信息!')
          return false
        }
      })
    },
    updatePassword (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/user/modifypw', qs.stringify(this.userPasswordForm)).then(_ => {
            this.$notify.success('密码更新成功，请重新登陆！')
            this.logout()
          })
        } else {
          this.$message.error('请输入有效的信息!')
          return false
        }
      })
    },
    logout () {
      this.$axios.get('/user/logout').then(_ => {
        this.$router.replace({ path: '/login' })
      })
    },
    saveAvatar (url) {
      this.$axios.post('/user/modifyavatar', qs.stringify({ avatar: url })).then(_ => {
        this.$notify.success('头像保存成功')
        this.dialogVisible = false
        this.showUser()
      })
    },
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    handleCurrentChange (val) {
      this.currentPage = val
      var page = { pageIndex: val, pageSize: this.pageSize }
      this.$axios.get('/attachment', { params: page }).then(value => {
        this.setPageValue(value)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    }
  },
  created () {
    this.showTag()
    this.showCate()
    this.showUser()
    this.showAttachment()
  }
}
</script>

<style scoped>
  .personal-box {
    width: 100%;
    height: 100%;
    z-index: 1;
    position: relative;
  }

  .personal-bg, .personal-bg {
    position: absolute;
  }

  .personal-bg {
    z-index: 2;
    width: 100%;
    height: 120px;
  }

  .personal-avatar {
    z-index: 999;
    width: 100px;
    height: 100px;
    left: 50%;
    transform: translate(-50%, 50%);
  }

  .personal-info {
    text-align: center;
    margin-top: 60px;
  }

  .personal-info span {
    display: block;
  }
  .tags-list,.cate_list{
    margin: 10px
  }
</style>
