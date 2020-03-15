<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp">
      <el-row :gutter="20">
        <el-card>
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="关键字">
            <el-input v-model="form.keyWords" placeholder="关键字"></el-input>
          </el-form-item>
          <el-form-item label="附件类型">
            <el-select v-model="form.type" placeholder="文章分类">
              <el-option value="">全部</el-option>
              <el-option v-for="item in typeDate"
                         :key="item.id"
                         :label="item.name"
                         :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button icon="el-icon-search" >查询</el-button>
              <el-button type="primary" icon="el-icon-upload" @click="dialogVisible = true" >上传</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
        </el-card>
        <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="415px"
          center>
          <el-upload
            :limit="3"
            drag
            action="#"
            multiple
            :http-request="submitUpload">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">文件不超过2MB</div>
          </el-upload>
        </el-dialog>
      </el-row>
      <el-row style="margin-top: 20px;">
        <el-col style="margin: 5px" :span="4" :sm="5" :xs="24" :lg="4" v-for="item in attachmentList" :key="item.id">
          <el-card>
            <el-image :src="handlerUrl(item.path)"  :lazy="true" :preview-src-list="srcList" style="width: 100%; height: 200px"/>
            <el-link @click="openDrawer(item.id)">{{item.name}}</el-link>
          </el-card>
        </el-col>
      </el-row>
      <el-drawer
        title="附件基本信息"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
        <div class="attachement-info-box">
          <p>附件名称<i class="el-icon-upload"></i></p>
          <p class="info-text" v-text="this.attachment.name"></p>
          <p>宽度</p>
          <p class="info-text" v-text="this.attachment.width"></p>
          <p>高度</p>
          <p class="info-text" v-text="this.attachment.height"></p>
          <p>路径</p>
          <p class="info-text" v-text="this.attachment.path"></p>
          <p>缩略图路径</p>
          <p class="info-text" v-text="this.attachment.thumbPath"></p>
          <p>媒体类型</p>
          <p class="info-text" v-text="this.attachment.mediaType"></p>
          <p>图片类型</p>
          <p class="info-text" v-text="this.attachment.suffix"></p>
          <p>创建时间</p>
          <p class="info-text" v-text="this.attachment.createTime"></p>
          <p>附件大小</p>
          <p class="info-text" v-text="this.attachment.size"></p>
          <el-row type="flex" justify="end">
            <el-button icon="el-icon-delete" type="danger" @click="removeAttachment">删除</el-button>
          </el-row>
        </div>
      </el-drawer>
      <el-pagination
        style="margin: 10px 0;"
        background
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
        layout="total, sizes,prev, pager, next"
        :page-sizes="[8,12]"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :total="pageTotal">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import TopNav from '../components/TopNav'
import BreadCrumb from '../components/BreadCrumb'
import moment from 'moment'

export default {
  name: 'Attachment',
  components: { TopNav, BreadCrumb },
  data () {
    return {
      bread: {
        firstBread: '附件',
        secondBread: '附件列表'
      },
      form: {
        keyWords: '',
        type: ''
      },
      direction: 'rtl',
      drawer: false,
      pageTotal: 0,
      currentPage: 0,
      pageSize: 0,
      typeDate: [],
      dialogVisible: false,
      attachmentList: [],
      srcList: [],
      attachment: {
        id: '',
        name: '',
        path: '',
        thumbPath: '',
        mediaType: '',
        suffix: '',
        width: '',
        height: '',
        size: '',
        createTime: ''
      }
    }
  },
  mounted () {
    this.initList()
  },
  methods: {
    submitUpload (p) {
      var fileObj = p.file
      let formData = new FormData()
      formData.append('file', fileObj)
      this.$axios.post('/attachment/upload', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      }).then(_ => {
        this.$notify.success('上传成功')
        this.initList()
      })
    },
    initList () {
      this.$axios.get('/attachment').then(value => {
        this.setPageValue(value)
        for (var n = 0; n < value.data.list.length; n++) {
          this.srcList.push(this.handlerUrl(value.data.list[n].path))
        }
        console.info(value.data.list)
        console.info(this.srcList)
      })
    },
    handlerUrl (url) {
      console.info('http://localhost:8089/' + url)
      return 'http://localhost:8089/' + url
    },
    setPageValue (value) {
      this.attachmentList = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
    },
    handleCurrentChange (val) {
      var page = { pageIndex: val, pageSize: this.pageSize }
      this.$axios.get('/attachment', { params: page }).then(value => {
        this.setPageValue(value)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    },
    handleClose (done) {
      this.$confirm('确定关闭吗？')
        .then(_ => {
          done()
        }).catch(_ => {})
    },
    dateFormat (d) {
      return moment(d).format('YYYY-MM-DD h:mm:ss a')
    },
    openDrawer (id) {
      this.drawer = true
      this.$axios.get('/attachment/' + id).then(value => {
        this.attachment.createTime = this.dateFormat(value.data.data.createTime)
        this.attachment.suffix = value.data.data.suffix
        this.attachment.height = value.data.data.height
        this.attachment.width = value.data.data.width
        this.attachment.mediaType = value.data.data.mediaType
        this.attachment.name = value.data.data.name
        this.attachment.path = value.data.data.path
        this.attachment.thumbPath = value.data.data.thumbPath
        this.attachment.size = this.change2Mb(value.data.data.size)
        this.attachment.id = value.data.data.id
        console.info(this.attachment.createTime)
      })
    },
    change2Mb (size) {
      if (!size) { return '' }
      var num = 1024.00
      if (size < num) { return size + 'B' }
      if (size < Math.pow(num, 2)) { return (size / num).toFixed(2) + 'K' }
      if (size < Math.pow(num, 3)) return (size / Math.pow(num, 2)).toFixed(2) + 'M'
      if (size < Math.pow(num, 4)) return (size / Math.pow(num, 3)).toFixed(2) + 'G'
      return (size / Math.pow(num, 4)).toFixed(2) + 'T'
    }
  }
}
</script>

<style scoped>
  .attachement-info-box{
    padding: 20px;
  }
  .info-text{
    color: #909399;
    font-size: 14px;
  }
</style>
