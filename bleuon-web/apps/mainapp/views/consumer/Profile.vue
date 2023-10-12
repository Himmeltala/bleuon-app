<script lang="ts" setup>
/**
 * @description 个人资料中心
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { ConsumerApi, FileApi } from "@mainapp/apis";
import { DateUtil } from "@common/utils";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";
import ClassicCkEditor from "@mainapp/components/ClassicCkEditor.vue";

const route = useRoute();
const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);
const formData = ref(await ConsumerApi.findById(`${route.params.id}`));
const activeName = ref<"createDynamic">("createDynamic");

const handleClick = (tab: TabsPaneContext) => {
  console.log(tab);
};

const dynamicValue = ref("");
const dynamicList = ref([]);

async function fetchDynamicList() {
  dynamicList.value = await ConsumerApi.findAllDynamicByCriteria({
    sequences: [{ isAsc: false, col: "create_date" }],
    consumerId: `${route.params.id}`
  });
}

function uploadDynamicImg(formData: FormData) {
  formData.append("path", "/dynamic");
  return FileApi.uploadCkEditorImage(formData);
}

function commitDynamic() {
  ConsumerApi.addDynamic({ content: dynamicValue.value }, async () => {
    await fetchDynamicList();
  });
}

function diggDynamic(item: DynamicModel) {
  item.digg += 1;
  ConsumerApi.upgradeDynamic({ digg: item.digg, id: item.id }, () => {
    ElMessage.success("支持成功！");
  });
}

function buryDynamic(item: DynamicModel) {
  item.bury += 1;
  ConsumerApi.upgradeDynamic({ bury: item.bury, id: item.id }, () => {
    ElMessage.success("反对成功！");
  });
}

function deleteDynamic(item: DynamicModel, index: number) {
  ConsumerApi.deleteDynamic({ id: item.id }, () => {
    dynamicList.value.splice(index, 1);
  });
}

await fetchDynamicList();
</script>

<template>
  <div class="profile slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader active-name="personal"></CommonHeader>
    <div class="f-s-b py-20 px-20 mx-50 mt-5 rd-2 bg-bg-overlay">
      <div class="f-c-c">
        <router-link to="/u/setting">
          <img :src="formData.avatar" class="cursor-pointer rd-50% h-30 w-30" />
        </router-link>
        <div class="ml-10">
          <div class="username f-c-s mb-5">
            <div class="font-bold text-1.5rem mr-10">{{ formData.username }}</div>
            <router-link to="/u/setting">
              <el-button>编辑资料</el-button>
            </router-link>
          </div>
          <div class="usertags mb-5">
            <el-tag v-if="formData.position" class="mr-4" type="warning">
              {{ formData.position }}
            </el-tag>
            <el-tag v-if="formData.company">{{ formData.company }}</el-tag>
          </div>
          <div class="signature text-text-secondary">
            {{ formData.signature || "这个人很懒，什么也没有留下" }}
          </div>
        </div>
      </div>
      <div class="f-c-b">
        <div class="text-center mr-10">
          <div class="font-bold text-1.5rem mb-2">0</div>
          <div>浏览量</div>
        </div>
        <div class="text-center mr-10">
          <div class="font-bold text-1.5rem mb-2">0</div>
          <div>点赞量</div>
        </div>
      </div>
    </div>
    <div class="mt-5 mb-5 px-50">
      <div class="bg-bg-overlay px-5 pb-5 rd-2">
        <el-tabs class="min-h-60vh" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="发布动态" name="createDynamic">
            <div class="mt-5 mb-10">
              <ClassicCkEditor
                v-if="token.id === formData.id"
                v-model="dynamicValue"
                :upload-img="uploadDynamicImg"></ClassicCkEditor>
              <div class="f-c-e mt-2">
                <el-button type="primary" @click="commitDynamic">发表动态</el-button>
              </div>
            </div>
            <div class="font-bold text-text-regular">动态列表</div>
            <div v-if="dynamicList" class="mt-10">
              <div
                class="b-b-1 b-border-primary b-b-solid pb-5 f-s-s mt-5"
                v-for="(item, index) in dynamicList"
                :key="item.id">
                <div class="mr-10">
                  <img class="w-15 h-15 rd-50%" :src="formData.avatar" />
                </div>
                <div>
                  <div>
                    <div class="text-text-regular">
                      {{ formData.username }}
                    </div>
                    <div class="mt-1 f-c-s text-0.8rem text-text-regular">
                      <div class="i-tabler-clock mr-1"></div>
                      {{ DateUtil.formatted(item.createDate) }}
                    </div>
                  </div>
                  <div class="mt-4" v-html="item.content"></div>
                  <div class="f-c-s mt-6 text-text-secondary text-0.9rem">
                    <div class="mr-15 hover f-c-c" @click="diggDynamic(item)">
                      <div class="i-tabler-thumb-up mr-1"></div>
                      {{ item.digg }}
                    </div>
                    <div class="mr-15 hover f-c-c" @click="buryDynamic(item)">
                      <div class="i-tabler-thumb-down mr-1"></div>
                      {{ item.bury }}
                    </div>
                    <div v-if="formData.id == token.id">
                      <el-button
                        @click="deleteDynamic(item, index)"
                        type="danger"
                        size="small"
                        text>
                        删除
                      </el-button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <template #label>
              <span :class="{ 'font-bold': activeName === 'createDynamic' }">发布动态</span>
            </template>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
