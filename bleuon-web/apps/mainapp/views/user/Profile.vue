<script setup lang="ts">
/**
 * @description 个人资料中心
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import type { TabsPaneContext } from "element-plus";

import CommonHeader from "@mainapp/components/CommonHeader.vue";

const activeName = ref<"follows" | "fans">("follows");

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event);
};
</script>

<template>
  <div class="profile">
    <CommonHeader active-name="personal"></CommonHeader>
    <div class="user-data">
      <div class="f-s-b pt-30 pb-20 px-50">
        <div class="f-c-c">
          <img
            src="https://img2.baidu.com/it/u=1397727792,1861968739&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
            class="rd-50% h-30 w-30" />
          <div class="ml-10">
            <div class="username f-c-c mb-5">
              <div class="font-bold text-1.5rem mr-10">Himmelbleu</div>
              <el-button @click="$router.push('/u/setting')">编辑资料</el-button>
            </div>
            <div class="usertags mb-5">
              <el-tag type="warning" class="mr-4">本科</el-tag>
              <el-tag>计算机科学与技术</el-tag>
            </div>
            <div class="signature text-text-secondary">Time tick away, dream faded away!</div>
          </div>
        </div>
        <div class="f-c-b">
          <div class="text-center mr-10">
            <div class="font-bold text-1.5rem mb-2">0</div>
            <div>预览量</div>
          </div>
          <div class="text-center mr-10">
            <div class="font-bold text-1.5rem mb-2">0</div>
            <div>点赞量</div>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-5 px-50">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="我的关注" name="follows">
          <template #label>
            <span :class="{ 'font-bold': activeName === 'follows' }">我的关注</span>
          </template>
        </el-tab-pane>
        <el-tab-pane label="我的粉丝" name="fans">
          <template #label>
            <span :class="{ 'font-bold': activeName === 'fans' }">我的粉丝</span>
          </template>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style scoped lang="scss">
.user-data {
  background-image: url("/user-profile-bg.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-position: 50%;
}
</style>
