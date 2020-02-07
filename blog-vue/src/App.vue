<template>
  <div class="login-box" id="app">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App'
}
</script>

<style>
  body {
    background-color: #f0f2f5;
  }
  .prompt-form{
    color: rgba(0,0,0,.45);
    line-height: 1.524;
    margin-top: -2px;
  }
</style>
