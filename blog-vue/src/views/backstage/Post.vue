<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp" style="v-height:100%;">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item prop="title">
      <el-input v-model="form.title"  style="min-width: 600px;margin-bottom: 10px" placeholder="请输入文章的标题"/>
        </el-form-item>
      </el-form>
      <mavon-editor v-model="form.originalContent" ref="md" @change="change" style="min-height: 600px;margin-bottom: 10px"/>
      <el-row type="flex" justify="end">
<!--        <el-button type="danger" plain icon="el-icon-edit" @click="saveDraft('form')">移至草稿箱</el-button>-->
        <el-button type="primary" icon="el-icon-upload" @click="drawer = true">发表文章</el-button>
      </el-row>
      <el-drawer
        size="30%"
        title="文章基本设置"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
        <div>
          <el-form class="drawer-form" ref="form" label-width="80px" :model="form" :rules="rules">
            <el-form-item label="创建时间">
              <el-date-picker v-bind:disabled="disable" type="date" placeholder="选择日期" v-model="form.createTime" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="分类目录" prop="categoryIds">
              <el-select v-model="form.categoryIds" placeholder="请选择分类" style="width: 100%;" multiple>
                <el-option  v-for="item in categoryList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="文章标签" prop="tagIds">
              <el-select v-model="form.tagIds" placeholder="请选择标签" style="width: 100%;" multiple>
                <el-option  v-for="item in tagList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="访问密码">
              <el-input type="password" v-model="form.password" show-password v-bind:placeholder="passwordHind"></el-input>
            </el-form-item>
            <el-form-item label="文章备注" prop="summary">
              <el-input type="textarea" v-model="form.summary" placeholder="请输入文章的备注"></el-input>
            </el-form-item>
            <el-form-item label="开启评价">
                <el-radio v-model="form.disallowComment" label="true">是</el-radio>
                <el-radio v-model="form.disallowComment" label="false">否</el-radio>
            </el-form-item>
            <el-divider/>
            <div style="width: 100%;margin-bottom: 10px">
              <span class="demonstration" style="color: #909399">缩略图</span>
              <el-image :src="imgSrc" :fit="fit" style="width: 100%;height: 200px;" @click="innerDrawer = true"></el-image>
            </div>
            <el-row type="flex" justify="end">
              <el-button icon="el-icon-edit" @click="saveDraft('form')">移至草稿箱</el-button>
              <el-button icon="el-icon-upload" type="primary" @click="submit('form')">发布</el-button>
            </el-row>
          </el-form>
        </div>
        <el-drawer
          title="缩略图列表"
          :append-to-body="true"
          style="height: 1200px;"
          size="20%"
          :before-close="handleInnerClose"
          :visible.sync="innerDrawer">
          <div class="autoScroll" style="width: 100%;height: 800px;overflow: auto">
          <el-col style="margin: 5px" :span="11" v-for="item in attachmentList" :key="item.id">
            <transition name="el-fade-in">
              <el-card>
                <el-image :src="handlerUrl(item.path)" :lazy="true" @click="handlerThumbnail(item.path)" style="width: 100%; height: 200px"/>
              </el-card>
            </transition>
          </el-col>
          <el-col :span="24">
          <el-pagination
            small
            style="margin: 10px;"
            background
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            layout="total, sizes,prev, pager, next"
            :page-sizes="[4,8,12]"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            :total="pageTotal">
          </el-pagination>
          </el-col>
          </div>
        </el-drawer>
      </el-drawer>
    </div>
  </div>
</template>

