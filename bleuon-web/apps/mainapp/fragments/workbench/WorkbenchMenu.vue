<script lang="ts" setup>
/**
 * @description 工作台侧边栏菜单
 * @author zheng
 * @since 2023/8/23
 */

import { Requests } from "@common/requests";

defineProps({
  activeItem: {
    type: String,
    required: true
  }
});
const emits = defineEmits(["update:activeItem"]);

const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
const router = useRouter();

function navigateTo(name: string) {
  emits("update:activeItem", name);
  const rsv = router.resolve({ name: name });
  router.push({ path: rsv.path });
}

onBeforeMount(() => {
  const name = router.currentRoute.value.name;
  emits("update:activeItem", name);
});

function createFlowchart() {
  Requests.Flowchart.add({ consumerId: token.id }, res => {
    router.push(`/flowchart/${res.data.id}`);
  });
}
</script>

<template>
  <div
    class="menu slim-slider h-100vh flow-auto px-5 b-r-1 b-r-solid b-border-primary bg-bg-primary">
    <div class="menu-header">
      <div class="mt-5">
        <router-link to="/">
          <img class="h-15 w-40 object-cover" src="/bleuon-icon.png" />
        </router-link>
      </div>
      <el-dropdown :teleported="false" class="mt-5 w-100%" trigger="click">
        <el-button class="w-100%" type="primary">＋新建</el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="createFlowchart">
              <div class="w-40 f-c-s">
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
        画布 <span class="text-red">(敬请期待)</span>
      </div>
    </div>
    <div class="mt-5 pb-5 b-b-1 b-b-solid b-border-primary">
      <div
        :class="{ active: activeItem == 'auth-blueprint-square' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-blueprint-square')">
        <div class="mr-2 i-tabler-template"></div>
        模板社区
      </div>
      <div
        :class="{ active: activeItem == 'auth-discussion-square' }"
        class="menu-item f-c-s"
        @click="navigateTo('auth-discussion-square')">
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
        回收站 <span class="text-red">(敬请期待)</span>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.menu-item {
  --uno: cursor-pointer select-none p-2 rd-2 transition-all-300;

  &.active {
    --uno: bg-theme-6;
  }

  &:hover {
    --uno: bg-theme-6;
  }
}
</style>
