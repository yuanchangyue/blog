<template>
  <div class="nav-box">
    <header>
      <a href="#" class="logo">BLOGGER</a>
      <div class="toggle-collapse" @click="collapse">
        <div class="toggle-icons">
          <i class="el-icon-s-fold"></i>
        </div>
      </div>
      <ul>
        <li>
          <div class="search-box">
            <input class="search-input" type="text" placeholder="搜索一下">
            <a href="#">
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
      userData: JSON.parse(sessionStorage.getItem('user'))
    }
  },
  methods: {
    logout () {
      this.$axios.get('/user/logout').then(_ => {
        sessionStorage.clear()
        this.$router.replace({ path: '/blog/viewpoint' })
      })
    },
    collapse () {
      var header = $('header')
      header.toggleClass('collapse')
    }
  },
  mounted () {
    window.addEventListener('scroll', function () {
      var scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      var header = document.querySelector('header')
      if (header !== null) {
        header.classList.toggle('sticky', scrollTop > 0)
      }
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

  .nav-box {
    background: #1a1a1a;
    display: flex;
    justify-content: flex-end;
  }

  header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    transition: all 0.6s ease-in-out;
    padding: 40px 60px;
    z-index: 9;
    height: 0;
    min-height: 10vh;
    overflow: hidden;
  }

  header.sticky {
    padding: 2rem 100px;
    background: #ffffff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  }

  header .logo {
    position: relative;
    font-weight: 700;
    color: #000000;
    text-decoration: none;
    font-size: 2em;
    letter-spacing: 2px;
    transition: .6s;
  }

  header .toggle-collapse {
    position: absolute;
    top: 0;
    cursor: pointer;
    width: 90%;
    display: none;
  }

  header .toggle-collapse .toggle-icons {
    display: flex;
    justify-content: flex-end;
    padding-top: 40px;
  }

  header .toggle-collapse .toggle-icons i {
    color: #ffffff;
    font-size: 30px;
  }

  .collapse {
    height: 20rem;
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
    color: #000000;
    letter-spacing: 2px;
    font-weight: 700;
    transition: .6s;
    font-size: 18px;
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

  @media only screen and (max-width: 750px) {
    header, header ul {
      flex-direction: column;
    }

    header .toggle-collapse {
      display: block;
    }

    header .toggle-collapse .toggle-icons i {
      color: #000000;
    }

    header .toggle-collapse .toggle-icons {
      padding-top: 10px;
    }
  }
</style>
