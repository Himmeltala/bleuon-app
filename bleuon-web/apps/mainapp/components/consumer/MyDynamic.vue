<script setup lang="ts">
import { DateUtil, TextUtil } from "@common/utils";
import { ConsumerAPI, FileAPI } from "@mainapp/apis";

import ClassicCkEditor from "@mainapp/components/ClassicCkEditor.vue";

const props = defineProps({
  consumer: {
    type: Object as PropType<any>
  }
});

const list = ref([]);
const ckeditorValue = ref("");
const token = localStorage.getToken(KeyVals.MAINAPP_TOKEN_KEY);

async function fetchList() {
  list.value = await ConsumerAPI.findAllDynamicByCriteria({
    sequences: [{ isAsc: false, col: "create_date" }],
    consumerId: `${props.consumer.id}`
  });
}

function uploadDynamicImage(formData: FormData) {
  formData.append("filepath", "/dynamic");
  return FileAPI.uploadImageFile(formData);
}

function commit() {
  const len = TextUtil.strlen(ckeditorValue.value);
  if (len < 3) {
    ElMessage.error("发表至少 3 个字的动态！");
    return;
  }

  ConsumerAPI.addDynamic({ content: ckeditorValue.value, consumerId: token.id }, async () => {
    await fetchList();
  });
}

function digg(item: DynamicModel) {
  item.digg += 1;
  ConsumerAPI.upgradeDynamic({ digg: item.digg, id: item.id }, () => {
    ElMessage.success("支持成功！");
  });
}

function bury(item: DynamicModel) {
  item.bury += 1;
  ConsumerAPI.upgradeDynamic({ bury: item.bury, id: item.id }, () => {
    ElMessage.success("反对成功！");
  });
}

function remove(item: DynamicModel, index: number) {
  ConsumerAPI.deleteDynamic({ id: item.id }, () => {
    list.value.splice(index, 1);
  });
}

await fetchList();
</script>

<template>
  <div class="my-dynamic">
    <div class="px-5 py-5 rd-2 bg-bg-overlay" v-if="token.id === consumer.id">
      <ClassicCkEditor
        v-model="ckeditorValue"
        :imgae-uploader="uploadDynamicImage"></ClassicCkEditor>
      <div class="f-c-e mt-2">
        <el-button type="primary" @click="commit">发表动态</el-button>
      </div>
    </div>
    <div v-if="list">
      <div
        class="bg-bg-overlay rd-2 px-5 py-5 f-s-s mt-2"
        v-for="(item, index) in list"
        :key="item.id">
        <div class="mr-10">
          <img class="w-15 h-15 rd-50%" :src="consumer.avatar" />
        </div>
        <div>
          <div>
            <div class="text-text-regular">
              {{ consumer.username }}
            </div>
            <div class="mt-1 f-c-s text-0.8rem text-text-regular">
              <div class="i-tabler-clock mr-1"></div>
              {{ DateUtil.formatted(item.createDate) }}
            </div>
          </div>
          <div class="mt-4" v-html="item.content"></div>
          <div class="f-c-s mt-6 text-text-secondary text-0.9rem">
            <div class="mr-15 hover f-c-c" @click="digg(item)">
              <div class="i-tabler-thumb-up mr-1"></div>
              {{ item.digg }}
            </div>
            <div class="mr-15 hover f-c-c" @click="bury(item)">
              <div class="i-tabler-thumb-down mr-1"></div>
              {{ item.bury }}
            </div>
            <div v-if="consumer.id == token.id">
              <el-button @click="remove(item, index)" type="danger" size="small" text>
                删除
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
