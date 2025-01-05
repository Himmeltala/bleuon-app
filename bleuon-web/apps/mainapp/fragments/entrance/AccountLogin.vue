<script lang="ts" setup>
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
    Requests.Consumer.authLogin(formData, token => {
      localStorage.setToken(Consts.MAINAPP_TOKEN_KEY, token);
      router.push("/workbench");
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
          clearable
          placeholder="请输入手机号/邮箱/用户名"
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
</template>

<style lang="scss" scoped></style>
