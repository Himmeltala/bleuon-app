<script setup lang="ts">
defineProps({
  dynamicCompName: {
    type: String,
    required: true
  }
});
defineEmits(["update:dynamicCompName"]);

const formData = reactive({
  account: "",
  password: ""
});
</script>

<template>
  <div class="logon w-100% h-100% relative f-c-c">
    <div class="w-40%">
      <div class="mb-10 text-1.6rem">欢迎注册BleuOn</div>
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
              <span class="font-bold">注册</span>
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="f-c-e">
        <div class="text-primary cursor-pointer" @click="$emit('update:dynamicCompName', 'Login')">
          已有账号，返回登录
        </div>
      </div>
    </div>
    <div class="absolute text-center bottom-5 w-40% text-0.8rem text-b">
      注册即表示您已同意<span class="hover"><u>服务条款</u></span>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