<script>
import TopNav from '../../components/TopNav'
import BreadCrumb from '../../components/BreadCrumb'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  name: 'Post',
  components: { TopNav, BreadCrumb, mavonEditor },
  data: function () {
    return {
      bread: {
        firstBread: '文章',
        secondBread: '写文章'
      },
      fit: '',
      drawer: false,
      innerDrawer: false,
      direction: 'rtl',
      categoryList: [],
      tagList: '',
      form: {
        categoryIds: [],
        tagIds: [],
        title: '',
        password: '',
        createTime: '',
        disallowComment: 'false',
        summary: '',
        formatContent: '',
        originalContent: '',
        status: 0,
        thumbnail: ''
      },
      rules: {
        summary: [
          { required: true, message: '请输入简介内容', trigger: 'blur' }
        ],
        categoryIds: [
          { type: 'array', required: true, message: '请至少选择一个分类', trigger: 'change' }
        ],
        tagIds: [
          { type: 'array', required: true, message: '请至少选择一个标签', trigger: 'change' }
        ],
        title: [
          { required: true, message: '文章标题不能为空', trigger: 'blur' }
        ]
      },
      imgSrc: require('../../assets/empty.svg'),
      updateId: '',
      disable: false,
      radio: '1',
      passwordHind: '可以设置访问密码',
      isInsert: true,
      pageTotal: 0,
      currentPage: 0,
      pageSize: 4,
      attachmentList: [],
      attachmentTypeList: [],
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
  methods: {
    change (value, render) {
      this.form.formatContent = render
    },
    saveDraft (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var formData = this.form
          this.form.status = 1
          this.$axios.post('/post', formData).then(value => {
            this.$notify({
              title: '成功',
              message: '文章已经移至草稿箱',
              type: 'success'
            })
            this.$router.push({ path: '/management/postlist' })
          }).catch(_ => {
          })
        } else {
          this.$message.error('请输入有效的信息!')
          return false
        }
      })
    },
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isInsert) {
            this.$axios.post('/post', this.form).then(_ => {
              this.$notify({
                title: '成功',
                message: '文章发布成功',
                type: 'success'
              })
              this.$router.push({ path: '/management/postlist' })
            }).catch(_ => {
              this.$notify({
                title: '警告',
                message: '文章发布失败',
                type: 'warning'
              })
            })
          } else {
            this.$axios.put('/post/' + this.updateId, this.form).then(_ => {
              this.$notify({
                title: '成功',
                message: '文章编辑成功',
                type: 'success'
              })
              this.$router.push({ path: '/management/postlist' })
            }).catch(_ => {
              this.$notify({
                title: '警告',
                message: '文章编辑失败',
                type: 'warning'
              })
            })
          }
        } else {
          this.$message.error('请输入有效的信息!')
          return false
        }
      })
    },
    handleClose (done) {
      this.$confirm('确认取消发布文章吗？')
        .then(_ => {
          done()
        }).catch(_ => {})
    },
    handleInnerClose (done) {
      this.$confirm('确认关闭缩略图吗？')
        .then(_ => {
          done()
        }).catch(_ => {})
    },
    showCategoryAndTag () {
      this.$axios.get('/category/list').then(value => {
        this.categoryList = value.data.data
      }).catch(_ => {
        this.$notify.warning('分类获取失败')
      })
      this.$axios.get('/tag').then(value => {
        this.tagList = value.data
      }).catch(_ => {
        this.$notify({
          title: '警告',
          message: '标签获取失败',
          type: 'warning'
        })
      })
    },
    handleCurrentChange (val) {
      this.currentPage = val
      var page = { pageIndex: val, pageSize: this.pageSize }
      this.$axios.get('/attachment', { params: page }).then(value => {
        this.setPageValue(value)
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.handleCurrentChange()
    },
    initList () {
      this.$axios.get('/attachment?pageSize=' + this.pageSize).then(value => {
        this.setPageValue(value)
      })
    },
    handlerUrl (url) {
      return 'http://localhost:8089/' + url
    },
    setPageValue (value) {
      this.attachmentList = value.data.list
      this.pageTotal = value.data.total
      this.pageSize = value.data.pageSize
      this.currentPage = value.data.pageNum
    },
    handlerThumbnail (path) {
      this.imgSrc = this.handlerUrl(path)
      this.form.thumbnail = path
      this.innerDrawer = false
      this.fit = 'cover'
    }
  },
  created () {
    this.initList()
    this.showCategoryAndTag()
    this.updateId = this.$route.params.postId
    if (this.updateId != null) {
      this.isInsert = false
      this.passwordHind = '********'
      this.$axios.get('/post/' + this.updateId).then(value => {
        var data = value.data
        if (data.code === 200) {
          this.disable = true
          this.form.title = data.data.title
          this.form.originalContent = data.data.originalContent
          this.form.summary = data.data.summary
          this.form.thumbnail = data.data.thumbnail
          if (data.data.thumbnail != null && data.data.thumbnail !== '') {
            this.imgSrc = this.handlerUrl(data.data.thumbnail)
          }
          this.form.disallowComment = data.data.disallowComment.toString()
          for (var n = 0; n < data.data.tags.length; n++) {
            this.form.tagIds.push(data.data.tags[n].id)
          }
          for (var m = 0; m < data.data.categories.length; m++) {
            this.form.categoryIds.push(data.data.categories[m].id)
          }
        }
      })
    }
  }
}

</script>

<style scoped>
  .drawer-form{
    padding: 20px;
  }
  .el-col{
    overflow:scroll;
  }
</style>
