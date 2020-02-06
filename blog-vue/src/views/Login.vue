<template>
    <div class="login" id="Login">
        <transition class="el-zoom-in-top">
            <div style="display: flex;justify-content: center;margin-top: 65px">
                <el-card class="login-card">
                    <h2>登录</h2>
                    <el-form>
                        <el-form-item>
                            <el-input v-model="form.name" prefix-icon="el-icon-user" placeholder="请输入用户名"/>
                        </el-form-item>
                        <el-form-item>
                            <el-input type="password" v-model="form.password" prefix-icon="el-icon-lock"
                                      placeholder="请输入密码"/>
                        </el-form-item>
                        <el-form-item>
                            <el-button style="width: 100%;" type="primary">登录</el-button>
                        </el-form-item>
                        <p class="toregister">没有账号？点击马上
                            <router-link to="/register">注册</router-link>
                        </p>
                    </el-form>
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
        name: ''
      }
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
        margin-top: 60px;
    }
    .login-card{
        width: 400px;
    }
    .toregister{
        text-align: right;
        font-size: 12px;
    }
</style>
