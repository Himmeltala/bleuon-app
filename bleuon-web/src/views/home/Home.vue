<script setup lang="ts">
import type { ActiveItem } from "./typings/home-typing";
import Menu from "./components/Menu.vue";

const activeItem = ref<ActiveItem>("MyRecent");
</script>

<template>
  <div class="home f-s-c min-h-100vh">
    <Menu
      v-model:active-item="activeItem"
      class="px-5 min-h-100vh w-17% b-r-1 b-r-solid b-r-#e4e4e4"></Menu>
    <div class="content min-h-100vh w-83% bg-#F6F7F8 p-10">
      <RouterView v-slot="{ Component }">
        <template v-if="Component">
          <KeepAlive>
            <component :is="Component" />
          </KeepAlive>
        </template>
      </RouterView>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
