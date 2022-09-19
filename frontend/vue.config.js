const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false, //무슨옵션?
  devServer: {
    proxy: 'http://localhost:8080'
  }
})
