<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav/>
    <div class="content">
      <ul class="side-bar">
        <li :class="collectionStatus?checkStatus:normalStatus">
          <font-awesome-icon style="cursor: pointer" @click="collect" :icon="['fa','bookmark']"></font-awesome-icon>
        </li>
      </ul>
      <div class="post" v-html="postObj.content"></div>
      <div class="post-end" style="text-align: center">
        <el-divider/>
        <p class="end-text">本文章来自爬虫,重新排版处理.</p>
        <el-link :href="postObj.url">查看原文</el-link>
      </div>
    </div>
    <FrontFooter/>
    <el-backtop></el-backtop>
  </div>
</template>

<script>
import FrontTopNav from '../../components/FrontTopNav'
import FrontFooter from '../../components/FrontFooter'

export default {
  name: 'PostPage',
  data () {
    return {
      post: {
        id: ''
      },
      postObj: '',
      userData: JSON.parse(localStorage.getItem('user')),
      collection: {
        userId: '',
        crawlerPostId: ''
      },
      collectionStatus: false,
      normalStatus: 'side-bar-li',
      checkStatus: 'side-bar-li-hover'
    }
  },
  components: { FrontTopNav, FrontFooter },
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
        console.info(_.data.data)
        this.collectionStatus = _.data.data
      })
    },
    collect () {
      console.info(this.userData)
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
          console.info(_.data.data.id)
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
  body {
    padding: 0;
    margin: 0;
  }
  .content {
    margin: 80px auto;
    width: 750px;
    padding-top: 30px;
    height: 100%;
  }
  .side-bar {
    position: fixed;
    left: 300px;
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
    transition: all .3s linear 0s ;
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
    transition: all .3s linear 0s ;
  }
  .side-bar .side-bar-li:hover{
    color: #409EFF;
    cursor: pointer;
  }
  .content .post >>> p, div, h2, h4, h5, h6 {
    color: #333;
    line-height: 29px;
    margin-top: 20px;
  }
  .content .post >>> img {
    margin: 8px 0 8px 0;
    max-width: 100%;
    width: 100%;
    vertical-align: middle;
  }
  .end-text{
    font-size: 12px;
    line-height: 22px;
    text-align: center;
    color: #655e5e;
    margin-top: 40px;
  }
</style>
