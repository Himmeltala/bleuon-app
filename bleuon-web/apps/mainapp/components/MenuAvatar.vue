<script setup lang="ts">
/**
 * @description 可以点击出菜单的头像组件
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { USER_API } from "@mainapp/apis";

const disabled = ref(false);

function confirmLogout() {
  USER_API.logout(() => {
    location.reload();
  });
}
</script>

<template>
  <div class="f-c-c relative">
    <img
      @click="disabled = !disabled"
      src="https://img2.baidu.com/it/u=1397727792,1861968739&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
      class="rd-50% w-10 h-10 cursor-pointer" />
    <div
      class="options__panel select-none px-1 z-2 absolute top-10 right-0 w-50 bg-white rd-2"
      :class="disabled ? 'block' : 'hidden'">
      <div class="py-1 b-b-1 b-b-solid b-b-#e4e4e4">
        <div class="item text-0.9rem f-c-s" @click="$router.push('/home/history')">
          <div class="i-tabler-files mr-2"></div>
          个人文件
        </div>
        <div class="item text-0.9rem f-c-s" @click="$router.push('/u/setting')">
          <div class="i-tabler-brand-storytel mr-2"></div>
          账户中心
        </div>
        <div class="item text-0.9rem f-c-s" @click="$router.push('/u/profile/123')">
          <div class="i-tabler-user mr-2"></div>
          个人主页
        </div>
      </div>
      <div class="py-1 b-b-1 b-b-solid b-b-#e4e4e4">
        <div class="item text-0.9rem f-c-s" @click="$router.push('/')">
          <div class="i-tabler-home mr-2"></div>
          官网首页
        </div>
      </div>
      <div class="py-1">
        <el-popconfirm @confirm="confirmLogout" title="是否确定退出登录？">
          <template #reference>
            <div class="item text-0.9rem f-c-s">
              <div class="i-tabler-logout mr-2"></div>
              退出登录
            </div>
          </template>
        </el-popconfirm>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.options__panel {
  box-shadow: 0 6px 16px 1px rgba(0, 0, 0, 0.08), 0 9px 28px 8px rgba(0, 0, 0, 0.05);

  .item {
    --uno: cursor-pointer px-4 py-2 rd-2 transition-all-300;

    &:hover {
      --uno: bg-#F3F5F9;
    }
  }
}
</style>
