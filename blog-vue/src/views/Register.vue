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
                    <el-form ref="form" :model="form" size="small">
                        <div v-show="isFirst">
                            <el-form-item>
                                <el-input prefix-icon="el-icon-user-solid" v-model="form.name" placeholder="请输入用户名称"/>
                            </el-form-item>
                            <el-form-item>
                                <el-input prefix-icon="el-icon-user" v-model="form.nickname" placeholder="请输入用户昵称"/>
                            </el-form-item>
                            <el-form-item>
                                <el-input prefix-icon="el-icon-message" v-model="form.email" placeholder="请输入用户邮箱"/>
                            </el-form-item>
                            <el-form-item>
                                <el-input type="password" prefix-icon="el-icon-lock" v-model="form.password" placeholder="请输入用户密码"/>
                            </el-form-item>
                            <el-form-item>
                                <el-input type="password" prefix-icon="el-icon-lock" v-model="form.againPassword"
                                          placeholder="请输入再一次输入密码"/>
                            </el-form-item>
                        </div>
                        <el-form-item v-if="isSecond">
                            <el-input prefix-icon="el-icon-reading" v-model="form.title" placeholder="请输入博客标题"/>
                        </el-form-item>
                        <el-form-item style="text-align: left">
                            <el-button v-if="isBack" @click="back">上一步</el-button>
                            <el-button type="primary" v-if="!isBack" @click="next">下一步</el-button>
                            <el-button type="primary" v-if="isBack">注册</el-button>
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
    return {
      form: {
        name: ''
      },
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
        margin-top: 60px;
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
