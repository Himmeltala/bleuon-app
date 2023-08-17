<script setup lang="ts">
const formData = reactive({
  phoneNum: "",
  identifyingCode: ""
});
</script>

<template>
  <div>
    <el-form>
      <el-form-item>
        <el-input clearable size="large" v-model="formData.phoneNum" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item>
        <div class="f-c-b w-100%">
          <div class="w-70%">
            <el-input
              clearable
              class="w-100%"
              size="large"
              v-model="formData.identifyingCode"
              placeholder="请输入验证码" />
          </div>
          <div class="w-30% f-c-e">
            <el-button size="large">获取验证码</el-button>
          </div>
        </div>
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
