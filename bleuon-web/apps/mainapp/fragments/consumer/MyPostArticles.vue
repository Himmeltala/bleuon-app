<script setup lang="ts">
/**
 * @description 我发表的帖子
 * @author zheng
 * @since 2023/10/20
 */

import { DateUtil } from "@common/utils";
import { Requests } from "@common/requests";

const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
const id = useRoute().params.id.toString();
const mainData = ref<PageInfo<ArticleModel>>();

async function fetchData() {
  mainData.value = await Requests.Discussion.findAllByCriteria({
    consumerId: id,
    sequences: [{ isAsc: false, col: "create_date" }]
  });
}

await fetchData();
</script>

<template>
  <div>
    <div class="post-item cursor-pointer rd-2 bg-bg-overlay p-5 mb-4" v-for="item in mainData.list">
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
            <el-tag size="small" v-for="titleTagItem in JSON.parse(item.titleTag)" class="mr-2">
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
      <div class="post-info f-c-e text-text-secondary">
        <div class="mr-10" v-if="token.id == item.consumerId">
          <el-button
            type="primary"
            plain
            size="small"
            @click="$router.push('/community/article-editor?id=' + item.id + '&type=edit')">
            编辑
          </el-button>
        </div>
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
</template>

<style scoped lang="scss">
.post-body:hover {
  .title {
    --uno: text-theme-primary;
  }
}
</style>
