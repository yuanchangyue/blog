<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav/>
    <div class="content" style="min-height: 500px">
      <el-row :gutter="30">
        <el-col :span="16">
          <el-carousel height="200px" :autoplay="true" v-loading="loading">
            <el-carousel-item v-for="item in siteHeadImg" :key="item">
              <el-image :src="item" fit="cover" style="width: 100%;"></el-image>
            </el-carousel-item>
          </el-carousel>
          <transition-group appear>
            <el-card class="post-item" v-for="p in postList" :key="p.id" style="margin-top: 10px">
              <el-row>
                <el-col v-show="p.headpic!==''&&p.headpic!==null">
                  <el-image :src="p.headpic" style="height: 200px;width: 100%;" fit="cover"></el-image>
                </el-col>
                <el-col>
                  <h3 class="post-title" @click="toPage(p.id)" v-text="p.title"></h3>
                  <summary v-text="p.brief"></summary>
                  <el-row>
                    <el-image v-if="p.prePic2!==''&&p.prePic2!==null" :src="p.prePic2"
                              style="height: 80px;width: 80px;margin:10px;" fit="cover"></el-image>
                    <el-image v-if="p.prePic2!==''&&p.prePic2!==null" :src="p.prePic3"
                              style="height: 80px;width: 80px;margin:10px;" fit="cover"></el-image>
                  </el-row>
                  <el-row style="margin-top: 5px">
                    <el-col :span="20">
                      <el-avatar style="width: 20px;height: 20px;margin-right: 10px;" :src="siteObj.pic"></el-avatar>
                      <span v-text="p.siteName"></span>
                    </el-col>
                    <el-col :span="4">
                      <span v-text="dateFormat(p.createTime)"></span>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-card>
          </transition-group>
          <el-button style="margin: 20px;cursor: pointer;width: 80%;height: 40px" @click="loadingMore">更多</el-button>
        </el-col>
        <el-col :span="8">
          <el-card>
            <el-row>
              <el-col :span="4">
                <el-avatar :src="siteObj.pic" style="width: 40px;height: 40px;"></el-avatar>
              </el-col>
              <el-col :span="12">
                <h2 style="padding: 0;margin: 5px;" v-text="siteObj.name"></h2>
              </el-col>
              <el-col :span="6">
                <el-button type="primary" size="small" icon="el-icon-message-solid" @click="subscribeTo(siteObj.id)">
                  {{subscriptText}}
                </el-button>
              </el-col>
            </el-row>
            <summary v-text="siteObj.brief"></summary>
            <p style="color: #909399;font-size: 10px;text-align: left">*爬虫自锤子阅读</p>
          </el-card>
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
  name: 'SitePage',
  components: { FrontTopNav, FrontFooter },
  data () {
    return {
      site: {
        id: '',
        pageIndex: 1,
        pageSize: 5
      },
      subscriptText: '订阅',
      postList: [],
      siteHeadImg: [],
      siteObj: '',
      loading: true,
      userData: JSON.parse(localStorage.getItem('user')),
      subscript: {
        userId: '',
        siteId: ''
      }
    }
  },
  methods: {
    getPostList () {
      this.$axios.get('/site?pageIndex' + this.site.pageIndex + '&pageSize=' + this.site.pageSize + '&siteId=' + this.site.id)
        .then(value => {
          this.postList = value.data.data.list
          this.getHeadImg(value.data.data.list)
          this.loading = false
        })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD')
    },
    getHeadImg (list) {
      list.forEach(post => {
        if (post.headpic !== '' && post.headpic != null) {
          this.siteHeadImg.push(post.headpic)
        }
      })
    },
    getSite () {
      this.$axios.get('/site/' + this.site.id)
        .then(value => {
          this.siteObj = value.data.data
          console.info(value.data.data)
          this.subscript.siteId = this.siteObj.id
          this.checkSubscribe()
        })
    },
    loadingMore () {
      this.site.pageIndex = this.site.pageIndex + 1
      console.info(this.site.pageIndex)
      this.$axios.get('/site?pageIndex=' + this.site.pageIndex + '&pageSize=' + this.site.pageSize + '&siteId=' + this.site.id)
        .then(value => {
          value.data.data.list.forEach(p => {
            this.postList.push(p)
          })
          if (value.data.data.list == null || value.data.data.list.length < 1) {
            this.$message.info('没有更多文章')
          }
        })
    },
    toPage (id) {
      this.$router.push({ name: 'PostPage', params: { postId: id } })
    },
    subscribeTo (id) {
      if (this.userData == null) {
        this.$message.error('登陆后才可以订阅该站点')
        return
      }
      this.subscript.userId = this.userData.id
      this.subscript.siteId = id
      this.$axios.post('/subscription', this.subscript).then(_ => {
        if (_.data.code === 200) {
          this.$notify.success(_.data.data)
          this.subscriptText = '已经订阅'
        }
      }).catch(_ => {
        this.$notify.warning('订阅失败！')
      })
    },
    checkSubscribe () {
      if (this.userData == null) {
        this.subscriptText = '订阅'
      }
      console.info('this.siteObj' + this.siteObj.id)
      this.subscript.userId = this.userData.id
      this.$axios.post('/subscription/check', this.subscript).then(_ => {
        console.info(_.data.data)
        if (_.data.data === false) {
          this.subscriptText = '订阅'
        } else {
          this.subscriptText = '已经订阅'
        }
      })
    }
  },
  created () {
    this.site.id = localStorage.getItem('siteId')
    this.getPostList()
    this.getSite()
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
    padding-top: 30px;
    width: 60%;
    height: 100%;
  }

  .post-title:hover{
    color: #409EFF;
    cursor: pointer;
  }

  .post-item {
    transition: all .3s linear 0s;
  }
</style>
