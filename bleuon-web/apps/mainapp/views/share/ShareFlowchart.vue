<script lang="ts" setup>
/**
 * @description 分享的 Flowchart 流程图
 * @author zheng
 * @since 2023/9/30
 * @link https://github.com/himmelbleu/bleuon-app
 */

// jointjs css
import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";

import { dia, initJointJs } from "@mainapp/lib";
import { FlowchartApi } from "@mainapp/apis";
import { ListenerService } from "@mainapp/service/diagraming/flowchart";
import * as Data from "@mainapp/data/diagraming/flowchart";
import { DateUtil } from "@common/utils";

// components
import HeaderToolsBottom from "@mainapp/components/diagraming/flowchart/HeaderToolsBottom.vue";
import HeaderToolsTop from "@mainapp/components/diagraming/flowchart/HeaderToolsTop.vue";
import FooterTools from "@mainapp/components/diagraming/flowchart/FooterTools.vue";

const route = useRoute();
const router = useRouter();
const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();

const mainDataSource = ref<FlowchartModel>({});
const texteditor = shallowRef<HTMLInputElement>();

provide(KeyVals.BLEUON_FLOWCHART_PAPER, paper);
provide(KeyVals.BLEUON_FLOWCHART_GRAPH, graph);
provide(KeyVals.BLEUON_FLOWCHART_DATA, mainDataSource);
const token = localStorage.getToken(KeyVals.MAINAPP_TOKEN_KEY);

async function fetchData() {
  const data = await FlowchartApi.findIsShare({ id: route.params.id.toString() }, () => {
    router.back();
  });
  mainDataSource.value = data;
}

function remodeling() {
  paper.value.freeze();

  if (mainDataSource.value.json) {
    graph.value.fromJSON(JSON.parse(mainDataSource.value.json));
  }

  paper.value.options.defaultConnector = JSON.parse(mainDataSource.value.connectorDefault);
  paper.value.options.defaultRouter = JSON.parse(mainDataSource.value.routerDefault);

  paper.value.unfreeze();

  paper.value.on({
    "element:pointerclick": view => {
      ListenerService.onPointerClickElement(view);
    },
    "element:pointerdblclick": view => {
      ListenerService.onPointerDbclickElement(view, texteditor.value);
    },
    "link:pointerclick": view => {
      ListenerService.onPointerClickLink(view);
    },
    "link:pointerdblclick": view => {
      ListenerService.onPointerDbclickElement(view, texteditor.value);
    },
    "blank:pointerclick": () => {
      ListenerService.onPointerClickBlank();
    },
    "blank:mousewheel": evt => {
      ListenerService.onMousewheelBlank(evt, paper.value);
    }
  });
}

onMounted(() => {
  const jointjs = initJointJs({
    el: "bleuon__flowchart-content",
    width: "100vw",
    height: "75vh",
    gridSize: mainDataSource.value.gridSize,
    bgColor: mainDataSource.value.bgColor,
    drawGrid: JSON.parse(mainDataSource.value.drawGrid)
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;

  remodeling();
});

const dialogVisible = ref(false);

function replicate() {
  mainDataSource.value.fileName = mainDataSource.value.fileName;
  FlowchartApi.replicate(mainDataSource.value, res => ElMessage.success(res.message));
}

await fetchData();
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-bg-primary px-4 py-4">
      <HeaderToolsTop :data="mainDataSource" class="mb-4">
        <template #tools>
          <el-tooltip content="分享">
            <div class="hover i-tabler-share mr-4" @click="dialogVisible = !dialogVisible"></div>
          </el-tooltip>
          <el-tooltip v-if="token" content="导入模板">
            <div>
              <div class="hover i-tabler-file-import" @click="replicate"></div>
            </div>
          </el-tooltip>
        </template>
      </HeaderToolsTop>
      <HeaderToolsBottom
        :is-clicked-element="Data.isClickedElement.value"
        :is-clicked-link="Data.isClickedLink.value" />
    </div>
    <div class="bleuon__flowchart-wrapper f-c-b">
      <div class="left"></div>
      <div class="right">
        <div id="bleuon__flowchart-content"></div>
        <FooterTools class="h-3vh" />
      </div>
      <div class="bleuon__flowchart-extra">
        <input ref="texteditor" class="bleuon__flowchart-input absolute hidden" type="text" />
      </div>
    </div>
    <el-dialog v-model="dialogVisible" title="分享流程图" width="40%">
      <el-form ref="shareFormRef" label-position="right" label-width="100px">
        <el-form-item label="状态">流程图已经公开，点击链接浏览</el-form-item>
        <el-form-item label="链接地址">
          <router-link :to="'/share/flowchart/' + mainDataSource.id">
            <el-link type="primary">
              http://localhost:5173/#/share/flowchart/{{ mainDataSource.id }}
            </el-link>
          </router-link>
        </el-form-item>
        <el-form-item label="截止日期">
          {{ DateUtil.formatted(mainDataSource.deadShareDate) }}
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<style lang="scss">
.joint-element {
  .joint-port {
    circle {
      fill: transparent;
      stroke: transparent;
      stroke-width: 0;
      stroke-dasharray: none;
    }
  }
}
</style>
