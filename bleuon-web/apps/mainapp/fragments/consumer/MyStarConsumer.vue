<script setup lang="ts">
/**
 * @description 收藏的流程图
 * @author zheng
 * @since 2023/10/13
 */

import { Requests } from "@common/requests";

const props = defineProps({
  consumer: {
    type: Object as PropType<any>
  }
});

const mainData = ref(
  await Requests.Consumer.findAllCollectingConsumerByCriteria({ collectorId: props.consumer.id })
);

function cancelStar(item: CollectingConsumerModel, index: number) {
  Requests.Consumer.deleteCollecting({ id: item.id }, () => {
    mainData.value.splice(index, 1);
  });
}
</script>

<template>
  <div v-if="mainData.length" class="my-star-consumer bg-bg-overlay rd-2 px-5 py-5">
    <div
      v-for="(item, index) in mainData"
      class="b-b-solid b-b-1 b-b-border-primary pb-4 f-c-b"
      :class="{ 'mb-5': index != mainData.length - 1 }">
      <div class="data f-c-s">
        <router-link :to="'/u/profile/' + item.consumer.id">
          <img :src="item.consumer.avatar" class="w-15 h-15 rd-50% mr-5" />
        </router-link>
        <div>
          <router-link :to="'/u/profile/' + item.consumer.id">
            <div class="hover font-400 text-1.1rem">{{ item.consumer.username }}</div>
          </router-link>
          <div class="mt-4 text-text-regular text-0.8rem">{{ item.consumer.signature }}</div>
        </div>
      </div>
      <div class="options">
        <div>
          <el-button type="danger" size="small" @click="cancelStar(item, index)"
            >取消关注</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
