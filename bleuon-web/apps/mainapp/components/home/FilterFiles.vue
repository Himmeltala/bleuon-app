<script setup lang="ts">
/**
 * @description 筛选查询
 * @author 郑人滏 42020306
 * @since 2023/10/1
 * @link https://github.com/himmelbleu/bleuon-app
 */

defineProps({
  title: {
    type: String,
    required: true
  }
});
</script>

<template>
  <div class="f-c-b">
    <div>{{ title }}</div>
    <div class="f-c-c">
      <div>
        <el-tooltip content="筛选" placement="bottom">
          <el-button @click="" size="small">
            <template #icon>
              <div class="i-tabler-filter"></div>
            </template>
          </el-button>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
