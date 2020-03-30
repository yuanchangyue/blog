<template>
  <div class="all-warp">
    <TopNav/>
    <BreadCrumb :bread1="bread.firstBread" :bread2="bread.secondBread"/>
  </div>
</template>

<script>

import TopNav from '../components/TopNav'
import BreadCrumb from '../components/BreadCrumb'

export default {
  name: 'Personal',
  components: { TopNav, BreadCrumb },
  data: function () {
    return {
      bread: {
        firstBread: '个人中心',
        secondBread: '个人资料'
      }
    }
  }
}
</script>

<style scoped>
</style>
