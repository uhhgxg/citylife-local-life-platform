# CityLife Local Life Platform

CityLife 是一个面向城市本地生活场景的 Java 学习项目：用户可以发现附近商户、浏览探店内容、关注创作者，并参与限时优惠券活动。

本仓库用于 Git、Redis 和 Spring Boot 工程化学习，代码基于公开的黑马点评学习项目整理，并进行了项目场景与文档包装，不代表原创商业系统。

## 项目场景

平台围绕“发现好店—发布体验—互动关注—领取优惠”的本地生活链路展开：

- 用户短信登录与会话续期
- 商户分类、商户详情与分页查询
- Redis 缓存、缓存穿透与过期策略实践
- 探店笔记发布、点赞、关注与滚动分页
- 商户优惠券与秒杀下单
- 每日签到与连续签到统计

## 技术栈

- Java 8、Spring Boot 2.3、Spring MVC
- MySQL、MyBatis-Plus
- Redis、Redisson、Lua
- Maven、Lombok、Hutool

## 快速开始

1. 准备 Java 8、Maven、MySQL 和 Redis。
2. 初始化 `src/main/resources/db/hmdp.sql`。
3. 根据本地环境设置 `MYSQL_URL`、`MYSQL_USERNAME`、`MYSQL_PASSWORD`、`REDIS_HOST`、`REDIS_PORT` 和 `REDIS_PASSWORD`。
4. 执行 `mvn spring-boot:run`，服务默认运行在 `8081` 端口。

## 学习重点

建议按“登录 → 商户查询与缓存 → 秒杀 → 探店互动 → 工程化治理”的顺序阅读源码，重点关注 `service`、`utils`、`config` 和 `resources` 目录。

## 来源与使用说明

本仓库仅用于个人 Git 学习、代码阅读和工程实践。原始公开学习项目来源：

`https://github.com/MagicToDo/hm-dianping`

如将本项目用于公开展示，请同时遵守上游项目及其依赖的许可证与署名要求。

## 登录与用户会话

用户通过短信验证码完成登录，服务端使用 Redis 保存登录凭证，并通过拦截器刷新活跃用户的过期时间。核心代码位于 `UserController`、`UserServiceImpl`、`LoginInterceptor` 和 `RefreshTokenInterceptor`。
## 商户发现与分页缓存

首页按照商户类型查询门店，详情页通过缓存减少数据库访问。查询链路集中在 `ShopController`、`ShopServiceImpl` 与 `CacheClient`，适合用来观察缓存键设计、序列化和过期时间的取舍。
## 缓存预热与穿透治理

商户类型等低频变更数据可以在启动或运维脚本中预热；对于不存在的数据，使用空值短暂缓存或布隆过滤器等方式降低重复穿透压力。这里重点练习缓存与数据库的一致性边界。
## 限时优惠券与秒杀下单

CityLife 将优惠券库存和用户下单链路作为高并发练习场景：用户在活动窗口内抢券，服务端校验库存、生成订单并避免同一用户重复下单。相关代码位于 `VoucherController` 和 `VoucherOrderServiceImpl`。
## Lua 原子操作与超卖治理

库存扣减和重复下单判断需要尽量在 Redis 内完成原子校验，项目通过 `seckill.lua` 将多步判断合并，减少并发窗口；`unlock.lua` 用于安全释放分布式锁。
## 每日签到与连续签到

用户每天可以在个人中心签到，服务端利用 Redis bitmap 记录月份内的签到位，并通过位运算统计连续签到天数。该场景适合练习空间效率与时间边界处理。
## 探店笔记、点赞与关注

用户可以发布探店笔记、点赞内容并关注其他用户。发布和互动链路分别落在 `BlogController`、`BlogServiceImpl` 与 `FollowServiceImpl`，可用于理解登录态、分页结果和关系数据的组合。
## 点赞排行与有序集合

笔记点赞时间可以写入 Redis 有序集合，用于快速获取热门内容；数据库负责保存最终业务事实，Redis 负责承接高频排序读取。阅读时重点比较两者的职责边界。
## 缓存雪崩与失效治理

缓存过期时间应避免集中在同一时刻，可以加入随机抖动；热点数据还可以使用逻辑过期、互斥锁或限流保护数据库。这里记录的是学习方向，实际生产参数需要结合压测结果确定。
## 异常处理与接口边界

控制器只负责接收请求和返回结果，业务异常统一交给 `WebExceptionAdvice` 处理，并通过 `Result` 返回稳定的接口结构。这样可以减少重复的 try/catch，便于前端和监控系统识别错误。

