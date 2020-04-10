<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav></FrontTopNav>
    <div class="banner">
      <el-image style="width: 100%;height: 100%;" fit="cover" :src="defaultBg"></el-image>
    </div>
    <div class="content">
      <el-row :gutter="30" v-loading="loading">
        <el-col :span="16" v-if="postData==null||postData.length<=0" style="text-align: center;vertical-align: middle">
          <el-image :src="nullBg" style="width: 400px;"></el-image>
          <p style="text-align: center">没有文章，赶快去写文章吧</p>
        </el-col>
        <el-col :span="16" v-else>
          <el-divider content-position="left">我的</el-divider>
          <el-row :gutter="20">
            <transition-group appear>
              <el-col :span="12" v-for="p in postData" :key="p.id" style="margin-top: 10px;">
                <el-image v-if="p.thumbnail!==''" style="width: 100%;height: 200px" fit="cover"
                          :src="handlerUrl(p.thumbnail)"></el-image>
                <el-image v-else style="width: 100%;height: 200px" fit="cover" :src="defaultBg"/>
                <h2 v-text="p.title"></h2>
                <summary v-text="p.summary"></summary>
                <summary v-text="subStringToContent(p.originalContent)"></summary>
                <a href="#" class="more-btn" @click="toPost(p.id)">更多</a>
              </el-col>
            </transition-group>
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
        </el-col>
        <el-col :span="8" align="center">
          <h2 class="user-title" v-text="userData.username"></h2>
          <el-avatar :src="handlerUrl(userData.avatar)" :size="80"></el-avatar>
          <summary v-text="userData.description"></summary>
          <h2 class="user-title">分类</h2>
          <ul style="display: flex; align-items: center; justify-content: center">
            <li v-for="c in categoryData" :key="c.id"><a class="cate-box" href="#" v-text="c.name" @click="toCate(c.id)"/></li>
          </ul>
          <h2 class="user-title">标签</h2>
          <el-button @click="gotoTag(t.id)" v-for="t in tagData" :key="t.id" v-text="t.name" size="small" style="margin: 5px;"></el-button>
        </el-col>
      </el-row>
      <el-divider content-position="left">最新文章</el-divider>
      <el-row style="margin-bottom: 50px;" v-loading="loading">
          <el-col :span="8" style="min-width: 300px" v-for="post in latestPost" :key="post.id">
            <el-col :span="9">
              <el-avatar shape="square" :src="handlerUrl(post.thumbnail)" style="width: 100px;height: 100px;"></el-avatar>
            </el-col>
            <el-col :span="14">
              <p style="padding: 0;margin: 0;" v-text="post.title"></p>
              <summary style="color: #909399;font-size: 12px;line-height: 20px"
                       v-text="'来自：'+post.userDTO.username"></summary>
              <summary style="color: #909399;font-size: 12px;line-height: 20px"
                       v-text="dateFormat(post.editTime)"></summary>
              <a href="#" style="text-decoration: none;color: #000;line-height: 40px;"  @click="toPost(post.id)">详细</a>
            </el-col>
          </el-col>
      </el-row>
      <el-divider content-position="left">订阅站点</el-divider>
      <el-row style="margin-bottom: 20px;" v-loading="loading">
          <el-col :span="6" v-for="s in subscriptionData" :key="s.id">
            <el-col :span="10">
              <el-avatar  :src="s.site.pic" style="width: 80px;height: 80px;"></el-avatar>
            </el-col>
            <el-col :span="14" style="align-items: center">
              <p  @click="moreSite(s.site.id)" style="color: #303133;cursor: pointer" v-text="s.site.name"></p>
              <summary style="font-size: 12px;color: #606266" v-text="s.site.brief"/>
            </el-col>
          </el-col>
      </el-row>
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
  name: 'Index',
  components: { FrontTopNav, FrontFooter },
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
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0,
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
    getSubscription () {
      this.$axios.get('/subscription/' + this.userData.id).then(_ => {
        this.subscriptionData = _.data.data.list
      })
      console.info(this.subscriptionData)
    },
    toPost (id) {
      localStorage.setItem('userPostId', id)
      this.$router.push({ name: 'UserPostPage', params: { postId: id } })
    },
    moreSite (id) {
      console.info(id)
      localStorage.setItem('siteId', id)
      this.$router.push({ name: 'SitePage', params: { siteId: id } })
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    },
    subStringToContent (str) {
      return str.substr(0, 40)
    },
    gotoTag (id) {
      localStorage.setItem('tagId', id)
      this.$router.push({ name: 'TagPage', params: { tagId: id, userId: this.userData.id } })
    },
    toCate (id) {
      localStorage.setItem('cateId', id)
      this.$router.push({ name: 'CatePage', params: { cateId: id, userId: this.userData.id } })
    }
  },
  created () {
    this.showPostList()
    this.showTagList()
    this.showCategory()
    this.getLatestPostList()
    this.getSubscription()
    localStorage.setItem('isFlag', true)
  }
}
</script>

<style scoped>
  body{
    padding: 0;
    margin: 0;
  }
  .banner {
    height: 50vh;
    position: relative;
  }
  .content{
    margin: 30px auto;
    width: 60%;
    height: 100%;
  }
  ul{
    list-style: none;
  }
  .user-title {
    height: 50px;
    width: 140px;
    line-height: 50px;
    border-bottom: 1px solid #222222;
    border-top: 1px solid #222222;
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
</style>
