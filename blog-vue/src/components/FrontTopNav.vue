<template >
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <nav class="nav">
      <div style="width: 60%;display: flex;">
      <el-col class="logo">LOGO</el-col>
      <el-col class="link">
        <el-link class="link-text" href="/blog/index">Blog</el-link>
        <el-link class="link-text" href="/blog/viewpoint">看点</el-link>
        <el-link class="link-text" href="/login" v-if="userData==null">登陆</el-link>
        <el-link class="link-text" href="/management/personal" v-if="userData!=null">后台管理</el-link>
        <el-link class="link-text" @click="logout" v-if="userData!=null">退出</el-link>
        <form class="form-inline position-relative">
          <input class="form-control" type="search" placeholder="搜索">
          <button class="query-btn" type="submit"><i class="el-icon-search" style="width: 25px;height: 25px;"></i>
          </button>
        </form>
      </el-col>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'FrontTopNav',
  data () {
    return {
      userData: JSON.parse(localStorage.getItem('user'))
    }
  },
  methods: {
    logout () {
      this.$axios.get('/user/logout').then(_ => {
        localStorage.clear()
        this.$router.replace({ path: '/blog/viewpoint' })
      })
    }
  }
}
</script>

<style scoped>
  .nav {
    padding: 10px;
    width: 100%;
    display: flex;
    position: fixed;
    justify-content: center;
    left: 50%;
    top: -40px;
    transform: translate(-50%, 50%);
    z-index: 1000;
    background: #ffffff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  }
  .logo {
    flex: 1;
    line-height: 60px;
    font-family: Smudgie,serif;
    font-size: 40px;
  }

  .link {
    width: 600px;
    display: flex;
    text-underline: none !important;
  }

  .link-text {
    font-weight: 300;
    justify-content: end;
    margin: 0 20px;
    color: #303133;
    font-size: 18px;
  }

  .query-btn {
    font-size: 20px;
    border: 0;
    background: transparent;
    position: absolute;
    right: 0;
    top: 50%;
    outline: none;
    -webkit-transform: translateY(-50%);
    transform: translateY(-50%);
  }

  .position-relative {
    position: relative !important;
  }

  .form-inline .form-control {
    display: inline-block;
    width: auto;
    vertical-align: middle;
  }

  .form-control {
    margin-top: 15px;
    margin-left: 20px;
    padding-right: 40px;
    height: 30px;
    font-size: 18px;
    outline: none;
    border: 0;
    font-weight: 200;
    border-bottom: 1px solid #ababab;
    border-radius: 0;
  }
  .form-control:hover{
    border-bottom: 1px solid #409EFF;
  }
</style>
