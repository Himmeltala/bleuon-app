<script lang="ts" setup>
import { UserApi } from "@mainapp/apis";
import { FormValidatorsUtil } from "@common/utils";

const router = useRouter();

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
    { validator: FormValidatorsUtil.emailValidator(isEmailCorrect), trigger: "change" },
    { validator: FormValidatorsUtil.emailValidator(isEmailCorrect), trigger: "blur" }
  ],
  code: [
    {
      required: true,
      message: "请输入验证码",
      trigger: "blur"
    },
    { validator: FormValidatorsUtil.verifyCodeValidator(isCodeCorrect), trigger: "change" },
    { validator: FormValidatorsUtil.verifyCodeValidator(isCodeCorrect), trigger: "blur" }
  ]
});

function confirmGetVerifyCode() {
  FormValidatorsUtil.getVerifyCode(
    interval,
    coudButtonCount,
    codeButtonDisabled,
    (callback: any) => {
      UserApi.askMailVerifyCode(formData.email, "login", () => callback());
    }
  );
}

function confirmSubmitForm() {
  FormValidatorsUtil.validate(formRef.value, () => {
    UserApi.verifyMailCode(formData, formData.code, "login", () => {
      router.push("/workbench");
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
      <el-form-item prop="code">
        <div class="f-c-b w-100%">
          <div class="w-70%">
            <el-input
              v-model="formData.code"
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
          :disabled="!isEmailCorrect || !isCodeCorrect"
          class="w-100%"
          size="large"
          type="primary"
          @click="confirmSubmitForm">
          <span class="font-bold">登录</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped></style>
