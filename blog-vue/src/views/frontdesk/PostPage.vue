<template>
  <div class="blog-all" style="width: 100%;background: #ffffff;">
    <FrontTopNav></FrontTopNav>
    <div class="content">
      <ul class="side-bar">
        <li class="side-bar-li">
          <font-awesome-icon :icon="['fa','heart']"></font-awesome-icon>
        </li>
        <li class="side-bar-li">
          <font-awesome-icon :icon="['fa','comment']"></font-awesome-icon>
        </li>
      </ul>
      <div class="post" v-html="postObj.content"></div>
    </div>
    <el-backtop></el-backtop>
  </div>
</template>

<script>
import FrontTopNav from '../../components/FrontTopNav'
export default {
  name: 'PostPage',
  data () {
    return {
      post: {
        id: ''
      },
      postObj: ''
    }
  },
  components: { FrontTopNav },
  methods: {
    getPost () {
      this.$axios.get('/crawlerpost/' + this.post.id).then(value => {
        this.postObj = value.data.data
        console.info(value)
      })
    }
  },
  created () {
    this.post.id = this.$route.params.postId
    this.getPost()
  }
}
</script>

<style scoped>
  body {
    padding: 0;
    margin: 0;
  }

  .content {
    margin: 0 auto;
    width: 640px;
    height: 100%;
    padding: 60px;
  }

  .side-bar {
    position: fixed;
    left: 450px;
    top: 400px;
    list-style: none;
  }

  .side-bar .side-bar-li {
    width: 60px;
    color: #909399;
    height: 60px;
    line-height: 60px;
    background: #e1e1e1;
    border-radius: 50%;
    margin-bottom: 10px;
    text-align: center;
    transition: all .3s linear 0s ;
  }

  .side-bar .side-bar-li:hover{
    color: #409EFF;
    cursor: pointer;
  }

  .content .post >>> p, div, h2, h4, h5, h6 {
    color: #333;
    line-height: 29px;
    margin-top: 20px;
  }

  .content .post >>> img {
    margin: 8px 0 8px 0;
    max-width: 100%;
    width: 100%;
    vertical-align: middle;
  }
</style>
