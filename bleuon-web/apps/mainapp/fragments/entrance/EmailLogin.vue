<script lang="ts" setup>
import { Requests } from "@common/requests";
import { ElFormUtil } from "@common/utils";

const router = useRouter();
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
    Requests.Consumer.askLoginEmailCaptcha({ config: { params: { email: formData.email } } }, () =>
      callback()
    );
  });
}

function confirmSubmitForm() {
  ElFormUtil.validate(formRef.value, () => {
    Requests.Consumer.verifyLoginEmailCaptcha(formData, token => {
      localStorage.setToken(Consts.MAINAPP_TOKEN_KEY, token);
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
