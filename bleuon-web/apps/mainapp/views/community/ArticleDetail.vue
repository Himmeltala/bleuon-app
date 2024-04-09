<script setup lang="ts">
/**
 * @description 帖子详情
 * @author zheng
 * @since 2023/10/15
 */

import { DateUtil, TextUtil } from "@common/utils";
import { Requests } from "@common/requests";
import CommonHeader from "@mainapp/fragments/CommonHeader.vue";

const route = useRoute();
const mainData = ref<ArticleModel>();
const commentList = ref<PageInfo<ArticleCommentModel>>();
const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);

async function fetchData(params: any) {
  const id = route.params.id.toString();
  mainData.value = await Requests.Discussion.findDetailByCriteria({
    ...params,
    ...{ articleId: id }
  });
}

async function fetchCommentList(params: any) {
  const id = route.params.id.toString();
  commentList.value = await Requests.Discussion.findCommentsByCriteria({
    ...params,
    ...{ articleId: id }
  });
}

function diggArticle() {
  Requests.Discussion.upgradeArticle(
    {
      model: { id: mainData.value.id },
      config: {
        ignore200: true,
        params: { isDigg: true }
      }
    },
    () => {
      mainData.value.digg += 1;
      ElMessage.success("感谢您的支持！");
    }
  );
}

function buryArticle() {
  Requests.Discussion.upgradeArticle(
    {
      model: { id: mainData.value.id },
      config: {
        ignore200: true,
        params: {
          isBury: true
        }
      }
    },
    () => {
      mainData.value.bury += 1;
      ElMessage.success("感谢您的建议！");
    }
  );
}

const commentContent = ref("");

function uploadCommentImage(formData: FormData) {
  formData.append("filepath", "/article/comments");
  return Requests.File.uploadImageFile(formData);
}

function collectConsumer() {
  Requests.Consumer.addCollecting({
    collectorId: token.id,
    consumerId: mainData.value.consumer.id
  });
}

function addComment() {
  const len = TextUtil.strlen(commentContent.value);
  if (len < 3) {
    ElMessage.error("发表至少 3 个字的评论！");
    return;
  }
  Requests.Discussion.addComment(
    {
      articleId: mainData.value.id,
      consumerId: token.id,
      content: commentContent.value
    },
    async () => {
      await fetchCommentList({
        currPage: currPage.value,
        pageSize: pageSize.value,
        sequences: [{ isAsc: isCommentDateAsc.value, col: "create_date" }]
      });
    }
  );
}

function deleteComment(id: string) {
  Requests.Discussion.deleteComment(
    { id, articleId: mainData.value.id, consumerId: token.id },
    async () => {
      await fetchCommentList({
        currPage: currPage.value,
        pageSize: pageSize.value,
        sequences: [{ isAsc: isCommentDateAsc.value, col: "create_date" }]
      });
    }
  );
}

function diggComment(item: ArticleCommentModel) {
  item.digg += 1;
  Requests.Discussion.upgradeComment({
    id: item.id,
    articleId: mainData.value.id,
    digg: item.digg
  });
}

function buryComment(item: ArticleCommentModel) {
  item.bury += 1;
  Requests.Discussion.upgradeComment({
    id: item.id,
    articleId: mainData.value.id,
    bury: item.bury
  });
}

const isCommentDateAsc = ref(true);

async function changeCommentAsc() {
  isCommentDateAsc.value = !isCommentDateAsc.value;
  await fetchCommentList({ sequences: [{ isAsc: isCommentDateAsc.value, col: "create_date" }] });
}

const currPage = ref(1);
const pageSize = ref(10);

async function handleSizeChange() {
  await fetchCommentList({
    currPage: currPage.value,
    pageSize: pageSize.value,
    sequences: [{ isAsc: isCommentDateAsc.value, col: "create_date" }]
  });
}

async function handleCurrentChange() {
  await fetchCommentList({
    currPage: currPage.value,
    pageSize: pageSize.value,
    sequences: [{ isAsc: isCommentDateAsc.value, col: "create_date" }]
  });
}

onMounted(() => {
  Requests.Discussion.upgradeArticle(
    {
      model: { id: mainData.value.id },
      config: {
        ignore200: true,
        params: {
          isViews: true
        }
      }
    },
    () => {
      mainData.value.views += 1;
    }
  );
});

await fetchData({});
await fetchCommentList({
  currPage: currPage.value,
  pageSize: pageSize.value,
  sequences: [{ isAsc: isCommentDateAsc.value, col: "create_date" }]
});
</script>

