<script lang="ts" setup>
/**
 * @description 讨论社区
 * @author zheng
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { DateUtil } from "@common/utils";
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
          <div class="mt-4">
            <div
              class="post-item cursor-pointer rd-2 bg-bg-overlay p-5 mb-2"
              v-for="item in mainDataSource">
              <div class="poster f-c-s">
                <div class="f-c-s mr-4">
                  <img :src="item.consumer.avatar" class="w-10 h-10 rd-50% mr-4" />
                  <div class="text-text-regular">{{ item.consumer.username }}</div>
                </div>
                <div class="f-c-s text-text-secondary text-0.8rem">
                  <div>{{ DateUtil.formatted(item.createDate, "MM-dd HH:mm") }}</div>
                </div>
              </div>
              <div class="post-body">
                <router-link :to="'/community/article/' + item.id">
                  <div class="title mt-5 f-c-s flex-wrap">
                    <el-tag
                      size="small"
                      v-for="titleTagItem in JSON.parse(item.titleTag)"
                      class="mr-2">
                      {{ titleTagItem }}
                    </el-tag>
                    <div class="font-bold text-1.1rem">{{ item.title }}</div>
                  </div>
                  <div class="desc mt-4 text-text-secondary text-0.9rem">{{ item.desc }}</div>
                  <div class="desc-imgs mt-4 f-c-s">
                    <img
                      class="rd-2 w-20% h-35 mr-2 object-cover"
                      v-for="descImgItem in JSON.parse(item.descImgs)"
                      :src="descImgItem" />
                  </div>
                </router-link>
              </div>
              <div class="mt-4">
                <el-tag
                  size="small"
                  type="info"
                  v-for="descTagItem in JSON.parse(item.descTag)"
                  class="mr-2">
                  {{ descTagItem }}
                </el-tag>
              </div>
              <div class="options f-c-e text-text-secondary">
                <div class="f-c-s mr-10">
                  <div class="i-tabler-eye mr-1"></div>
                  <div>{{ item.views }}</div>
                </div>
                <div class="f-c-s mr-10">
                  <div class="i-tabler-thumb-up mr-1"></div>
                  <div>{{ item.digg }}</div>
                </div>
                <div class="f-c-s">
                  <div class="i-tabler-thumb-down mr-1"></div>
                  <div>{{ item.bury }}</div>
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

<style lang="scss" scoped>
.post-body:hover {
  .title {
    --uno: text-theme-primary;
  }
}
</style>
