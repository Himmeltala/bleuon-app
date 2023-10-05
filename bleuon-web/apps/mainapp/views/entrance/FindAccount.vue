<script lang="ts" setup>
/**
 * @description 找回账号
 * @author 郑人滏 42020306
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import FindAccountVerifyCode from "./components/FindAccountVerifyCode.vue";
import FindAccountRsetPassword from "./components/FindAccountResetPassword.vue";

defineProps({
  dynamicCompName: {
    type: String,
    required: true
  }
});
defineEmits(["update:dynamicCompName"]);

const tabIndex = ref<"FindAccountRsetPassword" | "FindAccountVerifyCode">("FindAccountVerifyCode");

const tabs = {
  FindAccountRsetPassword,
  FindAccountVerifyCode
};

const email = ref("");
</script>

<template>
  <div class="find-account w-100% h-100% relative f-c-c">
    <div class="w-40%">
      <div class="mb-10 text-1.6rem">找回您的密码</div>
      <KeepAlive>
        <component
          :is="tabs[tabIndex]"
          v-model:dynamic-comp-name="tabIndex"
          v-model:email="email"></component>
      </KeepAlive>
      <div
        class="f-c-e text-primary cursor-pointer"
        @click="$emit('update:dynamicCompName', 'Login')">
        想起密码？返回登录
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
