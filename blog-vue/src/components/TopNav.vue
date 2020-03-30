<template>
    <div class="top-nav-bar">
      <span @click="drawer = true" class="simple-menu-item logo-title">{{ navButton }}</span>
      <el-menu :default-active="this.$route.path" router class="nav" mode="horizontal">
        <el-submenu :index="''+m.id" v-for="m in menuData" :key="m.id">
          <template slot="title"><i :class="m.icon"/>{{m.name}}</template>
         <el-menu-item :index="c.url" v-for="c in m.children" :key="c.id">{{c.name}}</el-menu-item>
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
      logo: require('../assets/logo.png'),
      menuData: JSON.parse(localStorage.getItem('menu'))
    }
  },
  methods: {
    logout () {
      this.$axios.get('/user/logout').then(value => {
        this.$notify('退出成功')
        this.$router.replace({ path: '/login' })
      })
    }
  },
  created () {
    console.info(this.menuData)
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
