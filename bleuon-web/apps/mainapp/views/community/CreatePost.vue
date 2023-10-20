<script setup lang="ts">
/**
 * @description 发表帖子
 * @author zheng
 * @since 2023/10/20
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { DiscussionAPI, FileAPI } from "@mainapp/apis";
import { TextUtil } from "@common/utils";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";
import ClassicCkEditor from "@mainapp/components/ClassicCkEditor.vue";

const formData = reactive<PostModel>({
  title: "",
  content: "",
  desc: "",
  titleTag: "",
  descTag: "",
  descImgs: "",
  type: ""
});
const formRules = reactive({
  title: [
    {
      required: true,
      message: "请给帖子附上标题！",
      trigger: "blur"
    }
  ],
  content: [
    {
      validator: (rule: any, value: any, callback: any) => {
        const len = TextUtil.strlen(value);
        if (len < 10) {
          callback("帖子内容必须大于 10 个字！");
        } else {
          callback();
        }
      },
      required: true
    }
  ],
  desc: [
    {
      required: true,
      message: "请给帖子附上描述！",
      trigger: "blur"
    }
  ]
});
const formEl = ref();
const token = localStorage.getToken(KeyVals.MAINAPP_TOKEN_KEY);

function triggValidate() {
  formEl.value.validateField("content");
}

function uploadImageFile(formData: FormData) {
  return FileAPI.uploadImageFile(formData);
}
</script>

<template>
  <div class="create-post h-100vh flow-hidden bg-bg-page">
    <CommonHeader></CommonHeader>
    <div class="content py-5 slim-slider flow-auto f-s-c">
      <div class="wrapper w-60vw">
        <div class="bg-bg-overlay rd-2 py-5 px-10">
          <div class="f-c-b mb-10">
            <div class="font-bold text-1.2rem">编辑帖子</div>
            <div>
              <el-button size="small" type="danger" plain @click="$router.back()">返回</el-button>
            </div>
          </div>
          <el-form
            ref="formEl"
            label-position="left"
            label-width="85px"
            :rules="formRules"
            :model="formData">
            <el-form-item label="标题" prop="title">
              <el-input v-model="formData.title" placeholder="请输入标题"></el-input>
            </el-form-item>
            <el-form-item label="内容" prop="content">
              <div class="w-100%">
                <ClassicCkEditor
                  @change="triggValidate"
                  v-model="formData.content"
                  :imgae-uploader="uploadImageFile" />
              </div>
            </el-form-item>
            <el-form-item label="帖子描述" prop="desc">
              <el-input
                v-model="formData.desc"
                type="textarea"
                :rows="2"
                placeholder="请输入帖子描述"></el-input>
            </el-form-item>
            <el-form-item label="标题 Tag"> </el-form-item>
            <el-form-item label="描述 Tag"> </el-form-item>
            <el-form-item label="帖子封面"> </el-form-item>
            <el-form-item label="帖子类型"> </el-form-item>
            <el-form-item>
              <div class="f-c-e w-100%">
                <el-button type="primary">发表帖子</el-button>
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.create-post {
  background-image: url(https://www.miyoushe.com/_nuxt/img/background.cd0a312.png);
  background-position: 0 5rem;
  background-repeat: no-repeat;
  background-size: 100%;
}

.content {
  height: calc(100vh - 5rem);
}
</style>
