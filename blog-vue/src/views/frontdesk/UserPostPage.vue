<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav></FrontTopNav>
    <div class="content">
      <el-row class="content-box"  :gutter="40" v-loading="loading">
        <el-col :span="18">
          <div class="post-box">
            <ul class="side-bar">
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
          <el-divider content-position="left" id="comment-box">结束了</el-divider>
          <div class="comment-box">
            <h4 style="text-align: center">全部评论</h4>
            <div class="write-comment-box">
              <div class="not-login-comment" v-if="userData==null" style="display: flex;align-items: center ">
                <font-awesome-icon :icon="['fa','user-circle']" style="font-size: 40px;margin-right: 10px;"/>
                <span>请在<el-link type="danger">登陆</el-link>后评论</span>
              </div>
              <div class="login-comment" v-if="userData!=null">
                <div>
                  <el-avatar class="user-comment-avatar" :src="handlerUrl(userData.avatar)" :size="50"></el-avatar>
                  <span v-text="userData.username"/>
                </div>
                <div class="edit-comment">
                  <el-input class="comment-input" v-model="post.content" rows="8" type="textarea" placeholder="写下你的评论"></el-input>
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
                  <h3 v-text="c.commentDTO.username" style="margin-right: 10px;"></h3>
                  <span style="font-size: 12px" v-text="dateFormat(c.createTime)"></span>
                  </div>
                  <summary v-text="c.content"/>
                </div>
              </div>
            </el-card>
            <el-card style="margin: 10px 0;color: #909399;text-align: center">没有更多了</el-card>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="other">
            <div>
              <el-divider content-position="left">最新文章</el-divider>
              <el-row v-for="post in latestPost" :key="post.id" :gutter="20">
                <el-col :span="10">
                  <el-image :src="handlerUrl(post.thumbnail)" style="width: 100%;height: 70px;" fit="cover"  @click="toPost(post.id)"></el-image>
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
                <li @click="toCate(c.id)" v-for="c in categoryData" :key="c.id"><a class="cate-box" href="#" v-text="c.name"/></li>
              </ul>
              <el-divider content-position="left">最新标签</el-divider>
              <ul style="display: flex">
                <li @click="gotoTag(t.id)" v-for="t in tagData" :key="t.id"><a class="cate-box" href="#" v-text="t.name"/></li>
              </ul>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-backtop/>
    <FrontFooter/>
  </div>
</template>

<script>
import FrontFooter from '../../components/FrontFooter'
import FrontTopNav from '../../components/FrontTopNav'
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
      userData: JSON.parse(localStorage.getItem('user')),
      collection: {
        userId: '',
        postId: ''
      },
      collectionStatus: false,
      normalStatus: 'side-bar-li',
      checkStatus: 'side-bar-li-hover'
    }
  },
  components: { FrontTopNav, FrontFooter },
  methods: {
    getPost () {
      this.$axios.get('/post/' + this.post.postId).then(value => {
        console.info(value.data.data.userDTO)
        this.post.userId = value.data.data.userDTO.id
        this.postObj = value.data.data
        this.loading = false
      })
      this.check()
    },
    getComment: function () {
      console.info('userDTO' + this.postObj)
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
        console.info(value.data)
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
      localStorage.setItem('userPostId', id)
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
      localStorage.setItem('tagId', id)
      this.$router.push({ name: 'TagPage', params: { tagId: id, userId: this.userData.id } })
    },
    toCate (id) {
      localStorage.setItem('cateId', id)
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
        this.$axios.delete('/collection/' + this.userData.id + '/' + this.post.postId).then(_ => {
          this.$notify.success(_.data.data)
          this.check()
        })
      } else {
        this.collection.userId = this.userData.id
        this.collection.postId = this.post.postId
        this.$axios.post('/collection', this.collection).then(_ => {
          console.info(_.data.data.id)
          this.userPostId = _.data.data.id
          this.$notify.success('收藏成功')
          this.check()
        })
      }
    }
  },
  created () {
    this.post.postId = localStorage.getItem('userPostId')
    this.getPost()
    this.showTagList()
    this.showCategory()
    this.getLatestPostList()
    this.getComment()
  }
}
</script>

<style scoped>
  body {
    padding: 0;
    margin: 0;
  }
  .content {
    width: 750px;
    height: 100%;
    margin: 80px auto;
    padding-top: 30px;
  }
  .content-box{
    max-width: 750px;
  }
  .post-box{
    position: relative;
  }
  .side-bar{
    text-align: center;
    position: fixed;
    top: 10%;
    left: 400px;
    -webkit-transform: translateY(30%);
    transform: translateY(30%);
    z-index: 90;
  }
  a{
    color: #909399;
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
  ul{
    padding-left: 0;
    list-style: none;
  }
  .login-comment{
    display: flex;
    width: 100%;
    height: 200px;
  }
  .edit-comment{
    width: 100%;
  }
  .comment-input{
    outline: none;
    border: none;
    width: 100%;
  }
  .user-comment-avatar{
    margin: 5px;
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
