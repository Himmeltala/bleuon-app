<script lang="ts" setup>
/**
 * @description 可以点击出菜单的头像组件
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";

const user = useStorage<UserData>(KeyVals.MAINAPP_USER, {});

function confirmLogout() {
  UserApi.logout(() => {
    location.reload();
  });
}

const root = document.querySelector("html");
const mode = useStorage(KeyVals.MAINAPP_THEME_MODE, "");
const isDark = ref(mode.value == "dark" ? true : false);

function switchThemeMode() {
  const name = isDark.value ? "dark" : "light";
  root.className = name;
  mode.value = name;
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
              <router-link to="/workbench">
                <div class="f-c-s">
                  <div class="i-tabler-files mr-2"></div>
                  个人文件
                </div>
              </router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/u/setting">
                <div class="f-c-s">
                  <div class="i-tabler-user mr-2"></div>
                  个人资料
                </div>
              </router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link :to="'/u/profile/' + user.id">
                <div class="f-c-s">
                  <div class="i-tabler-user mr-2"></div>
                  个人主页
                </div>
              </router-link>
            </el-dropdown-item>
          </div>
          <div class="b-b-solid b-border-primary b-b-1 pb-2 mb-2">
            <el-dropdown-item>
              <router-link to="/">
                <div class="f-c-s">
                  <div class="i-tabler-home mr-2"></div>
                  官网首页
                </div>
              </router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-switch
                @change="switchThemeMode"
                :active-text="isDark ? '黑夜' : '白天'"
                size="small"
                v-model="isDark" />
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
