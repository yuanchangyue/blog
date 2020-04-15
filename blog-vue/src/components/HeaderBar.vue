<template>
  <div>
    <header>
      <a href="" class="logo">Blogger</a>
      <ul>
        <li>
          <div class="search-box">
            <input class="search-input" type="text" placeholder="搜索一下">
            <a href="">
              <i class="el-icon-search"/>
            </a>
          </div>
        </li>
        <li><a href="/blog/index">博客</a></li>
        <li><a href="/blog/viewpoint">看点</a></li>
        <li><a href="/login" v-if="userData==null">登陆</a></li>
        <li><a href="/management/personal" v-if="userData!=null">后台管理</a></li>
        <li><a href="#" @click="logout" v-if="userData!=null">退出</a></li>
      </ul>
    </header>
  </div>
</template>

<script>

export default {
  name: 'Header',
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
  },
  mounted () {
    window.addEventListener('scroll', function () {
      var scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      var header = document.querySelector('header')
      header.classList.toggle('sticky', scrollTop > 0)
    })
  }
}
</script>

<style scoped>
  * {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
    font-family: 'Smudgie', serif;
  }

  header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    transition: 0.6s;
    padding: 40px 60px;
    z-index: 9999;
  }

  header.sticky {
    padding: 5px 100px;
    background: #ffffff;
  }

  header .logo {
    position: relative;
    font-weight: 700;
    color: #ffffff;
    text-decoration: none;
    font-size: 2em;
    text-transform: uppercase;
    letter-spacing: 2px;
    transition: .6s;
  }

  header ul {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  header ul li {
    position: relative;
    list-style: none;
  }

  header ul li a {
    position: relative;
    margin: 0 15px;
    text-decoration: none;
    color: #ffffff;
    letter-spacing: 2px;
    font-weight: 700;
    transition: .6s;
    font-size: 18px;
  }

  .banner {
    position: relative;
    width: 100%;
    height: 100vh;
    background: url("../assets/login-bg.jpg");
    background-size: cover;
  }

  header.sticky .logo,
  header.sticky ul li a {
    color: #222222;
  }

  header ul .search-box {
    display: flex;
    height: 30px;
    margin-right: 10px;
    border-radius: 40px;
    background: #ffffff;
    align-items: center;
    transition: .2s ease-in;
  }

  header ul .search-box a {
    color: #6e6d7a;
  }

  header ul .search-box .search-input {
    border: none;
    outline: none;
    font-size: 16px;
    transition: .2s ease-in;
    width: 0;
  }

  header ul .search-box:hover {
    padding-left: 20px;
    padding-right: 5px;
  }

  header ul .search-box:hover > .search-input {
    padding: 2px 5px;
    width: 120px;
  }
</style>
