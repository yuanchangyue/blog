<template>
  <div class="blog-all" style="width: 100%;background: #f4f4f4;">
    <header-bar/>
    <div class="post-top">
      <span v-if="postObj.userDTO.username!==undefined" style="color: #939598;font-weight: 700" v-text="'由' + postObj.userDTO.username + '编写'"></span>
      <h1 v-text="postObj.title"></h1>
    </div>
    <div class="content" id="content">
      <div class="container">
        <div class="content-box">
          <div class="post-box">
            <ul class="side-bar" id="side-bar">
              <li class="side-bar-li" @click="like">
                <font-awesome-icon :icon="['fa','heart']"></font-awesome-icon>&nbsp;
                <span>{{postObj.likes}}</span>
              </li>
              <li :class="collectionStatus?checkStatus:normalStatus">
                <font-awesome-icon style="cursor: pointer" @click="collect" :icon="['fa','bookmark']"/>
              </li>
              <li class="side-bar-li">
                <a href="#comment-box">
                  <font-awesome-icon :icon="['fa','comment']"></font-awesome-icon>
                </a>
              </li>
            </ul>
            <div class="post" v-html="postObj.formatContent"></div>
          </div>
          <div class="other">
              <el-divider content-position="left">最新文章</el-divider>
              <el-row v-for="post in latestPost" :key="post.id" :gutter="20">
                <el-col :span="10">
                  <el-image :src="handlerUrl(post.thumbnail)" style="width: 100%;height: 70px;" fit="cover"
                            @click="toPost(post.id)"></el-image>
                </el-col>
                <el-col :span="14">
                  <p style="padding: 0;margin: 0;" v-text="post.title"></p>
                  <summary v-if="post.userDTO.username!==undefined" style="color: #909399;font-size: 12px;line-height: 20px"
                           v-text="'来自：'+post.userDTO.username"></summary>
                  <summary style="color: #909399;font-size: 12px;line-height: 20px"
                           v-text="dateFormat(post.editTime)"></summary>
                </el-col>
              </el-row>
              <el-divider content-position="left">最新分类</el-divider>
              <ul style="display: flex">
                <li @click="toCate(c.id)" v-for="c in categoryData" :key="c.id"><a class="cate-box" href="#"
                                                                                   v-text="c.name"/></li>
              </ul>
              <el-divider content-position="left">最新标签</el-divider>
              <ul style="display: flex">
                <li @click="gotoTag(t.id)" v-for="t in tagData" :key="t.id"><a class="cate-box" href="#"
                                                                               v-text="t.name"/></li>
              </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="comment-box container">
      <el-divider content-position="left" style="background: #f4f4f4" id="comment-box">结束了</el-divider>
      <h4 style="text-align: center">全部评论</h4>
      <div class="write-comment-box">
        <div class="not-login-comment" v-if="userData==null" style="display: flex;align-items: center ">
          <font-awesome-icon :icon="['fa','user-circle']" style="font-size: 40px;margin-right: 10px;"/>
          <span>请在<el-link type="danger">登陆</el-link>后评论</span>
        </div>
        <div class="login-comment" v-if="userData!=null">
          <div>
            <el-avatar class="user-comment-avatar" :src="handlerUrl(userData.avatar)" :size="60"></el-avatar>
            <h5 style="text-align: center" v-if="userData.username!==undefined"  v-text="userData.username"/>
          </div>
          <div class="edit-comment">
            <el-input class="comment-input" v-model="post.content" rows="8" type="textarea"
                      placeholder="写下你的评论"></el-input>
          </div>
        </div>
        <div style="width: 100%;text-align: right">
          <el-button @click="commentSubmit">提交</el-button>
        </div>
      </div>
      <el-card v-for="c in comment" :key="c.id" style="margin:10px 0;">
        <div style="display: flex;align-items: center">
          <div style="width: 100px;">
            <el-avatar class="user-comment-avatar" :src="handlerUrl(c.commentDTO.avatar)" :size="50"></el-avatar>
          </div>
          <div style="width: 100%;">
            <div style="display: flex;align-items: center">
              <h4 v-if="c.commentDTO.username!==undefined"  v-text="c.commentDTO.username" style="margin-right: 10px;"></h4>
            </div>
            <p v-text="c.content"></p>
            <p style="font-size: 12px" v-text="dateFormat(c.createTime)"></p>
          </div>
        </div>
      </el-card>
      <el-card style="margin: 10px 0;color: #909399;text-align: center">没有更多了</el-card>
    </div>
    <el-backtop/>
    <FrontFooter/>
  </div>
