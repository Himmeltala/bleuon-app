<script lang="ts" setup>
import { ElFormUtil } from "@common/utils";
import { Requests } from "@common/requests";

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
    { validator: ElFormUtil.usernameValidator(isUnameCorrect), trigger: "change" },
    { validator: ElFormUtil.usernameValidator(isUnameCorrect), trigger: "blur" }
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

function confirmRegister() {
  ElFormUtil.validate(formRef.value, async () => {
    Requests.Consumer.accountRegister(formData, () => {
      ElMessage.success("请返回登录页面进行登录！");
    });
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules">
      <el-form-item prop="username">
        <el-input
          v-model="formData.username"
          :maxlength="16"
          :minlength="4"
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
          :disabled="!isUnameCorrect || !isPasswordCorrect || !isRePasswdCorrect"
          class="w-100%"
          size="large"
          type="primary"
          @click="confirmRegister">
          <span class="font-bold">注册</span>
        </el-button>
      </el-form-item>
    </el-form>
    <div class="text-text-secondary text-0.8rem text-end mb-4">
      注：注册后请尽快绑定邮箱，以防账号丢失。
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
