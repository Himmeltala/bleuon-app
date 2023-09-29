<script setup lang="ts">
import { UserApi } from "@mainapp/apis";
import {
  emailValidator,
  verifyCodeValidator,
  getVerifyCode,
  commitForm
} from "@mainapp/utils/form-validators";
import type { FormRules } from "element-plus";

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
  code: ""
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
  ]
});

function confirmGetVerifyCode() {
  getVerifyCode(interval, coudButtonCount, codeButtonDisabled, (callback: any) => {
    UserApi.askMailVerifyCode(formData.email, "reset", () => callback());
  });
}

const isVerifySuccess = ref(false);

function confirmSubmitForm() {
  commitForm(formRef.value,  () => {
    UserApi.verifyMailCode(formData, formData.code, "reset", () => {
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
      <el-form-item>
        <el-button
          v-if="!isVerifySuccess"
          @click="confirmSubmitForm"
          :disabled="!isEmailCorrect || !isCodeCorrect"
          size="large"
          class="w-100%"
          type="primary">
          <span class="font-bold">点击校验</span>
        </el-button>
        <el-button
          v-else
          @click="$emit('update:dynamicCompName', 'FindAccountRsetPassword')"
          :disabled="!isEmailCorrect || !isCodeCorrect"
          size="large"
          class="w-100%"
          type="success">
          <span class="font-bold">点击下一步</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
