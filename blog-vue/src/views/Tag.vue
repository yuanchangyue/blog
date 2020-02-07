<template>
    <div class="all-warp">
        <TopNav/>
        <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
        <div class="tag-box">
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-card class="tag-operation">
                        <div slot="header">
                            <span>{{ title }}</span>
                        </div>
                        <el-form>
                            <el-form-item label="标签名称:*">
                                <el-input type="text" v-model="form.name" placeholder="请输入标签名称" size="small"/>
                            </el-form-item>
                            <el-form-item label="标签别名:*">
                                <el-input type="text" v-model="form.slugName" placeholder="请输入标签别名" size="small"/>
                            </el-form-item>
                            <el-form-item>
                                <el-button size="small" type="primary" @click="insertTag">{{ button }}</el-button>
                                <el-button v-if="!isInsert" size="small" @click="returnInsert">返回添加</el-button>
                            </el-form-item>
                        </el-form>
                    </el-card>
                </el-col>
                <el-col :span="12">
                    <el-card class="tag-view">
                        <div slot="header">
                            <span>全部标签</span>
                        </div>
                        <div class="tags-list">
                            <el-tag v-for="item in items"
                                    :key="item.name"
                                    :id="item.id" @close="handleClose(item)" @click="update(item.id)"
                                    closable>
                                {{ item.name }}
                            </el-tag>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import TopNav from '../components/TopNav'
import BreadCrumb from '../components/BreadCrumb'
export default {
  name: 'Tag',
  components: { TopNav, BreadCrumb },
  mounted () {
    this.showTagList()
  },
  data () {
    return {
      bread: {
        firstBread: '文章',
        secondBread: '标签'
      },
      items: [],
      title: '新增标签',
      button: '新增',
      isInsert: true,
      currentTagId: '',
      form: {
        name: '',
        slugName: ''
      }
    }
  },
  methods: {
    showMessage: function (type, messageStr) {
      this.$message({
        type: type,
        message: messageStr
      })
    },
    showTagList () {
      this.$axios.get('/tag').then(res => {
        console.info(res.data)
        this.items = res.data
      }).catch(() => {
        this.showMessage('error', '网络错误')
      })
    },
    handleClose (tag) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('/tag/' + tag.id).then(() => {
          this.showMessage('success', '删除成功')
          this.items.splice(this.items.indexOf(tag), 1)
        }).catch(() => {
          this.showMessage('info', '已取消删除')
        })
      }).catch(() => {
        this.showMessage('info', '已取消删除')
      })
    },
    update (tagId) {
      this.$axios.get('/tag/' + tagId).then(value => {
        this.title = '修改标签'
        this.form.name = value.data.name
        this.form.slugName = value.data.slugName
        this.button = '更新'
        this.isInsert = false
        this.currentTagId = tagId
      }).catch(reason => {
      })
    },
    returnInsert () {
      this.title = '新增标签'
      this.form.name = ''
      this.form.slugName = ''
      this.button = '新增'
      this.isInsert = true
    },
    insertTag () {
      var formData = {
        name: this.form.name,
        slugName: this.form.slugName
      }
      if (this.isInsert) {
        this.$axios.post('/tag', formData).then(value => {
          this.showMessage('success', '新增' + value.data.name + '标签成功!')
          this.showTagList()
        }).catch(reason => {
          this.showMessage('info', '新增失败!')
        })
      } else {
        this.$axios.put('/tag/' + this.currentTagId, formData).then(value => {
          this.showMessage('success', '修改标签成功!')
        }).catch(reason => {
          this.showMessage('info', '修改失败!')
        })
      }
    }
  }
}
</script>

<style scoped>
    *{
        padding: 0;
        margin: 0;
    }
    .all-warp {
        height: 100%;
        box-sizing: border-box;
    }
    .tag-box {
        height: 100%;
        padding: 30px 80px;
    }
    .el-tag{
        padding:0 8px;
        margin:10px 8px;
    }
</style>
