<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav></FrontTopNav>
    <div class="content">
      <ul class="side-bar">
        <li class="side-bar-li">
          <font-awesome-icon :icon="['fa','heart']"></font-awesome-icon>
        </li>
        <li class="side-bar-li">
          <font-awesome-icon :icon="['fa','comment']"></font-awesome-icon>
        </li>
      </ul>
      <el-row :gutter="40" v-loading="loading">
        <el-col :span="18">
          <div class="post" v-html="postObj.formatContent"></div>
          <div class="comment-box">
            <h4 style="text-align: center">全部评论</h4>
            <div class="write-comment-box">
              <div class="not-login-comment" style="display: flex;align-items: center ">
                <font-awesome-icon :icon="['fa','user-circle']" style="font-size: 40px;margin-right: 10px;"/>
                <span>请在<el-link type="danger">登陆</el-link>后评论</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="other">
            <div class="latest-post">
              <el-divider content-position="left">最新文章</el-divider>
              <el-row v-for="post in latestPost" :key="post.id">
                <el-col :span="10">
                  <el-image :src="handlerUrl(post.thumbnail)" style="width: 100%;height: 70px;" fit="cover"></el-image>
                </el-col>
                <el-col :span="14">
                  <p style="padding: 0;margin: 0;" v-text="post.title"></p>
                  <summary style="color: #909399;font-size: 12px;line-height: 20px"
                           v-text="'来自：'+post.userDTO.username"></summary>
                  <summary style="color: #909399;font-size: 12px;line-height: 20px"
                           v-text="dateFormat(post.editTime)"></summary>
                </el-col>
              </el-row>
              <el-divider content-position="left">最新分类</el-divider>
              <ul style="display: flex">
                <li v-for="c in categoryData" :key="c.id"><a class="cate-box" href="#" v-text="c.name"/></li>
              </ul>
              <el-divider content-position="left">最新标签</el-divider>
              <ul style="display: flex">
                <li v-for="t in tagData" :key="t.id"><a class="cate-box" href="#" v-text="t.name"/></li>
              </ul>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-backtop></el-backtop>
  </div>
</template>

<script>
import FrontTopNav from '../../components/FrontTopNav'
import moment from 'moment'
export default {
  name: 'UserPostPage',
  data () {
    return {
      post: {
        id: ''
      },
      postObj: '',
      categoryData: [],
      tagData: [],
      latestPost: [],
      loading: true
    }
  },
  components: { FrontTopNav },
  methods: {
    getPost () {
      this.$axios.get('/post/' + this.post.id).then(value => {
        this.postObj = value.data.data
        this.loading = false
      })
    },
    getLatestPostList () {
      this.$axios.get('/post/latest').then(value => {
        this.latestPost = value.data.data
      })
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    },
    showTagList () {
      this.$axios.get('/tag/latest').then(value => {
        console.info(value.data)
        this.tagData = value.data.data
      }).catch(_ => {
      })
    },
    showCategory () {
      this.$axios.get('/category/latest').then(value => {
        console.info(value.data)
        this.categoryData = value.data.data
      }).catch(_ => {
      })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD')
    }
  },
  created () {
    this.post.id = localStorage.getItem('userPostId')
    this.getPost()
    this.showTagList()
    this.showCategory()
    this.getLatestPostList()
  }
}
</script>

<style scoped>
  body {
    padding: 0;
    margin: 0;
  }

  .content {
    margin: 0 auto;
    width: 750px;
    height: 100%;
    padding: 60px;
  }

  ul{
    padding-left: 0;
    list-style: none;
  }

  .cate-box{
    cursor: pointer;
    transition: .2s ease;
    display: block;
    background: #f4f4f4;
    margin: 3px;
    text-decoration: none;
    text-align: center;
    padding: 2px 5px;
    color: #000;
  }

  .content .post >>> h1 {
    margin: 0 auto 10px;
    font-size: 24px;
    font-weight: 500;
    line-height: 1.2;
  }

  .content .post >>> ul li {
    color: #6e6d7a;
    font-size: 16px;
    line-height: 1.5;
  }

  .content .post >>> p code {
    padding: 5px;
    margin: 0;
    font-size: 85%;
    background-color: rgba(27,31,35,.05);
    border-radius: 3px;
  }
  .content .post >>> p strong {
    color: #222222;
  }
  .content .post >>> p, li {
    color: #6e6d7a;
    font-size: 16px;
    line-height: 1.5;
  }

  .content .post >>> blockquote {
    margin: 0;
    padding: 0;
    font-size: 100%;
    vertical-align: baseline;
    border: 0;
    background: transparent;
    font-style: italic;
    position: relative;
  }

  .content .post >>> blockquote::before {
    content: " ";
    background: #409EFF;
    height: 100%;
    width: 5px;
    position: absolute;
    left: -20px;
  }

  .content .post >>> pre code {
    padding: 0;
    font-size: inherit;
    color: inherit;
    white-space: pre-wrap;
    background-color: transparent;
    border-radius: 0;
  }

  .content .post >>> img {
    display: block;
    max-width: 100%;
    height: auto;
  }

  .content .post >>> pre {
    display: block;
    padding: 9.5px;
    margin: 0 0 10px;
    font-size: 13px;
    width: 100%;
    line-height: 1.42857143;
    color: #333;
    word-break: break-all;
    word-wrap: break-word;
    background-color: #f5f5f5;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  .content .post >>> table {
    text-align: left;
    width: 100%;
    max-width: 100%;
    margin-bottom: 1rem;
    border: 1px solid #dee2e6;
  }

  .content .post >>> table thead tr th,
  .content .post >>> table tbody tr td ,
  .content .post >>> table tbody tr th {
    padding: 20px;
    vertical-align: top;
    border: 1px solid #e1e1e1;
  }

</style>
