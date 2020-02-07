<template>
    <div class="bread-box">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{bread1}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{bread2}}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
export default {
  name: 'BreadCrumb',
  props: {
    bread1: String,
    bread2: String
  }
}
</script>

<style scoped>
    .bread-box {
        margin-top: 10px;
        padding:15px 80px;
        box-shadow: 0 2px 2px -2px rgba(0, 0, 0, .2);
        background-color: #ffffff;
        box-sizing: border-box;
    }
</style>
