<template>
    <div class="blog-all" style="width: 100%;background: #ffffff;">
      <FrontTopNav></FrontTopNav>
      <div class="content">
        <el-row :gutter="30" v-loading="loading">
          <el-col :span="16">
            <el-row :gutter="20">
              <transition-group appear>
              <el-col :span="12" v-for="p in postData" :key="p.id" style="margin-top: 10px;">
                <el-image style="width: 100%;" src="https://themes.gohugo.io/theme/liva-hugo/images/post/post-1.jpg" ></el-image>
                <h2 v-text="p.title"></h2>
                <summary v-text="p.summary"></summary>
                <summary v-text="subStringToContent(p.originalContent)"></summary>
                <a href="#" class="more-btn" @click="toPost(p.id)">更多</a>
              </el-col>
              </transition-group>
            </el-row>
          </el-col>
          <el-col :span="8" align="center">
            <h2 class="user-title" v-text="userData.username"></h2>
            <el-avatar :src="userData.avatar" :size="100"></el-avatar>
            <summary v-text="userData.description"></summary>
            <h2 class="user-title">分类</h2>
            <summary v-for="c in categoryData" :key="c.id" v-text="c.name"></summary>
            <h2 class="user-title">标签</h2>
            <el-button v-for="t in tagData" :key="t.id" v-text="t.name" size="small" style="margin: 5px;"></el-button>
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
  name: '',
  components: { FrontTopNav, FrontFooter },
  data () {
    return {
      form: {
        keyWords: '',
        status: '',
        categoryId: ''
      },
      categoryData: [],
      tagData: [],
      postData: [],
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0,
      userData: '',
      loading: true
    }
  },
  methods: {
    showPostList () {
      this.$axios.get('/post').then(value => {
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
      var page = { pageIndex: val, pageSize: this.pageSize }
      this.$axios.get('/post', { params: page }).then(value => {
        this.setPageValue(value)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
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
        this.categoryData = value.data.data
      }).catch(_ => {
      })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    getUser () {
      this.$axios.get('/user').then(value => {
        this.userData = value.data.data
      })
    },
    toPost (id) {
      localStorage.setItem('userPostId', id)
      this.$router.push({ name: 'UserPostPage', params: { postId: id } })
    },
    subStringToContent (str) {
      return str.substr(0, 60)
    }
  },
  created () {
    this.showPostList()
    this.showTagList()
    this.showCategory()
    this.getUser()
    localStorage.setItem('isFlag', true)
  }
}
</script>

<style scoped>
  body{
    padding: 0;
    margin: 0;
  }
  .content{
    margin: 20px auto;
    width: 60%;
    height: 100%;
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
</style>
