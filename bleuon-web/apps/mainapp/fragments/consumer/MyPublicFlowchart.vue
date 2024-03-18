<script setup lang="ts">
/**
 * @description 公开的流程图
 * @author zheng
 * @since 2023/10/13
 */

import { Requests } from "@common/requests";
import { DateUtil } from "@common/utils";

import File from "@mainapp/components/File.vue";

const props = defineProps({
  consumer: {
    type: Object as PropType<any>
  }
});

const mainData = ref(
  await Requests.Consumer.findAllFlowchart({ collectorId: `${props.consumer.id}`, isPublic: 1 })
);
</script>

<template>
  <div class="my-public-flowchart">
    <div class="file-list f-s-s flex-wrap flex-gap-1.25rem">
      <File
        v-for="item in mainData"
        :key="item.id"
        :file-image="item.dataUri"
        :path="'/flowchart/' + item.id"
        :options="false">
        <template #footer>
          <div class="f-c-s flex-nowrap mt-4 w-100%">
            <div class="mr-2 i-tabler-chart-bubble text-theme-primary"></div>
            <div class="text-0.9rem text-ellipsis line-clamp-1">{{ item.filename }}</div>
          </div>
          <div class="text-0.8rem mt-4">
            <div class="text-text-regular">
              创建:{{ DateUtil.formatted(item.createDate, "MM-dd HH:mm:ss") }}
            </div>
            <div class="text-text-secondary mt-1">
              修改:{{ DateUtil.formatted(item.modifyDate, "MM-dd HH:mm:ss") }}
            </div>
          </div>
        </template>
      </File>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
