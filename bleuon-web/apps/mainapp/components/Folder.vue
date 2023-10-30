<script lang="ts" setup>
/**
 * @description 文件夹
 * @author zheng
 * @since 2023/8/23
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
  lastIndex: {
    type: Number,
    required: true
  },
  folderName: {
    type: String,
    required: true
  }
});

const emits = defineEmits(["update:lastIndex"]);
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
  <div
    class="folder relative f-c-s px-4 py-4 rd-2 bg-white transition-all-300 cursor-pointer b-1 b-transparent b-solid hover:b-1 hover:b-primary hover:b-solid">
    <div :class="disabled && times <= 0 ? 'block' : 'hidden'" class="options">
      <div
        class="options__icon f-c-c absolute top-2 right-2 cursor-pointer w-5 h-10 rd-2 bg-#383838cc"
        @click="handleClick">
        <div class="i-tabler-dots-vertical text-white"></div>
      </div>
    </div>
    <div
      :class="disabled && times <= 0 ? 'block' : 'hidden'"
      class="options__panel select-none p-1 z-2 absolute top-10 right-2 w-80% bg-white rd-2">
      <div class="text-0.9rem f-c-s">
        <div class="i-tabler-edit mr-2"></div>
        重命名
      </div>
      <div class="text-0.9rem f-c-s">
        <div class="i-tabler-share mr-2"></div>
        分享
      </div>
      <div class="text-0.9rem f-c-s">
        <div class="i-tabler-trash-x mr-2"></div>
        删除
      </div>
    </div>
    <div class="i-tabler-folder-filled mr-2 text-primary"></div>
    <div class="select-none">{{ folderName }}</div>
  </div>
</template>

<style lang="scss" scoped>
.folder {
  flex: 0 1 15% !important;
  box-shadow: 0 1px 0 rgba(0, 0, 0, 0.16);

  &:hover {
    .options {
      display: block;

      .options__icon:hover {
        --uno: bg- #383838f2;
      }
    }
  }

  .options__panel {
    box-shadow: 0 6px 16px 1px rgba(0, 0, 0, 0.08), 0 9px 28px 8px rgba(0, 0, 0, 0.05);

    & > div {
      --uno: cursor-pointer px-4 py-2 rd-2 transition-all-300;

      &:hover {
        --uno: bg- #f3f5f9;
      }
    }
  }
}
</style>
