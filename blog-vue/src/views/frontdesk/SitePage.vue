<template>
  <section class="blog-all" style="width: 100%;background: #f4f4f4;">
    <header-bar/>
    <div class="site-top" data-aos="fade-right">
      <img :src="siteObj.pic" alt=""/>
      <div class="site-sub">
        <h2 v-text="siteObj.name"></h2>
        <p v-text="siteObj.brief"></p>
        <el-button class="btn subscript-btn" size="small" icon="el-icon-message-solid" @click="subscribeTo(siteObj.id)">
          {{subscriptText}}
        </el-button>
      </div>
    </div>
    <div class="content" id="content">
      <div class="container">
        <section id="blog-loading">
          <article data-aos="zoom-in-up" class="post-panel" v-for="(p) in postList" :key="p.id">
            <img :src="p.headpic" v-show="p.headpic!==''&&p.headpic!==null" alt="">
            <h2>
              <a class="post-info" @click="toPage(p.id)" v-text="p.title" href="#"></a>
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
      </div>
    </div>
    <div class="load-btn">
      <i class="el-icon-download" @click="loadingMore">加载更多</i>
    </div>
    <FrontFooter/>
  </section>
</template>

<script>

import HeaderBar from '../../components/PostHeaderBar'
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
      },
      no_columns: 3
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
          no_columns: this.no_columns,
          padding_x: 30,
          padding_y: 20,
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
  },
  mounted: function () {
    window.addEventListener('scroll', function () {
      var scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      var content = document.getElementById('content')
      content.classList.toggle('content-toggle', scrollTop > 0)
      console.info(scrollTop)
      console.info(content)
    })
  }
}
</script>

<style scoped>

  .site-top .site-sub {
    width: 400px;
  }

  .content {
    position: relative;
    width: 60%;
    height: 100%;
    margin: 5rem auto;
    background: #ffffff;
    padding: 2rem 0;
    box-shadow: 0 1px 2px rgba(0, 0, 0, .5);
    transition: all .3s ease-in 0s;
  }

  .content-toggle {
    width: 75%;
  }

  .load-btn {
    width: 100%;
    height: 100%;
    margin: 3rem 0;
    text-align: center;
  }

  /*-------- top 站点信息 start--------*/

  .site-top {
    position: relative;
    width: 60%;
    margin: 10rem auto 10px;
    display: flex;
  }

  .site-top  img {
    width: 100px;
    height: 100px;
  }

  .site-top .site-sub h2{
    padding: 0;
    margin: 0 0 5px 0;
  }
  .site-top .site-sub {
    position: relative;
    height: 100%;
    margin-left: 2rem;
  }

  .el-icon-download {
    background: #292C30;
    padding: 2rem 0;
    cursor: pointer;
    font-size: 20px;
    margin: 20px auto;
    display: inline-block;
    width: 30%;
    color: white;
    text-align: center;
  }

  .subscript-btn {
    background: #292C30;
    color: white;
  }

  /*-------- top 站点信息 end--------*/

  /*------- post 列表 start*/
  #blog-loading {
    position: relative;
    justify-content: center;
    width: 100%;
    max-width: 100%;
    margin: 40px auto;
    padding: 0;
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
    width: 100%;
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

  /*------- post 列表 end*/
  @media (max-width: 1200px) {
  }

  @media (max-width: 768px) {
    .content{
      width: 95%;
    }
    ..site-top{
      width: 95%;
    }
  }

  @media (max-width: 480px) {
  }
</style>
