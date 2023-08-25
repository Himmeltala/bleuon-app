<script setup lang="ts">
import FindAccountByEmail from "./FindAccountByEmail.vue";
import FindAccountByPhone from "./FindAccountByPhone.vue";
import FindAccountByUsername from "./FindAccountByUsername.vue";

defineProps({
  dynamicCompName: {
    type: String,
    required: true
  }
});
defineEmits(["update:dynamicCompName"]);

const tabIndex = ref<"FindAccountByEmail" | "FindAccountByPhone" | "FindAccountByUsername">(
  "FindAccountByUsername"
);
const tabs = {
  FindAccountByEmail,
  FindAccountByPhone,
  FindAccountByUsername
};
</script>

<template>
  <div class="find-account w-100% h-100% relative f-c-c">
    <div class="w-40%">
      <div class="mb-10 text-1.6rem">找回您的密码</div>
      <div class="mb-10 f-c-s">
        <div
          @click="tabIndex = 'FindAccountByUsername'"
          class="mr-10 cursor-pointer pb-4 text-1.1rem"
          :class="
            tabIndex === 'FindAccountByUsername'
              ? 'active font-bold b-b-solid b-b-primary b-b-2'
              : 'text-b'
          ">
          通过用户名
        </div>
        <div
          @click="tabIndex = 'FindAccountByPhone'"
          class="mr-10 cursor-pointer pb-4 text-1.1rem"
          :class="
            tabIndex === 'FindAccountByPhone'
              ? 'active font-bold b-b-solid b-b-primary b-b-2'
              : 'text-b'
          ">
          通过手机号
        </div>
        <div
          @click="tabIndex = 'FindAccountByEmail'"
          class="cursor-pointer pb-4 text-1.1rem"
          :class="
            tabIndex === 'FindAccountByEmail'
              ? 'active font-bold b-b-solid b-b-primary b-b-2'
              : 'text-b'
          ">
          通过邮箱验证码
        </div>
      </div>
      <KeepAlive>
        <component :is="tabs[tabIndex]"></component>
      </KeepAlive>
      <div
        class="f-c-e text-primary cursor-pointer"
        @click="$emit('update:dynamicCompName', 'Login')">
        想起密码？返回登录
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
