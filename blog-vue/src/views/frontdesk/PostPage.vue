<template>
  <div class="blog-all" id="blog-all" style="width: 100%;background: #f4f4f4;">
    <header-bar/>
    <div class="content" id="content">
      <ul class="side-bar">
        <li :class="collectionStatus?checkStatus:normalStatus">
          <font-awesome-icon style="cursor: pointer" @click="collect" :icon="['fa','bookmark']"></font-awesome-icon>
        </li>
      </ul>
      <div class="post" v-html="postObj.content"></div>
      <div class="post-end">
        <el-divider/>
        <p class="end-text">本文章来自爬虫,重新排版处理.<el-link :href="postObj.url">查看原文</el-link></p>
      </div>
    </div>
    <FrontFooter/>
    <el-backtop/>
  </div>
</template>

<script>
import HeaderBar from '../../components/PostHeaderBar'
import FrontFooter from '../../components/FrontFooter'

export default {
  name: 'PostPage',
  data () {
    return {
      post: {
        id: ''
      },
      postObj: '',
      userData: JSON.parse(sessionStorage.getItem('user')),
      collection: {
        userId: '',
        crawlerPostId: ''
      },
      collectionStatus: false,
      normalStatus: 'side-bar-li',
      checkStatus: 'side-bar-li-hover'
    }
  },
  components: { HeaderBar, FrontFooter },
  methods: {
    getPost () {
      this.$axios.get('/crawlerpost/' + this.post.id).then(value => {
        this.postObj = value.data.data
        this.check()
      })
    },
    check () {
      this.collection.userId = this.userData.id
      this.collection.crawlerPostId = this.post.id
      this.$axios.post('/collection/check', this.collection).then(_ => {
        this.collectionStatus = _.data.data
      })
    },
    collect () {
      if (this.userData == null) {
        this.$message.error('登陆后才可以收藏该文章')
        return
      }
      if (this.collectionStatus) {
        this.$axios.delete('/collection/' + this.userData.id + '/' + this.post.id).then(_ => {
          this.$notify.success(_.data.data)
          this.check()
        })
      } else {
        this.collection.userId = this.userData.id
        this.collection.crawlerPostId = this.post.id
        this.$axios.post('/collection', this.collection).then(_ => {
          this.$notify.success('收藏成功')
          this.check()
        })
      }
    }
  },
  created () {
    this.post.id = this.$route.params.postId
    this.getPost()
  }
}
</script>

<style scoped>

  .content {
    max-width: 800px;
    height: 100%;
    margin: 15rem auto;
    background: #ffffff;
    box-shadow: 0 1px 2px rgba(0, 0, 0, .5);
    transition: all .3s ease-in 0s;
  }

  .content .post {
    max-width: 750px;
    padding: 4rem;
    margin: 0 auto;
  }

  /*------ 侧边栏 ------*/

  .side-bar {
    position: fixed;
    left: 400px;
    top: 400px;
    list-style: none;
  }

  .side-bar .side-bar-li {
    width: 60px;
    color: #909399;
    height: 60px;
    line-height: 60px;
    background: #e1e1e1;
    border-radius: 50%;
    margin-bottom: 10px;
    text-align: center;
    transition: all .3s linear 0s;
  }

  .side-bar .side-bar-li-hover {
    width: 60px;
    color: #409EFF;
    height: 60px;
    line-height: 60px;
    background: #e1e1e1;
    border-radius: 50%;
    margin-bottom: 10px;
    text-align: center;
    transition: all .3s linear 0s;
  }

  .side-bar .side-bar-li:hover {
    color: #409EFF;
    cursor: pointer;
  }

  /*------ 侧边栏 ------*/

  /*------ 文章css -----*/

  .content .post >>> p, div, h2, h4, h5, h6 {
    color: #333;
    line-height: 29px;
  }

  .content .post >>> img {
    margin: 8px 0 8px 0;
    max-width: 100%;
    width: 100%;
    vertical-align: middle;
  }

  /*------ 文章css -----*/

  /*------ 文章结尾 -----*/
  .end-text {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    line-height: 22px;
    text-align: center;
    color: #655e5e;
    padding: 1.5rem 0 2rem 0;
  }

  /*------ 文章结尾 -----*/

  @media (max-width: 1200px) {
    .content {
      width: 85%;
    }
    .side-bar {
      display: none;
    }
  }

  @media (max-width: 768px) {
    .content {
      width: 95%;
    }
  }
</style>
