<script lang="ts" setup>
/**
 * @description 找回账号
 * @author zheng
 * @since 2023/8/23
 */

import FindAccountVerifyCode from "@mainapp/fragments/entrance/FindAccountVerifyCode.vue";
import FindAccountRsetPassword from "@mainapp/fragments/entrance/FindAccountResetPassword.vue";

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
      <component
        :is="tabs[tabIndex]"
        v-model:dynamic-comp-name="tabIndex"
        v-model:email="email"></component>
      <div
        class="f-c-e text-primary cursor-pointer"
        @click="$emit('update:dynamicCompName', 'Login')">
        想起密码？返回登录
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
