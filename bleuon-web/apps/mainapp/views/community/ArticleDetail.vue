<script setup lang="ts">
/**
 * @description 帖子详情
 * @author zheng
 * @since 2023/10/15
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { DateUtil } from "@common/utils";
import { DiscussionAPI, FileAPI } from "@mainapp/apis";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";
import ClassicCkEditor from "@mainapp/components/ClassicCkEditor.vue";

const route = useRoute();
const mainData = ref<PostModel>();

async function fetchData(params: any) {
  const id = route.params.id.toString();
  mainData.value = await DiscussionAPI.findDetailByCriteria({ ...params, ...{ postId: id } });
}

const commentContent = ref("");

function uploadCommentImage(formData: FormData) {
  formData.append("path", "/post");
  return FileAPI.uploadImageFile(formData);
}

await fetchData({});
</script>

<template>
  <div class="article-detail h-100vh flow-hidden bg-bg-page">
    <CommonHeader active-name="discussion"></CommonHeader>
    <div class="content py-5 slim-slider flow-auto f-s-c">
      <div class="wrapper w-60vw f-s-b">
        <div class="w-70%">
          <div class="post-detail rd-2 p-10 bg-bg-overlay">
            <div class="title mb-4 text-1.4rem font-bold">{{ mainData.title }}</div>
            <div class="mb-4 text-text-regular">
              来自于:
              <el-tag
                size="small"
                v-for="titleTagItem in JSON.parse(mainData.titleTag)"
                class="mr-2">
                {{ titleTagItem }}
              </el-tag>
            </div>
            <div class="post-info mb-4 f-c-e text-text-secondary">
              <div class="f-c-s mr-10">
                <div class="i-tabler-eye mr-1"></div>
                <div>{{ mainData.views }}</div>
              </div>
              <div class="f-c-s mr-10">
                <div class="i-tabler-thumb-up mr-1"></div>
                <div>{{ mainData.digg }}</div>
              </div>
              <div class="f-c-s">
                <div class="i-tabler-thumb-down mr-1"></div>
                <div>{{ mainData.bury }}</div>
              </div>
            </div>
            <div class="create-date text-center text-text-secondary mb-10">
              发表于:{{ DateUtil.formatted(mainData.createDate) }}
            </div>
            <div class="text-1.1rem" v-html="mainData.content"></div>
            <div class="mt-10">
              <el-tag
                size="small"
                type="info"
                v-for="descTagItem in JSON.parse(mainData.descTag)"
                class="mr-2">
                {{ descTagItem }}
              </el-tag>
            </div>
            <div class="mt-4 text-end text-text-secondary">
              修改于:{{ DateUtil.formatted(mainData.modifyDate) }}
            </div>
            <div class="mt-10 f-c-c">
              <div class="hover f-c-s mr-10" @click="">
                <div class="i-tabler-thumb-up mr-1"></div>
                <div>{{ mainData.digg }}</div>
              </div>
              <div class="hover f-c-s" @click="">
                <div class="i-tabler-thumb-down mr-1"></div>
                <div>{{ mainData.bury }}</div>
              </div>
            </div>
          </div>
          <div class="mt-5 rd-2 bg-bg-overlay p-5">
            <div class="text-text-secondary mb-4 text-0.9rem">看帖是喜欢，评论才是真爱：</div>
            <ClassicCkEditor v-model="commentContent" :imgae-uploader="uploadCommentImage" />
            <div class="f-c-e mt-5">
              <el-button type="primary">评论</el-button>
            </div>
          </div>
          <div class="comment-list mt-5 bg-bg-overlay rd-2">
            <div
              v-if="mainData.comments.list?.length"
              v-for="(item, index) in mainData.comments.list"
              class="p-5"
              :class="{
                'b-b-1 b-b-solid b-border-dark': mainData.comments.list.length - 1 !== index
              }">
              <div class="consumer f-c-b">
                <router-link :to="'/u/profile/' + item.consumer.id">
                  <div class="f-c-s">
                    <img class="w-10 h-10 rd-50% mr-2 cursor-pointer" :src="item.consumer.avatar" />
                    <div class="text-text-regular hover">{{ item.consumer.username }}</div>
                  </div>
                </router-link>
                <div class="text-text-secondary text-0.8rem">
                  {{ DateUtil.formatted(item.createDate) }}
                </div>
              </div>
              <div>
                <div class="mt-5 ml-12" v-html="item.content"></div>
                <div class="mt-5 f-c-e text-text-regular">
                  <div class="hover f-c-s mr-10" @click="">
                    <div class="i-tabler-thumb-up mr-1"></div>
                    <div>{{ item.digg }}</div>
                  </div>
                  <div class="hover f-c-s" @click="">
                    <div class="i-tabler-thumb-down mr-1"></div>
                    <div>{{ item.bury }}</div>
                  </div>
                </div>
              </div>
            </div>
            <el-result v-else icon="info" title="无内容">
              <template #sub-title>
                <p>还没有评论哦~发一条吧！</p>
              </template>
            </el-result>
          </div>
        </div>
        <div class="side-tools w-28%">
          <div class="consumer rd-2 bg-bg-overlay p-5">
            <div class="f-c-s">
              <router-link :to="'/u/profile/' + mainData.consumer.id">
                <img class="cursor-pointer w-20 h-20 rd-50%" :src="mainData.consumer.avatar" />
              </router-link>
              <div class="ml-4">
                <router-link :to="'/u/profile/' + mainData.consumer.id">
                  <div class="text-text-regular hover">{{ mainData.consumer.username }}</div>
                  <div class="text-text-secondary text-0.8rem mt-2">
                    {{ mainData.consumer.signature }}
                  </div>
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.article-detail {
  background-image: url(https://www.miyoushe.com/_nuxt/img/background.cd0a312.png);
  background-position: 0 5rem;
  background-repeat: no-repeat;
  background-size: 100%;
}

.content {
  height: calc(100vh - 5rem);
}
</style>
