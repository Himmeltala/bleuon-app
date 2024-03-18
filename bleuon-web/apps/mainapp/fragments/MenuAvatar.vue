<script lang="ts" setup>
/**
 * @description 可以点击出菜单的头像组件
 * @author zheng
 * @since 2023/8/23
 */

import { Requests } from "@common/requests";

const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
const consumer = ref(await Requests.Consumer.findBy({ id: token.id }));

const root = document.querySelector("html");
const mode = useStorage(Consts.MAINAPP_THEME_MODE, "");
const isDark = ref(mode.value == "dark");

function switchThemeMode() {
  const name = isDark.value ? "dark" : "light";
  root.className = name;
  mode.value = name;
}

function confirmLogout() {
  Requests.Consumer.authLogout();
}

switchThemeMode();
</script>

<template>
  <div class="f-c-c relative">
    <el-dropdown :teleported="false">
      <img :src="consumer.avatar" class="rd-50% w-10 h-10 cursor-pointer" />
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
              <router-link :to="'/u/profile/' + consumer.id">
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
                v-model="isDark"
                :active-text="isDark ? '黑夜' : '白天'"
                size="small"
                @change="switchThemeMode" />
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
