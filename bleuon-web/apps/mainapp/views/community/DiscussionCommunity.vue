<script lang="ts" setup>
/**
 * @description 讨论社区
 * @author zheng
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { DiscussionAPI } from "@mainapp/apis";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";

const mainDataSource = ref<PostModel[]>([]);

async function fetchData() {
  mainDataSource.value = await DiscussionAPI.findAllByCriteriaNotComments({});
}

await fetchData();
</script>

<template>
  <div class="discussion-community slim-slider min-h-100vh bg-bg-page">
    <CommonHeader active-name="discussion"></CommonHeader>
    <div class="content f-c-c">
      <div class="wrapper w-60vw mt-5 f-s-b">
        <div class="posts w-70%">
          <el-carousel :interval="4000" type="card" height="12rem">
            <el-carousel-item class="rd-2">
              <img
                class="object-cover w-100% h-100%"
                src="https://bbs-static.miyoushe.com/static/2023/10/08/ddebfddc36c1d149c6efb98ac7d4ac1d_7310668953522602706.jpg" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img
                class="object-cover w-100% h-100%"
                src="https://bbs-static.miyoushe.com/static/2023/10/07/aef96812567533bd45aadfb27127f451_4204914147863808525.jpg" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img
                class="object-cover w-100% h-100%"
                src="https://bbs-static.miyoushe.com/static/2023/10/11/4f96f9a0afc8eb445baee629c4a30be2_2084764690953835457.png" />
            </el-carousel-item>
          </el-carousel>
          <div>
            <div v-for="item in mainDataSource">
              <div class="poster">
                <div class="f-c-s">
                  <img :src="item.consumer.avatar" class="w-10 h-10 rd-50% mr-4" />
                  <div class="text-text-regular">{{ item.consumer.username }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="tools w-25%"></div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
