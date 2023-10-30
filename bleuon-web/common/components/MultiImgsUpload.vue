<script setup lang="ts">
/**
 * @description 多文件上传组件
 * @author zheng
 * @since 2023/10/20
 */

import { genFileId } from "element-plus";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";

const props = defineProps({
  modelValue: {
    type: Array as PropType<string[]>,
    required: true
  },
  startUpload: {
    type: Function as PropType<(formData: FormData) => XMLHttpRequest | Promise<unknown>>,
    required: true
  },
  removeFile: {
    type: Function as PropType<(item: string, callback: Function) => void>
  },
  limit: {
    type: Number,
    default: 3
  }
});

const emits = defineEmits<{
  (event: "update:modelValue", urls: string[]): void;
}>();

function initalData(data: string[]) {
  urls.value = data;
}

defineExpose({ initalData });

const urls = ref<string[]>([]);
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
  urls.value.push(response.data.data);
  emits("update:modelValue", urls.value);
};

function startUpload(options: any): XMLHttpRequest | Promise<unknown> {
  const formData = new FormData();
  formData.append("file", options.file);
  return props.startUpload(formData);
}

function deleteFileItem(item: string, index: number) {
  props.removeFile(item, () => {
    urls.value.splice(index, 1);
  });
}

function openUploadDialog() {
  if (urls.value.length === props.limit) {
    ElMessage.error(`最多只能上传 ${props.limit} 张图片`);
  } else {
    dialog.value = !dialog.value;
  }
}
</script>

<template>
  <div class="multi-imgs-upload">
    <div class="f-c-s">
      <div
        class="hover f-c-c b-1 b-solid b-border-primary h-30 w-30 mr-2 rd-2"
        @click="openUploadDialog">
        点击上传图片
      </div>
      <div class="relative h-30 w-30 mr-2" v-for="(item, index) in urls">
        <div v-if="urls.length" class="absolute top-2 right-2">
          <div class="bg-white rd-2 opacity-80" @click="deleteFileItem(item, index)">
            <div class="hover i-tabler-x"></div>
          </div>
        </div>
        <img :src="item" class="object-cover rd-2 h-100% w-100%" />
      </div>
    </div>
    <el-dialog v-model="dialog" title="上传图片" width="30%">
      <el-upload
        drag
        ref="upload"
        accept="image/png, image/jpeg"
        :limit="limit"
        :on-exceed="handleExceed"
        :on-success="onSuccessUpload"
        :before-upload="beforeUpload"
        :http-request="startUpload">
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

<style scoped lang="scss"></style>
