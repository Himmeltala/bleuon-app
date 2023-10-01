<script setup lang="ts">
/**
 * @description 文件
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

const props = defineProps({
  disabled: {
    type: Boolean,
    required: true
  },
  index: {
    type: Number,
    required: true
  },
  path: {
    type: String,
    required: true
  },
  lastIndex: {
    type: Number,
    required: true
  },
  fileImage: {
    type: String
  },
  fileName: {
    type: String,
    required: true
  },
  modifyDate: {
    type: String,
    required: true
  },
  createDate: {
    type: String
  }
});

const emits = defineEmits([
  "update:lastIndex",
  "publish",
  "download",
  "copy",
  "delete",
  "resetFileName",
  "share"
]);
const times = ref(1);

function handleClick() {
  if (!props.disabled) times.value = 1;
  if (times.value > 0) {
    times.value = 0;
  } else {
    times.value++;
  }
  emits("update:lastIndex", props.index);
}
</script>

<template>
  <div class="file relative">
    <div class="options" :class="disabled && times <= 0 ? 'block' : 'hidden'">
      <div
        @click="handleClick"
        class="options__icon f-c-c absolute top-2 right-2 cursor-pointer w-10 h-6 rd-2 bg-#383838cc">
        <div class="i-tabler-dots text-white"></div>
      </div>
    </div>
    <div
      class="options__panel select-none p-1 z-2 absolute top-10 right-2 w-80% bg-white rd-2"
      :class="disabled && times <= 0 ? 'block' : 'hidden'">
      <div class="text-0.9rem f-c-s" @click="$router.push(path)">
        <div class="i-tabler-eye mr-2"></div>
        预览
      </div>
      <div class="text-0.9rem f-c-s" @click="$emit('resetFileName')">
        <div class="i-tabler-edit mr-2"></div>
        重命名
      </div>
      <div class="text-0.9rem f-c-s" @click="$emit('share')">
        <div class="i-tabler-share mr-2"></div>
        分享
      </div>
      <div class="text-0.9rem f-c-s" @click="$emit('publish')">
        <div class="i-tabler-send mr-2"></div>
        发布
      </div>
      <div class="text-0.9rem f-c-s" @click="$emit('download')">
        <div class="i-tabler-download mr-2"></div>
        下载
      </div>
      <div class="text-0.9rem f-c-s" @click="$emit('copy')">
        <div class="i-tabler-copy mr-2"></div>
        复制
      </div>
      <div class="text-0.9rem f-c-s" @click="$emit('delete')">
        <div class="i-tabler-trash-x mr-2"></div>
        删除
      </div>
    </div>
    <div class="image h-50 rd-2" @click="$router.push(path)">
      <img
        v-if="fileImage"
        class="w-100% h-100% rd-2 object-fill cursor-pointer bg-white"
        :src="fileImage" />
      <div v-else class="w-100% h-100% rd-2 cursor-pointer bg-white"></div>
    </div>
    <div class="f-c-s flex-nowrap mt-4 w-100%">
      <div class="mr-2 i-tabler-chart-bubble text-theme-primary"></div>
      <div class="text-0.9rem text-ellipsis line-clamp-1">{{ fileName }}</div>
    </div>
    <div class="text-text-secondary text-0.8rem mt-2 f-c-s">
      <div class="i-tabler-clock-edit mr-1"></div>
      更新于 {{ modifyDate }}
    </div>
    <div v-if="createDate" class="text-text-secondary text-0.8rem mt-1 f-c-s">
      <div class="i-tabler-clock-edit mr-1"></div>
      创建于 {{ createDate }}
    </div>
  </div>
</template>

<style scoped lang="scss">
.file {
  flex: 0 1 15% !important;

  &:hover {
    .options {
      display: block;

      .options__icon:hover {
        --uno: bg-#383838f2;
      }
    }
  }

  .options__panel {
    box-shadow: 0 6px 16px 1px rgba(0, 0, 0, 0.08), 0 9px 28px 8px rgba(0, 0, 0, 0.05);

    & > div {
      --uno: cursor-pointer px-4 py-2 rd-2 transition-all-300;

      &:hover {
        --uno: bg-#F3F5F9;
      }
    }
  }

  .image {
    box-shadow: 0 1px 0 rgba(0, 0, 0, 0.16);
  }
}
</style>
