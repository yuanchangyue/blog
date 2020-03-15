<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="content-warp">
      <el-input v-model="form.title" style="min-width: 600px;margin-bottom: 10px" placeholder="请输入文章的标题"/>
      <mavon-editor v-model="form.originalContent" ref="md" @change="change" style="min-height: 600px;margin-bottom: 10px"/>
      <el-row type="flex" justify="end">
        <el-button type="danger" plain icon="el-icon-edit" @click="saveDraft">移至草稿箱</el-button>
        <el-button type="primary" icon="el-icon-upload" @click="drawer = true">发表文章</el-button>
      </el-row>
      <el-drawer
        title="文章基本设置"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
        <div>
          <el-form class="drawer-form" ref="form" label-width="80px">
            <el-form-item label="创建时间">
              <el-date-picker v-bind:disabled="disable" type="date" placeholder="选择日期" v-model="form.createTime" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="分类目录">
              <el-select v-model="form.categoryIds" placeholder="请选择分类" style="width: 100%;" multiple>
                <el-option  v-for="item in categoryList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                </el-option>
              </el-select>
              <div class="prompt-form">* 类别不能空</div>
            </el-form-item>
            <el-form-item label="文章标签">
              <el-select v-model="form.tagIds" placeholder="请选择标签" style="width: 100%;" multiple>
                <el-option  v-for="item in tagList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                </el-option>
              </el-select>
              <div class="prompt-form">* 标签不能空</div>
            </el-form-item>
            <el-form-item label="访问密码">
              <el-input type="password" v-model="form.password" show-password v-bind:placeholder="passwordHind"></el-input>
            </el-form-item>
            <el-form-item label="文章备注">
              <el-input type="textarea" v-model="form.summary" placeholder="请输入文章的备注"></el-input>
            </el-form-item>
            <el-form-item label="开启评价">
                <el-radio v-model="form.disallowComment" label="true">是</el-radio>
                <el-radio v-model="form.disallowComment" label="false">否</el-radio>
            </el-form-item>
            <el-row type="flex" justify="end">
              <el-button icon="el-icon-edit" @click="saveDraft">移至草稿箱</el-button>
              <el-button icon="el-icon-upload" type="primary" @click="submit">发布</el-button>
            </el-row>
          </el-form>
        </div>
      </el-drawer>
    </div>
  </div>
</template>

<script>
import TopNav from '../components/TopNav'
import BreadCrumb from '../components/BreadCrumb'
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
      drawer: false,
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
        status: 0
      },
      updateId: '',
      disable: false,
      radio: '1',
      passwordHind: '可以设置访问密码',
      isInsert: true
    }
  },
  methods: {
    change (value, render) {
      this.form.formatContent = render
    },
    saveDraft () {
      var formData = this.form
      this.form.status = 1
      this.$axios.post('/post', formData).then(value => {
        console.info(value)
        this.$notify({
          title: '成功',
          message: '文章已经移至草稿箱',
          type: 'success'
        })
        this.$router.push({ path: '/postlist' })
      }).catch(_ => {
      })
    },
    submit () {
      if (this.isInsert) {
        this.$axios.post('/post', this.form).then(value => {
          this.$notify({
            title: '成功',
            message: '文章发布成功',
            type: 'success'
          })
          this.$router.push({ path: '/postlist' })
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
          this.$router.push({ path: '/postlist' })
        }).catch(_ => {
          this.$notify({
            title: '警告',
            message: '文章编辑失败',
            type: 'warning'
          })
        })
      }
    },
    handleClose (done) {
      this.$confirm('确认取消发布文章吗？')
        .then(_ => {
          done()
        }).catch(_ => {})
    },
    showCategoryAndTag () {
      this.$axios.get('/category/list').then(value => {
        this.categoryList = value.data.data
      }).catch(_ => {
        this.$notify({
          title: '警告',
          message: '分类获取失败',
          type: 'warning'
        })
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
    }
  },
  created () {
    this.showCategoryAndTag()
    this.updateId = this.$route.params.postId
    if (this.updateId != null) {
      this.isInsert = false
      this.passwordHind = '********'
      this.$axios.get('/post/' + this.updateId).then(value => {
        var data = value.data
        console.info(data)
        if (data.code === 200) {
          this.disable = true
          this.form.title = data.data.title
          this.form.originalContent = data.data.originalContent
          this.form.summary = data.data.summary
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
</style>
