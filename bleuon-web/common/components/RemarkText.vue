<script setup lang="ts">
defineProps({
  title: {
    type: String,
    required: true
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
      <div class="mr-2 font-bold text-1.1rem">{{ title }}</div>
      <div class="text-text-secondary text-0.9rem">{{ sub }}</div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
