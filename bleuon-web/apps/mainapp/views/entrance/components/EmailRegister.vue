<script setup lang="ts">
import type { FormRules } from "element-plus";
import {
  emailValidator,
  verifyCodeValidator,
  passwordValidator,
  rePasswdValidator,
  getVerifyCode,
  commitForm
} from "@mainapp/utils/form-validators";
import { USER_API } from "@mainapp/apis";

const coudButtonCount = ref(60);
let interval: number;
const codeButtonDisabled = ref(false);
const isEmailCorrect = ref(false);
const isCodeCorrect = ref(false);
const isPasswordCorrect = ref(false);
const isRePasswdCorrect = ref(false);

const formRef = ref();
const formData = reactive({
  email: "",
  code: "",
  password: "",
  rePasswd: ""
});

const formRules = reactive<FormRules>({
  email: [
    { required: true, message: "请输入电子邮箱", trigger: "blur" },
    { validator: emailValidator(isEmailCorrect), trigger: "change" },
    { validator: emailValidator(isEmailCorrect), trigger: "blur" }
  ],
  code: [
    {
      required: true,
      message: "请输入验证码",
      trigger: "blur"
    },
    { validator: verifyCodeValidator(isCodeCorrect), trigger: "change" },
    { validator: verifyCodeValidator(isCodeCorrect), trigger: "blur" }
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur"
    },
    { validator: passwordValidator(isPasswordCorrect), trigger: "change" },
    { validator: passwordValidator(isPasswordCorrect), trigger: "blur" }
  ],
  rePasswd: [
    {
      required: true,
      message: "请确认密码",
      trigger: "blur"
    },
    { validator: rePasswdValidator(isRePasswdCorrect, formData), trigger: "change" },
    { validator: rePasswdValidator(isRePasswdCorrect, formData), trigger: "blur" }
  ]
});

function confirmGetVerifyCode() {
  getVerifyCode(interval, coudButtonCount, codeButtonDisabled, (callback: any) => {
    USER_API.askMailVerifyCode(formData.email, "register", () => callback());
  });
}

function confirmSubmitForm() {
  commitForm(formRef.value,  () => {
    USER_API.verifyMailCode(formData, formData.code, "register", () => {
      ElMessage({
        type: "success",
        message: "恭喜您，请返回登录页面进行邮箱登录！",
        grouping: true
      });
    });
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules">
      <el-form-item prop="email">
        <el-input clearable size="large" v-model="formData.email" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item prop="code">
        <div class="f-c-b w-100%">
          <div class="w-70%">
            <el-input
              :maxlength="6"
              :minlength="6"
              clearable
              class="w-100%"
              size="large"
              v-model="formData.code"
              placeholder="请输入验证码" />
          </div>
          <div class="w-30% f-c-e">
            <el-button
              :disabled="codeButtonDisabled || !isEmailCorrect"
              size="large"
              @click="confirmGetVerifyCode">
              <span v-if="coudButtonCount < 60 && coudButtonCount >= 0">
                请等待 {{ coudButtonCount }}s
              </span>
              <span v-else>获取验证码</span>
            </el-button>
          </div>
        </div>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          clearable
          :maxlength="16"
          :minlength="8"
          size="large"
          v-model="formData.password"
          placeholder="设置密码：支持任何字符"
          show-password />
      </el-form-item>
      <el-form-item prop="rePasswd">
        <el-input
          clearable
          :maxlength="16"
          :minlength="8"
          size="large"
          v-model="formData.rePasswd"
          placeholder="确认密码：两次密码保持一致"
          show-password />
      </el-form-item>
      <el-form-item>
        <el-button
          @click="confirmSubmitForm"
          :disabled="!isEmailCorrect || !isCodeCorrect || !isPasswordCorrect || !isRePasswdCorrect"
          size="large"
          class="w-100%"
          type="primary">
          <span class="font-bold">注册</span>
        </el-button>
      </el-form-item>
    </el-form>
    <div class="text-b text-0.8rem text-end mb-4">注：注册后可立即前往登录。</div>
  </div>
</template>

<style scoped lang="scss"></style>
