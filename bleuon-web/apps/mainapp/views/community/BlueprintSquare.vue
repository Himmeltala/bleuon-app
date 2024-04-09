<script lang="ts" setup>
/**
 * @description 社区模板
 * @author zheng
 * @since 2023/10/5
 */

import { Requests } from "@common/requests";
import { DateUtil } from "@common/utils";
import { ElSelectData } from "@common/data";
import CommonHeader from "@mainapp/fragments/CommonHeader.vue";
import File from "@mainapp/components/File.vue";

const scene = ref("全部");
const price = ref("");
const ranking = ref("");

const mainData = shallowRef<BlueprintFlowchartModel[]>();

async function fetchData(params?: BlueprintFlowchartModel) {
  mainData.value = await Requests.Blueprint.findAll(params);
  triggerRef(mainData);
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
    filename: searchVal.value
  });
}

await fetchData();
</script>

<template>
  <div class="blueprint-square slim-slider h-100vh flow-auto bg-bg-page">
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
          <div class="file-type f-c-b mt-10">
            <div class="w-8% f-c-s">
              <div class="i-tabler-chart-bubble mr-2"></div>
              场景
            </div>
            <el-radio-group v-model="scene" class="w-92%" @change="onSceneChange">
              <el-radio v-for="item in ElSelectData.sceneList" :label="item.label">
                {{ item.label }}
              </el-radio>
            </el-radio-group>
          </div>
          <div class="file-price f-c-b mt-2">
            <div class="w-8% f-c-s">
              <div class="i-tabler-wallet mr-2"></div>
              价格
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
              <div class="i-tabler-arrows-transfer-up mr-2"></div>
              其他
            </div>
            <el-radio-group v-model="ranking" class="w-92%" @change="onOtherChange">
              <el-radio label="">全部</el-radio>
              <el-radio v-for="item in ElSelectData.rankingList" :label="item.label">
                {{ item.label }}
              </el-radio>
            </el-radio-group>
          </div>
        </div>
        <div class="file-list mt-5 f-c-s flex-wrap flex-gap-1.25rem">
          <template v-if="mainData">
            <File
              v-for="item in mainData"
              :key="item.id"
              :path="'/blueprint/flowchart/' + item.id"
              :file-image="item.flowchart.dataUri"
              :options="false">
              <template #footer>
                <div class="f-c-s flex-nowrap mt-4 w-100%">
                  <div class="mr-2 i-tabler-chart-bubble text-theme-primary"></div>
                  <div class="text-0.9rem text-ellipsis line-clamp-1">
                    {{ item.flowchart.filename }}
                  </div>
                </div>
                <router-link :to="'/u/profile/' + item.flowchart.consumer.id">
                  <div class="hover f-c-s text-text-secondary text-0.8rem mt-4">
                    <img :src="item.flowchart.consumer.avatar" class="mr-2 w-6 h-6 rd-50%" />
                    <div>
                      {{ item.flowchart.consumer.username }}
                    </div>
                  </div>
                </router-link>
                <div class="text-0.8rem mt-4">
                  <div class="text-text-regular">
                    创建:{{ DateUtil.formatted(item.createDate, "MM-dd HH:mm:ss") }}
                  </div>
                  <div class="text-text-secondary mt-1">
                    修改:{{ DateUtil.formatted(item.modifyDate, "MM-dd HH:mm:ss") }}
                  </div>
                </div>
              </template>
            </File>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
