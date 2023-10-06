<script lang="ts" setup>
/**
 * @description 可以点击出菜单的头像组件
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";

const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);
const user = ref(await UserApi.findOne(token.id));

function confirmLogout() {
  UserApi.logout(() => {
    location.reload();
  });
}
</script>

<template>
  <div class="f-c-c relative">
    <el-dropdown :teleported="false">
      <img class="rd-50% w-10 h-10 cursor-pointer" :src="user.avatar" />
      <template #dropdown>
        <el-dropdown-menu>
          <div class="b-b-solid b-border-primary b-b-1 pb-2 mb-2">
            <el-dropdown-item>
              <div class="f-c-s" @click="$router.push('/workbench')">
                <div class="i-tabler-files mr-2"></div>
                个人文件
              </div>
            </el-dropdown-item>
            <el-dropdown-item>
              <div class="f-c-s" @click="$router.push('/u/setting/')">
                <div class="i-tabler-user mr-2"></div>
                个人资料
              </div>
            </el-dropdown-item>
            <el-dropdown-item>
              <div class="f-c-s" @click="$router.push('/u/profile/' + token.id)">
                <div class="i-tabler-user mr-2"></div>
                个人主页
              </div>
            </el-dropdown-item>
          </div>
          <div class="b-b-solid b-border-primary b-b-1 pb-2 mb-2">
            <el-dropdown-item>
              <div class="f-c-s" @click="$router.push('/')">
                <div class="i-tabler-home mr-2"></div>
                官网首页
              </div>
            </el-dropdown-item>
          </div>
          <div>
            <el-dropdown-item>
              <el-popconfirm title="是否确定退出登录？" @confirm="confirmLogout">
                <template #reference>
                  <div class="f-c-s">
                    <div class="i-tabler-logout mr-2"></div>
                    退出登录
                  </div>
                </template>
              </el-popconfirm>
            </el-dropdown-item>
          </div>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<style lang="scss" scoped></style>
