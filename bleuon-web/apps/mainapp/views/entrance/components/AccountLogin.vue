<script setup lang="ts">
import { USER_API } from "@mainapp/apis";
import type { FormRules } from "element-plus";
import { accountValidator, passwordValidator, commitForm } from "@mainapp/utils/form-validators";

const router = useRouter();

const isAccountCorrect = ref(false);
const isPasswordCorrect = ref(false);

const formRef = ref();
const formData = reactive<IUser>({
  username: "",
  password: ""
});
const formRules = reactive<FormRules>({
  username: [
    { required: true, message: "请输入账号信息", trigger: "blur" },
    { validator: accountValidator(isAccountCorrect), trigger: "change" },
    { validator: accountValidator(isAccountCorrect), trigger: "blur" }
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur"
    },
    { validator: passwordValidator(isPasswordCorrect), trigger: "change" },
    { validator: passwordValidator(isPasswordCorrect), trigger: "blur" }
  ]
});

function confirmLogin() {
  commitForm(formRef.value, async () => {
    USER_API.accountLogin(formData, () => {
      router.push("/home");
    });
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :rules="formRules" :model="formData">
      <el-form-item prop="username">
        <el-input
          clearable
          size="large"
          v-model="formData.username"
          placeholder="请输入手机号/邮箱/用户名" />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          clearable
          :maxlength="16"
          :minlength="8"
          size="large"
          v-model="formData.password"
          placeholder="请输入密码"
          show-password />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="!isAccountCorrect || !isPasswordCorrect"
          @click="confirmLogin"
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
