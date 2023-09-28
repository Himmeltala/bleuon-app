<script setup lang="ts">
import type { FormRules } from "element-plus";
import { passwordValidator, rePasswdValidator, commitForm } from "@mainapp/utils/form-validators";
import { USER_API } from "@mainapp/apis";

const props = defineProps({
  email: {
    type: String
  }
});

const formRef = ref();
const formData = reactive({
  email: "",
  password: "",
  rePasswd: ""
});

const isPasswordCorrect = ref(false);
const isRePasswdCorrect = ref(false);

const formRules = reactive<FormRules>({
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

onMounted(() => {
  formData.email = props.email;
});

function confirmSubmitForm() {
  commitForm(formRef.value, () => {
    USER_API.resetPassword(formData, () => {
      ElMessage.success("密码重置成功，请返回登录！");
    });
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules">
      <el-form-item prop="email">
        <el-input disabled clearable size="large" v-model="formData.email" />
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
          @click="confirmSubmitForm"
          :disabled="!isPasswordCorrect || !isRePasswdCorrect"
          size="large"
          class="w-100%"
          type="primary">
          <span class="font-bold">确定重置密码</span>
        </el-button>
      </el-form-item>
    </el-form>
    <div class="text-b text-0.8rem text-end mb-4">注：重置后可立即前往登录。</div>
  </div>
</template>

<style scoped lang="scss"></style>
