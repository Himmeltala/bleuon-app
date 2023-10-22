<script setup lang="ts">
/**
 * @description Home 基础组件
 * @author zheng
 * @since 2023/10/22
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import MenuAvatar from "@subapp/fragments/MenuAvatar.vue";

const route = useRoute();
const router = useRouter();
const routes = route.matched[0].children;
const routeList = ref([]);

function getCurrentRouteList() {
  return router.currentRoute.value.matched;
}

routeList.value = getCurrentRouteList();

watch(route, () => {
  routeList.value = getCurrentRouteList();
});
</script>

<template>
  <div class="base-home">
    <el-container>
      <el-header>
        <MenuAvatar />
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu unique-opened default-active="/home/authority/admin/find" router>
            <el-sub-menu :index="`${fatherIndex + 1}`" v-for="(fatherRoute, fatherIndex) in routes">
              <template #title>
                <el-icon>
                  <component :is="fatherRoute.meta.icon"></component>
                </el-icon>
                <div>{{ fatherRoute.meta.title }}</div>
              </template>
              <el-menu-item
                v-for="childRoute in fatherRoute.children"
                :index="'/home/' + fatherRoute.path + '/' + childRoute.path">
                <template #title>
                  <el-icon>
                    <component :is="childRoute.meta.icon"></component>
                  </el-icon>
                  <div>{{ childRoute.meta.title }}</div>
                </template>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main style="padding: 0 1.25rem">
          <div class="mb-10">
            <el-breadcrumb separator="/">
              <template v-for="(item, index) in routeList">
                <el-breadcrumb-item v-if="index != routeList.length - 1" :to="item.path">
                  <div class="f-c-s">
                    <el-icon class="mr-1">
                      <component :is="item.meta.icon"></component>
                    </el-icon>
                    {{ item.meta.title }}
                  </div>
                </el-breadcrumb-item>
                <el-breadcrumb-item v-else>
                  <div class="f-c-s">
                    <el-icon class="mr-1">
                      <component :is="item.meta.icon"></component>
                    </el-icon>
                    {{ item.meta.title }}
                  </div>
                </el-breadcrumb-item>
              </template>
            </el-breadcrumb>
          </div>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss"></style>
