<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav/>
    <el-carousel :interval="6000" type="card" height="340px">
      <el-carousel-item v-for="item in img" :key="item.img">
        <el-image :src="item.img" fit="scale-down"></el-image>
      </el-carousel-item>
    </el-carousel>
    <div class="content">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane :name="c.cateId" :label="c.cateName" v-for="c in cate" :key="c.cateId">
          <transition name="el-fade-in-linear" duration="40000">
            <el-row :gutter="10">
              <el-col :span="6" v-for="site in siteData" :key="site.id" align="left" style="margin: 20px 0;">
                <el-badge :value="site.articleNum">
                  <el-image style="width: 200px;height: 200px;" :src="site.pic"></el-image>
                </el-badge>
                <p style="font-size: 12px;color: #909399">[{{c.cateName}}]</p>
                <h3 style="" v-text="site.name"></h3>
                <p style="color: #909399;font-size: 14px" v-text="site.brief"></p>
                <span class="more-btn" @click="moreSite(site.id)">更多</span>
              </el-col>
            </el-row>
          </transition>
        </el-tab-pane>
      </el-tabs>
    <el-pagination
      style="margin: 50px 0;"
      background
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      layout="total, sizes,prev, pager, next"
      :page-sizes="[8,16,24]"
      :current-page.sync="currentPage"
      :page-size="pageSize"
      :total="pageTotal">
    </el-pagination>
    </div>
    <FrontFooter/>
  </div>
</template>

<script>
import FrontTopNav from '../../components/FrontTopNav'
import FrontFooter from '../../components/FrontFooter'

export default {
  name: 'Index',
  components: { FrontTopNav, FrontFooter },
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
      img: [ { img: 'http://image.s-reader.com/setting/rss/d8b796f69090873a41cc248bd6d49e3c.png' },
        { img: 'http://image.s-reader.com/setting/rss/7b1f3e6b48cca71fe8af933979e57ff4.png' },
        { img: 'http://image.s-reader.com/setting/rss/26b3e2fdb45f189312de5f1bdbdbea1b.png' },
        { img: 'http://image.s-reader.com/setting/rss/44a632495c1a4a5df3d6f38a8674edcd.png' } ],
      siteData: [],
      query: {
        keyWords: '',
        cateId: ''
      },
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
    }
  },
  created () {
    this.getSite()
  }
}
</script>

<style scoped>
  body{
    padding: 0;
    margin: 0;
  }
  .content{
    margin:0 auto;
    width: 60%;
  }
  .more-btn {
    transition: background 0.3s linear, color 0.3s linear;
    width: 40px;
    height: 30px;
    font-size: 14px;
    padding: 5px 20px;
    border: 1px solid #409EFF;
    color: #222222;
    cursor: pointer;
  }
  .more-btn:hover{
    background: #409EFF;
    color: #ffffff;
  }
  h3,h5{
    margin:10px 3px;
  }

</style>
