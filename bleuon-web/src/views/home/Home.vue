<script setup lang="ts">
import type { ActiveItem } from "./typings/home-typing";
import Menu from "./components/Menu.vue";

const activeItem = ref<ActiveItem>("MyRecent");
</script>

<template>
  <div class="home f-s-c bg-#F6F7F8">
    <Menu class="w-17%" v-model:active-item="activeItem"></Menu>
    <div class="content h-100vh flow-auto p-10 w-83%">
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
