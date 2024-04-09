<script lang="ts" setup>
/**
 * @description Flowchart 流程图
 * @author zheng
 * @since 2023/9/9
 */

import "jointjs/css/layout.css";
import "jointjs/css/themes/default.css";
import { dia } from "jointjs";
import { createJointjs } from "@common/lib/jointjs";
import { getDataURI } from "@common/lib/jointjs/utils";
import { Services } from "@mainapp/service";
import { ElDatePickerData, ElSelectData } from "@common/data";
import { ElFormUtil, PreventUtil } from "@common/utils";
import { Requests } from "@common/requests";
import FooterTools from "@mainapp/fragments/flowchart/FooterTools.vue";
import HeaderBottomTools from "@mainapp/fragments/flowchart/HeaderBottomTools.vue";
import HeaderTopTools from "@mainapp/fragments/flowchart/HeaderTopTools.vue";
import Sidebar from "@mainapp/fragments/flowchart/Sidebar.vue";

const route = useRoute();
const paper = shallowRef<dia.Paper>();
const graph = shallowRef<dia.Graph>();
const mainData = ref<FlowchartModel>({});
const texteditor = shallowRef<HTMLInputElement>();

provide(Consts.BLEUON_FLOWCHART_PAPER, paper);
provide(Consts.BLEUON_FLOWCHART_GRAPH, graph);
provide(Consts.BLEUON_FLOWCHART_DATA, mainData);

const lastView = shallowRef({
  view: null,
  model: null
});
const currView = shallowRef(null);
const activeLink = ref(false);
const activeElem = ref(false);
const scale = ref(1);
const offsetX = ref(0);
const offsetY = ref(0);

async function fetchData() {
  mainData.value = await Requests.Flowchart.findById({ id: route.params.id.toString() });
}

await fetchData();

function remodeling() {
  paper.value.freeze();

  if (mainData.value.json) {
    graph.value.fromJSON(JSON.parse(mainData.value.json));
  }

  paper.value.unfreeze();
}

onMounted(() => {
  const jointjs = createJointjs({
    el: "jointjs-content",
    width: "85vw",
    height: "75vh",
    gridSize: mainData.value.gridSize,
    bgColor: mainData.value.bgColor,
    drawGrid: JSON.parse(mainData.value.drawGrid),
    defaultConnector: JSON.parse(mainData.value.connectorDefault),
    defaultRouter: JSON.parse(mainData.value.routerDefault)
  });

  paper.value = jointjs.paper;
  graph.value = jointjs.graph;

  remodeling();

  const events = new Services.Listener.JointJsEventService(
    lastView,
    currView,
    activeLink,
    activeElem,
    scale,
    offsetX,
    offsetY
  );

  paper.value.on({
    "element:pointerclick": view => {
      events.onPointerClickElement(view);
    },
    "element:pointerdblclick": (view, evt) => {
      events.onPointerDoubleClickElement(view, texteditor.value);
    },
    "link:pointerclick": view => {
      events.onPointerClickLink(view);
    },
    "link:pointerdblclick": view => {
      events.onPointerDoubleClickElement(view, texteditor.value);
    },
    "blank:pointerclick": () => {
      events.onPointerClickBlank();
    },
    "blank:mousewheel": evt => {
      events.onMousewheelBlank(evt, paper.value);
    }
  });

  // @ts-ignore
  graph.value.on("change", upgradeThrottle);

  events.onKeydownWithShortcutKey({
    ctrlWithSKey: () =>
      upgradeMetaData(message => {
        if (message.code === 200) {
          ElMessage.success("保存成功！");
        } else {
          ElMessage.error("保存失败！");
        }
      })
  });
});

async function upgradeMetaData(success?: (message: any) => void) {
  const { width, height } = paper.value.getArea();
  const { defaultConnector, defaultRouter, gridSize } = paper.value.options;
  mainData.value = {
    ...mainData.value,
    width,
    height,
    gridSize,
    id: route.params.id.toString(),
    json: JSON.stringify(graph.value.toJSON()),
    connectorDefault: JSON.stringify(defaultConnector),
    routerDefault: JSON.stringify(defaultRouter),
    dataUri: await getDataURI(paper.value)
  };

  Requests.Flowchart.upgrade({ model: mainData.value, config: { ignore200: true } }, success);
}

const upgradeThrottle = PreventUtil.throttle(() => upgradeMetaData(), 300);

const shareDialogVisible = ref(false);
const shareFormRef = ref<FormInstance>();
const shareFormRules = reactive<FormRules<any>>({
  deadShareDate: [
    {
      type: "date",
      required: true,
      message: "请选择一个截止日期",
      trigger: "change"
    },
    {
      type: "date",
      required: true,
      message: "请选择一个截止日期",
      trigger: "blur"
    }
  ]
});

function confirmShare() {
  ElFormUtil.validate(shareFormRef.value, () => {
    mainData.value.isShare = 1;
    Requests.Flowchart.upgrade({ model: mainData.value, config: { ignore200: true } }, message => {
      if (message.code === 200) {
        ElMessage.success("分享成功！");
      } else {
        mainData.value.isShare = 0;
        ElMessage.error("分享失败！");
      }
    });
  });
}

function cancelShare() {
  mainData.value.isShare = 0;
  Requests.Flowchart.upgrade({ model: mainData.value, config: { ignore200: true } }, message => {
    if (message.code === 200) {
      ElMessage.success("取消分享成功！");
    } else {
      mainData.value.isShare = 1;
      ElMessage.error("取消分享失败！");
    }
  });
}

