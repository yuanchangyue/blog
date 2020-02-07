<template>
    <div class="top-nav-bar">
        <span class="simple-menu-item logo-title">BLOG</span>
        <el-menu :default-active="this.$route.path" router class="nav" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1" class="simple-menu-item">控制台</el-menu-item>
            <el-submenu index="2">
                <template slot="title">
                    <i class="el-icon-user"/>个人中心
                </template>
                <el-menu-item index="2-1">个人资料</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
                <template slot="title"><i class="el-icon-edit"/>文章</template>
                <el-menu-item index="3-1">文章列表</el-menu-item>
                <el-menu-item index="3-2">写文章</el-menu-item>
                <el-menu-item index="/category">分类目录</el-menu-item>
                <el-menu-item index="/tag">文章标签</el-menu-item>
            </el-submenu>
            <el-submenu index="5">
                <template slot="title"><i class="el-icon-setting"/>看点</template>
                <el-menu-item index="5-1">更多文章</el-menu-item>
            </el-submenu>
            <el-menu-item index="3" class="simple-menu-item"><i class="el-icon-picture-outline"/>相册
            </el-menu-item>
            <el-menu-item index="4" class="simple-menu-item"><i class="el-icon-chat-line-round"/>评价
            </el-menu-item>
            <el-submenu index="6">
                <template slot="title"><i class="el-icon-setting"/>设置</template>
                <el-menu-item index="6-1">操作日志</el-menu-item>
            </el-submenu>
        </el-menu>
        <ul class="user-box">
            <li><div class="block"><el-avatar :size="25"/></div></li>
            <li><el-badge :value="0"><i class="el-icon-bell"/></el-badge></li>
        </ul>
    </div>
</template>

<script>
export default {
  data () {
    return {
      activeIndex: '1'
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      console.log(key, keyPath)
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
