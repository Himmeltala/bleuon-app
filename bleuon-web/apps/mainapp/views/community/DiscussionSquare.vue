<script lang="ts" setup>
/**
 * @description 讨论社区
 * @author zheng
 * @since 2023/10/5
 */

import { DateUtil } from "@common/utils";
import { Requests } from "@common/requests";
import { ElSelectData } from "@common/data";
import CommonHeader from "@mainapp/fragments/CommonHeader.vue";

const mainData = ref<PageInfo<ArticleModel>>();

async function fetchData(pamras: any) {
  mainData.value = await Requests.Discussion.findAllByCriteria(pamras);
}

const searchVal = ref("");
const type = ref("");
const rankingType = ref("");
const dateSequence = ref("降序");
const currPage = ref(1);
const pageSize = ref(5);

async function onTypeChange(value: any) {
  await fetchData({ type: value });
}

async function onRankingTypeChange(value: any) {
  await fetchData({ rankingType: value });
}

async function onDateSequenceChange(value: any) {
  await fetchData({ sequences: [{ isAsc: value === "升序" ? true : false, col: "create_date" }] });
}

async function search() {
  await fetchData({
    title: searchVal.value,
    sequences: [{ isAsc: dateSequence.value === "升序" ? true : false, col: "create_date" }]
  });
}

async function handleSizeChange() {
  await fetchData({
    type: type.value,
    rankingType: rankingType.value,
    title: searchVal.value,
    currPage: currPage.value,
    pageSize: pageSize.value,
    sequences: [{ isAsc: dateSequence.value === "升序" ? true : false, col: "create_date" }]
  });
}

async function handleCurrentChange() {
  await fetchData({
    type: type.value,
    rankingType: rankingType.value,
    title: searchVal.value,
    currPage: currPage.value,
    pageSize: pageSize.value,
    sequences: [{ isAsc: dateSequence.value === "升序" ? true : false, col: "create_date" }]
  });
}

await fetchData({ sequences: [{ isAsc: false, col: "create_date" }] });
</script>

<template>
  <div class="discussion-community h-100vh flow-hidden bg-bg-page">
    <CommonHeader active-name="discussion"></CommonHeader>
    <div class="content py-5 slim-slider flow-auto f-s-c">
      <div class="wrapper w-60vw f-s-b">
        <div class="posts w-70%">
          <el-carousel :interval="4000" type="card" height="12rem">
            <el-carousel-item class="rd-2">
              <img
                class="object-cover w-100% h-100%"
                src="https://pocdn.processon.com/admin/file_img/64fabc62a8b074573703961d.png" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img
                class="object-cover w-100% h-100%"
                src="https://pocdn.processon.com/admin/file_img/64fabc75a8b0745737039629.png" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img
                class="object-cover w-100% h-100%"
                src="https://pocdn.processon.com/admin/file_img/64fabb61a8b07457370395cc.png" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img
                class="object-cover w-100% h-100%"
                src="https://pocdn.processon.com/admin/file_img/64fabbdea8b07457370395f3.png" />
            </el-carousel-item>
          </el-carousel>
          <div class="mt-4">
            <div
              class="post-item cursor-pointer rd-2 bg-bg-overlay p-5 mt-2"
              v-for="item in mainData.list">
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
                      class="mr-2"
                      v-if="item.titleTag"
                      v-for="titleTagItem in JSON.parse(item.titleTag)">
                      {{ titleTagItem }}
                    </el-tag>
                    <div class="font-bold text-1.1rem">{{ item.title }}</div>
                  </div>
                  <div class="desc mt-4 text-text-secondary text-0.9rem">{{ item.desc }}</div>
                  <div class="desc-imgs mt-4 f-c-s">
                    <img
                      class="rd-2 w-20% h-35 mr-2 object-cover"
                      v-if="item.descImgs"
                      v-for="descImgItem in JSON.parse(item.descImgs)"
                      :src="descImgItem" />
                  </div>
                </router-link>
              </div>
              <div class="mt-4">
                <el-tag
                  size="small"
                  type="info"
                  class="mr-2"
                  v-if="item.descTag"
                  v-for="descTagItem in JSON.parse(item.descTag)">
                  {{ descTagItem }}
                </el-tag>
              </div>
              <div class="post-info f-c-e text-text-secondary">
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
          <div class="pager mt-2 f-c-e bg-bg-overlay p-2 rd-2">
            <el-pagination
              background
              v-model:current-page="currPage"
              v-model:page-size="pageSize"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              layout="sizes, prev, pager, next, jumper"
              :page-sizes="[5, 10, 15, 20]"
              :total="mainData.total" />
          </div>
        </div>
        <div class="tools w-28%">
          <div class="bg-bg-overlay rd-2 py-5 px-10">
            <div>
              <el-button
                type="primary"
                class="w-100%"
                @click="$router.push('/community/article-editor')">
                发表帖子
                <template #icon>
                  <div class="i-tabler-edit"></div>
                </template>
              </el-button>
            </div>
          </div>
          <div class="bg-bg-overlay mt-4 rd-2 p-5">
            <div class="font-bold text-0.9rem mb-5 f-c-s">
              <div class="i-tabler-category-2 mr-2"></div>
              筛选查询
            </div>
            <div>
              <el-input @keyup.enter="search" v-model="searchVal" placeholder="输入关键字查询">
                <template #suffix>
                  <div class="i-tabler-search"></div>
                </template>
              </el-input>
            </div>
            <div class="mt-4 font-bold text-0.8rem text-text-secondary">
              注：以下可附加更多查询条件
            </div>
            <div class="date-sequence mt-4">
              <div class="mb-2 f-c-s">
                <div class="i-tabler-clock mr-2"></div>
                排序
              </div>
              <el-radio-group v-model="dateSequence" @change="onDateSequenceChange">
                <el-radio label="降序" value="降序">降序</el-radio>
                <el-radio label="升序" value="升序">升序</el-radio>
              </el-radio-group>
            </div>
            <div class="post-type mt-4">
              <div class="mb-2 f-c-s">
                <div class="i-tabler-bookmark mr-2"></div>
                类型
              </div>
              <el-radio-group v-model="type" @change="onTypeChange">
                <el-radio label="">全部</el-radio>
                <el-radio v-for="item in ElSelectData.articleTypeList" :label="item.label">
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </div>
            <div class="post-ranking-type mt-4">
              <div class="mb-2 f-c-s">
                <div class="i-tabler-arrows-transfer-up mr-2"></div>
                其他
              </div>
              <el-radio-group v-model="rankingType" @change="onRankingTypeChange">
                <el-radio label="">全部</el-radio>
                <el-radio v-for="item in ElSelectData.postRankingTypeList" :label="item.label">
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.discussion-community {
  background-image: url(https://www.miyoushe.com/_nuxt/img/background.cd0a312.png);
  background-position: 0 5rem;
  background-repeat: no-repeat;
  background-size: 100%;
}

.content {
  height: calc(100vh - 5rem);
}

.post-body:hover {
  .title {
    --uno: text-theme-primary;
  }
}
</style>
