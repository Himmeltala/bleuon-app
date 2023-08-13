<script setup lang="ts">
const props = defineProps({
  activeName: {
    type: String as PropType<"personal" | "recommend" | "template">,
    required: false
  }
});
</script>

<template>
  <div class="header w-100% f-c-b px-10 h-20 b-b-1 b-b-solid b-b-#e4e4e4 bg-white">
    <div class="flex-grow-1 cursor-pointer f-c-s" @click="$router.push('/')">
      <img src="/bleuon-icon.png" class="w-45 h-15 object-cover" />
    </div>
    <div class="flex-grow-1 f-c-b text-1.1rem">
      <div
        class="hover"
        @click="$router.push('/u/setting')"
        :class="{ 'text-primary text-bold': activeName == 'personal' }">
        个人
      </div>
      <div
        class="hover"
        @click="$router.push('/u/recommend')"
        :class="{ 'text-primary text-bold': activeName == 'recommend' }">
        推荐
      </div>
      <div
        class="hover"
        @click="$router.push('/u/template')"
        :class="{ 'text-primary text-bold': activeName == 'template' }">
        模板
      </div>
    </div>
    <div class="flex-grow-1 f-c-e">
      <img
        src="https://img2.baidu.com/it/u=1397727792,1861968739&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
        class="rd-50% h-10 w-10" />
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
