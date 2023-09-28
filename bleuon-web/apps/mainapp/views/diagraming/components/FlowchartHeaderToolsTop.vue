<script setup lang="ts">
import { dia } from "@mainapp/lib";
import { convertSvgToImage } from "@mainapp/lib/tools";
import MenuAvatar from "@mainapp/components/MenuAvatar.vue";

const paper = inject<Ref<dia.Paper>>("bleuonPaper");
const graph = inject<Ref<dia.Graph>>("bleuonGraph");

const editFile = ref(false);

const config = ref({
  height: 1000,
  width: 1000,
  fileName: "未命名的文件"
});

function download() {
  const { width, height } = paper.value.getArea();
  config.value.width = width;
  config.value.height = height;
  convertSvgToImage(paper.value, graph.value, "jpeg", config.value);
}
</script>

<template>
  <div class="border-#dfe2e5 border-b-1 border-b-solid f-c-b pb-4">
    <div class="left f-c-s">
      <el-button @click="$router.back()" type="primary" bg text>
        <template #icon>
          <div class="i-tabler-chevron-left"></div>
        </template>
      </el-button>
      <img
        @click="$router.push('/home')"
        src="/bleuon-icon.png"
        class="mr-4 w-30 h-15 object-cover cursor-pointer" />
      <div v-show="editFile" @keyup.enter="editFile = !editFile">
        <el-input v-model="config.fileName" size="small" placeholder="请输入文件名" />
      </div>
      <div class="text-1rem text-gray-700" v-show="!editFile" @click="editFile = !editFile">
        {{ config.fileName }}
      </div>
    </div>
    <div class="right f-c-s">
      <div class="mr-6 f-c-s text-gray-500 text-1.5rem">
        <div @click="download" class="hover mr-4 i-tabler-download"></div>
        <div class="hover i-tabler-share"></div>
      </div>
      <MenuAvatar />
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
