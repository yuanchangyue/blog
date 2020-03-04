<template>
    <div class="top-nav-bar">
        <span @click="drawer = true" class="simple-menu-item logo-title">{{ navButton }}</span>
        <el-menu :default-active="this.$route.path" router class="nav" mode="horizontal">
            <el-menu-item index="1" class="simple-menu-item hidden-md-and-down">控制台</el-menu-item>
            <el-submenu index="2" class="hidden-md-and-down">
                <template slot="title">
                    <i class="el-icon-user"/>个人中心
                </template>
                <el-menu-item index="2-1">个人资料</el-menu-item>
            </el-submenu>
            <el-submenu index="3" class="hidden-md-and-down">
                <template slot="title"><i class="el-icon-edit"/>文章</template>
                <el-menu-item index="/postlist">文章列表</el-menu-item>
                <el-menu-item index="/post">写文章</el-menu-item>
                <el-menu-item index="/category">分类目录</el-menu-item>
                <el-menu-item index="/tag">文章标签</el-menu-item>
            </el-submenu>
            <el-submenu index="4" class="hidden-md-and-down">
                <template slot="title"><i class="el-icon-setting"/>看点</template>
                <el-menu-item index="4-1">更多文章</el-menu-item>
            </el-submenu>
            <el-menu-item index="5" class="simple-menu-item hidden-md-and-down"><i class="el-icon-picture-outline"/>相册
            </el-menu-item>
            <el-menu-item index="6" class="simple-menu-item hidden-md-and-down"><i class="el-icon-chat-line-round"/>评价
            </el-menu-item>
            <el-submenu class="hidden-md-and-down" index="7">
                <template slot="title"><i class="el-icon-setting"/>设置</template>
                <el-menu-item index="7-1">操作日志</el-menu-item>
            </el-submenu>
        </el-menu>
        <ul class="user-box">
            <li>
              <el-dropdown>
                <el-avatar :size="25" :src="logo"></el-avatar>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item><el-link @click="logout" :underline="false">退出登陆</el-link></el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </li>
            <li>
                <el-badge :value="0"><i class="el-icon-bell"/></el-badge>
            </li>
        </ul>
    </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/display.css'

export default {
  data () {
    return {
      navButton: 'LOGO',
      activeIndex: '1',
      drawer: false,
      direction: 'ltr',
      menuMode: 'horizontal',
      logo: require('../assets/logo.png')
    }
  },
  methods: {
    logout () {
      console.info('aadf')
      this.$axios.get('/user/logout').then(value => {
        console.info(value.data)
        this.$message({
          message: '已经退出',
          type: 'info'
        })
        this.$router.replace({ path: '/login' })
      })
    }
  }
}
</script>

<style scoped>
    .top-nav-bar {
        margin: -8px;
        padding: 0 80px;
        box-shadow: 0 0 5px rgba(0, 0, 0, .2);
        display: flex;
        background-color: #fff;
    }
    @media only screen and (max-width: 768px) {
        .top-nav-bar{
            padding: 0 10px;
        }
    }
    @media screen and (min-width:768px) and (max-width:1024px){
        .top-nav-bar{
            padding: 0 10px;
        }
    }
    .logo-title {
        line-height: 60px;
        font-size: 24px;
    }
    .nav {
        border-bottom: 0;
    }
    .simple-menu-item {
        margin: 0 5px;
    }
    .user-box{
        flex: 1;
        text-align: right;
        list-style: none;
        color: #909399;
    }
    .user-box li{
        float: right;
        margin: 0 10px;
    }
</style>
