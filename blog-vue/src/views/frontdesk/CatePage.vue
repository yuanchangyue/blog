<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav></FrontTopNav>
    <div class="content">
      <el-row>
        <h1 v-if="cateObj!==null" v-text="'#' + cateObj.name" style="text-align: center"></h1>
      </el-row>
      <el-row :gutter="40" v-loading="loading">
        <el-col :span="18" v-if="postData==null||postData.length<=0" style="text-align: center;vertical-align: middle">
          <el-image :src="nullBg" style="width: 400px;"></el-image>
          <p style="text-align: center">当前标签下没有文章，赶快去写文章吧</p>
        </el-col>
        <el-col :span="18" v-else>
          <div>
            <transition-group appear>
              <el-col :span="8" v-for="p in postData" :key="p.id" style="margin-top: 10px;">
                <el-image v-if="p.thumbnail!==''" style="width: 100%;height: 200px" fit="cover" :src="handlerUrl(p.thumbnail)"></el-image>
                <el-image v-else style="width: 100%;height: 200px" fit="cover" :src="defaultBg"/>
                <h2 v-text="p.title"></h2>
                <summary v-text="p.summary"></summary>
                <summary class="content-text" v-text="subStringToContent(p.originalContent)"></summary>
                <a href="#" class="more-btn" @click="toPost(p.id)">更多</a>
              </el-col>
            </transition-group>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="other">
            <div>
              <el-divider content-position="left">最新标签</el-divider>
              <ul style="display: flex; align-items: center; justify-content: center">
                <li v-for="t in tagData" :key="t.id" @click="gotoTag(t.id)"><a class="cate-box" href="#" v-text="t.name"/></li>
              </ul>
              <el-divider content-position="left">最新文章</el-divider>
              <el-row v-for="post in latestPost" :key="post.id" :gutter="10">
                <el-col :span="10">
                  <el-image :src="handlerUrl(post.thumbnail)"  @click="toPost(post.id)" style="width: 100%;height: 70px;" fit="cover"></el-image>
                </el-col>
                <el-col :span="14">
                  <p style="padding: 0;margin: 0;" v-text="post.title"></p>
                  <summary style="color: #909399;font-size: 12px;line-height: 20px"
                           v-text="'来自：'+post.userDTO.username"></summary>
                  <summary style="color: #909399;font-size: 12px;line-height: 20px"
                           v-text="dateFormat(post.editTime)"></summary>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-pagination
        align="center"
        style="margin: 10px 0;"
        @current-change="handleCurrentChange"
        layout=" prev, pager, next"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :total="pageTotal">
      </el-pagination>
    </div>
    <el-backtop></el-backtop>
    <FrontFooter/>
  </div>
</template>

<script>
import FrontTopNav from '../../components/FrontTopNav'
import FrontFooter from '../../components/FrontFooter'
import moment from 'moment'
export default {
  name: '',
  components: { FrontTopNav, FrontFooter },
  data () {
    return {
      cateObj: '',
      loading: true,
      categoryData: [],
      tagData: [],
      postData: [],
      latestPost: [],
      pageTotal: 0,
      currentPage: 0,
      pageSize: 20,
      nullBg: require('../../assets/not_found.svg'),
      defaultBg: require('../../assets/login-bg.jpg'),
      cate: {
        tagId: '',
        categoryId: ''
      }
    }
  },
  methods: {
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    subStringToContent (str) {
      return str.substr(0, 30)
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    },
    getLatestPostList () {
      this.$axios.get('/post/latest').then(value => {
        this.latestPost = value.data.data
      })
    },
    showPostByTag () {
      this.$axios.post('/post/query?pageSize=' + this.pageSize, this.cate).then(value => {
        console.info(value.data)
        this.setPageValue(value)
      }).catch(_ => {})
    },
    setPageValue (value) {
      console.info(value.data)
      this.postData = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
      this.loading = false
    },
    handleCurrentChange (val) {
      this.$axios.post('/post/query?pageIndex=' + val + '&pageSize=' + this.pageSize, this.cate).then(value => {
        this.setPageValue(value)
      })
    },
    showTagList () {
      this.$axios.get('/tag/latest').then(value => {
        this.tagData = value.data.data
      }).catch(_ => {
      })
    },
    showCategory () {
      this.$axios.get('/category/latest').then(value => {
        this.categoryData = value.data.data
      }).catch(_ => {
      })
    },
    getCate () {
      console.info(this.cate.categoryId)
      this.$axios.get('/category/' + this.cate.categoryId).then(value => {
        this.cateObj = value.data
        console.info(this.cateObj)
      }).catch(_ => {
      })
    },
    toPost (id) {
      localStorage.setItem('userPostId', id)
      this.$router.push({ name: 'UserPostPage', params: { postId: id } })
    },
    gotoTag (id) {
      console.info('go to tag' + id)
      localStorage.setItem('tagId', id)
      this.$router.push({ name: 'TagPage', params: { tagId: id } })
    }
  },
  created () {
    this.cate.categoryId = localStorage.getItem('cateId')
    this.showTagList()
    this.showCategory()
    this.showPostByTag()
    this.getLatestPostList()
    this.getCate()
  }
}
</script>

<style scoped>
  .content{
    margin: 80px auto;
    padding-top: 30px;
    width: 60%;
    height: 100%;
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
  ul{
    padding: 0;
    list-style: none;
  }
  .more-btn{
    margin-top: 10px;
    display: block;
    text-align: center;
    width: 100px;
    height: 40px;
    color: #222222;
    line-height: 40px;
    text-decoration: none;
    border: 1px solid #e1e1e1;
    transition: all .2s linear 0s;
  }
  .more-btn:hover {
    background: #409EFF;
    border:1px solid #409EFF;
    color: #f0f2f5;
  }
</style>
