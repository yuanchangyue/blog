<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav></FrontTopNav>
    <div class="content">
      <el-row>
        <h1 v-if="cateObj!==null" v-text="'#' + cateObj.name" style="text-align: center"></h1>
      </el-row>
    </div>
    <el-backtop></el-backtop>
    <FrontFooter/>
  </div>
</template>

<script>
import FrontTopNav from '../../components/FrontTopNav'
import FrontFooter from '../../components/FrontFooter'

export default {
  name: '',
  components: { FrontTopNav, FrontFooter }
}
</script>

<style scoped>

</style>