<template>
  <div class="article-detail h-100vh flow-hidden bg-bg-page">
    <CommonHeader active-name="discussion"></CommonHeader>
    <div class="content py-5 slim-slider flow-auto f-s-c">
      <div class="wrapper w-60vw f-s-b">
        <div class="w-70%">
          <div class="post-detail rd-2 p-10 bg-bg-overlay">
            <div class="title mb-4 text-1.4rem font-bold">{{ mainData.title }}</div>
            <div class="mb-2 text-text-secondary">
              帖子类型:
              <el-tag size="small" class="ml-2">
                {{ mainData.type }}
              </el-tag>
            </div>
            <div class="text-text-secondary">
              标题 Tag:
              <el-tag
                size="small"
                type="success"
                v-for="titleTagItem in JSON.parse(mainData.titleTag)"
                class="mr-2 ml-2">
                {{ titleTagItem }}
              </el-tag>
            </div>
            <div class="f-c-e mb-4" v-if="token.id === mainData.consumerId">
              <el-button
                @click="$router.push('/community/article-editor?id=' + mainData.id + '&type=edit')"
                type="primary"
                plain
                size="small">
                编辑
              </el-button>
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
            <div class="create-date text-0.9rem text-center text-text-secondary mb-10">
              发表于:{{ DateUtil.formatted(mainData.createDate) }}
            </div>
            <div class="article-detail-content text-1.1rem" v-html="mainData.content"></div>
            <div class="mt-10">
              <el-tag
                size="small"
                type="info"
                v-for="descTagItem in JSON.parse(mainData.descTag)"
                class="mr-2">
                {{ descTagItem }}
              </el-tag>
            </div>
            <div class="mt-10 f-c-e text-text-secondary">
              <div class="hover f-c-s mr-5">
                <el-button type="primary" plain @click="diggArticle">
                  <template #icon>
                    <div class="i-tabler-thumb-up"></div>
                  </template>
                  <div>点赞 {{ mainData.digg }}</div>
                </el-button>
              </div>
              <div class="hover f-c-s">
                <el-button type="danger" plain @click="buryArticle">
                  <template #icon>
                    <div class="i-tabler-thumb-down"></div>
                  </template>
                  <div>反对 {{ mainData.bury }}</div>
                </el-button>
              </div>
            </div>
            <div class="mt-4 text-0.9rem text-end text-text-secondary">
              修改于:{{ DateUtil.formatted(mainData.modifyDate) }}
            </div>
          </div>
          <div class="mt-5 rd-2 bg-bg-overlay p-5">
            <div class="text-text-secondary mb-4 text-0.9rem">看帖是喜欢，评论才是真爱：</div>
            <ClassicCkEditor v-model="commentContent" :imgae-uploader="uploadCommentImage" />
            <div class="f-c-e mt-5">
              <el-button type="primary" @click="addComment">评论</el-button>
            </div>
          </div>
          <div class="comment-list mt-5 bg-bg-overlay rd-2">
            <div class="p-5 f-c-b mb-5">
              <div class="text-text-regular font-bold">评论列表</div>
              <div
                @click="changeCommentAsc"
                class="hover text-0.9rem f-c-c text-text-secondary select-none">
                <div class="i-tabler-arrows-sort mr-1"></div>
                <div v-if="isCommentDateAsc">日期升序</div>
                <div v-else>日期降序</div>
              </div>
            </div>
            <div
              v-if="commentList.list?.length"
              v-for="(item, index) in commentList.list"
              class="px-5 mb-10">
              <div class="pb-5 b-b-1 b-b-solid b-border-primary">
                <div class="consumer f-c-b">
                  <router-link :to="'/u/profile/' + item.consumer.id">
                    <div class="f-c-s">
                      <img
                        class="w-10 h-10 rd-50% mr-2 cursor-pointer"
                        :src="item.consumer.avatar" />
                      <div class="text-text-regular hover">{{ item.consumer.username }}</div>
                    </div>
                  </router-link>
                  <div class="text-text-secondary text-0.8rem">
                    {{ DateUtil.formatted(item.createDate) }}
                  </div>
                </div>
                <div>
                  <div
                    class="mt-5 ml-12 article-detail-comment-content"
                    v-html="item.content"></div>
                  <div class="mt-5 f-c-e text-text-secondary">
                    <div class="hover f-c-s mr-10" @click="diggComment(item)">
                      <div class="i-tabler-thumb-up mr-1"></div>
                      <div>{{ item.digg }}</div>
                    </div>
                    <div class="hover f-c-s mr-10" @click="buryComment(item)">
                      <div class="i-tabler-thumb-down mr-1"></div>
                      <div>{{ item.bury }}</div>
                    </div>
                    <div
                      v-if="item.consumer.id === token.id"
                      class="hover f-c-s"
                      @click="deleteComment(item.id)">
                      <div class="i-tabler-trash mr-1"></div>
                      删除
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <el-result v-else icon="info" title="无内容">
              <template #sub-title>
                <p>还没有评论哦~发一条吧！</p>
              </template>
            </el-result>
            <div v-if="commentList.list?.length" class="pager pb-5 pr-2 mt-5 f-c-e">
              <el-pagination
                background
                v-model:current-page="currPage"
                v-model:page-size="pageSize"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                layout="sizes, prev, pager, next, jumper"
                :page-sizes="[5, 10, 15, 20]"
                :total="commentList.total" />
            </div>
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
            <div class="f-c-e mt-4" v-if="token.id !== mainData.consumer.id">
              <el-button type="primary" @click="collectConsumer">关注用户</el-button>
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

<style lang="scss">
.article-detail-comment-content,
.article-detail-content {
  img {
    --uno: object-cover max-w-100% h-auto rd-2 !important;
  }
}
</style>
