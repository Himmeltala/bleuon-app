<script lang="ts" setup>
/**
 * @description 注册页
 * @author zheng
 * @since 2023/8/23
 */

import AccountRegister from "@mainapp/fragments/entrance/AccountRegister.vue";
import EmailRegister from "@mainapp/fragments/entrance/EmailRegister.vue";

defineProps({
  dynamicCompName: {
    type: String,
    required: true
  }
});
defineEmits(["update:dynamicCompName"]);

const tabs = [EmailRegister, AccountRegister];
const tabIndex = ref(0);
</script>

<template>
  <div class="register w-100% h-100% relative f-c-c">
    <div class="w-40%">
      <div class="mb-10 text-1.6rem">欢迎注册 BleuOn</div>
      <TabPage :tabs="tabs" :tab-index="tabIndex">
        <template #item>
          <div class="f-c-s mb-5">
            <TabPageItem class="mr-5" :index="0" v-model="tabIndex">邮箱注册</TabPageItem>
            <TabPageItem :index="1" v-model="tabIndex">账号注册</TabPageItem>
          </div>
        </template>
        <component :is="tabs[tabIndex]"></component>
      </TabPage>
      <div
        class="text-primary cursor-pointer f-c-e"
        @click="$emit('update:dynamicCompName', 'Login')">
        已经有账号？
      </div>
    </div>
    <div class="absolute text-center bottom-5 w-40% text-0.8rem text-text-secondary">
      登录即表示您已同意<span class="hover"><u>服务条款</u></span>
    </div>
  </div>
</template>
