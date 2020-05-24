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
          <el-avatar :size="25" :src="handlerUrl(userData.avatar)"></el-avatar>
          <el-dropdown-menu slot="dropdown" style="width: 200px;">
            <el-row style="display: flex;justify-content: center">
              <el-avatar :src="handlerUrl(userData.avatar)" fit="cover" :size="40"></el-avatar>
            </el-row>
            <el-row>
              <p style="text-align: center;color: #606266" v-text="userData.username"></p>
              <p style="text-align: center;color: #909399" v-text="userData.email"></p>
            </el-row>
            <el-divider style="padding: 0 !important;;margin: 0 !important;"/>
            <el-row style="display: flex;justify-content: center;padding: 0;margin: 0;">
              <el-link @click="personal" :underline="false" type="primary">个人资料</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link @click="logout" :underline="false" type="danger">退出登陆</el-link>
            </el-row>
          </el-dropdown-menu>
        </el-dropdown>
      </li>
      <li>
        <el-link href="/blog/viewpoint"><i class="el-icon-link"/></el-link>
      </li>
    </ul>
  </div>
</template>

<script>

export default {
  data () {
    return {
      navButton: 'BLOGGER',
      activeIndex: '1',
      drawer: false,
      direction: 'ltr',
      menuMode: 'horizontal',
      logo: require('../assets/logo.png'),
      menuData: JSON.parse(sessionStorage.getItem('menu')),
      userData: JSON.parse(sessionStorage.getItem('user'))
    }
  },
  methods: {
    logout () {
      this.$axios.get('/user/logout').then(_ => {
        sessionStorage.clear()
        this.$router.replace({ path: '/login' })
      })
    },
    personal () {
      this.$router.replace({ path: '/management/personal' })
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
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
    align-items: center;
    justify-content: space-between;
    background-color: #fff;
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

  .user-box {
    padding-top: 20px;
    text-align: right;
    list-style: none;
    color: #909399;
  }

  .user-box li {
    float: right;
    margin: 0 10px;
  }

  .el-icon-link{
    font-size: 26px;
  }
</style>
