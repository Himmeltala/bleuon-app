<script setup lang="ts">
import AccountLogin from "./AccountLogin.vue";
import PhoneLogin from "./PhoneLogin.vue";

const tabIndex = ref<"AccountLogin" | "PhoneLogin">("AccountLogin");
const tabs = {
  AccountLogin,
  PhoneLogin
};
</script>

<template>
  <div class="login f-c-c h-100vh">
    <div class="w-30% h-100% bg-amber"></div>
    <div class="w-70% h-100% relative f-c-c">
      <div class="w-40%">
        <div class="mb-10 text-1.6rem">欢迎使用BleuOn</div>
        <div class="mb-10 f-c-s">
          <div
            @click="tabIndex = 'AccountLogin'"
            class="mr-10 cursor-pointer pb-4 text-1.1rem"
            :class="
              tabIndex === 'AccountLogin'
                ? 'active font-bold b-b-solid b-b-primary b-b-2'
                : 'text-b'
            ">
            账号登录
          </div>
          <div
            @click="tabIndex = 'PhoneLogin'"
            class="cursor-pointer pb-4 text-1.1rem"
            :class="
              tabIndex === 'PhoneLogin' ? 'active font-bold b-b-solid b-b-primary b-b-2' : 'text-b'
            ">
            短信登录
          </div>
        </div>
        <KeepAlive>
          <component :is="tabs[tabIndex]"></component>
        </KeepAlive>
        <div class="f-c-b">
          <div class="text-b cursor-pointer">忘记密码？</div>
          <div class="text-primary cursor-pointer">新用户账号注册</div>
        </div>
        <div class="other-login mt-15 f-c-c text-b">
          <div>其他方式登录</div>
        </div>
      </div>
      <div class="absolute text-center bottom-5 w-40% text-0.8rem text-b">
        登录即表示您已同意<span class="hover"><u>服务条款</u></span>
      </div>
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
