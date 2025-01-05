<script setup lang="ts">
defineProps({
  title: {
    type: String,
    required: false
  },
  sub: {
    type: String,
    required: false
  }
});
</script>

<template>
  <div class="remark-text">
    <div class="f-e-s">
      <div v-if="title" class="mr-2 text-0.9rem text-text-primary">{{ title }}</div>
      <div v-if="sub" class="text-text-secondary text-0.8rem">{{ sub }}</div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
