<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <header-bar title="奇文共欣赏，疑义相与析" btn="探索" :banner="true" bg="about.jpg"/>
    <div class="content" id="content">
      <el-row type="flex" justify="space-between" :gutter="80">
        <el-col :md="18">
          <el-tabs class="tab-box" v-model="activeName" @tab-click="handleClick">
            <el-tab-pane :name="c.cateId" :label="c.cateName" v-for="c in cate" :key="c.cateId">
              <el-row :gutter="20">
                <el-col data-aos="fade-up" :data-aos-delay="index*100" class="site-box" :xs="24" :sm="12" :md="8"
                        :lg="6" v-for="(site,index) in siteData" :key="site.id">
                  <el-image class="show-img" style="width: 200px;height: 150px;" fit="cover" :src="site.pic"></el-image>
                  <h3 class="site-name" @click="moreSite(site.id)" v-text="site.name"></h3>
                  <p class="site-brief" v-text="site.brief"></p>
                  <p style="font-size: 12px;color: #909399">[{{c.cateName}}]</p>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            layout="total, sizes,prev, pager, next"
            :page-sizes="[8,16,24]"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            :total="pageTotal">
          </el-pagination>
        </el-col>
        <el-col :md="6">
          <el-row :gutter="20" style="margin-top: 5px;">
            <el-divider content-position="center">推荐文章</el-divider>
            <section data-aos="zoom-in-up" :data-aos-delay="index*100" class="post-item"
                     v-show="p.headpic!==''&&p.headpic!==null" v-for="(p,index) in randomList" :key="p.id">
              <el-image class="post-img" :src="p.headpic" fit="cover"></el-image>
              <p class="post-title" @click="toPage(p.id)" v-text="p.title"></p>
              <div class="post-info">
                <span class="post-site" v-text="'来自：' + p.siteName"></span>
              </div>
            </section>
            <el-divider content-position="center">没有了~</el-divider>
          </el-row>
        </el-col>
      </el-row>
    </div>
    <FrontFooter/>
  </div>
</template>

<script>
import HeaderBar from '../../components/HeaderBar'
import FrontFooter from '../../components/FrontFooter'
import moment from 'moment'

export default {
  name: 'Index',
  components: { HeaderBar, FrontFooter },
  data () {
    return {
      activeName: '10',
      cate: [{ cateName: '文艺', cateId: '10' },
        { cateName: '生活', cateId: '11' },
        { cateName: '科技', cateId: '15' },
        { cateName: '新闻', cateId: '16' },
        { cateName: '视频', cateId: '45' },
        { cateName: '科学', cateId: '43' },
        { cateName: '商业', cateId: '34' },
        { cateName: '非虚构', cateId: '44' }],
      siteData: [],
      query: {
        keyWords: '',
        cateId: ''
      },
      randomList: '',
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0
    }
  },
  methods: {
    handleClick (tab) {
      console.log(tab.name)
      this.query.cateId = tab.name
      this.getSite()
    },
    getSite () {
      this.$axios.post('/site', this.query).then(value => {
        if (value.data.code === 200) {
          this.setPageValue(value.data)
        }
      })
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD')
    },
    setPageValue (value) {
      this.siteData = value.data.siteVOPageInfo.list
      this.pageTotal = value.data.siteVOPageInfo.total
      this.pageSize = value.data.siteVOPageInfo.pageSize
      this.currentPage = value.data.siteVOPageInfo.pageNum
    },
    moreSite (id) {
      localStorage.setItem('siteId', id)
      this.$router.push({ name: 'SitePage', params: { siteId: id } })
    },
    handleCurrentChange (val) {
      if (val) {
        this.currentPage = val
      }
      this.$axios.post('/site?pageIndex=' + this.currentPage + '&pageSize=' + this.pageSize, this.query).then(value => {
        this.setPageValue(value.data)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    },
    toPage (id) {
      this.$router.push({ name: 'PostPage', params: { postId: id } })
    },
    getRandomList () {
      this.$axios.get('/crawlerpost/randomlist').then(_ => {
        console.info(_.data)
        this.randomList = _.data.data
      })
    }
  },
  created () {
    this.getSite()
    this.getRandomList()
  }
}
</script>

<style scoped>
  .site-name,.site-brief{
    transition: all .3s linear 0s;
  }
  .site-box{
    position: relative;
    margin: 10px 0;
  }
  .site-brief {
    color: #909399;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .site-name{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .site-name:hover{
    color: #409EFF;
    cursor: pointer;
  }
  .content{
    margin: 10px auto;
    width: 60%;
    height: 100%;
  }
  h3,h5{
    margin:10px 3px;
  }
  .post-item{
  }
  .post-title:hover{
    color: #409EFF;
    cursor: pointer;
  }
  .post-item{
    position: relative;
  }
  .post-img{
    width: 250px;
    height: 150px;
    box-sizing: border-box;
  }
  .post-img::before{
    position: absolute;
    content: " ";
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.5);
  }
  .post-title {
    position: absolute;
    top: 0;
    color: #ffffff;
    left: 10px;
    font-size: 20px;
    font-weight: 500;
    transition: all .3s linear 0s;
    z-index: 99;
    word-break: break-all;
    text-overflow: ellipsis;
    display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
    -webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
    -webkit-line-clamp: 2; /** 显示的行数 **/
    overflow: hidden;  /** 隐藏超出的内容 **/
  }
  .post-item:hover > .post-title{
    top: 8px;
  }
  .post-item:hover > .post-info:before{
    height: 50px;
  }
  .post-info{
    width: 100%;
    height: 100%;
  }
  .post-info:before {
    position: absolute;
    content: " ";
    background: rgba(255,255,255,.3);
    width: 100%;
    height: 0;
    z-index: 88;
    left: 0;
    bottom:  0;
    clip-path: polygon(0 100%,100% 0,100% 100%);
    transition: .3s linear;
  }
  .post-site {
    position: absolute;
    display: block;
    font-size: 12px;
    padding-bottom: 10px;
    font-weight: bold;
    color: #ffffff;
    z-index: 99;
    bottom: 0;
    right: 10px;
  }
  .tab-box /deep/ .el-tabs__nav-wrap::after{
    background-color: #ffffff !important;
  }
</style>
