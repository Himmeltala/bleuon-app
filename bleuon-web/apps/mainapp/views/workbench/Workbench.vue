<script lang="ts" setup>
/**
 * @description 工作台页
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import type { ActiveItem } from "@mainapp/typings/workbench";
import WorkbenchMenu from "@mainapp/components/WorkbenchMenu.vue";

const activeItem = ref<ActiveItem>("auth-flowchart-list");
</script>

<template>
  <div class="workbench f-s-c bg-bg-primary">
    <WorkbenchMenu v-model:active-item="activeItem" class="w-17%"></WorkbenchMenu>
    <div class="content h-100vh flow-auto px-10 pb-10 w-83%">
      <RouterView></RouterView>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
