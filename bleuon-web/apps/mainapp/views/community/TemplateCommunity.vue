<script lang="ts" setup>
/**
 * @description 社区模板
 * @author zheng
 * @since 2023/10/5
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { TemplateCommunityApi } from "@mainapp/apis";
import { DateUtil } from "@common/utils";
import { ElSelectData } from "@common/data";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";

const scene = ref("全部");
const price = ref("");
const ranking = ref("");

const templateListData = shallowRef<TemplateFlowchartData[]>();

async function fetchData(params?: TemplateFlowchartData) {
  templateListData.value = await TemplateCommunityApi.findAll(params);
  triggerRef(templateListData);
}

function onSceneChange(value: any) {
  fetchData({ scene: value, price: price.value, ranking: ranking.value });
}

function onPriceChange(value: any) {
  fetchData({ scene: scene.value, price: value, ranking: ranking.value });
}

function onOtherChange(value: any) {
  fetchData({ scene: scene.value, price: price.value, ranking: value });
}

const searchVal = ref("");

function onEnter() {
  fetchData({
    scene: scene.value,
    price: price.value,
    ranking: ranking.value,
    fileName: searchVal.value
  });
}

await fetchData();
</script>

<template>
  <div class="template-community slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader active-name="template"></CommonHeader>
    <div class="content f-c-c">
      <div class="wrapper w-75vw mt-5">
        <div class="categories bg-bg-overlay p-5 rd-2 b-b-1 b-b-solid b-border-primary pb-5">
          <div class="f-c-c">
            <div class="f-c-s">
              <div class="w-30 font-500 text-1.2rem">搜索内容</div>
              <div class="w-120">
                <el-input v-model="searchVal" placeholder="请输入关键字搜索" @keyup.enter="onEnter">
                  <template #suffix>
                    <div class="i-tabler-search"></div>
                  </template>
                </el-input>
              </div>
            </div>
          </div>
          <div class="mt-5 mb-5 font-500 text-1.2rem">全部模板</div>
          <div class="file-type f-c-b mt-2">
            <div class="w-8% f-c-s">
              <div class="i-tabler-chart-bubble mr-2"></div>
              场景：
            </div>
            <el-radio-group v-model="scene" class="w-92%" @change="onSceneChange">
              <el-radio v-for="item in ElSelectData.sceneList" :label="item.label">
                {{ item.label }}
              </el-radio>
            </el-radio-group>
          </div>
          <div class="file-price f-c-b mt-2">
            <div class="w-8% f-c-s">
              <div class="i-tabler-chart-bubble mr-2"></div>
              价格：
            </div>
            <el-radio-group v-model="price" class="w-92%" @change="onPriceChange">
              <el-radio label="">全部</el-radio>
              <el-radio v-for="item in ElSelectData.priceList" :label="item.label">
                {{ item.label }}
              </el-radio>
            </el-radio-group>
          </div>
          <div class="file-other f-c-b mt-2">
            <div class="w-8% f-c-s">
              <div class="i-tabler-chart-bubble mr-2"></div>
              其他：
            </div>
            <el-radio-group v-model="ranking" class="w-92%" @change="onOtherChange">
              <el-radio label="">全部</el-radio>
              <el-radio v-for="item in ElSelectData.rankingList" :label="item.label">
                {{ item.label }}
              </el-radio>
            </el-radio-group>
          </div>
        </div>
        <div
          v-if="templateListData"
          class="file-list w-75vw mt-5 mb-5 f-c-s flex-wrap flex-gap-1.25rem">
          <div v-for="item in templateListData" :key="item.id" class="file-item pb-5 px-5 rd-2">
            <router-link :to="'/template/flowchart/' + item.id">
              <img
                :src="item.flowchart.dataUri"
                class="w-100% rd-2 object-fill h-50 cursor-pointer bg-white" />
            </router-link>
            <div class="font-600 text-text-primary mt-2">
              {{ item.flowchart.fileName }}
            </div>
            <div class="mt-2 f-c-s text-text-regular text-0.9rem">
              <div class="f-c-s mr-4">
                <div class="i-tabler-eye mr-2"></div>
                <span>{{ item.views }}</span>
              </div>
              <div class="f-c-s mr-4">
                <div class="i-tabler-copy mr-2"></div>
                <span>{{ item.copies }}</span>
              </div>
            </div>
            <div class="text-text-secondary text-0.8rem mt-2 f-c-s">
              <div class="i-tabler-clock-edit mr-1"></div>
              {{ DateUtil.formatted("MM-dd HH:mm:ss", item.flowchart.modifyDate) }}
            </div>
            <router-link :to="'/u/profile/' + item.flowchart.user.id">
              <div class="text-text-secondary cursor-pointer f-c-s mt-4">
                <img :src="item.flowchart.user.avatar" class="mr-2 rd-50% w-6 h-6" />
                <div>{{ item.flowchart.user.username }}</div>
              </div>
            </router-link>
            <!-- <div class="mt-2">tags</div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.file-item {
  flex: 0 1 calc(20% - 1.25rem);
  --uno: bg-bg-overlay;
  box-shadow: var(--el-box-shadow-lighter);
}
</style>
