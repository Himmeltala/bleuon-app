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
│   │   ├─ mappers              // MyBatis 映射文件
│   │   └─ ...
└─ pom.xml
```

1. controllers：存放控制器类，负责处理 HTTP 请求和返回响应。
2. service：存放服务层的类，处理业务逻辑，可能与多个控制器共享。
3. models：存放实体类、数据模型和数据传输对象（DTO）。
4. configs：存放配置类，例如数据库配置、安全配置、Swagger 配置等。
5. application.yml：应用的配置文件，包含应用的属性设置。
6. mappers：存放 MyBatis 的 Mapper 接口，用于定义 SQL 映射操作。

# 调用逻辑

XxxController -> XxxService extends IService

XxxServiceImpl implements XxxService extends ServiceImpl

XxxMapper implements BaseMapper