<script lang="ts" setup>
/**
 * @description 文件
 * @author zheng
 * @since 2023/8/23
 */

defineProps({
  path: {
    type: String,
    required: true
  },
  fileImage: {
    type: String
  },
  options: {
    type: Boolean,
    default: true
  },
  rename: {
    type: Function as PropType<(...args: any[]) => any>
  },
  download: {
    type: Function as PropType<(...args: any[]) => any>
  },
  replicate: {
    type: Function as PropType<(...args: any[]) => any>
  },
  remove: {
    type: Function as PropType<(...args: any[]) => any>
  }
});
</script>

<template>
  <div class="file relative bg-bg-overlay rd-2">
    <div class="file-cover h-50 rd-2">
      <router-link :to="path">
        <img
          v-if="fileImage"
          :src="fileImage"
          class="w-100% h-100% rd-2 object-cover cursor-pointer bg-white" />
        <div v-else class="w-100% h-100% rd-2 cursor-pointer bg-white"></div>
      </router-link>
    </div>
    <el-dropdown v-if="options" :teleported="false">
      <div class="file-options absolute top--11.75rem left-0.8rem">
        <div class="file-options-icon f-c-c cursor-pointer w-10 h-6 rd-2 bg-gray-600">
          <div class="i-tabler-dots text-white"></div>
        </div>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>
            <router-link :to="path">
              <div class="f-c-s">
                <div class="i-tabler-eye mr-2"></div>
                预览
              </div>
            </router-link>
          </el-dropdown-item>
          <el-dropdown-item @click="rename" v-if="rename">
            <div class="f-c-s">
              <div class="i-tabler-edit mr-2"></div>
              重命名
            </div>
          </el-dropdown-item>
          <el-dropdown-item @click="download" v-if="download">
            <div class="f-c-s">
              <div class="i-tabler-download mr-2"></div>
              下载
            </div>
          </el-dropdown-item>
          <el-dropdown-item @click="replicate" v-if="replicate">
            <div class="f-c-s">
              <div class="i-tabler-copy mr-2"></div>
              复制
            </div>
          </el-dropdown-item>
          <el-dropdown-item @click="remove" v-if="remove">
            <div class="f-c-s">
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
