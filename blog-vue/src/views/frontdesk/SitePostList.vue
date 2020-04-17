<template>
  <div id="last-blog">
    <div class="blog-container">
      <div class="owl-carousel owl-theme blog-post">
        <article class="blog-content" v-for="(p) in postList" :key="p.id">
          <img :src="p.headpic">
          <div class="blog-title">
            <h3 v-text="p.title"></h3>
          </div>
          <button class="btn btn-blog">更多</button>
          <span v-text="p.createTime"></span>
        </article>
      </div>
      <div class="owl-navigation">
        <span class="owl-nav-prev"><i class="el-icon-back"/></span>
        <span class="owl-nav-next"><i class="el-icon-right"/></span>
      </div>
    </div>
  </div>
</template>

<script>
import '../../assets/css/owl.carousel.min.css'
import '../../assets/css/owl.theme.default.min.css'
export default {
  name: '',
  data () {
    return {
      postList: []
    }
  },
  methods: {
    getPostList () {
      this.$axios.get('/site?pageIndex=1&pageSize=8&siteId=1795')
        .then(value => {
          this.postList = value.data.data.list
          this.$nextTick(function () {
            this.init()
          })
        })
    },
    init () {
      $('.owl-carousel').owlCarousel({
        loop: true,
        autoplay: true,
        autoplayTimeout: 3000,
        nav: true,
        dots: false,
        navText: [$('.owl-navigation .owl-nav-prev'), $('.owl-navigation .owl-nav-next')],
        responsive: {
          0: {
            items: 1
          },
          600: {
            items: 3
          },
          1000: {
            items: 3
          }
        }
      })
    }
  },
  created () {
    this.getPostList()
  }
}
</script>

<style scoped>

  #last-blog {
    height: 100vh;
    width: 100%;
  }

  #last-blog .blog-post {
    padding-top: 6rem;
  }

  #last-blog .blog-post .blog-content {
    display: flex;
    flex-direction: column;
    text-align: center;
    width: 80%;
    margin: 3rem 2rem;
    box-shadow: 0 15px 20px rgba(0, 0, 0, .2);
  }

  #last-blog .blog-content .blog-title {
    padding: 2rem 0;
  }

  .owl-nav-prev,
  .owl-nav-next {
    font-size: 2rem;
    background: #333333;
  }
  .owl-nav-prev i,
  .owl-nav-next i {
    outline: none;
  }

</style>
