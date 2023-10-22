# 项目结构

父工程子项目，apps 下有前台项目和后台项目。common 为所有 apps 共用的内容。

```
bleuon-web
├─ apps
│   ├─ mainapp                  // 前台项目
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
│   ├─ apis                     // 接口层
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

JointJS 是一个开源 JavaScript 库，用于创建交互式可视化图表和图形用户界面。它主要用于构建流程图、组织图、UML 图、网络拓扑图和其他类型的可视化图表。

## UnoCSS

原子化 CSS 框架，提升 CSS 复用率，减少 CSS 打包体积。

## TypeScript

TypeScript 是 JavaScript 的严格语法超集，类型的支持减少出错，使得 JS 更加严谨。

## KonvaJS

KonvaJS 是一个开源的 JavaScript 库，用于在 HTML5 Canvas 上创建交互式的可视化图形和图形用户界面。Konva 使得在 Web 应用程序中绘制、操作和交互可视化元素变得非常容易。
