<template>
  <div class="blog-all" style="width: 100%;background: #f4f4f4;">
    <header-bar/>
    <div class="query-title container">
      <h1>搜索结果</h1>
    </div>
    <section class="query-box container">
      <div class="query">
        <div class="empty-box" v-if="queryResult.articles==null">
          <img src="../../assets/empty.svg" alt="">
        </div>
        <div class="post-warp" v-else>
          <div class="posts-content" data-aos="zoom-in" v-for="p in queryResult.articles" :key="p.id">
            <div class="post-image">
              <div>
                <img v-if="p.thumbnail!=null && p.thumbnail!==''" :src="handlerUrl(p.thumbnail)" alt="">
                <img v-else :src="defaultBg" alt="">
              </div>
            </div>
            <div class="post-info">
              <a class="post-title" href="#" v-html="p.title"></a>
              <p class="post-summary" v-html="p.summary"></p>
            </div>
            <button class="btn post-btn" @click="toPost(p.id)">更多<i class="el-icon-right"></i></button>
          </div>
        </div>
        <el-divider content-position="left">网络文章</el-divider>
        <div class="crawler-post-warp">
          <article data-aos="zoom-in-up" class="post-panel" v-for="(p) in queryResult.crawlerSmartisonPosts"
                   :key="p.id">
            <img :src="p.headpic" v-show="p.headpic!==''&&p.headpic!==null" alt="">
            <h2>
              <a class="post-info" @click="toPage(p.id)" v-text="p.title" href="#"></a>
            </h2>
            <p v-text="p.brief"></p>
            <div>
              <el-image v-if="p.prePic2!==''&&p.prePic2!==null" :src="p.prePic2"
                        style="height: 80px;width: 80px;margin:10px;" fit="cover"></el-image>
              <el-image v-if="p.prePic2!==''&&p.prePic2!==null" :src="p.prePic3"
                        style="height: 80px;width: 80px;margin:10px;" fit="cover"></el-image>
            </div>
          </article>
        </div>
        <el-divider content-position="left">媒体站点</el-divider>
        <div class="site-warp">
          <div @click="moreSite(s.id)" data-aos="fade-up" :data-aos-delay="index*100" class="sites" v-for="(s,index) in queryResult.siteVOList"
               :key="s.id">
            <img :src="s.pic"/>
            <div class="site-info">
              <h2 v-text="s.name"></h2>
              <p v-text="s.brief"></p>
              <p v-text="s.crawlerPostCate.name"></p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <el-backtop></el-backtop>
    <FrontFooter/>
  </div>
</template>

<script>
import HeaderBar from '../../components/PostHeaderBar'
import FrontFooter from '../../components/FrontFooter'

export default {
  name: 'Query',
  components: { HeaderBar, FrontFooter },
  data () {
    return {
      queryResult: JSON.parse(sessionStorage.getItem('queryItem')),
      defaultBg: require('../../assets/login-bg.jpg')
    }
  },
  methods: {
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    },
    toPost (id) {
      sessionStorage.setItem('userPostId', id)
      this.$router.push({ name: 'UserPostPage', params: { postId: id } })
    },
    subStringToContent (str) {
      return str.substr(0, 200)
    },
    toPage (id) {
      this.$router.push({ name: 'PostPage', params: { postId: id } })
    },
    moreSite (id) {
      sessionStorage.setItem('siteId', id)
      this.$router.push({ name: 'SitePage', params: { siteId: id } })
    }
  },
  mounted () {
  }
}
</script>

<style scoped>

  .query-title {
    margin-top: 15rem;
    margin-bottom: 10rem;
    width: 60%;
  }

  section {
    position: relative;
    background: #ffffff;
    width: 60%;
    margin-bottom: 5rem;
  }

  /*------文章列表------*/

  .posts-content{
    border: 1px solid #f4f4f4;
    padding: .5rem;
    margin-bottom: 1rem;
  }
  .post-summary {
    margin: 1rem 0;
  }

  .post-summary >>> .highlight,
  .post-title >>> .highlight {
    background: #409EFF;
    color: white;
    padding: 5px;
  }

  .post-image, .post-info {
    padding: 1rem 2rem;
    position: relative;
  }

  .post-image .post-info span {
    margin: 0 .5rem;
  }

  .post-image img {
    width: 100%;
    height: 400px;
    object-fit: cover;
    transition: 0.6s linear;
  }

  .post-btn {
    background: #292C30;
    border-radius: 0;
    padding: .7rem 1.5rem;
    width: 10rem;
    color: #e1e1e1;
    font-size: 16px;
    font-weight: 700;
    margin-left: 2rem;
  }

  .query {
    position: relative;
    justify-content: center;
    width: 100%;
    max-width: 800px;
    margin: 40px auto;
    padding: 0;
  }

  /*------爬虫文章------*/
  .crawler-post-warp {
    width: 100%;
    display: grid;
    overflow: hidden;
    box-sizing: border-box;
    grid-template-columns: 33% 33% 33%;
    grid-gap: 5px;
  }

  .crawler-post-warp .post-panel img {
    max-width: 100%;
    width: 100%;
    height: 200px;
    object-fit: cover;
  }

  .crawler-post-warp .post-panel h2 a {
    color: #000000;
  }

  .crawler-post-warp .post-panel p {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    line-clamp: 3;
    -webkit-box-orient: vertical;
  }

  .crawler-post-warp .post-panel h2 {
    margin: 10px 0 10px;
    padding: 0;
    font-size: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    line-clamp: 1;
    -webkit-box-orient: vertical;
  }

  .crawler-post-warp .post-panel {
    width: 100%;
    padding: 10px;
    background: #ffffff;
    border: 1px solid #f4f4f4;
    box-sizing: border-box;
  }

  /*------媒体站点------*/
  .site-warp {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 10px 10px;
  }

  .site-warp .sites {
    width: 100%;
    height: 100%;
    display: flex;
    cursor: pointer;
  }

  .site-warp .sites .site-info {
    margin: 1rem;
  }

  .site-warp .sites .site-info p {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    line-clamp: 1;
    -webkit-box-orient: vertical;
  }

  .site-warp .sites .site-info h2 {
    margin: 0 0 1rem 0;
    padding: 0;
    font-size: 24px;
  }

  .site-warp .sites img {
    width: 100px;
    height: 100px;
  }

  /*-------屏幕自适应 start-------*/
  @media only screen and (max-width: 1130px) {
    .crawler-post-warp {
      grid-template-columns: 50% 50%;
    }
  }

  @media only screen and (max-width: 750px) {
    .crawler-post-warp {
      grid-template-columns: repeat(1, 1fr);
    }
  }

  @media only screen and (max-width: 520px) {
  }

  /*-------屏幕自适应 end-------*/
</style>
