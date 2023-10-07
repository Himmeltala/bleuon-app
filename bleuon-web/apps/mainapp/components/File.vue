<script lang="ts" setup>
/**
 * @description 文件
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

defineProps({
  path: {
    type: String,
    required: true
  },
  fileName: {
    type: String,
    required: true
  },
  fileImage: {
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
  <div class="file relative bg-bg-overlay rd-2">
    <div class="file-cover h-50 rd-2" @click="$router.push(path)">
      <img
        v-if="fileImage"
        :src="fileImage"
        class="w-100% h-100% rd-2 object-fill cursor-pointer bg-white" />
      <div v-else class="w-100% h-100% rd-2 cursor-pointer bg-white"></div>
    </div>
    <el-dropdown :teleported="false">
      <div class="file-options absolute top--11.75rem left-0.8rem">
        <div class="file-options-icon f-c-c cursor-pointer w-10 h-6 rd-2 bg-gray-600">
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
    <div class="file-footer mx-2 mb-4">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.file {
  flex: 0 1 calc(16.67% - 1.25rem);

  .file-options {
    display: none;
  }

  &:hover {
    .file-options {
      display: block;
    }
  }

  box-shadow: var(--el-box-shadow-lighter);
}
</style>
