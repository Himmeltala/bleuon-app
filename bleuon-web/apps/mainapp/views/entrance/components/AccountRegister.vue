<script setup lang="ts">
import type { FormRules } from "element-plus";
import {
  usernameValidator,
  passwordValidator,
  rePasswdValidator,
  commitForm
} from "@mainapp/utils/form-validators";
import { USER_API } from "@mainapp/apis";

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

function confirmRegister() {
  commitForm(formRef.value, async () => {
    USER_API.accountRegister(formData, () => {
      ElMessage({
        type: "success",
        message: "恭喜您，请返回登录页面进行登录！",
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
    <div class="text-b text-0.8rem text-end mb-4">注：注册后请尽快绑定邮箱，以防账号丢失。</div>
  </div>
</template>

<style scoped lang="scss"></style>
