# youyamvc
优雅MVC 网站地址：http://www.magicalcoder.com

简介：
youyamvc 由MagicalCoder网站开发的一款基于SpringMvc搭建的javaWeb框架，
配合MagicalCoder提供的在线编程工具，通过拖拽快速完成您的业务功能，为我们开发者节省大量的时间，
使用了这个，或许您再也不用加班啦，那将是我们最大的荣幸与目标

技术选型：
1 技术框架 springMvc + MyBatis + Jsp
2 其他技术 tomcat maven  memcache
3 jdk1.6以上 默认1.6 如需更改请查询pom.xml

项目搭建过程
1 下载项目
2 安装maven
3 导入工程
4 安装mysql5.0以上版本
5 安装memcache
    linux apt-get install memcached
    window 1. 下载memcache的windows稳定版，解压放某个盘下面，比如在c:\memcached
           2. 在终端（也即cmd命令界面）下输入 'c:\memcached\memcached.exe -d install' 安装
           3. 再输入： 'c:\memcached\memcached.exe -d start' 启动。NOTE: 以后memcached将作为windows的一个服务每次开机时自动启动。这样服务器端已经安装完毕了。
6 配置
src/main/resources/jdbc.properties
src/main/resources/xmemcached.properties

7 导入项目sql
  youyamvc.sql

8 tomcat启动项目
  后台：http://localhost:8080/admin     用户名密码 admin/admin

9 至此您完成了youyamvc的部署

10 youyamvc只是一个框架，真正能帮助您的 请访问 http://www.magicalcoder.com/admin 注册您的专属账号
    使用在线编程工具，生成代码后，覆盖到youyamvc 然后重启您的工程，看看MagicalCoder为您编写的代码是否能正常运行吧
    还是亲自来尝试一下吧