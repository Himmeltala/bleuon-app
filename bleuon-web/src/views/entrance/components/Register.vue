<script setup lang="ts">
import AccountRegister from "./AccountRegister.vue";
import EmailRegister from "./EmailRegister.vue";

defineProps({
  dynamicCompName: {
    type: String,
    required: true
  }
});
defineEmits(["update:dynamicCompName"]);

const tabIndex = ref<"AccountRegister" | "EmailRegister">("AccountRegister");
const tabs = {
  AccountRegister,
  EmailRegister
};
</script>

<template>
  <div class="register w-100% h-100% relative f-c-c">
    <div class="w-40%">
      <div class="mb-10 text-1.6rem">欢迎注册 BleuOn</div>
      <div class="mb-10 f-c-s">
        <div
          @click="tabIndex = 'AccountRegister'"
          class="mr-10 cursor-pointer pb-4 text-1.1rem"
          :class="
            tabIndex === 'AccountRegister'
              ? 'active font-bold b-b-solid b-b-primary b-b-2'
              : 'text-b'
          ">
          账号注册
        </div>
        <div
          @click="tabIndex = 'EmailRegister'"
          class="cursor-pointer pb-4 text-1.1rem"
          :class="
            tabIndex === 'EmailRegister' ? 'active font-bold b-b-solid b-b-primary b-b-2' : 'text-b'
          ">
          邮箱注册
        </div>
      </div>
      <KeepAlive>
        <component :is="tabs[tabIndex]"></component>
      </KeepAlive>
      <div
        class="text-primary cursor-pointer f-c-e"
        @click="$emit('update:dynamicCompName', 'Login')">
        已经有账号？
      </div>
    </div>
    <div class="absolute text-center bottom-5 w-40% text-0.8rem text-b">
      登录即表示您已同意<span class="hover"><u>服务条款</u></span>
    </div>
  </div>
</template>
