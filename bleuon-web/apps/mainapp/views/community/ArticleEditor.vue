<script setup lang="ts">
/**
 * @description 帖子编辑器（发表和编辑）
 * @author zheng
 * @since 2023/10/20
 */

import { Requests } from "@common/requests";
import { ElFormUtil, TextUtil } from "@common/utils";
import { ElSelectData } from "@common/data";
import CommonHeader from "@mainapp/fragments/CommonHeader.vue";

const route = useRoute();
const formData = ref<ArticleModel>({
  title: "",
  content: "",
  desc: "",
  titleTag: "[]",
  descTag: "[]",
  descImgs: "[]",
  type: ""
});

const titleTagToJson: any = computed({
  set(value) {
    formData.value.titleTag = JSON.stringify(value);
  },
  get() {
    return JSON.parse(formData.value.titleTag);
  }
});

const descTagToJson: any = computed({
  set(value) {
    formData.value.descTag = JSON.stringify(value);
  },
  get() {
    return JSON.parse(formData.value.descTag);
  }
});

const descImgsToJson: any = computed({
  set(value) {
    formData.value.descImgs = JSON.stringify(value);
  },
  get() {
    return JSON.parse(formData.value.descImgs);
  }
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
  ],
  type: [
    {
      required: true,
      message: "请给帖子附上类型！",
      trigger: "blur"
    }
  ]
});
const formEl = ref();
const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);

function triggValidate() {
  formEl.value.validateField("content");
}

function startUploadDescImgs(formData: FormData) {
  formData.append("filepath", "/article/desc");
  return Requests.File.uploadImageFile(formData);
}

function removeDescImgItem(str: string, callback: Function) {
  Requests.File.deleteImageFile(str, () => callback());
}

function uploadImageFile(formData: FormData) {
  formData.append("filepath", "/article/content");
  return Requests.File.uploadImageFile(formData);
}

function startCreateArticle() {
  ElFormUtil.validate(formEl.value, () => {
    formData.value.consumerId = token.id;
    Requests.Discussion.addArticle(formData.value, () => {
      location.reload();
    });
  });
}

const classicCkEditor = ref();
const multiImgsUpload = ref();
const descEnterTags = ref();
const titleEnterTags = ref();

function repostArticle() {
  ElFormUtil.validate(formEl.value, () => {
    formData.value.consumerId = token.id;
    Requests.Discussion.upgradeArticle({ model: formData.value });
  });
}

const viewType = ref();

onMounted(async () => {
  const articleId = route.query.id.toString();
  viewType.value = route.query.type.toString();

  if (viewType.value === "edit") {
    formData.value = await Requests.Discussion.findDetailByCriteria({ articleId });
    classicCkEditor.value.initalData(formData.value.content);
    multiImgsUpload.value.initalData(JSON.parse(formData.value.descImgs));
    descEnterTags.value.initalData(JSON.parse(formData.value.descTag));
    titleEnterTags.value.initalData(JSON.parse(formData.value.titleTag));
  }
});
</script>

<template>
  <div class="article-editor h-100vh flow-hidden bg-bg-page">
    <CommonHeader></CommonHeader>
    <div class="content py-5 slim-slider flow-auto f-s-c">
      <div class="wrapper w-60vw">
        <div class="bg-bg-overlay rd-2 py-5 px-10">
          <div class="f-c-b mb-10">
            <div class="font-bold text-1.2rem">帖子编辑器</div>
            <div>
              <el-button plain @click="$router.back()">返回社区</el-button>
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
                  ref="classicCkEditor"
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
            <el-form-item label="标题 Tag">
              <EnterTags
                ref="titleEnterTags"
                width="w-100%"
                v-model="titleTagToJson"
                :content-min="2"
                :advance="[{ value: '涨知识' }, { value: '图文教程' }, { value: '问题咨询' }]"
                :limit="5" />
            </el-form-item>
            <el-form-item label="描述 Tag">
              <EnterTags
                ref="descEnterTags"
                width="w-100%"
                :content-min="2"
                :advance="[
                  { value: '关系图' },
                  { value: '鱼骨图' },
                  { value: '甘特图' },
                  { value: '时间轴' }
                ]"
                v-model="descTagToJson"
                :limit="5" />
            </el-form-item>
            <el-form-item label="帖子封面">
              <MultiImgsUpload
                ref="multiImgsUpload"
                v-model="descImgsToJson"
                :remove-file="removeDescImgItem"
                :start-upload="startUploadDescImgs" />
            </el-form-item>
            <el-form-item label="帖子类型" prop="type">
              <el-select v-model="formData.type" placeholder="选择帖子类型">
                <el-option
                  v-for="item in ElSelectData.articleTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <div class="f-c-e w-100%">
                <div class="mr-4">
                  <el-button plain @click="$router.back()">返回社区</el-button>
                </div>
                <el-button v-if="viewType === 'edit'" type="primary" @click="repostArticle">
                  重新发表
                </el-button>
                <el-button v-else type="primary" @click="startCreateArticle">发表帖子</el-button>
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.article-editor {
  background-image: url(https://www.miyoushe.com/_nuxt/img/background.cd0a312.png);
  background-position: 0 5rem;
  background-repeat: no-repeat;
  background-size: 100%;
}

.content {
  height: calc(100vh - 5rem);
}
</style>
