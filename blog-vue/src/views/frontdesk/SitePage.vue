<template>
  <section class="blog-all" style="width: 100%;background: #ffffff;">
    <header-bar title="" btn="" :banner="false"/>
    <div class="content" id="content">
      <div class="site-top">
        <el-card class="site-carousel">
          <el-carousel height="200px" :autoplay="true" v-loading="loading">
            <el-carousel-item v-for="item in siteHeadImg" :key="item">
              <el-image :src="item" fit="cover" style="width: 100%;"></el-image>
            </el-carousel-item>
          </el-carousel>
        </el-card>
        <el-card class="site-sub">
          <div style="display: flex;align-items: center;justify-content: space-between">
            <div style="display: flex;align-items: center">
              <el-avatar :src="siteObj.pic" style="width: 40px;height: 40px;"></el-avatar>
              <h2 style="padding: 0;margin: 5px;" v-text="siteObj.name"></h2>
            </div>
            <el-button type="primary" size="small" icon="el-icon-message-solid" @click="subscribeTo(siteObj.id)">
              {{subscriptText}}
            </el-button>
          </div>
          <summary v-text="siteObj.brief"></summary>
          <p style="color: #909399;font-size: 10px;text-align: left">*来自网络爬虫内容</p>
        </el-card>
      </div>
      <section id="blog-loading">
        <article data-aos="zoom-in-up" class="post-panel" v-for="(p) in postList" :key="p.id">
          <img :src="p.headpic" v-show="p.headpic!==''&&p.headpic!==null" alt="">
          <h2>
            <a class="post-title" @click="toPage(p.id)" v-text="p.title" href="#"></a>
          </h2>
          <p v-text="p.brief"></p>
          <el-row>
            <el-image v-if="p.prePic2!==''&&p.prePic2!==null" :src="p.prePic2"
                      style="height: 80px;width: 80px;margin:10px;" fit="cover"></el-image>
            <el-image v-if="p.prePic2!==''&&p.prePic2!==null" :src="p.prePic3"
                      style="height: 80px;width: 80px;margin:10px;" fit="cover"></el-image>
          </el-row>
          <div style="display: flex;align-items: center;justify-content: space-between">
            <div style="display: flex ;align-items: center;">
              <el-avatar style="width: 20px;height: 20px;margin-right: 5px;" :src="siteObj.pic"></el-avatar>
              <span v-text="p.siteName"></span>
            </div>
            <span v-text="dateFormat(p.createTime)"></span>
          </div>
        </article>
      </section>
      <i class="el-icon-download" style="cursor: pointer;font-size: 40px;margin: 50px auto;display:inline-block;width: 100%; text-align: center" @click="loadingMore"/>
    </div>
    <FrontFooter/>
  </section>
</template>

<script>

import HeaderBar from '../../components/HeaderBar'
import FrontFooter from '../../components/FrontFooter'
import moment from 'moment'

export default {
  name: 'SitePage',
  components: { HeaderBar, FrontFooter },
  data () {
    return {
      site: {
        id: '',
        pageIndex: 1,
        pageSize: 20
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
      this.$axios.get('/site?pageIndex=' + this.site.pageIndex + '&pageSize=' + this.site.pageSize + '&siteId=' + this.site.id)
        .then(value => {
          this.postList = value.data.data.list
          this.initPostCard()
          this.getHeadImg(value.data.data.list)
          this.loading = false
        })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD')
    },
    getHeadImg (list) {
      for (var i = 0; list.size <= 4; i++) {
        if (list[i].headpic !== '' && list[i].headpic != null) {
          this.siteHeadImg.push(list[i].headpic)
        }
      }
    },
    getSite () {
      this.$axios.get('/site/' + this.site.id)
        .then(value => {
          this.siteObj = value.data.data
          this.subscript.siteId = this.siteObj.id
          this.checkSubscribe()
        })
    },
    loadingMore () {
      this.site.pageIndex = this.site.pageIndex + 1
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
      this.subscript.userId = this.userData.id
      this.$axios.post('/subscription/check', this.subscript).then(_ => {
        if (_.data.data === false) {
          this.subscriptText = '订阅'
        } else {
          this.subscriptText = '已经订阅'
        }
      })
    },
    initPostCard: function () {
      this.$nextTick(function () {
        $('#blog-loading').pinterest_grid({
          no_columns: 3,
          padding_x: 10,
          padding_y: 10,
          margin_bottom: 50,
          single_column_breakpoint: 700
        })
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
  .content {
    width: 70%;
    margin: 0 auto;
  }
  #content .site-top{
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  #content .site-top .site-carousel{
    width: 890px;
  }
  #content .site-top .site-sub {
    width: 400px;
    height: 100%;
  }

  #blog-loading {
    position: relative;
    width: 100%;
    margin: 0 auto;
    height: auto;
  }

  #blog-loading .post-panel {
    position: absolute;
    width: 100%;
    padding: 10px;
    background: #ffffff;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .3);
  }

  #blog-loading .post-panel:hover {
    box-shadow: 1px 1px 10px rgba(0, 0, 0, .5);
    transition: all 0.3s linear;
  }

  #blog-loading .post-panel img {
    max-width: 100%;
    height: auto;
  }

  #blog-loading .post-panel h2 {
    margin: 10px 0 10px;
    padding: 0;
    font-size: 20px;
  }

  #blog-loading .post-panel p {
    margin: 0;
    padding: 0 0 10px;
    font-size: 14px;
    text-indent:10px;
  }

/*  @media (max-width: 1200px) {
    #blog-loading {
      columns: 3;
      width: calc(100% - 50px);
      box-sizing: border-box;
      column-gap: 20px;
    }
    .content{
      min-width: 100%;
    }
    .site-sub{display: none}
    .site-carousel{
      min-width: 100%;
    }
  }
  @media (max-width: 768px) {
    #blog-loading {
      columns: 2;
    }
  }
  @media (max-width: 480px) {
    #blog-loading {
      columns: 1;
    }
  }*/
</style>
