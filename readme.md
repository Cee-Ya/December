# December 基础版本

> 适用于java 后端快速开发的基础版本，目前已经集成了常用的功能，后续会不断完善，欢迎大家提出宝贵意见
>
> 主旨为master为基础分支，其他子分支为功能性分支，个人拉取主分支后，假设需要支付就直接merge pay分支即可
> 
> 目前使用JDK17版本


## 目前引用的开源框架

| 名称         | 版本    | 链接                                                    |
| ------------ | ------- | ------------------------------------------------------- |
| spring-boot  | 2.7.0   | https://github.com/spring-projects/spring-boot          |
| mybatis-plus | 3.5.3.1 | https://github.com/baomidou/mybatis-plus                |
| ip2region    | 2.7.0   | https://github.com/lionsoul2014/ip2region               |
| Sa-Token     | 1.34.0  | http://sa-token.dev33.cn/                               |
| easyexcel    | 3.2.1   | https://github.com/alibaba/easyexcel                    |
| poi-tl       | 1.12.1  | http://deepoove.com/poi-tl                              |
| smart-doc    | 2.6.7   | https://smart-doc-group.github.io/#/zh-cn/?id=smart-doc |

## 分支功能
 - master 基础分支
 - obj-storage 云存储分支 进行中
 - message 短信分支 未开始
 - pay 支付分支 进行中
 - pdf pdf转换分支 未开始

## 目前开发计划
1. ~~云存储接入（计划接入阿里OSS，腾讯COS，七牛云）~~  已完成
2. 统一支付接入（计划接入微信支付，支付宝支付）  进行中
3. docker脚本编写  未开始
4. 日志压缩并上传到云存储  未开始
5. 任务调度  未开始
6. 传参安全开发，暂定传输加密以及验签  未开始

