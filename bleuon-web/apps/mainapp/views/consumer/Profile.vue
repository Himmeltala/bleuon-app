<script lang="ts" setup>
/**
 * @description 个人空间
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { ConsumerApi } from "@mainapp/apis";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";
import MyDynamic from "@mainapp/components/consumer/MyDynamic.vue";
import MyPublicFlowchart from "@mainapp/components/consumer/MyPublicFlowchart.vue";
import MyShareFlowchart from "@mainapp/components/consumer/MyShareFlowchart.vue";

const route = useRoute();
const formData = ref(await ConsumerApi.findById(`${route.params.id}`));

type TabIndexType = "MyDynamic" | "MyPublicFlowchart" | "MyShareFlowchart";
const tabIndex = ref<TabIndexType>("MyDynamic");
const tabs = {
  MyDynamic,
  MyPublicFlowchart,
  MyShareFlowchart
};

function changeTabIndex(name: TabIndexType) {
  tabIndex.value = name;
}
</script>

<template>
  <div class="profile slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader active-name="personal"></CommonHeader>
    <div class="f-s-b py-20 px-20 mx-50 mt-2 rd-2 bg-bg-overlay">
      <div class="f-c-c">
        <router-link to="/u/setting">
          <img :src="formData.avatar" class="cursor-pointer rd-50% h-30 w-30" />
        </router-link>
        <div class="ml-10">
          <div class="username f-c-s mb-5">
            <div class="font-bold text-1.5rem mr-10">{{ formData.username }}</div>
            <router-link to="/u/setting">
              <el-button>编辑资料</el-button>
            </router-link>
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
      <div class="bg-bg-overlay rd-2 p-5 mb-2 f-c-s">
        <div
          :class="tabIndex === 'MyDynamic' ? 'active' : 'noactive'"
          class="slider mr-5"
          @click="changeTabIndex('MyDynamic')">
          动态列表
        </div>
        <div
          :class="tabIndex === 'MyPublicFlowchart' ? 'active' : 'noactive'"
          class="slider mr-5"
          @click="changeTabIndex('MyPublicFlowchart')">
          公开的流程图
        </div>
        <div
          :class="tabIndex === 'MyShareFlowchart' ? 'active' : 'noactive'"
          class="slider"
          @click="changeTabIndex('MyShareFlowchart')">
          分享的流程图
        </div>
      </div>
      <component :is="tabs[tabIndex]" :consumer="formData"></component>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.active {
  --uno: b-b-theme-primary text-theme-primary;
}

.noactive {
  --uno: b-b-transparent text-text-secondary;
}

.slider {
  --uno: b-b-solid b-b-2 font-bold pb-2 hover;
}
</style>
