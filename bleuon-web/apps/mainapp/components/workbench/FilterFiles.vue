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

const emits = defineEmits(["dateCollateChange"]);

const isModifyDateAsc = ref(false);
const isCreateDateAsc = ref(false);

watch(isModifyDateAsc, (newVal, oldVal) => {
  emits("dateCollateChange", [
    {
      isAsc: isModifyDateAsc.value,
      col: "modify_date"
    }
  ]);
});

watch(isCreateDateAsc, (newVal, oldVal) => {
  emits("dateCollateChange", [
    {
      isAsc: isCreateDateAsc.value,
      col: "create_date"
    }
  ]);
});
</script>

<template>
  <div class="f-c-b">
    <div>{{ title }}</div>
    <div class="f-c-c">
      <div>
        <el-tooltip content="筛选" placement="top">
          <el-dropdown :hide-on-click="false" :teleported="false" trigger="click">
            <el-button @click="" size="small">
              <template #icon>
                <div class="i-tabler-filter"></div>
              </template>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="isModifyDateAsc = !isModifyDateAsc">
                  <div class="text-0.8rem f-c-s select-none">
                    <div
                      class="mr-2"
                      :class="
                        isModifyDateAsc ? 'i-tabler-sort-ascending-2' : 'i-tabler-sort-descending-2'
                      "></div>
                    <span>
                      修改日期<span v-if="isModifyDateAsc">升序</span><span v-else>降序</span>
                    </span>
                  </div>
                </el-dropdown-item>
                <el-dropdown-item @click="isCreateDateAsc = !isCreateDateAsc">
                  <div class="text-0.8rem f-c-s select-none">
                    <div
                      class="mr-2"
                      :class="
                        isCreateDateAsc ? 'i-tabler-sort-ascending-2' : 'i-tabler-sort-descending-2'
                      "></div>
                    <span>
                      创建日期<span v-if="isCreateDateAsc">升序</span><span v-else>降序</span>
                    </span>
                  </div>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
