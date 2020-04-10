<template>
  <div class="footer">
    <el-row style="width: 60%;margin:0 auto;">
      <el-col :span="6" >
        <h1 style=" font-family: Smudgie,serif;font-size: 40px; color: #f0f2f5">LOGO</h1>
      </el-col>
      <el-col :span="6">
        <h4 style="color: #f0f2f5">联系我</h4>
        <p style="color: #f0f2f5"><i class="el-icon-phone"></i> : 1530306873</p>
        <p style="color: #f0f2f5"><i class="el-icon-add-location"></i> : 重庆市</p>
        <p style="color: #f0f2f5"><i class="el-icon-message"></i> : yuanchangyue@qq.com</p>
      </el-col>
      <el-col :span="6">
        <h4 style="color: #f0f2f5">社交</h4>
        <p style="color: #f0f2f5"><font-awesome-icon :icon="['fab', 'qq']"></font-awesome-icon>  604028466@qq.com</p>
        <p style="color: #f0f2f5"><font-awesome-icon :icon="['fab', 'weixin']"></font-awesome-icon> nansu05</p>
        <p style="color: #f0f2f5"><font-awesome-icon :icon="['fab', 'github']"></font-awesome-icon> www.github.com/yuanchangyue</p>
      </el-col>
      <el-col :span="6">
        <h4 style="color: #f0f2f5">快速连接</h4>
        <p><el-link style="color: #f0f2f5">首页</el-link></p>
        <p><el-link style="color: #f0f2f5">看点</el-link></p>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: ''
}
</script>

<style scoped>
  .footer{
    height: 300px;
    width: 100%;
    background: #2c3e50;
    padding-top: 30px;
  }
</style>
