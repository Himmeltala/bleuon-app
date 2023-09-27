<script setup lang="ts">
import { dia } from "@mainapp/lib";
import { convertSvgToImage } from "@mainapp/lib/tools";

const paper = inject<Ref<dia.Paper>>("bleuonPaper");
</script>

<template>
  <div class="f-c-s">
    <el-button @click="$router.push('/home')" type="primary" bg text>
      <template #icon>
        <div class="i-tabler-chevron-left"></div>
      </template>
    </el-button>
    <img
      @click="$router.push('/home')"
      src="/bleuon-icon.png"
      class="w-30 h-15 object-cover cursor-pointer" />
    <div>
      <el-input size="small" placeholder="输入文件名" />
      <div class="f-c-s text-gray-500 mt-4">
        <div
          @click="convertSvgToImage(paper.childNodes.svg, 'jpeg', { height: 1000, width: 1000 })"
          class="hover mr-4 text-1.1rem i-tabler-download"></div>
        <div class="hover text-1.1rem i-tabler-share"></div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