</template>

<script>
import FrontFooter from '../../components/FrontFooter'
import HeaderBar from '../../components/PostHeaderBar'
import moment from 'moment'

export default {
  name: 'UserPostPage',
  data () {
    return {
      post: {
        pageIndex: 1,
        pageSize: 5,
        postId: '',
        content: '',
        userId: '',
        commentUserId: '',
        parentId: 0
      },
      postObj: '',
      categoryData: [],
      tagData: [],
      latestPost: [],
      comment: [],
      loading: true,
      userData: JSON.parse(sessionStorage.getItem('user')),
      collection: {
        userId: '',
        postId: ''
      },
      collectionStatus: false,
      normalStatus: 'side-bar-li',
      checkStatus: 'side-bar-li-hover',
      commentY: 0
    }
  },
  components: { HeaderBar, FrontFooter },
  methods: {
    getPost () {
      this.$axios.get('/post/' + this.post.postId).then(value => {
        this.post.userId = value.data.data.userDTO.id
        this.postObj = value.data.data
        this.loading = false
      })
      this.check()
    },
    getComment: function () {
      this.$axios.get('/comments', {
        params: {
          pageIndex: this.post.pageIndex,
          pageSize: this.post.pageSize,
          postId: this.post.postId,
          parentId: this.post.parentId,
          content: this.post.content,
          commentUserId: this.userData.id,
          userId: this.post.userId
        }
      }).then(value => {
        this.comment = value.data.data.list
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
        this.tagData = value.data.data
      }).catch(_ => {
      })
    },
    toPost (id) {
      this.loading = true
      sessionStorage.setItem('userPostId', id)
      this.post.postId = id
      window.location.reload()
    },
    commentSubmit () {
      var commentParam = {
        postId: this.post.postId,
        parentId: this.post.parentId,
        content: this.post.content,
        commentUserId: this.userData.id,
        userId: this.post.userId
      }
      this.$axios.post('/comments', commentParam).then(_ => {
        if (_.data.code === 200) {
          this.$notify.success('评价完成,等待博主审核后即可看见！')
          this.getComment()
        }
      })
    },
    showCategory () {
      this.$axios.get('/category/latest').then(value => {
        this.categoryData = value.data.data
      }).catch(_ => {
      })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD')
    },
    gotoTag (id) {
      sessionStorage.setItem('tagId', id)
      this.$router.push({ name: 'TagPage', params: { tagId: id, userId: this.userData.id } })
    },
    toCate (id) {
      sessionStorage.setItem('cateId', id)
      this.$router.push({ name: 'CatePage', params: { cateId: id, userId: this.userData.id } })
    },
    like () {
      if (this.userData == null) {
        this.$message.error('请登陆后再点赞！')
        return
      }
      this.$axios.put('/post/' + this.postObj.id + '/likes').then(_ => {
        this.getPost()
      })
    },
    check () {
      this.collection.userId = this.userData.id
      this.collection.postId = this.post.postId
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
        this.$axios.delete('/collection/' + this.userData.id + '/' + this.post.postId).then(_ => {
          this.$notify.success(_.data.data)
          this.check()
        })
      } else {
        this.collection.userId = this.userData.id
        this.collection.postId = this.post.postId
        this.$axios.post('/collection', this.collection).then(_ => {
          this.userPostId = _.data.data.id
          this.$notify.success('收藏成功')
          this.check()
        })
      }
    }
  },
  created () {
    this.post.postId = sessionStorage.getItem('userPostId')
    this.getPost()
    this.showTagList()
    this.showCategory()
    this.getLatestPostList()
    this.getComment()
  },
  mounted: function () {
    let top = 0
    setTimeout(() => {
      let comment = document.getElementById('comment-box')
      console.info('comment' + comment)
      if (comment !== null) {
        let boundingClientRect = comment.getBoundingClientRect()
        top = boundingClientRect.top - 400
      }
    }, 1000)
    window.addEventListener('scroll', function () {
      let scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      let content = document.getElementById('content')
      if (content !== null) {
        content.classList.toggle('content-toggle', scrollTop > 0)
        let sideBar = document.getElementById('side-bar')
        if (scrollTop >= top) {
          sideBar.style.opacity = 0
        } else {
          sideBar.style.opacity = 1
        }
      }
    })
  }
}
</script>

