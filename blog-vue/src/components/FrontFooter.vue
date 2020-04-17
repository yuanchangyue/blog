<template>
  <footer class="footer">
    <div class="container">
      <div class="logo">
        <h2 style=" font-family: Smudgie,serif;font-size: 40px; color: #f0f2f5">BLOGGER</h2>
      </div>
      <div class="about-us">
        <h1>联系我</h1>
        <p><i class="el-icon-phone icon"></i><a href="">1530306873</a></p>
        <p><i class="el-icon-add-location icon"><a href=""></a>重庆市</i></p>
        <p><i class="el-icon-message icon"></i><a href="">yuanchangyue@qq.com</a></p>
      </div>
      <div class="social">
        <h1>社交</h1>
        <p>
          <font-awesome-icon :icon="['fab', 'qq']" class="icon"></font-awesome-icon>
          <a href="">604028466@qq.com</a>
        </p>
        <p>
          <font-awesome-icon :icon="['fab', 'weixin']" class="icon"></font-awesome-icon>
          <a href="">nansu05</a>
        </p>
        <p>
          <font-awesome-icon :icon="['fab', 'github']" class="icon"></font-awesome-icon>
          <a href="http://www.github.com/yuanchangyue">www.github.com/yuanchangyue</a>
        </p>
      </div>
      <div class="fast-link">
        <h1>快速连接</h1>
        <div>
          <a href="">首页</a>
        </div>
        <div>
          <a href="">看点</a>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>

  footer.footer h1 {
    color: #f0f2f5;
  }

  footer.footer .icon {
    font-size: 16px;
    color: #f0f2f5;
  }

  footer.footer a {
    color: #e1e1e1;
    margin-left: 1rem;
    font-weight: 700;
  }

  footer.footer {
    height: 100%;
    background: url('../assets/footer.jpg') center;
  }

  footer.footer .container {
    display: grid;
    grid-template-columns: 0 25% 25% 25% 25%;
  }

  footer.footer .container > div {
    flex-grow: 1;
    flex-basis: 0;
    padding: 2rem 2rem;
  }

  @media only screen and (max-width: 1130px) {
    footer.footer .container {
      grid-template-columns: repeat(2, 1fr);
    }
  }

  @media only screen and (max-width: 750px) {
    footer.footer .container {
      grid-template-columns: repeat(1, 1fr);
    }
  }

  @media only screen and (max-width: 520px) {
    footer.footer .container > div {
      padding: 1rem .9rem !important;
    }
  }
</style>
