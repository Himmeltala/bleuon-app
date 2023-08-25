<script setup lang="ts">
import AccountLogin from "./AccountLogin.vue";
import EmailLogin from "./EmailLogin.vue";

defineProps({
  dynamicCompName: {
    type: String,
    required: true
  }
});
defineEmits(["update:dynamicCompName"]);

const tabIndex = ref<"AccountLogin" | "EmailLogin">("AccountLogin");
const tabs = {
  AccountLogin,
  EmailLogin
};
</script>

<template>
  <div class="login w-100% h-100% relative f-c-c">
    <div class="w-40%">
      <div class="mb-10 text-1.6rem">欢迎使用 BleuOn</div>
      <div class="mb-10 f-c-s">
        <div
          @click="tabIndex = 'AccountLogin'"
          class="mr-10 cursor-pointer pb-4 text-1.1rem"
          :class="
            tabIndex === 'AccountLogin' ? 'active font-bold b-b-solid b-b-primary b-b-2' : 'text-b'
          ">
          账号登录
        </div>
        <div
          @click="tabIndex = 'EmailLogin'"
          class="cursor-pointer pb-4 text-1.1rem"
          :class="
            tabIndex === 'EmailLogin' ? 'active font-bold b-b-solid b-b-primary b-b-2' : 'text-b'
          ">
          邮箱登录
        </div>
      </div>
      <KeepAlive>
        <component :is="tabs[tabIndex]"></component>
      </KeepAlive>
      <div class="f-c-b">
        <div class="text-b cursor-pointer" @click="$emit('update:dynamicCompName', 'FindAccount')">
          忘记密码？
        </div>
        <div
          class="text-primary cursor-pointer"
          @click="$emit('update:dynamicCompName', 'Register')">
          新用户账号注册
        </div>
      </div>
      <div class="other-login mt-15 f-c-c text-b">
        <div>其他方式登录</div>
      </div>
    </div>
    <div class="absolute text-center bottom-5 w-40% text-0.8rem text-b">
      登录即表示您已同意<span class="hover"><u>服务条款</u></span>
    </div>
  </div>
</template>

<style scoped lang="scss">
.other-login {
  position: relative;

  &::before {
    content: "";
    width: 90px;
    height: 1px;
    background: #dde0e4;
    position: absolute;
    left: 2rem;
  }

  &::after {
    content: "";
    width: 90px;
    height: 1px;
    background: #dde0e4;
    position: absolute;
    right: 2rem;
  }
}
</style>
