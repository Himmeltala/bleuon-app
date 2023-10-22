<script setup lang="ts">
const route = useRoute();
const routes = route.matched[0].children;
</script>

<template>
  <div class="base-home">
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu unique-opened default-active="/home/authority/consumer" router>
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
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss"></style>
