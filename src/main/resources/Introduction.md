# 分布式Session的实践

1. 启动三次服务器

   ![](/images/1.jpg)

2. 在其中一个服务器的Session中放入key和value

3. 在另外两个服务器可以取出key对应的value



# 作用

> 通过在 SpirngBoot 项目中引入Spring Session 和 SpringBoot 整合 Redis 的 start 依赖，我们可以快速搭建分布式 Session 环境，同时也可以在 application.properties 中自定义 Session 的过期时间、命名空间、刷新模式的配置。