<style scoped>
  .content {
    position: relative;
    background: #ffffff;
    width: 60%;
    height: 100%;
    margin: 8rem auto 10rem;
    padding: 6rem 0;
    transition: all .3s linear;
    box-shadow: 0 1px 2px rgba(0, 0, 0, .5);
  }

  .content-toggle {
    width: 75%;
  }

  .content-box {
    max-width: 900px;
    width: 100%;
    margin: 0 auto;
    display: grid;
    grid-template-columns: 70% 30%;
  }

  .content-box .post-box .post{
    max-width: 750px;
    padding: 4rem;
    margin: 0 auto;
  }
  .content-box .post-box {
    max-width: 750px;
    box-sizing: border-box;
    margin-right: 2rem;
    border-right: 1px solid #f4f4f4;
    padding-right: 2rem;
  }

  .content-box .other {
    overflow: hidden;
    max-width: 750px;
  }

  .post-box {
    position: relative;
  }

  a {
    color: #909399;
  }

  .post-top {
    margin: 10rem auto 0;
    width: 60%;
  }

  /*------ 侧边栏 ------*/
  .side-bar {
    text-align: center;
    position: fixed;
    top: 40%;
    left: 450px;
    -webkit-transform: translateY(30%);
    transform: translateY(30%);
    z-index: 90;
    opacity: 1;
    transition:all .3s linear;
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

  /*----- 评论区 -----*/
  .comment-box{
    max-width: 750px;
  }
  /*----- 评论区 -----*/

  ul {
    padding-left: 0;
    list-style: none;
  }

  .login-comment {
    display: flex;
    width: 100%;
    height: 200px;
  }

  .edit-comment {
    width: 100%;
    margin-left: 20px;
  }

  .comment-input {
    outline: none;
    border: none;
    width: 100%;
  }

  .user-comment-avatar {
    margin: 5px;
  }

  .cate-box {
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
    background-color: rgba(27, 31, 35, .05);
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
  .content .post >>> table tbody tr td,
  .content .post >>> table tbody tr th {
    padding: 20px;
    vertical-align: top;
    border: 1px solid #e1e1e1;
  }

  .el-divider__text {
    background: #ffffff !important;
  }

  /*-------屏幕自适应 start-------*/
  @media only screen and (max-width: 1300px) {
    .content-box  {
      grid-template-columns: 100%;
    }
    .post-top {
      width: 85%;
    }
    .content {
      width: 85%;
    }
    .comment-box{
      max-width: 750px;
    }
    .content-box .post-box {
      border: none;
    }
    .content-box .other {
      max-width: 500px;
    }
  }

  @media only screen and (max-width: 1350px) {
    .content {
      width: 90%;
    }
    .post-top {
      width: 90%;
    }
    .side-bar{
      display: none;
    }
    .comment-box{
      max-width: 400px;
    }
  }

  @media only screen and (max-width: 520px) {
  }

  /*-------屏幕自适应 end-------*/

</style>
