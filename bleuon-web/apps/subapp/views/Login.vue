<script setup lang="ts">
/**
 * @description 后台系统登录
 * @author zheng
 * @since 2023/10/22
 */

import { Requests } from "@common/requests";
import { ElFormUtil } from "@common/utils";

const router = useRouter();
const isAccountCorrect = ref(false);
const isPasswordCorrect = ref(false);

const formRef = ref();
const formData = reactive<ConsumerModel>({
  username: "",
  password: ""
});
const formRules = reactive<FormRules>({
  username: [
    { required: true, message: "请输入账号信息", trigger: "blur" },
    { validator: ElFormUtil.accountValidator(isAccountCorrect), trigger: "change" },
    { validator: ElFormUtil.accountValidator(isAccountCorrect), trigger: "blur" }
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur"
    },
    { validator: ElFormUtil.passwordValidator(isPasswordCorrect), trigger: "change" },
    { validator: ElFormUtil.passwordValidator(isPasswordCorrect), trigger: "blur" }
  ]
});

function confirmLogin() {
  ElFormUtil.validate(formRef.value, async () => {
    Requests.Admin.authLogin(formData, token => {
      localStorage.setToken(Consts.SUBAPP_TOKEN_KEY, token);
      router.push("/");
    });
  });
}
</script>

<template>
  <div class="login f-c-c h-100vh">
    <div class="w-40% h-100% relative">
      <img class="w-45 h-15 object-cover absolute top-10 left-10" src="/bleuon-icon.png" />
      <div class="absolute top-50% left-30 color-gray-100 text-2rem">BlueOn 后台管理系统<br /></div>
      <div class="absolute bottom-10 left-10 color-gray-200 text-0.9rem">
        © 2023 郑人滏. All rights reserved.
      </div>
      <img
        class="w-100% h-100% object-fill"
        src="https://img.js.design/assets/Resources/background/login-bg-5.jpg" />
    </div>
    <div class="w-60% h-100%">
      <div class="login w-100% h-100% relative f-c-c">
        <div class="w-40%">
          <div class="mb-10 text-1.6rem">欢迎登录 BleuOn 管理系统</div>
          <div>
            <el-form ref="formRef" :model="formData" :rules="formRules">
              <el-form-item prop="username">
                <el-input
                  v-model="formData.username"
                  clearable
                  placeholder="请输入用户名"
                  size="large" />
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="formData.password"
                  :maxlength="16"
                  :minlength="8"
                  clearable
                  placeholder="请输入密码"
                  show-password
                  size="large" />
              </el-form-item>
              <el-form-item>
                <el-button
                  :disabled="!isAccountCorrect || !isPasswordCorrect"
                  class="w-100%"
                  size="large"
                  type="primary"
                  @click="confirmLogin">
                  <span class="font-bold">登录</span>
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
