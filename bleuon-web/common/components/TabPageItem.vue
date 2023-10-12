<script setup lang="ts">
defineProps({
  index: {
    type: Number,
    required: true
  },
  modelValue: {
    type: Number,
    required: true
  }
});

defineEmits(["update:modelValue"]);
</script>

<template>
  <div
    class="tab-page-item slider"
    :class="index === modelValue ? 'active' : 'noactive'"
    @click="$emit('update:modelValue', index)">
    <slot></slot>
  </div>
</template>

<style scoped lang="scss">
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
