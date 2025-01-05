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
