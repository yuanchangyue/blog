<template>
    <div>
        <TopNav/>
        <BreadCrumb :bread1="bread.firstBread"/>
    </div>
</template>

<script>
import TopNav from '../../components/TopNav'
import BreadCrumb from '../../components/BreadCrumb'
export default {
  name: '',
  components: { TopNav, BreadCrumb },
  data () {
    return {
      bread: {
        firstBread: '控制台'
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
</style>
