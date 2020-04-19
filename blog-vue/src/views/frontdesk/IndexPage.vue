<template>
  <div class="blog-all" id="blog-all" style="width: 100%;background: #f4f4f4">
    <header-bar title="记录生活" btn="开始" :banner="true" bg="work.jpg"/>
    <div class="content" id="content">
      <!--最新文章-->
      <section id="last-blog" data-aos="fade-up">
        <div class="container">
          <h1 class="model-title">最新文章</h1>
          <div class="owl-carousel owl-theme blog-post">
            <article class="blog-content" v-for="post in latestPost" :key="post.id">
              <img :src="handlerUrl(post.thumbnail)">
              <div class="blog-title">
                <h3 v-text="post.title"></h3>
              </div>
              <button class="btn btn-blog" @click="toPost(post.id)">更多</button>
              <span class="post-user" v-text="'来自：'+post.userDTO.username"></span>
              <span class="post-time" v-text="dateFormat(post.editTime)"></span>
            </article>
          </div>
          <div class="owl-navigation">
            <span class="owl-nav-prev"><i class="el-icon-back"/></span>
            <span class="owl-nav-next"><i class="el-icon-right"/></span>
          </div>
        </div>
      </section>
      <!--我的文章-->
      <section class="container">
        <div class="site-content">
          <div class="posts">
            <div class="posts-content" data-aos="zoom-in" v-for="p in postData" :key="p.id">
              <div class="post-image">
                <div>
                  <img v-if="p.thumbnail!==''" :src="handlerUrl(p.thumbnail)" alt="">
                  <img v-else :src="defaultBg" alt="">
                </div>
              </div>
              <div class="post-title">
                  <a href="#" v-text="p.title"></a>
                  <p v-text="subStringToContent(p.originalContent)"></p>
              </div>
              <button class="btn post-btn" @click="toPost(p.id)">更多<i class="el-icon-right"></i></button>
              <el-divider/>
            </div>
            <el-pagination
              align="center"
              background
              style="margin: 10px 0;"
              @current-change="handleCurrentChange"
              layout=" prev, pager, next"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              :total="pageTotal">
            </el-pagination>
          </div>
          <aside class="side-bar">
            <div class="category">
              <h1 class="model-title">分类</h1>
              <ul class="category-list">
                <li class="list-items" data-aos="zoom-in-right" :data-aos-delay="index*100"
                    v-for="(c,index) in categoryData" :key="c.id">
                  <a href="#" v-text="c.name" @click="toCate(c.id)"></a>
                </li>
              </ul>
            </div>
            <div class="tags">
              <h1 class="model-title">标签</h1>
              <div class="tags-list flex-row">
                <span class="tag" data-aos="flip-left" :data-aos-delay="index*100" @click="gotoTag(t.id)"
                      v-for="(t,index) in tagData" :key="t.id" v-text="t.name"></span>
              </div>
            </div>
            <div class="collection-post">
              <h1 class="model-title">收藏的文章</h1>
              <div class="posts-content" data-aos="zoom-in" :data-aos-delay="index*100" v-for="(p,index) in collectionData" :key="p.id">
                <div class="post-image">
                  <div>
                    <img v-if="p.crawlerPost!=null" :src="p.crawlerPost.headpic" alt="">
                    <img v-else :src="handlerUrl(p.post.thumbnail)" alt="">
                  </div>
                  <div class="post-info flex-row">
                    <span><i class="el-icon-user-solid"
                             v-text="p.crawlerPost!=null?p.crawlerPost.siteName:p.post.userDTO.username"></i></span>
                    <span><i class="el-icon-data-board">&nbsp;&nbsp;{{p.createTime}}</i></span>
                  </div>
                </div>
                <div class="post-title">
                  <a href="#" v-if="p.crawlerPost!=null" @click="toPage(p.crawlerPost.id)" v-text="p.crawlerPost.title"></a>
                  <a href="#" v-else @click="toPost(p.post.id)" v-text="p.post.title"></a>
                </div>
              </div>
            </div>
          </aside>
        </div>
      </section>
      <div class="site-list">
        <div class="container">
          <h1 class="model-title">收藏的站点</h1>
          <div class="sites flex-row">
            <div class="site-img" @click="moreSite(s.site.id)" v-for="(s,index) in subscriptionData" :key="s.id">
              <img data-aos="fade-up" :data-aos-delay="index*100" :src="s.site.pic"/>
            </div>
          </div>
        </div>
      </div>
    <FrontFooter/>
    </div>
    <el-backtop></el-backtop>
  </div>
