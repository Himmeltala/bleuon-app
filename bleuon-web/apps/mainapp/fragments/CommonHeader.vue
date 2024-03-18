<script lang="ts" setup>
/**
 * @description consumer 页面下的头部组件
 * @author zheng
 * @since 2023/8/23
 */

import MenuAvatar from "@mainapp/fragments/MenuAvatar.vue";

const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);

defineProps({
  activeName: {
    type: String as PropType<"personal" | "discussion" | "template">,
    required: false
  }
});
</script>

<template>
  <div class="header w-100% f-c-b px-10 h-20 b-b-1 b-b-solid b-border-primary bg-bg-overlay">
    <div class="flex-grow-1 f-c-s">
      <router-link to="/">
        <img class="w-45 h-15 cursor-pointer object-cover" src="/bleuon-icon.png" />
      </router-link>
    </div>
    <div class="flex-grow-1 f-c-b text-1.1rem">
      <router-link :to="'/u/profile/' + token.id">
        <div :class="{ active: activeName === 'personal' }" class="hover">个人空间</div>
      </router-link>
      <router-link to="/community/discussion">
        <div :class="{ active: activeName === 'discussion' }" class="hover">社区</div>
      </router-link>
      <router-link to="/community/blueprint">
        <div :class="{ active: activeName === 'template' }" class="hover">模板</div>
      </router-link>
    </div>
    <div class="flex-grow-1 f-c-e">
      <MenuAvatar></MenuAvatar>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.active {
  --uno: text-theme-primary font-bold !important;
}
</style>
