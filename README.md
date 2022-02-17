# image
图片处理

# 配置参数
1. 登录阿里云控制台
   开通商品抠图服务：https://vision.console.aliyun.com/cn-shanghai/detail/imageseg
   创建上海区域oss bucket：https://oss.console.aliyun.com/bucket
   生成并获取阿里云密钥： https://ram.console.aliyun.com/manage/ak
2. 修改application.yml中的配置项

# 测试：
 启动项目后访问: http://localhost:8080/product/matting?form=whiteBK&image=https://mepic.oss-cn-shanghai.aliyuncs.com/test.jpg

