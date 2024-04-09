<script lang="ts" setup>
/**
 * @description 个人空间
 * @author zheng
 * @since 2023/8/23
 */

import { Requests } from "@common/requests";
import CommonHeader from "@mainapp/fragments/CommonHeader.vue";
import MyDynamic from "@mainapp/fragments/consumer/MyDynamic.vue";
import MyPublicFlowchart from "@mainapp/fragments/consumer/MyPublicFlowchart.vue";
import MyShareFlowchart from "@mainapp/fragments/consumer/MyShareFlowchart.vue";
import MyStarConsumer from "@mainapp/fragments/consumer/MyStarConsumer.vue";
import MyPostArticles from "@mainapp/fragments/consumer/MyPostArticles.vue";

const route = useRoute();
const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
const formData = ref<ConsumerModel>();

const tabs = [MyDynamic, MyPublicFlowchart, MyShareFlowchart, MyPostArticles, MyStarConsumer];
const tabIndex = ref(0);

async function fetchData(id: string | string[]) {
  formData.value = await Requests.Consumer.findBy({ id: id.toString() });
}

function collectConsumer() {
  Requests.Consumer.addCollecting({ collectorId: token.id, consumerId: formData.value.id });
}

onBeforeRouteUpdate(async updateGuard => {
  await fetchData(updateGuard.params.id);
});

await fetchData(route.params.id);
</script>

<template>
  <div class="profile slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader active-name="personal"></CommonHeader>
    <div class="f-s-b py-20 px-20 mx-50 mt-5 rd-2 bg-bg-overlay">
      <div class="f-c-c">
        <router-link to="/u/setting">
          <img :src="formData.avatar" class="cursor-pointer rd-50% h-30 w-30" />
        </router-link>
        <div class="ml-10">
          <div class="username f-c-s mb-5">
            <div class="font-bold text-1.5rem mr-10">{{ formData.username }}</div>
            <template v-if="token.id === formData.id">
              <router-link to="/u/setting">
                <el-button>编辑资料</el-button>
              </router-link>
            </template>
            <template v-else>
              <el-button type="primary" plain @click="collectConsumer">关注用户</el-button>
            </template>
          </div>
          <div class="usertags mb-5">
            <el-tag v-if="formData.position" class="mr-4" type="warning">
              {{ formData.position }}
            </el-tag>
            <el-tag v-if="formData.company">{{ formData.company }}</el-tag>
          </div>
          <div class="signature text-text-secondary">
            {{ formData.signature || "这个人很懒，什么也没有留下" }}
          </div>
        </div>
      </div>
      <div class="f-c-b">
        <div class="text-center mr-10">
          <div class="font-bold text-1.5rem mb-2">0</div>
          <div>浏览量</div>
        </div>
        <div class="text-center mr-10">
          <div class="font-bold text-1.5rem mb-2">0</div>
          <div>点赞量</div>
        </div>
      </div>
    </div>
    <div class="my-2 px-50">
      <TabPage :tabs="tabs" :tab-index="tabIndex">
        <template #item>
          <div class="f-c-s bg-bg-overlay p-5 mb-2">
            <TabPageItem class="mr-5" :index="0" v-model="tabIndex">动态列表</TabPageItem>
            <TabPageItem class="mr-5" :index="1" v-model="tabIndex">公开的流程图</TabPageItem>
            <TabPageItem class="mr-5" :index="2" v-model="tabIndex">分享的流程图</TabPageItem>
            <TabPageItem class="mr-5" :index="3" v-model="tabIndex">发表的帖子</TabPageItem>
            <TabPageItem :index="4" v-if="token.id === formData.id" v-model="tabIndex">
              关注的用户
            </TabPageItem>
          </div>
        </template>
        <component :is="tabs[tabIndex]" :consumer="formData"></component>
      </TabPage>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
