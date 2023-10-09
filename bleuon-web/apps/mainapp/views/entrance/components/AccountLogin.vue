<script lang="ts" setup>
import { UserApi } from "@mainapp/apis";
import { FormValidatorsUtil } from "@common/utils";

const router = useRouter();

const isAccountCorrect = ref(false);
const isPasswordCorrect = ref(false);

const formRef = ref();
const formData = reactive<UserData>({
  username: "",
  password: ""
});
const formRules = reactive<FormRules>({
  username: [
    { required: true, message: "请输入账号信息", trigger: "blur" },
    { validator: FormValidatorsUtil.accountValidator(isAccountCorrect), trigger: "change" },
    { validator: FormValidatorsUtil.accountValidator(isAccountCorrect), trigger: "blur" }
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur"
    },
    { validator: FormValidatorsUtil.passwordValidator(isPasswordCorrect), trigger: "change" },
    { validator: FormValidatorsUtil.passwordValidator(isPasswordCorrect), trigger: "blur" }
  ]
});

function confirmLogin() {
  FormValidatorsUtil.validate(formRef.value, async () => {
    UserApi.authLogin(formData, token => {
      localStorage.setToken(KeyVals.MAINAPP_TOKEN_KEY, token);
      UserApi.fineByToken().then(data => {
        useStorage<UserData>(KeyVals.MAINAPP_USER, {}).value = data;
        router.push("/workbench");
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
