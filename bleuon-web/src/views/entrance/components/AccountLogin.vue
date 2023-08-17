<script setup lang="ts">
const formData = reactive({
  account: "",
  password: ""
});
</script>

<template>
  <div>
    <el-form>
      <el-form-item>
        <el-input
          clearable
          size="large"
          v-model="formData.account"
          placeholder="请输入手机号或邮箱" />
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
        <el-button size="large" class="w-100%" type="primary">
          <span class="font-bold">登录</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
