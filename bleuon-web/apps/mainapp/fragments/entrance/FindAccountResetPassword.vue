<script lang="ts" setup>
import { ElFormUtil } from "@common/utils";
import { Requests } from "@common/requests";

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

onMounted(() => {
  formData.email = props.email;
});

function confirmSubmitForm() {
  ElFormUtil.validate(formRef.value, () => {
    Requests.Consumer.resetPassword(formData, () => {
      ElMessage.success("密码重置成功，请返回登录！");
    });
  });
}
</script>

<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="formRules">
      <el-form-item prop="email">
        <el-input v-model="formData.email" clearable disabled size="large" />
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
          :disabled="!isPasswordCorrect || !isRePasswdCorrect"
          class="w-100%"
          size="large"
          type="primary"
          @click="confirmSubmitForm">
          <span class="font-bold">确定重置密码</span>
        </el-button>
      </el-form-item>
    </el-form>
    <div class="text-text-secondary text-0.8rem text-end mb-4">注：重置后可立即前往登录。</div>
  </div>
</template>

<style lang="scss" scoped></style>
