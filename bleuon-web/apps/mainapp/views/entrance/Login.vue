<script lang="ts" setup>
/**
 * @description 登录页
 * @author zheng
 * @since 2023/8/23
 */

import AccountLogin from "@mainapp/fragments/entrance/AccountLogin.vue";
import EmailLogin from "@mainapp/fragments/entrance/EmailLogin.vue";

defineProps({
  dynamicCompName: {
    type: String,
    required: true
  }
});
defineEmits(["update:dynamicCompName"]);

const tabs = [AccountLogin, EmailLogin];
const tabIndex = ref(0);
</script>

<template>
  <div class="login w-100% h-100% relative f-c-c">
    <div class="w-40%">
      <div class="mb-10 text-1.6rem">欢迎使用 BleuOn</div>
      <TabPage :tabs="tabs" :tab-index="tabIndex">
        <template #item>
          <div class="f-c-s mb-5">
            <TabPageItem class="mr-5" :index="0" v-model="tabIndex">账号登录</TabPageItem>
            <TabPageItem :index="1" v-model="tabIndex">邮箱登录</TabPageItem>
          </div>
        </template>
        <component :is="tabs[tabIndex]"></component>
      </TabPage>
      <div class="f-c-b">
        <div
          class="text-text-secondary cursor-pointer"
          @click="$emit('update:dynamicCompName', 'FindAccount')">
          忘记密码？
        </div>
        <div
          class="text-primary cursor-pointer"
          @click="$emit('update:dynamicCompName', 'Register')">
          新用户账号注册
        </div>
      </div>
      <div class="other-login mt-15 f-c-c text-text-secondary">
        <div>其他方式登录</div>
      </div>
    </div>
    <div class="absolute text-center bottom-5 w-40% text-0.8rem text-text-secondary">
      登录即表示您已同意<span class="hover"><u>服务条款</u></span>
    </div>
  </div>
</template>

<style lang="scss" scoped>
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
