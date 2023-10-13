<script lang="ts" setup>
/**
 * @description 欢迎页
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { ConsumerApi } from "@mainapp/apis";

const token = localStorage.getToken(KeyVals.MAINAPP_TOKEN_KEY);

function confirmLogout() {
  ConsumerApi.authLogout();
}
</script>

<template>
  <div class="welcome">
    <div class="header fixed right-0 top-0 w-100% bg-bg-overlay">
      <div class="px-55 f-c-b h-20 b-b-1 b-b-solid b-border-primary">
        <div class="flex-grow-1 cursor-pointer f-c-s">
          <router-link to="/">
            <img class="w-45 h-15 object-cover" src="/bleuon-icon.png" />
          </router-link>
        </div>
        <div class="flex-grow-2 f-c-b text-1.1rem">
          <div class="hover">推荐</div>
          <div class="hover">模板</div>
          <div class="hover">专题</div>
        </div>
        <div class="flex-grow-1 f-c-e">
          <template v-if="token">
            <router-link class="mr-4" to="/entrance">
              <el-button type="primary">
                <span>进入工作台</span>
                <template #icon>
                  <div class="i-tabler-door-enter"></div>
                </template>
              </el-button>
            </router-link>
            <el-popconfirm title="是否确定退出登录？" @confirm="confirmLogout">
              <template #reference>
                <el-button bg text type="danger">
                  <span>退出</span>
                  <template #icon>
                    <div class="i-tabler-logout"></div>
                  </template>
                </el-button>
              </template>
            </el-popconfirm>
          </template>
          <template v-else>
            <router-link class="mr-4" to="/entrance">
              <el-button bg text>
                <span>登录</span>
                <template #icon>
                  <div class="i-tabler-key"></div>
                </template>
              </el-button>
            </router-link>
            <router-link to="/entrance">
              <el-button type="primary">
                <span>注册</span>
                <template #icon>
                  <div class="i-tabler-login-2"></div>
                </template>
              </el-button>
            </router-link>
          </template>
        </div>
      </div>
    </div>
    <div class="mt-70">
      <div class="text-center text-3rem font-bold">免费在线流程图思维导图</div>
      <div class="text-center text-1.5rem mt-4 text-text-secondary">
        一款在线作图工具，支持原型图、UML、BPMN、网络拓扑图等多种图形绘制
      </div>
      <div class="text-center mt-20">
        <router-link to="/workbench">
          <el-button size="large" type="primary">免费试用</el-button>
        </router-link>
      </div>
    </div>
    <div class="mt-50">
      <!-- 这里放一个简单的编辑流程图的东西 -->
    </div>
    <div class="w-100% px-55 mt-50">
      <div class="f-c-b">
        <div class="w-60%">
          <!-- 录制一个gif1 -->
          <!-- 录制一个gif2 -->
          <!-- 录制一个gif3 -->
        </div>
        <div class="w-35%">
          <div class="mb-4 pb-4 cursor-pointer b-b-1 b-b-solid b-b-#e4e4e4">
            <div class="text-1.3rem font-bold">图片导出</div>
            <div class="text-text-secondary text-1.1rem mt-4">支出 png 格式导出</div>
          </div>
          <div class="mb-4 pb-4 cursor-pointer b-b-1 b-b-solid b-b-#e4e4e4">
            <div class="text-1.3rem font-bold">云端存储</div>
            <div class="text-text-secondary text-1.1rem mt-4">支出 png 格式导出</div>
          </div>
          <div class="pb-4 cursor-pointer b-b-1 b-b-solid b-b-#e4e4e4">
            <div class="text-1.3rem font-bold">多人共享</div>
            <div class="text-text-secondary text-1.1rem mt-4">支出 png 格式导出</div>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-50 w-100% download">
      <div>
        <!-- 这里放一个图片 -->
      </div>
      <div class="text-center">
        <div class="text-white">
          <img class="w-10 h-10 object-cover" src="/browser.png" />
          <div class="mt-4">网页版</div>
        </div>
      </div>
    </div>
    <!-- 底部 -->
    <div></div>
  </div>
</template>

<style lang="scss" scoped>
.download {
  height: calc(100vh - 5rem);
  background-image: url("https://www.processon.com/public_login/terminal_bg.7da39e2a.png");
}
</style>