const releaseDialogVisible = ref(false);
const releaseFormRef = ref<FormInstance>();
const releaseFormData = reactive({
  description: "",
  scene: "全部",
  price: "免费",
  tags: ""
});
const releaseFormRules = reactive<FormRules<any>>({
  description: [
    {
      required: true,
      message: "请输入文件描述",
      trigger: "blur"
    }
  ],
  scene: [
    {
      required: true,
      message: "请选择一个适用场景",
      trigger: "blur"
    }
  ],
  price: [
    {
      required: true,
      message: "请选择一个收费标准",
      trigger: "blur"
    }
  ]
});

const releaseTagList = ref([]);

function confirmRelease() {
  ElFormUtil.validate(releaseFormRef.value, () => {
    releaseFormData.tags = JSON.stringify(releaseTagList.value);
    Requests.Flowchart.release({ flowchartId: mainData.value.id, ...releaseFormData }, () => {
      mainData.value.isPublic = 1;
    });
  });
}

function cancelRelease() {
  Requests.Flowchart.cancelRelease({ flowchartId: mainData.value.id }, () => {
    mainData.value.isPublic = 0;
  });
}
</script>

<template>
  <div class="bleuon__flowchart-container h-100vh">
    <div
      class="bleuon__flowchart-header h-22vh border-border-primary border-b-1 border-b-solid bg-bg-primary px-4 py-4">
      <HeaderTopTools :data="mainData" class="mb-4" @change="upgradeMetaData">
        <template #tools>
          <el-tooltip content="分享">
            <div
              class="hover i-tabler-share mr-4"
              @click="shareDialogVisible = !shareDialogVisible"></div>
          </el-tooltip>
          <el-tooltip content="发布和公开">
            <div
              class="hover i-tabler-location-share"
              @click="releaseDialogVisible = !releaseDialogVisible"></div>
          </el-tooltip>
        </template>
      </HeaderTopTools>
      <HeaderBottomTools
        :curr-view="currView"
        :last-view="lastView"
        :active-elem="activeElem"
        :active-link="activeLink" />
    </div>
    <div class="bleuon__flowchart-box f-c-b">
      <Sidebar class="h-78vh" />
      <div class="content">
        <div class="wrapper relative">
          <div id="jointjs-content"></div>
          <div class="extra-tools">
            <input ref="texteditor" class="bleuon__flowchart-input absolute hidden" type="text" />
          </div>
        </div>
        <FooterTools v-model:scale="scale" class="h-3vh" />
      </div>
    </div>
    <el-dialog v-model="shareDialogVisible" title="分享流程图" width="40%">
      <el-form
        ref="shareFormRef"
        :model="mainData"
        :rules="shareFormRules"
        label-position="right"
        label-width="100px">
        <el-form-item label="状态">
          <div v-if="!mainData.isShare">流程图没有分享，请创建分享链接</div>
          <div v-else>流程图已经分享，点击链接浏览</div>
        </el-form-item>
        <el-form-item v-if="mainData.isShare" label="链接地址">
          <router-link :to="'/share/flowchart/' + mainData.id">
            <el-link type="primary">
              http://localhost:5173/#/share/flowchart/{{ mainData.id }}
            </el-link>
          </router-link>
        </el-form-item>
        <el-form-item label="截止日期" prop="deadShareDate">
          <el-date-picker
            v-model="mainData.deadShareDate"
            :shortcuts="ElDatePickerData.futureShortcuts"
            type="datetime" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span>
          <el-button :disabled="!!mainData.isShare" type="primary" @click="confirmShare">
            <template #icon>
              <div class="i-tabler-check"></div>
            </template>
            确定公开
          </el-button>
          <el-button :disabled="!mainData.isShare" @click="cancelShare">
            <template #icon>
              <div class="i-tabler-x"></div>
            </template>
            取消公开
          </el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="releaseDialogVisible" title="发布和公开流程图" width="40%">
      <el-form
        v-if="!mainData.isPublic"
        ref="releaseFormRef"
        :model="releaseFormData"
        :rules="releaseFormRules"
        label-position="left"
        label-width="90px">
        <el-form-item label="文件描述" prop="description">
          <el-input v-model="releaseFormData.description" />
        </el-form-item>
        <el-form-item label="场景" prop="scene">
          <el-select v-model="releaseFormData.scene" placeholder="请输入适用场景">
            <el-option
              v-for="item in ElSelectData.sceneList"
              :key="item.label"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-select v-model="releaseFormData.price" placeholder="请输入收费标准">
            <el-option
              v-for="item in ElSelectData.priceList"
              :key="item.label"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <EnterTags width="w-100%" v-model="releaseTagList"></EnterTags>
        </el-form-item>
      </el-form>
      <div v-else class="text-center text-1.2rem font-bold">
        已经发布到社区模板，若需要你可以取消发布
      </div>
      <template #footer>
        <span>
          <el-button
            v-if="!mainData.isPublic"
            @click="releaseDialogVisible = !releaseDialogVisible">
            <template #icon>
              <div class="i-tabler-x"></div>
            </template>
            取消
          </el-button>
          <el-button v-else type="danger" @click="cancelRelease">
            <template #icon>
              <div class="i-tabler-x"></div>
            </template>
            取消发布
          </el-button>
          <el-button :disabled="!!mainData.isPublic" type="primary" @click="confirmRelease">
            <template #icon>
              <div class="i-tabler-check"></div>
            </template>
            <span v-if="!mainData.isPublic">确定发布</span>
            <span v-else>已经发布</span>
          </el-button>
        </span>
      </template>
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
      --uno: transition-all-300;
    }
  }

  &:hover {
    .joint-port {
      circle {
        fill: white;
        stroke: black;
        stroke-width: 1px;
        stroke-dasharray: none;
      }
    }
  }
}

.active [joint-selector="line"] {
  stroke: #4666e5;
}
</style>
