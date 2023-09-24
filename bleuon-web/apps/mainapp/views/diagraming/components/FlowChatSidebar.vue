<script setup lang="ts">
/**
 * @description Flowchat 侧边栏工具
 * @author 郑人滏 42020306
 * @since 2023/9/24
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { dia } from "@mainapp/lib";
import { CreatorService } from "../service";

defineProps({
  paper: {
    type: dia.Paper
  },
  graph: {
    type: dia.Graph
  }
});
</script>

<template>
  <div
    class="bleuon__flowchat-sidebar bg-#f6f7f8 border-#dfe2e5 border-r-1 border-r-solid relative px-4 py-4">
    <el-button @click="CreatorService.addFlow(graph)">流程</el-button>
    <el-button @click="CreatorService.addDecision(graph)">判定</el-button>
    <el-button @click="CreatorService.addDecision(graph)">开始/结束</el-button>
    <el-button @click="CreatorService.addDecision(graph)">文档</el-button>
    <el-button @click="CreatorService.addDecision(graph)">数据</el-button>
    <el-button @click="CreatorService.addDecision(graph)">子流程</el-button>
    <el-button @click="CreatorService.addDecision(graph)">外部数据</el-button>
    <el-button @click="CreatorService.addDecision(graph)">内部数据</el-button>
    <el-button @click="CreatorService.addDecision(graph)">队列数据</el-button>
    <el-button @click="CreatorService.addDecision(graph)">数据库1</el-button>
    <el-button @click="CreatorService.addDecision(graph)">数据库2</el-button>
    <el-button @click="CreatorService.addDecision(graph)">数据库2</el-button>
    <el-button @click="CreatorService.addDecision(graph)">人工输入</el-button>
    <el-button @click="CreatorService.addDecision(graph)">卡片</el-button>
    <el-button @click="CreatorService.addDecision(graph)">条带</el-button>
    <el-button @click="CreatorService.addDecision(graph)">展示</el-button>
    <el-button @click="CreatorService.addDecision(graph)">人工操作</el-button>
    <el-button @click="CreatorService.addDecision(graph)">预备</el-button>
    <el-button @click="CreatorService.addDecision(graph)">并行模式</el-button>
    <el-button @click="CreatorService.addDecision(graph)">循环限值</el-button>
    <el-button @click="CreatorService.addDecision(graph)">或者</el-button>
    <el-button @click="CreatorService.addDecision(graph)">求和</el-button>
    <el-button @click="CreatorService.addDecision(graph)">跨页引用</el-button>
    <el-button @click="CreatorService.addDecision(graph)">排序</el-button>
    <el-button @click="CreatorService.addDecision(graph)">合并</el-button>
    <el-button @click="CreatorService.addOnPageReference(graph)">页面内引用</el-button>
  </div>
</template>

<style scoped lang="scss"></style>