</template>

<script>
import '../../assets/css/owl.carousel.min.css'
import '../../assets/css/owl.theme.default.min.css'
import HeaderBar from '../../components/HeaderBar'
import FrontFooter from '../../components/FrontFooter'
import moment from 'moment'

export default {
  name: 'Index',
  components: { HeaderBar, FrontFooter },
  data () {
    return {
      form: {
        keyWords: '',
        status: 0,
        categoryId: ''
      },
      categoryData: [],
      tagData: [],
      postData: [],
      latestPost: [],
      subscriptionData: [],
      collectionData: [],
      pageTotal: 0,
      currentPage: 0,
      pageSize: 3,
      loading: true,
      userData: JSON.parse(localStorage.getItem('user')),
      nullBg: require('../../assets/not_found.svg'),
      defaultBg: require('../../assets/login-bg.jpg')
    }
  },
  methods: {
    showPostList () {
      this.$axios.post('/post/query', this.form).then(value => {
        this.setPageValue(value)
      }).catch(_ => {
      })
    },
    setPageValue (value) {
      console.info(value.data.list)
      this.postData = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
      this.loading = false
    },
    handleCurrentChange (val) {
      this.$axios.post('/post/query?pageIndex=' + val + '&pageSize=' + this.pageSize, this.form).then(value => {
        this.setPageValue(value)
      })
    },
    getLatestPostList () {
      this.$axios.get('/post/latest').then(value => {
        this.latestPost = value.data.data
        console.info(this.latestPost)
        this.$nextTick(function () {
          this.initCarousel()
        })
      })
    },
    showTagList () {
      this.$axios.get('/tag').then(value => {
        this.tagData = value.data
      }).catch(_ => {
      })
    },
    showCategory () {
      this.$axios.get('/category').then(value => {
        this.categoryData = value.data.list
      }).catch(_ => {
      })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    getCollectionList () {
      this.loading = true
      this.$axios.get('/collection/list?userId=' + this.userData.id).then(value => {
        console.info(value.data.data.list)
        this.collectionData = value.data.data.list
        this.loading = false
      })
    },
    getSubscription () {
      this.loading = true
      this.$axios.get('/subscription/' + this.userData.id).then(_ => {
        this.subscriptionData = _.data.data.list
        console.info(this.subscriptionData)
        this.loading = false
      })
    },
    toPost (id) {
      localStorage.setItem('userPostId', id)
      this.$router.push({ name: 'UserPostPage', params: { postId: id } })
    },
    moreSite (id) {
      localStorage.setItem('siteId', id)
      this.$router.push({ name: 'SitePage', params: { siteId: id } })
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    },
    subStringToContent (str) {
      return str.substr(0, 200)
    },
    gotoTag (id) {
      localStorage.setItem('tagId', id)
      this.$router.push({ name: 'TagPage', params: { tagId: id, userId: this.userData.id } })
    },
    toCate (id) {
      localStorage.setItem('cateId', id)
      this.$router.push({ name: 'CatePage', params: { cateId: id, userId: this.userData.id } })
    },
    toPage (id) {
      this.$router.push({ name: 'PostPage', params: { postId: id } })
    },
    initCarousel () {
      $('.owl-carousel').owlCarousel({
        loop: true,
        autoplay: true,
        autoplayTimeout: 5000,
        nav: true,
        dots: false,
        navText: [$('.owl-navigation .owl-nav-prev'), $('.owl-navigation .owl-nav-next')],
        responsive: {
          0: {
            items: 1
          },
          320: {
            items: 1
          },
          560: {
            items: 2
          },
          960: {
            items: 3
          }
        }
      })
    }
  },
  created () {
    this.showPostList()
    this.showTagList()
    this.showCategory()
    this.getLatestPostList()
    this.getSubscription()
    this.getCollectionList()
  }
}
</script>

<style scoped>

  h1.model-title {
    margin: 0 20px;
    padding: 10px 0;
  }

  ul {
    list-style: none;
  }

  a {
    text-decoration: none;
  }

  .flex-row {
    display: flex;
    flex-wrap: wrap;
  }

  /*--------主要内容 start ----------*/
  .site-content {
    position: relative;
    width: 100%;
    display: grid;
    grid-template-columns: 70% 30%;
  }

  .site-content .posts-content > .post-image, .post-title {
    padding: 1rem 2rem;
    position: relative;
  }

  .site-content .posts-content > .post-image .post-info span {
    margin: 0 .5rem;
  }

  .site-content .posts-content > .post-image .post-info {
    position: absolute;
    bottom: 0;
    left: 5vw;
    background: #292C30;
    padding: 1rem;
    border-radius: 3rem;
    color: black;
    font-weight: 700;
  }

  .site-content .posts-content > .post-image > div {
    overflow: hidden;
  }

  .site-content .posts-content > .post-image img {
    width: 100%;
    height: 400px;
    object-fit: cover;
    transition: 0.6s linear;
  }

  .site-content .posts-content > .post-image img:hover {
    transform: scale(1.3);
  }

  .site-content .posts-content .post-title a {
    color: #303133;
    font-size: 1.6rem;
    font-weight: bold;
  }

  .site-content .posts-content .post-btn {
    background: #292C30;
    border-radius: 0;
    padding: .7rem 1.5rem;
    width: 10rem;
    color: #e1e1e1;
    font-size: 16px;
    font-weight: 700;
    margin-left: 2rem;
  }

  /*--------主要内容 end ----------*/

  /*--------侧边栏 start---------*/

  .site-content > .side-bar .category-list .list-items {
    background: #292C30;
    padding: 1rem 1rem;
    margin: .8rem;
    border-radius: 3rem;
    width: 70%;
    display: flex;
    justify-content: space-between;
  }

  .site-content > .side-bar .category-list .list-items a {
    color: #ffffff;
    font-weight: 700;
  }

  .site-content > .side-bar .collection-post .posts-content {
    padding: 1rem 0;
  }

  .site-content > .side-bar .collection-post h2 {
    padding-top: 2rem;
  }

  .site-content > .side-bar .collection-post .post-info {
    padding: .4rem .1rem !important;
    bottom: 0 !important;
    left: 1.5rem !important;
    border-radius: 0 !important;
    background: #fff !important;
  }

  .site-content > .side-bar .collection-post .post-title a {
    font-size: 1rem;
  }

  .site-content > .side-bar .collection-post .posts-content img {
    width: 100%;
    height: auto !important;
  }

  .site-content > .side-bar .tags .tags-list {
    padding-left: 5rem;
  }

  .site-content > .side-bar .tags .tags-list .tag {
    background: #292C30;
    padding: 1rem 2rem;
    border-radius: 3rem;
    margin: .4rem .6rem;
    color: #ffffff;
    font-weight: 700;
  }

  .site-content > .side-bar .tags {
    padding: 4rem 0;

  }

  /*--------侧边栏 end---------*/

  /*--------最新文章轮播 start---------*/
  #last-blog {
    height: 70vh;
    width: 100%;
  }

  #last-blog .blog-post {
    padding-top: 1rem;
    text-align: center;
  }

  #last-blog .blog-post .blog-content .post-user,
  #last-blog .blog-post .blog-content .post-time {
    padding: 0 0 2rem 0;
    font-size: 14px;
    color: #606266;
  }

  #last-blog .blog-post .blog-content {
    background: #fff;
    display: flex;
    flex-direction: column;
    text-align: center;
    width: 80%;
    margin: 3rem 2rem;
    box-shadow: 0 15px 20px rgba(0, 0, 0, .2);
  }

  #last-blog .blog-post .blog-content img {
    width: 100%;
    height: 200px;
    object-fit: cover;
  }

  #last-blog .blog-content .blog-title {
    padding: 2rem 0;
  }

  .btn-blog {
    background: #292C30;
    color: #ffffff;
    width: 10rem;
    border-radius: 20px;
    margin: 0 auto 5px;
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

  /*--------最新文章轮播 end---------*/

  /*-------- 站点列表 start ------*/
  .site-list {
    margin-bottom: 10px;
  }

  .site-list .sites {
    display: flex;
    justify-content: center;
  }

  .site-list .sites .site-img img {
    width: 100%;
    height: auto;
  }

  .site-list .sites .site-img {
    position: relative;
    overflow: hidden;
    width: 10rem;
    height: 10rem;
    border-radius: 50%;
    margin: 1rem 2rem;
    transition: .3s linear;
    cursor: pointer;
  }

  /*-------- 站点列表 end ------*/

  /*-------屏幕自适应 start-------*/
  @media only screen and (max-width: 1130px) {
    .site-content > .side-bar .collection-post .post-info {
      display: none;
    }
  }

  @media only screen and (max-width: 750px) {
    .site-content {
      grid-template-columns: 100%;
    }
  }

  @media only screen and (max-width: 520px) {
  }

  /*-------屏幕自适应 end-------*/
</style>
