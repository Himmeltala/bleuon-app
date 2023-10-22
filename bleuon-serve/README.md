# 项目结构

```
src
├─ main
│   ├─ java
│   │   └─ com
│   │       └─ bleuon
│   │           ├─ controllers    // 控制器层，处理 HTTP 请求
│   │           ├─ services       // 服务层，处理业务逻辑
│   │           ├─ models         // 实体类和数据模型
│   │           ├─ configs        // 配置类，如数据库配置、安全配置等
│   │           ├─ mappers        // Mybatis Mapper 接口
│   │           └─ BleuonServeApplication.java
│   ├─ resources
│   │   ├─ application.yml
│   │   ├─ mappers                // MyBatis 映射文件
│   │   └─ ...
└─ pom.xml
```

1. controllers：存放控制器类，负责处理 HTTP 请求和返回响应。
2. service：存放服务层的类，处理业务逻辑，可能与多个控制器共享。
3. models：存放实体类、数据模型和数据传输对象（DTO）。
4. configs：存放配置类，例如数据库配置、安全配置、Swagger 配置等。
5. application.yml：应用的配置文件，包含应用的属性设置。
6. mappers：存放 MyBatis 的 Mapper 接口，用于定义 SQL 映射操作。

# TODO

1. 在进入登录接口时，应该查询 Redis 是否存储了这个用户的 Token，如果存储了说明重复登录，直接返回之前的 Token 给客户端

# 技术介绍

## SpringSecurity

Spring Security是一个功能强大的身份验证和授权框架，用于保护Java应用程序的安全性。它提供了一系列工具和类，用于处理用户身份验证、授权、会话管理以及其他与安全相关的功能。

## SpringDoc OpenAPI

SpringDoc OpenAPI 是一个用于在Spring Boot应用程序中生成和发布OpenAPI规范（以前称为Swagger规范）的工具。OpenAPI规范是一种用于描述RESTful API的标准，它提供了API的详细文档，包括资源、路径、参数、请求和响应的格式等信息。

## Redis

Redis（Remote Dictionary Server）是一个开源的内存数据库，它提供了高性能的数据存储和检索功能。Redis以键值对的形式存储数据，被广泛用于缓存、会话管理、消息队列、实时统计数据等各种应用场景。

## MySQL

MySQL是一个开源的关系型数据库管理系统（RDBMS），它广泛用于各种Web应用程序和服务器端应用程序中。

## Mybatis+MybatisPlus

MyBatis是一个开源的Java持久层框架，用于将数据库操作与Java应用程序的数据访问层解耦。