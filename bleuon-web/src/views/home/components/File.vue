<script setup lang="ts">
const props = defineProps({
  disabledOps: {
    type: Boolean,
    required: true
  },
  index: {
    type: Number,
    required: true
  },
  clickedIndex: {
    type: Number,
    required: true
  }
});

const emits = defineEmits(["update:clickedIndex"]);
const clickedTimes = ref(1);

function onClickMoreOps() {
  if (!props.disabledOps) clickedTimes.value = 1;
  if (clickedTimes.value > 0) {
    clickedTimes.value = 0;
  } else {
    clickedTimes.value++;
  }
  emits("update:clickedIndex", props.index);
}
</script>

<template>
  <div class="file-item relative">
    <div class="more-ops" :class="disabledOps && clickedTimes <= 0 ? 'block' : 'hidden'">
      <div
        @click="onClickMoreOps"
        class="more-ops__icon f-c-c absolute top-2 right-2 cursor-pointer w-10 h-6 rd-2 bg-#383838cc">
        <div class="i-tabler-dots text-white"></div>
      </div>
    </div>
    <div
      class="more-ops__panel select-none p-1 z-2 absolute top-10 right-2 w-80% bg-white rd-2"
      :class="disabledOps && clickedTimes <= 0 ? 'block' : 'hidden'">
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-eye mr-2"></div>
        预览
      </div>
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-edit mr-2"></div>
        重命名
      </div>
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-share mr-2"></div>
        分享
      </div>
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-send mr-2"></div>
        发布和公开
      </div>
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-download mr-2"></div>
        下载
      </div>
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-copy mr-2"></div>
        复制
      </div>
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-arrows-move mr-2"></div>
        移动
      </div>
      <div class="panel-item text-0.9rem f-c-s">
        <div class="i-tabler-trash-x mr-2"></div>
        删除
      </div>
    </div>
    <div class="cover h-50 rd-2">
      <img
        class="w-100% h-100% rd-2 object-cover cursor-pointer"
        src="https://img2.baidu.com/it/u=1616455932,108201296&fm=253&fmt=auto&app=138&f=JPEG?w=281&h=500" />
    </div>
    <div class="f-c-s flex-nowrap mt-4 w-100%">
      <div class="mr-2 i-tabler-chart-bubble text-primary"></div>
      <div class="text-0.9rem text-ellipsis line-clamp-1">文件名</div>
    </div>
    <div class="text-b text-0.9rem mt-2">更新于 2个月前</div>
  </div>
</template>

<style scoped lang="scss">
.file-item {
  flex: 0 1 15% !important;
  --uno: transition-all-300;

  &:hover {
    .more-ops {
      display: block;

      .more-ops__icon:hover {
        --uno: bg-#383838f2;
      }
    }
  }

  .more-ops__panel > div {
    --uno: cursor-pointer px-4 py-2 rd-2;

    &:hover {
      --uno: bg-#F3F5F9;
    }
  }

  .cover {
    box-shadow: 0 1px 0 rgba(0, 0, 0, 0.16);
  }
}
</style>
