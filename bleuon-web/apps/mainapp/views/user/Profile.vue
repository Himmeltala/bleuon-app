<script lang="ts" setup>
/**
 * @description 个人资料中心
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";
import { DateUtil } from "@common/utils";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";
import CreateDynamic from "@mainapp/components/user/CreateDynamic.vue";

const route = useRoute();
const token = localStorage.getToken<TokenR>(KeyVals.MAINAPP_TOKEN_KEY);
const formData = ref(await UserApi.findById(`${route.params.id}`));
const activeName = ref<"createDynamic" | "dynamicList">("createDynamic");

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab);
};

function submitDynamic(value: string) {
  console.log(value);
  // 调用接口发布动态
}

const dynamicList = ref(await UserApi.findAllDynamic(`${route.params.id}`));

function diggDynamic(item: DynamicData) {
  item.digg += 1;
  UserApi.upgradeDynamic({ digg: item.digg, id: item.id }, () => {
    ElMessage.success("支持成功！");
  });
}

function buryDynamic(item: DynamicData) {
  item.bury += 1;
  UserApi.upgradeDynamic({ bury: item.bury, id: item.id }, () => {
    ElMessage.success("反对成功！");
  });
}

function eraseDynamic(item: DynamicData, index: number) {
  UserApi.deleteDynamic({ id: item.id }, () => {
    dynamicList.value.splice(index, 1);
  });
}
</script>

<template>
  <div class="profile slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader active-name="personal"></CommonHeader>
    <div class="f-s-b py-20 px-5 mx-50 mt-5 rd-2 bg-bg-overlay">
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
          <el-tab-pane v-if="token.id === formData.id" label="发布动态" name="createDynamic">
            <CreateDynamic @submit="submitDynamic"></CreateDynamic>
            <template #label>
              <span :class="{ 'font-bold': activeName === 'createDynamic' }">发布动态</span>
            </template>
          </el-tab-pane>
          <el-tab-pane :lazy="true" label="动态列表" name="dynamicList">
            <div v-if="dynamicList">
              <div
                class="b-b-1 b-border-primary b-b-solid pb-5 f-s-s mt-5"
                v-for="(item, index) in dynamicList"
                :key="item.id">
                <div class="mr-15">
                  <img class="w-15 h-15 rd-50%" :src="formData.avatar" />
                </div>
                <div>
                  <div class="text-0.9rem text-text-regular">
                    {{ formData.username }}
                  </div>
                  <div class="text-1.2rem mt-2 text-text-regular">
                    {{ item.title }}
                  </div>
                  <div class="mt-4">
                    <div class="mt-4">{{ item.content }}</div>
                  </div>
                  <div class="f-c-s mt-6 text-text-secondary text-0.9rem">
                    <div class="mr-15 hover f-c-c" @click="diggDynamic(item)">
                      <div class="i-tabler-thumb-up mr-1"></div>
                      {{ item.digg }}
                    </div>
                    <div class="hover f-c-c" @click="buryDynamic(item)">
                      <div class="i-tabler-thumb-down mr-1"></div>
                      {{ item.bury }}
                    </div>
                    <div class="ml-15 f-c-s">
                      <div class="i-tabler-clock mr-1"></div>
                      {{ DateUtil.formatted(item.createDate) }}
                    </div>
                    <div class="ml-15" v-if="formData.id == token.id">
                      <el-button
                        @click="eraseDynamic(item, index)"
                        type="danger"
                        size="small"
                        text
                        bg>
                        删除
                      </el-button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <template #label>
              <span :class="{ 'font-bold': activeName === 'dynamicList' }">动态列表</span>
            </template>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
