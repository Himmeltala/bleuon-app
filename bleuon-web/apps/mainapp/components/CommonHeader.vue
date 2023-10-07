<script lang="ts" setup>
/**
 * @description user 页面下的头部组件
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

// components
import MenuAvatar from "@mainapp/components/MenuAvatar.vue";

const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);

defineProps({
  activeName: {
    type: String as PropType<"personal" | "discussion" | "template">,
    required: false
  }
});
</script>

<template>
  <div class="header w-100% f-c-b px-10 h-20 b-b-1 b-b-solid b-border-primary bg-bg-overlay">
    <div class="flex-grow-1 cursor-pointer f-c-s" @click="$router.push('/')">
      <img class="w-45 h-15 object-cover" src="/bleuon-icon.png" />
    </div>
    <div class="flex-grow-1 f-c-b text-1.1rem">
      <div
        :class="{ active: activeName === 'personal' }"
        class="hover"
        @click="$router.push('/u/profile/' + token.id)">
        个人
      </div>
      <div
        :class="{ active: activeName === 'discussion' }"
        class="hover"
        @click="$router.push('/community/discussion')">
        社区
      </div>
      <div
        :class="{ active: activeName === 'template' }"
        class="hover"
        @click="$router.push('/community/template')">
        模板
      </div>
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
