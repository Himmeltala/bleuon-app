<script setup lang="ts">
import type { ActiveItem } from "./typings/home-typing";
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

const disabled = ref(false);

function createNewDiagram() {
  FlowchartApi.createOne(data => {
    router.push(`/flowchart/${data.id}`);
  });
}
</script>

<template>
  <div class="menu slim-slider h-100vh flow-auto px-5 b-r-1 b-r-solid b-border-primary bg-white">
    <div class="menu__header">
      <div class="mt-5">
        <img src="/bleuon-icon.png" class="h-15 w-40 object-cover" />
      </div>
      <div class="mt-5 relative">
        <el-button @click="disabled = !disabled" class="w-100%" type="primary">＋新建</el-button>
        <div
          class="options__panel select-none p-2 z-2 absolute top-12 left-0 w-65 bg-white rd-2"
          :class="disabled ? 'block' : 'hidden'">
          <div class="f-s-b flex-wrap flex-gap-1">
            <div class="item" @click="createNewDiagram">
              <div class="i-tabler-chart-grid-dots mr-1 text-theme-primary"></div>
              <div>流程图</div>
            </div>
            <div class="item">
              <div class="i-tabler-chalkboard mr-1 text-theme-primary"></div>
              <div>画布</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="menu__body mt-10 pb-5 b-b-1 b-b-solid b-border-primary">
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'auth-flowcharts' }"
        @click="navigateTo('auth-flowcharts')">
        <div class="mr-2 i-tabler-chart-grid-dots"></div>
        流程图
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'auth-shares-flowchart' }"
        @click="navigateTo('auth-shares-flowchart')">
        <div class="mr-2 i-tabler-share"></div>
        我分享的流程图
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'auth-stars-flowchart' }"
        @click="navigateTo('auth-stars-flowchart')">
        <div class="mr-2 i-tabler-star"></div>
        我收藏的流程图
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'auth-canvas-list' }"
        @click="navigateTo('auth-canvas-list')">
        <div class="mr-2 i-tabler-chalkboard"></div>
        画布
      </div>
    </div>
    <div class="mt-5 pb-5 b-b-1 b-b-solid b-border-primary">
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'auth-templates' }"
        @click="navigateTo('auth-templates')">
        <div class="mr-2 i-tabler-template"></div>
        模板社区
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'public-discussion' }"
        @click="navigateTo('public-discussion')">
        <div class="mr-2 i-tabler-friends"></div>
        讨论社区
      </div>
    </div>
    <div class="mt-5 pb-5">
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'auth-recycle' }"
        @click="navigateTo('auth-recycle')">
        <div class="mr-2 i-tabler-trash-x"></div>
        回收站
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.menu__item {
  --uno: cursor-pointer select-none p-3 rd-2 transition-all-300;

  &.active {
    --uno: bg-#F3F5F9;
  }

  &:hover {
    --uno: bg-#F3F5F9;
  }
}

.options__panel {
  box-shadow: 0 6px 16px 1px rgba(0, 0, 0, 0.08), 0 9px 28px 8px rgba(0, 0, 0, 0.05);

  .item {
    flex: 0 1 48% !important;
    --uno: cursor-pointer px-4 py-2 rd-2 transition-all-300 text-center f-c-s;

    &:hover {
      --uno: bg-#F3F5F9;
    }
  }
}
</style>
