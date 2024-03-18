<script lang="ts" setup>
import { ElFormUtil } from "@common/utils";
import { Requests } from "@common/requests";

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
  captcha: "",
  password: "",
  rePasswd: ""
});

const formRules = reactive<FormRules>({
  email: [
    { required: true, message: "请输入电子邮箱", trigger: "blur" },
    { validator: ElFormUtil.emailValidator(isEmailCorrect), trigger: "change" },
    { validator: ElFormUtil.emailValidator(isEmailCorrect), trigger: "blur" }
  ],
  captcha: [
    {
      required: true,
      message: "请输入验证码",
      trigger: "blur"
    },
    { validator: ElFormUtil.verifyCaptchaValidator(isCodeCorrect), trigger: "change" },
    { validator: ElFormUtil.verifyCaptchaValidator(isCodeCorrect), trigger: "blur" }
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur"
    },
    { validator: ElFormUtil.passwordValidator(isPasswordCorrect), trigger: "change" },
    { validator: ElFormUtil.passwordValidator(isPasswordCorrect), trigger: "blur" }
  ],
  rePasswd: [
    {
      required: true,
      message: "请确认密码",
      trigger: "blur"
    },
    {
      validator: ElFormUtil.rePasswdValidator(isRePasswdCorrect, formData),
      trigger: "change"
    },
    {
      validator: ElFormUtil.rePasswdValidator(isRePasswdCorrect, formData),
      trigger: "blur"
    }
  ]
});

function confirmGetVerifyCode() {
  ElFormUtil.askVerifyCaptcha(interval, coudButtonCount, codeButtonDisabled, callback => {
    Requests.Consumer.askRegisterEmailCaptcha({ email: formData.email }, () => callback());
  });
}

function confirmSubmitForm() {
  ElFormUtil.validate(formRef.value, () => {
    Requests.Consumer.verifyRegisterEmailCaptcha(
      formData,
      { email: formData.email, captcha: formData.captcha },
      () => {
        ElMessage.success("请返回登录页面进行邮箱登录！");
      }
    );
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules">
      <el-form-item prop="email">
        <el-input v-model="formData.email" clearable placeholder="请输入邮箱" size="large" />
      </el-form-item>
      <el-form-item prop="captcha">
        <div class="f-c-b w-100%">
          <div class="w-70%">
            <el-input
              v-model="formData.captcha"
              :maxlength="6"
              :minlength="6"
              class="w-100%"
              clearable
              placeholder="请输入6位验证码"
              size="large" />
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
          v-model="formData.password"
          :maxlength="16"
          :minlength="8"
          clearable
          placeholder="设置密码：支持任何字符"
          show-password
          size="large" />
      </el-form-item>
      <el-form-item prop="rePasswd">
        <el-input
          v-model="formData.rePasswd"
          :maxlength="16"
          :minlength="8"
          clearable
          placeholder="确认密码：两次密码保持一致"
          show-password
          size="large" />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="!isEmailCorrect || !isCodeCorrect || !isPasswordCorrect || !isRePasswdCorrect"
          class="w-100%"
          size="large"
          type="primary"
          @click="confirmSubmitForm">
          <span class="font-bold">注册</span>
        </el-button>
      </el-form-item>
    </el-form>
    <div class="text-text-secondary text-0.8rem text-end mb-4">注：注册后可立即前往登录。</div>
  </div>
</template>

<style lang="scss" scoped></style>
