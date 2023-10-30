<script setup lang="ts">
/**
 * @description 头像上传组件
 * @author zheng
 * @since 2023/10/10
 */

import { genFileId } from "element-plus";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";

const props = defineProps({
  imgUrl: {
    type: String,
    required: true
  },
  startUpload: {
    type: Function as PropType<(formData: FormData) => XMLHttpRequest | Promise<unknown>>,
    required: true
  }
});

const emits = defineEmits<{
  (event: "update:imgUrl", imgUrl: string): void;
}>();

const upload = ref<UploadInstance>();
const dialog = ref(false);

const handleExceed: UploadProps["onExceed"] = files => {
  upload.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  upload.value!.handleStart(file);
};

const beforeUpload: UploadProps["beforeUpload"] = rawFile => {
  if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error("图片大小不能超过 2MB！");
    return false;
  }
  return true;
};

const onSuccessUpload: UploadProps["onSuccess"] = response => {
  dialog.value = !dialog.value;
  upload.value!.clearFiles();
  emits("update:imgUrl", response.data.data);
};

function startUpload(options: any): XMLHttpRequest | Promise<unknown> {
  const formData = new FormData();
  formData.append("file", options.file);
  return props.startUpload(formData);
}
</script>

<template>
  <div class="avatar-upload">
    <div class="avatar-box h-40 w-40 relative">
      <div class="hover-box rd-50% h-100% w-100%" @click="dialog = !dialog">
        <div class="f-c-c h-100% w-100%">点击上传头像</div>
      </div>
      <img :src="imgUrl" class="rd-50% h-100% w-100%" />
    </div>
    <el-dialog v-model="dialog" title="上传头像" width="30%">
      <el-upload
        drag
        ref="upload"
        accept="image/png, image/jpeg"
        :limit="1"
        :on-exceed="handleExceed"
        :on-success="onSuccessUpload"
        :before-upload="beforeUpload"
        :http-request="startUpload"
        list-type="picture">
        <div class="f-c-c">
          <div class="i-ep-upload-filled w-30 h-30 text-text-secondary"></div>
        </div>
        <div class="text-0.8rem">拖拽文件至此，或点击上传</div>
        <template #tip>
          <div class="text-0.9rem mt-4">仅支持上传 JPG/JPEG、PNG 格式图片，且大小不超过 2MB。</div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.avatar-box {
  .hover-box {
    display: none;
  }

  &:hover {
    .hover-box {
      display: block;
      position: absolute;
      top: 0;
      right: 0;
      background-color: var(--bleuon-bg-color);
      opacity: 0.8;
    }
  }
}
</style>
