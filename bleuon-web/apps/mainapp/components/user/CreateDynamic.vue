<script setup lang="ts">
/**
 * @description 发布动态
 * @author zheng
 * @since 2023/10/10
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

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
    const formdata = new FormData();
    formdata.append("file", file);
    UserApi.uploadCkEditorImage(formdata).then(res => {
      resolve({
        default: res.data.data
      });
    });
  }

  abort() {
    // @ts-ignore
    server.abortUpload();
  }
}

const emits = defineEmits<{
  (event: "submit", value: string): void;
}>();

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
    })
    .catch(error => {
      ElMessage.error(error);
    });
});

function startSubmit() {
  emits("submit", ckeditor.getData());
}
</script>

<template>
  <div>
    <div ref="editor"></div>
    <div class="f-c-e mt-5">
      <el-button type="primary" @click="startSubmit">发布动态</el-button>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
