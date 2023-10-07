<script lang="ts" setup>
/**
 * @description 工作台侧边栏菜单
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import type { ActiveItem } from "@mainapp/typings/workbench";
import { FlowchartApi } from "@mainapp/apis";

const router = useRouter();
defineProps({
  activeItem: {
    type: String as PropType<ActiveItem>,
    required: true
  }
});
const emits = defineEmits(["update:activeItem"]);

function navigateTo(routerName: ActiveItem) {
  emits("update:activeItem", routerName);
  const rsv = router.resolve({ name: routerName });
  router.push({ name: rsv.name, path: rsv.path });
}

onBeforeMount(() => {
  const currRouterName = router.currentRoute.value.name as ActiveItem;
  emits("update:activeItem", currRouterName);
});

function createFlowchart() {
  FlowchartApi.createOne(data => {
    router.push(`/flowchart/${data.id}`);
  });
}
</script>

<template>
  <div
    class="menu slim-slider h-100vh flow-auto px-5 b-r-1 b-r-solid b-border-primary bg-bg-primary">
    <div class="menu-header">
      <div class="mt-5">
        <img class="h-15 w-40 object-cover" src="/bleuon-icon.png" />
      </div>
      <el-dropdown :teleported="false" class="mt-5 w-100%" trigger="click">
        <el-button class="w-100%" type="primary">＋新建</el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <div class="w-40 f-c-s" @click="createFlowchart">
                <div class="i-tabler-chart-grid-dots mr-4 text-theme-primary"></div>
                <div>新建流程图</div>
              </div>
            </el-dropdown-item>
            <el-dropdown-item>
              <div class="w-40 f-c-s">
                <div class="i-tabler-chalkboard mr-4 text-theme-primary"></div>
                <div>新建画布</div>
              </div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <div class="menu-content mt-10 pb-5 b-b-1 b-b-solid b-border-primary">
      <div
        :class="{ active: activeItem == 'auth-flowchart-list' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-flowchart-list')">
        <div class="mr-2 i-tabler-chart-grid-dots"></div>
        流程图
      </div>
      <div
        :class="{ active: activeItem == 'auth-flowchart-stars' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-flowchart-stars')">
        <div class="mr-2 i-tabler-star"></div>
        收藏的流程图
      </div>
      <div
        :class="{ active: activeItem == 'auth-canvas-list' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-canvas-list')">
        <div class="mr-2 i-tabler-chalkboard"></div>
        画布
      </div>
    </div>
    <div class="mt-5 pb-5 b-b-1 b-b-solid b-border-primary">
      <div
        :class="{ active: activeItem == 'auth-template-community' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-template-community')">
        <div class="mr-2 i-tabler-template"></div>
        模板社区
      </div>
      <div
        :class="{ active: activeItem == 'auth-discussion-community' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-discussion-community')">
        <div class="mr-2 i-tabler-friends"></div>
        讨论社区
      </div>
    </div>
    <div class="mt-5 pb-5">
      <div
        :class="{ active: activeItem == 'auth-recycle' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-recycle')">
        <div class="mr-2 i-tabler-trash-x"></div>
        回收站
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.menu-item {
  --uno: cursor-pointer select-none p-3 rd-2 transition-all-300;

  &.active {
    --uno: bg-theme-6;
  }

  &:hover {
    --uno: bg-theme-6;
  }
}
</style>
