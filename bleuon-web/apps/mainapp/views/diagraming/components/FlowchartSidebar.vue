<script setup lang="ts">
/**
 * @description Flowchart 侧边栏工具
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
    class="bleuon__flowchart-sidebar slim-slider h-100vh flow-auto bg-#f6f7f8 border-#dfe2e5 border-r-1 border-r-solid relative px-4 py-4">
    <div class="cells-for-flowchart">
      <div class="mb-4 font-bold">Flowchart 图形</div>
      <div class="f-c-s flex-wrap flex-gap-5">
        <el-tooltip content="流程">
          <img @click="CreatorService.addFlow(graph)" src="/icons/flowchat/flow.svg" class="item" />
        </el-tooltip>
        <el-tooltip content="判定">
          <img
            @click="CreatorService.addDecision(graph)"
            src="/icons/flowchat/decision.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="开始/结束">
          <img
            @click="CreatorService.addStartOrEnd(graph)"
            src="/icons/flowchat/start-or-end.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="文档">
          <img
            @click="CreatorService.addDocument(graph)"
            src="/icons/flowchat/document.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="数据">
          <img @click="CreatorService.addData(graph)" src="/icons/flowchat/data.svg" class="item" />
        </el-tooltip>
        <el-tooltip content="子流程">
          <img
            @click="CreatorService.addChildFlow(graph)"
            src="/icons/flowchat/child-flow.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="外部数据">
          <img
            @click="CreatorService.addExtraData(graph)"
            src="/icons/flowchat/extra-data.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="内部存储">
          <img
            @click="CreatorService.addInnerStore(graph)"
            src="/icons/flowchat/inner-store.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="队列数据">
          <img
            @click="CreatorService.addQueueData(graph)"
            src="/icons/flowchat/queue-data.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="横向数据库">
          <img
            @click="CreatorService.addDatabase1(graph)"
            src="/icons/flowchat/database-1.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="竖向数据库">
          <img
            @click="CreatorService.addDatabase2(graph)"
            src="/icons/flowchat/database-2.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="人工输入">
          <img
            @click="CreatorService.addManualInput(graph)"
            src="/icons/flowchat/manual-input.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="卡片">
          <img @click="CreatorService.addCard(graph)" src="/icons/flowchat/card.svg" class="item" />
        </el-tooltip>
        <el-tooltip content="条带">
          <img
            @click="CreatorService.addCassette(graph)"
            src="/icons/flowchat/cassette.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="展示">
          <img
            @click="CreatorService.addDisplay(graph)"
            src="/icons/flowchat/display.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="人工操作">
          <img
            @click="CreatorService.addManualOperation(graph)"
            src="/icons/flowchat/manual-operation.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="预备">
          <img
            @click="CreatorService.addPrepare(graph)"
            src="/icons/flowchat/prepare.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="并行模式">
          <img
            @click="CreatorService.addParallelMode(graph)"
            src="/icons/flowchat/parallel-mode.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="循环限值">
          <img
            @click="CreatorService.addCycleLimit(graph)"
            src="/icons/flowchat/cycle-limit.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="或者">
          <img @click="CreatorService.addOr(graph)" src="/icons/flowchat/or.svg" class="item" />
        </el-tooltip>
        <el-tooltip content="求和">
          <img
            @click="CreatorService.addCalcSum(graph)"
            src="/icons/flowchat/calc-sum.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="跨页引用">
          <img
            @click="CreatorService.addOffPageReference(graph)"
            src="/icons/flowchat/off-page-reference.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="页面内引用">
          <img
            @click="CreatorService.addOnPageReference(graph)"
            src="/icons/flowchat/on-page-reference.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="排序">
          <img
            @click="CreatorService.addSorting(graph)"
            src="/icons/flowchat/sorting.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="合并">
          <img
            @click="CreatorService.addMerge(graph)"
            src="/icons/flowchat/merge.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="备注1">
          <img
            @click="CreatorService.addRemark1(graph)"
            src="/icons/flowchat/remark-1.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="备注2">
          <img
            @click="CreatorService.addRemark2(graph)"
            src="/icons/flowchat/remark-2.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="注释1">
          <img
            @click="CreatorService.addQuote1(graph)"
            src="/icons/flowchat/quote-1.svg"
            class="item" />
        </el-tooltip>
        <el-tooltip content="注释2">
          <img
            @click="CreatorService.addQuote2(graph)"
            src="/icons/flowchat/quote-2.svg"
            class="item" />
        </el-tooltip>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.item {
  flex: 0 1 15% !important;
  --uno: w-10 h-10 object-fill cursor-pointer;
}
</style>
