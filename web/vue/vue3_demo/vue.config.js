const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    /*devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:6060', // 后端接口地址
                changeOrigin: true, // 是否允许跨越
                secure: false,
                pathRewrite: {
                    '^/api': ''// 重写,
                }
            }
        }
    }*/
})
