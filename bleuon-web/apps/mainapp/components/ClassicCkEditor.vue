<script setup lang="ts">
/**
 * @description Classic Ckeditor 富文本编辑器
 * @author zheng
 * @since 2023/10/12
 * @link https://github.com/himmelbleu/bleuon-app
 */

import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { AxiosResponse } from "axios";

const props = defineProps({
  modelValue: {
    type: String,
    required: true
  },
  uploadImg: {
    type: Function as PropType<(formData: FormData) => Promise<AxiosResponse<R>>>,
    required: true
  }
});

const emits = defineEmits<{
  (event: "update:modelValue", value: string): void;
}>();

class UploadAdapter {
  loader: any;

  constructor(loader: any) {
    this.loader = loader;
  }

  upload() {
    return this.loader.file.then(
      (file: any) =>
        new Promise((resolve, reject) => {
          this.uploadFile(file, resolve);
        })
    );
  }

  uploadFile(file: any, resolve: any) {
    const formData = new FormData();
    formData.append("file", file);
    props.uploadImg(formData).then(({ data }) => {
      resolve({
        default: data.data
      });
    });
  }

  abort() {
    // @ts-ignore
    server.abortUpload();
  }
}

const editor = ref<HTMLDivElement>();
let ckeditor: ClassicEditor;

onMounted(() => {
  ClassicEditor.create(editor.value, {
    heading: {
      options: [
        { model: "paragraph", title: "Paragraph", class: "ck-heading_paragraph" },
        { model: "heading1", view: "h1", title: "Heading 1", class: "ck-heading_heading1" },
        { model: "heading2", view: "h2", title: "Heading 2", class: "ck-heading_heading2" },
        { model: "heading3", view: "h3", title: "Heading 3", class: "ck-heading_heading3" }
      ]
    }
  })
    .then(ck => {
      ckeditor = ck;
      ckeditor.plugins.get("FileRepository").createUploadAdapter = loader => {
        return new UploadAdapter(loader);
      };

      ckeditor.model.document.on("change:data", () => {
        emits("update:modelValue", ckeditor.getData());
      });
    })
    .catch(error => {
      ElMessage.error(error);
    });
});
</script>

<template>
  <div class="classic-ckeditor">
    <div ref="editor"></div>
  </div>
</template>

<style scoped lang="scss"></style>
