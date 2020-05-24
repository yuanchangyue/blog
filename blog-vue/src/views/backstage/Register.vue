<template>
    <div class="login-box" id="register">
        <div style="display: flex;justify-content: center;margin-top: 65px">
            <el-card class="box-card">
                <h4 style="text-align: left">注册向导</h4>
                <el-divider/>
                <el-steps :active="active" align-center>
                    <el-step title="请填写博主信息"/>
                    <el-step title="请填写博客信息"/>
                </el-steps>
                <el-divider/>
                <transition name="el-fade-in-linear">
                    <el-form ref="form" :model="form" size="small" :rules="rules" >
                        <div v-show="isFirst">
                            <el-form-item label="用户名称" prop="name">
                                <el-input prefix-icon="el-icon-user-solid" v-model="form.name" placeholder="请输入用户名称"/>
                            </el-form-item>
                            <el-form-item label="用户邮箱" prop="email">
                                <el-input prefix-icon="el-icon-message" v-model="form.email" placeholder="请输入用户邮箱"/>
                            </el-form-item>
                            <el-form-item label="密码" prop="password">
                                <el-input type="password" prefix-icon="el-icon-lock" v-model="form.password"
                                          placeholder="请输入用户密码"/>
                            </el-form-item>
                            <el-form-item label="再次输入密码" prop="againPassword">
                                <el-input type="password" prefix-icon="el-icon-lock" v-model="form.againPassword"
                                          placeholder="请输入再一次输入密码"/>
                            </el-form-item>
                        </div>
                        <div v-show="isSecond">
                            <el-form-item label="博客昵称" prop="nickname" >
                                <el-input prefix-icon="el-icon-user" v-model="form.nickname" placeholder="请输入博客昵称"/>
                            </el-form-item>
                            <el-form-item label="博客简介" prop="description">
                                <el-input prefix-icon="el-icon-reading" v-model="form.description" placeholder="请输入博客简介"/>
                            </el-form-item>
                        </div>
                        <el-form-item style="text-align: left">
                            <el-button v-if="isBack" @click="back">上一步</el-button>
                            <el-button type="primary" v-if="!isBack" @click="next">下一步</el-button>
                            <el-button type="primary" v-if="isBack" @click="register('form')">注册</el-button>
                        </el-form-item>
                    </el-form>
                </transition>
            </el-card>
        </div>
    </div>
</template>

<script>
export default {
  name: 'register',
  components: {
  },
  data () {
    var nameRepeatValidate = (rule, value, callback) => {
      this.$axios.get('/user/name/' + this.form.name).then(_ => {
        console.log(_.data.data)
        if (_.data.data !== null) {
          callback(new Error('该用户名已经存在！'))
        } else {
          callback()
        }
      })
    }
    return {
      form: {
        name: '',
        password: '',
        nickname: '',
        email: '',
        description: '',
        againPassword: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' },
          { validator: nameRepeatValidate, message: '该用户名已存在', trigger: 'blur' }
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
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        againPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ]
      },
      responseResult: [],
      active: 1,
      isFirst: true,
      isBack: false,
      isSecond: false
    }
  },
  methods: {
    next () {
      this.isBack = true
      this.isFirst = false
      this.isSecond = true
      if (this.active++ > 1) this.active = 1
    },
    back () {
      this.active = 1
      this.isFirst = true
      this.isBack = false
      this.isSecond = false
    },
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/user', {
            username: this.form.name,
            password: this.form.password,
            nickname: this.form.nickname,
            email: this.form.email,
            againPassword: this.form.againPassword,
            description: this.form.description
          }).then(successResponse => {
            this.responseResult = JSON.stringify(successResponse.data)
            this.$notify.success('注册成功！')
            this.$router.replace({ path: '/login' })
          }).catch(() => {})
        } else {
          this.$message.error('请输入有效的信息!')
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
    #register {
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 140px;
    }
    .text {
        font-size: 14px;
    }
    .item {
        padding: 18px 0;
    }
    .box-card {
        width: 480px;
    }
</style>
