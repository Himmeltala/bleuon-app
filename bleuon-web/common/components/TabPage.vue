<script setup lang="ts">
defineProps({
  tabs: {
    type: Array,
    required: true
  },
  tabIndex: {
    type: Number,
    required: true
  }
});
</script>

<template>
  <div class="tab-page">
    <div class="items">
      <slot name="item"></slot>
    </div>
    <div class="content">
      <slot></slot>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
