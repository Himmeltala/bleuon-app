<script lang="ts" setup>
import { Requests } from "@common/requests";
import { ElFormUtil } from "@common/utils";

defineProps({
  dynamicCompName: {
    type: String,
    required: true
  },
  email: {
    type: String
  }
});
const emits = defineEmits(["update:dynamicCompName", "update:email"]);

let interval: number;
const coudButtonCount = ref(60);
const codeButtonDisabled = ref(false);
const isEmailCorrect = ref(false);
const isCodeCorrect = ref(false);

const formRef = ref();
const formData = reactive({
  email: "",
  captcha: ""
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
  ]
});

function confirmGetVerifyCode() {
  ElFormUtil.askVerifyCaptcha(interval, coudButtonCount, codeButtonDisabled, (callback: any) => {
    Requests.Consumer.askResetEmailCaptcha({ email: formData.email }, () => callback());
  });
}

const isVerifySuccess = ref(false);

function confirmSubmitForm() {
  ElFormUtil.validate(formRef.value, () => {
    Requests.Consumer.verifyEmailCaptcha(formData, () => {
      isVerifySuccess.value = true;
      emits("update:email", formData.email);
    });
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
              placeholder="请输入验证码"
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
      <el-form-item>
        <el-button
          v-if="!isVerifySuccess"
          :disabled="!isEmailCorrect || !isCodeCorrect"
          class="w-100%"
          size="large"
          type="primary"
          @click="confirmSubmitForm">
          <span class="font-bold">点击校验</span>
        </el-button>
        <el-button
          v-else
          :disabled="!isEmailCorrect || !isCodeCorrect"
          class="w-100%"
          size="large"
          type="success"
          @click="$emit('update:dynamicCompName', 'FindAccountRsetPassword')">
          <span class="font-bold">点击下一步</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped></style>
