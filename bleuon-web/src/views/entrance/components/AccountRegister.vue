<script setup lang="ts">
import type { FormRules } from "element-plus";
import {
  usernameValidator,
  passwordValidator,
  rePasswdValidator,
  submitForm
} from "@/services/form-validators";
import { UserApi } from "@/apis";

const formData = reactive({
  username: "",
  password: "",
  rePasswd: ""
});
const isUnameCorrect = ref(false);
const isPasswordCorrect = ref(false);
const isRePasswdCorrect = ref(false);
const formRef = ref();
const formRules = reactive<FormRules>({
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { validator: usernameValidator(isUnameCorrect), trigger: "change" },
    { validator: usernameValidator(isUnameCorrect), trigger: "blur" }
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

async function confirmRegister() {
  await submitForm(formRef.value, async () => {
    await UserApi.AccountRegister(formData, () => {
      ElMessage({
        type: "success",
        message: "请前往登录！",
        grouping: true
      });
    });
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules">
      <el-form-item prop="username">
        <el-input
          :maxlength="16"
          :minlength="4"
          clearable
          size="large"
          v-model="formData.username"
          placeholder="请输入用户名" />
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
          :disabled="!isUnameCorrect || !isPasswordCorrect || !isRePasswdCorrect"
          @click="confirmRegister"
          size="large"
          class="w-100%"
          type="primary">
          <span class="font-bold">注册</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
