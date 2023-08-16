<script setup lang="ts">
import type { ActiveItem } from "../typings/home-typing";

const props = defineProps({
  activeItem: {
    type: String as PropType<ActiveItem>,
    required: true
  }
});

const emits = defineEmits(["update:activeItem"]);

const router = useRouter();

function handleClickedItem(routerName: ActiveItem) {
  emits("update:activeItem", routerName);
  const rsv = router.resolve({ name: routerName });
  router.push({ name: rsv.name, path: rsv.path });
}

onBeforeMount(() => {
  const currRouterName = router.currentRoute.value.name;
  emits("update:activeItem", currRouterName);
});
</script>

<template>
  <div class="menu h-100vh flow-auto px-5 b-r-1 b-r-solid b-r-#e4e4e4 bg-white">
    <div class="menu__header">
      <div class="mt-5">
        <img src="/bleuon-icon.png" class="h-15 w-40 object-cover" />
      </div>
      <div class="mt-5">
        <el-button class="w-100%" type="primary">＋新建</el-button>
      </div>
    </div>
    <div class="menu__body mt-10 pb-5 b-b-1 b-b-solid b-b-#e4e4e4">
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'CreateByTemplate' }"
        @click="handleClickedItem('CreateByTemplate')">
        <div class="mr-2 i-tabler-apps"></div>
        从模板新建
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'MyRecent' }"
        @click="handleClickedItem('MyRecent')">
        <div class="mr-2 i-tabler-clock-hour-3"></div>
        最近文件
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'MyFiles' }"
        @click="handleClickedItem('MyFiles')">
        <div class="mr-2 i-tabler-folder"></div>
        我的文件
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'MyShares' }"
        @click="handleClickedItem('MyShares')">
        <div class="mr-2 i-tabler-share"></div>
        我的分享
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'MyStars' }"
        @click="handleClickedItem('MyStars')">
        <div class="mr-2 i-tabler-star"></div>
        我的收藏
      </div>
    </div>
    <div class="mt-5 pb-5 b-b-1 b-b-solid b-b-#e4e4e4">
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'Templates' }"
        @click="handleClickedItem('Templates')">
        <div class="mr-2 i-tabler-template"></div>
        模板社区
      </div>
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'Discussion' }"
        @click="handleClickedItem('Discussion')">
        <div class="mr-2 i-tabler-friends"></div>
        讨论社区
      </div>
    </div>
    <div class="mt-5 pb-5">
      <div
        class="menu__item f-c-s"
        :class="{ active: activeItem == 'Recycle' }"
        @click="handleClickedItem('Recycle')">
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
</style>
