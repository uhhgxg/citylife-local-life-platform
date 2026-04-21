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
