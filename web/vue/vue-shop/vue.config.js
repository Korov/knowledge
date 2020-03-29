module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端接口地址
        changeOrigin: true, // 是否允许跨越
        pathRewrite: {
          '^/api': '/api'// 重写,
        }
      }
    }
  }
}
