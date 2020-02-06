module.exports = {
  devServer: {
    port: '8081',
    proxy: {
      '/api': {
        target: 'https://localhost:8443',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
