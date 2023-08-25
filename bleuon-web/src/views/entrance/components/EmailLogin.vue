<script setup lang="ts">
import type { FormRules } from "element-plus";
import {
  emailValidator,
  verifyCodeValidator,
  getVerifyCode,
  submitForm
} from "@/services/form-validators";
import { UserApi } from "@/apis";

let interval: number;
const coudButtonCount = ref(60);
const codeButtonDisabled = ref(false);
const isMailCorrect = ref(false);
const isCodeCorrect = ref(false);

const formRef = ref();
const formData = reactive({
  mail: "",
  code: ""
});

const formRules = reactive<FormRules>({
  mail: [
    { required: true, message: "请输入电子邮箱", trigger: "blur" },
    { validator: emailValidator(isMailCorrect), trigger: "change" },
    { validator: emailValidator(isMailCorrect), trigger: "blur" }
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
  getVerifyCode(interval, coudButtonCount, codeButtonDisabled, async () => {
    await UserApi.askMailVerifyCode(formData.mail, "login");
  });
}

const router = useRouter();

async function confirmSubmitForm() {
  await submitForm(formRef.value, async () => {
    await UserApi.verifyMailCode(formData.mail, formData.code, "login", () => {
      router.push("/home");
    });
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules">
      <el-form-item prop="mail">
        <el-input clearable size="large" v-model="formData.mail" placeholder="请输入邮箱" />
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
              :disabled="codeButtonDisabled || !isMailCorrect"
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
          @click="confirmSubmitForm"
          :disabled="!isMailCorrect || !isCodeCorrect"
          size="large"
          class="w-100%"
          type="primary">
          <span class="font-bold">登录</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
