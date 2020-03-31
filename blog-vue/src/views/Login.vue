<template>
  <div class="login" id="Login">
    <transition class="el-zoom-in-top">
      <div style="display: flex;justify-content: center;margin-top: 65px;">
        <el-card class="login-card" body-style="padding:0px">
          <el-container>
            <el-row style="width: 400px;">
              <el-image class="login-img" :src="bgImg" fit="cover" style="width: 100%;height: 100%;"></el-image>
            </el-row>
            <el-row style="width: 380px;padding: 30px 30px 30px 50px;">
              <h2 style="text-align: left">登录</h2>
              <el-form>
                <el-form-item>
                  <el-input v-model="form.name" prefix-icon="el-icon-user" placeholder="请输入用户名"/>
                </el-form-item>
                <el-form-item>
                  <el-input type="password" v-model="form.password" prefix-icon="el-icon-lock"
                            placeholder="请输入密码"/>
                </el-form-item>
                <el-form-item>
                  <el-button style="width: 100%;" @click="login()" type="primary">登录</el-button>
                </el-form-item>
                <p class="toregister">没有账号？点击马上
                  <router-link to="/register" style="color: #409EFF;text-underline-style: none">注册</router-link>
                </p>
                <el-form-item>
                  <el-divider><p style="color: #909399">其它方式登陆</p></el-divider>
                </el-form-item>
              </el-form>
            </el-row>
          </el-container>
        </el-card>
      </div>
    </transition>
  </div>
</template>

<script>

export default {
  name: 'Login',
  components: {},
  data () {
    return {
      form: {
        name: '',
        password: ''
      },
      bgImg: require('../assets/login-bg.jpg')
    }
  },
  methods: {
    login () {
      var formData = {
        username: this.form.name,
        password: this.form.password
      }
      this.$axios.post('/user/login', formData).then(_ => {
        console.info(_.data)
        var routerItem = _.data.data.routerVOS
        var menu = _.data.data.menuVos
        localStorage.setItem('user', _.data.data.useDTO)
        localStorage.setItem('router', JSON.stringify(routerItem))
        localStorage.setItem('menu', JSON.stringify(menu))
        this.$notify.success(this.form.name + '登陆成功')
        this.$router.push('/postlist')
      }).catch(err => {
        this.$message.error('登陆失败， 原因：' + err.data.msg)
      })
    }
  }
}
</script>

<style scoped>
  #Login {
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 140px;
  }

  .login-card {
    width: 800px;
  }
  .toregister {
    text-align: right;
    font-size: 12px;
  }
  .login-img::before {
    position: absolute;
    content: "";
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.3);
  }
</style>
