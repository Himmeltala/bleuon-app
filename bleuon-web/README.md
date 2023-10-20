# 项目结构

父工程子项目，apps 下有前台项目和后台项目。common 为所有 apps 共用的内容。

```
bleuon-web
├─ apps
│   ├─ mainapp                  // 前台项目
│   │   ├─ apis                 // 接口层
│   │   ├─ components           // 前台项目的组件
│   │   ├─ fragments            // views 下的子视图，区别于组件高可复用
|   |   ├─ data                 // 数据层
|   |   ├─ lib                  // lib jointjs 相关 api
|   |   ├─ scss                 // 通用样式
|   |   ├─ service              // 业务层
|   |   ├─ typings              // ts 类型
|   |   ├─ utils
|   |   └─ views                // 视图层
│   └─ admin                    // 后台项目
├─ common                       // 存放全局内容，可以提供给 apps 下所有模块使用
│   ├─ components               // 全局组件
│   ├─ constants                // 全局常量
│   ├─ data                     // 全局数据
│   ├─ models                   // 全局模型
│   └─ utils                    // 全局工具
├─ uno.config.ts                // uncoss 配置文件
├─ vite.config.ts               // vite 配置文件
└─ index.html                   // mainapp 页面入口
```

# 技术介绍

## JointJS

## KonvaJS

## Vue3

## UnoCSS

## TypeScript
