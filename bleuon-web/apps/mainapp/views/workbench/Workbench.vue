<script lang="ts" setup>
/**
 * @description 工作台页
 * @author zheng
 * @since 2023/8/23
 */

import WorkbenchMenu from "@mainapp/fragments/workbench/WorkbenchMenu.vue";

const activeItem = ref("auth-flowchart-list");
</script>

<template>
  <div class="workbench f-s-c bg-bg-page">
    <WorkbenchMenu v-model:active-item="activeItem" class="w-17%"></WorkbenchMenu>
    <div class="content h-100vh flow-auto w-83%">
      <RouterView></RouterView>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
