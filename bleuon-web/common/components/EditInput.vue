<script lang="ts" setup>
/**
 * @description 输入框文本编辑
 * @author zheng
 * @since 2023/10/7
 */

const props = defineProps({
  /**
   * 显示的文本内容
   */
  text: {
    type: String,
    required: true
  },
  /**
   * 输入框 placeholder
   */
  placeholder: {
    type: String,
    default: "请输入内容到输入框"
  },
  /**
   * 是否基于原本的文本内容之上编辑
   */
  baseModification: {
    type: Boolean,
    default: false
  },
  /**
   * 输入框大小
   */
  size: {
    type: String as PropType<"" | "default" | "small" | "large">,
    default: "default"
  }
});
const emits = defineEmits(["update:text", "change", "enter"]);

const isEdit = ref(false);
const editText = ref("");

function onKeyupEnter() {
  isEdit.value = !isEdit.value;
  emits("enter", editText.value);
}

function onInputChange(v: string) {
  emits("update:text", v);
  emits("change", v);
}

function clickEdit() {
  isEdit.value = !isEdit.value;
  if (props.baseModification) {
    editText.value = props.text;
  }
}
</script>

<template>
  <div class="edit-input">
    <div v-show="!isEdit" class="f-c-s">
      <div class="mr-5">{{ text }}</div>
      <div class="i-tabler-pencil-minus hover" @click="clickEdit"></div>
    </div>
    <div v-show="isEdit" class="f-c-c">
      <div class="mr-5">
        <el-input
          v-model="editText"
          :placeholder="placeholder"
          :size="size"
          @change="onInputChange"
          @keyup.enter="onKeyupEnter" />
      </div>
      <div class="i-tabler-edit-off hover" @click="isEdit = !isEdit"></div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
