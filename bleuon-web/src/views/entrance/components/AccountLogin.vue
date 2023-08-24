<script setup lang="ts">
import { UserApi } from "@/apis";

const router = useRouter();
const formData = reactive<IUser>({
  username: "",
  password: ""
});

async function confirmLogin() {
  await UserApi.AccountLogin(formData, () => {
    router.push("/home");
  });
}
</script>

<template>
  <div>
    <el-form>
      <el-form-item>
        <el-input
          clearable
          size="large"
          v-model="formData.username"
          placeholder="请输入手机号/邮箱/用户名" />
      </el-form-item>
      <el-form-item>
        <el-input
          clearable
          size="large"
          v-model="formData.password"
          placeholder="请输入密码"
          show-password />
      </el-form-item>
      <el-form-item>
        <el-button @click="confirmLogin" size="large" class="w-100%" type="primary">
          <span class="font-bold">登录</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
