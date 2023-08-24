<script setup lang="ts">
import type { FormInstance, FormRules } from "element-plus";
import { UserApi } from "@/apis";

const count = ref(60);
let interval: number;
const disabled = ref(false);
const isMailCorrect = ref(false);
const isCodeCorrect = ref(false);

const formRef = ref();
const formData = reactive({
  mail: "",
  code: ""
});

const validateEmail = (rule: any, value: any, callback: any) => {
  const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  if (!regex.test(value)) {
    isMailCorrect.value = false;
    callback(new Error("请输入正确的邮箱格式"));
  } else if (value === "") {
    isMailCorrect.value = false;
    callback(new Error("请输入邮箱地址"));
  } else {
    isMailCorrect.value = true;
    callback();
  }
};

const validateCode = (rule: any, value: any, callback: any) => {
  if (value.length < 6) {
    isCodeCorrect.value = false;
    callback(new Error("请输入 6 位数的验证码"));
  } else if (value === "") {
    isCodeCorrect.value = false;
    callback(new Error("请输入验证码"));
  } else {
    isCodeCorrect.value = true;
    callback();
  }
};

const formRules = reactive<FormRules>({
  mail: [
    { required: true, message: "请输入电子邮箱", trigger: "blur" },
    { validator: validateEmail, trigger: "change" },
    { validator: validateEmail, trigger: "blur" },
  ],
  code: [
    {
      required: true,
      message: "请输入验证码",
      trigger: "change"
    },
    { validator: validateCode, trigger: "change" },
    { validator: validateCode, trigger: "blur" },
  ]
});

async function getMailCode() {
  if (!disabled.value) {
    UserApi.askMailCode(formData.mail, "login");
  }
  interval = setInterval(() => {
    count.value--;
    if (count.value < 0) {
      clearInterval(interval);
      disabled.value = false;
      count.value = 60;
    }
  }, 900);
  disabled.value = true;
}

const router = useRouter();

const confirmLogin = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      UserApi.mailCodeLogin(formData.mail, formData.code, "login", () => {
        router.push("/home");
      });
    }
  });
};
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
            <el-button :disabled="disabled" size="large" @click="getMailCode">
              <span v-if="count < 60 && count >= 0">请等待 {{ count }}s</span>
              <span v-else>获取验证码</span>
            </el-button>
          </div>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button
          @click="confirmLogin(formRef)"
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
