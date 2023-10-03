<script setup lang="ts">
/**
 * @description 文件
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

const props = defineProps({
  path: {
    type: String,
    required: true
  },
  fileImage: {
    type: String
  },
  fileName: {
    type: String,
    required: true
  },
  modifyDate: {
    type: String,
    required: true
  },
  createDate: {
    type: String
  },
  isReset: {
    type: Boolean,
    default: true
  }
});

const emits = defineEmits(["download", "clone", "delete", "reset"]);
</script>

<template>
  <div class="file relative">
    <el-dropdown :teleported="false">
      <div class="options absolute top-7 left-2">
        <div class="options__icon f-c-c cursor-pointer w-10 h-6 rd-2 bg-#383838cc">
          <div class="i-tabler-dots text-white"></div>
        </div>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>
            <div class="f-c-s" @click="$router.push(path)">
              <div class="i-tabler-eye mr-2"></div>
              预览
            </div>
          </el-dropdown-item>
          <el-dropdown-item v-if="isReset">
            <div class="f-c-s" @click="$emit('reset')">
              <div class="i-tabler-edit mr-2"></div>
              重命名
            </div>
          </el-dropdown-item>
          <el-dropdown-item>
            <div class="f-c-s" @click="$emit('download')">
              <div class="i-tabler-download mr-2"></div>
              下载
            </div>
          </el-dropdown-item>
          <el-dropdown-item>
            <div class="f-c-s" @click="$emit('clone')">
              <div class="i-tabler-copy mr-2"></div>
              复制
            </div>
          </el-dropdown-item>
          <el-dropdown-item>
            <div class="f-c-s" @click="$emit('delete')">
              <div class="i-tabler-trash-x mr-2"></div>
              删除
            </div>
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <div class="image h-50 rd-2" @click="$router.push(path)">
      <img
        v-if="fileImage"
        class="w-100% h-100% rd-2 object-fill cursor-pointer bg-white"
        :src="fileImage" />
      <div v-else class="w-100% h-100% rd-2 cursor-pointer bg-white"></div>
    </div>
    <div class="f-c-s flex-nowrap mt-4 w-100%">
      <div class="mr-2 i-tabler-chart-bubble text-theme-primary"></div>
      <div class="text-0.9rem text-ellipsis line-clamp-1">{{ fileName }}</div>
    </div>
    <div class="text-text-secondary text-0.8rem mt-2 f-c-s">
      <div class="i-tabler-clock-edit mr-1"></div>
      更新于 {{ modifyDate }}
    </div>
    <div v-if="createDate" class="text-text-secondary text-0.8rem mt-1 f-c-s">
      <div class="i-tabler-clock-edit mr-1"></div>
      创建于 {{ createDate }}
    </div>
  </div>
</template>

<style scoped lang="scss">
.file {
  flex: 0 1 15% !important;

  .options {
    display: none;
  }

  &:hover {
    .options {
      display: block;

      .options__icon:hover {
        --uno: bg-#383838f2;
      }
    }
  }

  .image {
    box-shadow: 0 1px 0 rgba(0, 0, 0, 0.16);
  }
}
</style>
