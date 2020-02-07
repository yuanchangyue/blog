<template>
<div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
    <div class="category-warp">
        <el-row :gutter="20">
            <el-col :span="10">
                <el-card class="box-card">
                    <div slot="header">
                        <span>新增分类</span>
                    </div>
                    <el-form>
                        <el-form-item label="名称: *">
                            <el-input size="small" v-model="form.name" placeholder="请输入类别名称"/>
                            <div class="prompt-form">* 名称不能空,长度不能超过20</div>
                        </el-form-item>
                        <el-form-item label="别名: *">
                            <el-input size="small" type="text" v-model="form.nickname" placeholder="请输入类别别名"/>
                            <div class="prompt-form">* 别名不能空,长度不能超过20,不能重复</div>
                        </el-form-item>
                        <el-form-item label="父级类别">
                            <el-select v-model="value" style="width: 100%;" size="small" placeholder="请选择类别">
                                <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                            <div class="prompt-form">* 选择父级类别(一级分类)</div>
                        </el-form-item>
                        <el-form-item label="描述">
                            <el-input type="textarea" v-model="form.description" placeholder="请输入描述内容"/>
                            <div class="prompt-form">* 关于类别描述</div>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">立即创建</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :span="14">
                <el-card class="box-card">
                    <div slot="header">
                        <span>全部分类</span>
                    </div>
                    <div>
                        <el-table
                                :data="tableData"
                                style="width: 100%">
                            <el-table-column
                                    prop="name"
                                    label="名称"
                                    width="180">
                            </el-table-column>
                            <el-table-column
                                    prop="slugName"
                                    label="别名"
                                    width="180">
                            </el-table-column>
                            <el-table-column
                                    prop="description"
                                    label="描述">
                            </el-table-column>
                            <el-table-column
                                    label="操作">
                                <div>
                                    <el-link type="primary">编辑</el-link>
                                </div>
                            </el-table-column>
                        </el-table>
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
  name: 'Category',
  components: { TopNav, BreadCrumb },
  data () {
    return {
      bread: {
        firstBread: '文章',
        secondBread: '类别'
      },
      categories: [],
      form: {
        name: ''
      },
      tableData: []
    }
  }
}
</script>

<style scoped>
    .category-warp {
        height: 100%;
        padding: 30px 80px;
    }
</style>
