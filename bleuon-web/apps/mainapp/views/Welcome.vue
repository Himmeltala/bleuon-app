<script lang="ts" setup>
/**
 * @description 欢迎页
 * @author zheng
 * @since 2023/8/23
 */

import { Requests } from "@common/requests";
const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);

function confirmLogout() {
  Requests.Consumer.authLogout();
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
          <router-link to="/u/setting">
            <div class="hover">个人资料</div>
          </router-link>
          <router-link to="/community/discussion">
            <div class="hover">社区</div>
          </router-link>
          <router-link to="/community/blueprint">
            <div class="hover">模板</div>
          </router-link>
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
    <div class="w-100% px-55 mt-50">
      <div class="f-c-b">
        <div class="w-60%">
          <el-carousel :interval="2000" height="30rem">
            <el-carousel-item class="rd-2">
              <img src="/20231020223140.png" class="object-contain w-100% h-100%" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img src="/20231020223441.png" class="object-contain w-100% h-100%" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img src="/20231020223456.png" class="object-contain w-100% h-100%" />
            </el-carousel-item>
            <el-carousel-item class="rd-2">
              <img src="/20231020223513.png" class="object-contain w-100% h-100%" />
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="w-35%">
          <div class="mb-4 pb-4 cursor-pointer b-b-1 b-b-solid b-b-#e4e4e4">
            <div class="text-1.3rem font-bold">图片导出</div>
            <div class="text-text-secondary text-1.1rem mt-4">支出 jpg 图片格式导出</div>
          </div>
          <div class="mb-4 pb-4 cursor-pointer b-b-1 b-b-solid b-b-#e4e4e4">
            <div class="text-1.3rem font-bold">云端存储</div>
            <div class="text-text-secondary text-1.1rem mt-4">存储到云端，不用担心丢失</div>
          </div>
          <div class="pb-4 cursor-pointer b-b-1 b-b-solid b-b-#e4e4e4">
            <div class="text-1.3rem font-bold">多人分享</div>
            <div class="text-text-secondary text-1.1rem mt-4">给好友分享，支持导出和导入模板</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
