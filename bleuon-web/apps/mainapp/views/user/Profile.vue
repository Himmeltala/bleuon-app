<script lang="ts" setup>
/**
 * @description 个人资料中心
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";

const formData = useStorage<UserData>(KeyVals.MAINAPP_USER, {});
const activeName = ref<"follows" | "fans">("follows");

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event);
};
</script>

<template>
  <div class="profile slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader active-name="personal"></CommonHeader>
    <div class="user-data bg-bg-primary">
      <div class="f-s-b pt-30 pb-20 px-50">
        <div class="f-c-c">
          <img class="rd-50% h-30 w-30" :src="formData.avatar" />
          <div class="ml-10">
            <div class="username f-c-s mb-5">
              <div class="font-bold text-1.5rem mr-10">{{ formData.username }}</div>
              <router-link to="/u/setting">
                <el-button>编辑资料</el-button>
              </router-link>
            </div>
            <div class="usertags mb-5">
              <el-tag class="mr-4" type="warning">{{ formData.position }}</el-tag>
              <el-tag>{{ formData.company }}</el-tag>
            </div>
            <div class="signature text-text-secondary">
              {{ formData.signature || "这个人很懒，什么也没有留下" }}
            </div>
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
      <div class="bg-bg-overlay px-5 pb-5 rd-2">
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
  </div>
</template>

<style lang="scss" scoped>
.user-data {
  // background-image: url("/user-profile-bg.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-position: 50%;
}
</style>
