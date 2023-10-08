<script lang="ts" setup>
/**
 * @description 设置
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";
import { ElSelectData } from "@common/data";
import { FormValidatorsUtil } from "@common/utils";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";

const formData = useStorage<UserData>(KeyVals.MAINAPP_USER, {});

function renewalBasicData() {
  UserApi.renewalByToken({
    username: formData.value.username,
    profession: formData.value.profession,
    company: formData.value.company,
    position: formData.value.position,
    avatar: formData.value.avatar,
    signature: formData.value.signature
  });
}

function resetBasicData() {}

let interval: number;
const renewalPwdDialog = ref(false);
const coudButtonCount = ref(60);
const codeButtonDisabled = ref(false);
const isCodeCorrect = ref(false);
const isPasswordCorrect = ref(false);
const isRePasswdCorrect = ref(false);

const renewalPwdFormRef = ref();
const renewalPwdFormData = reactive({
  captcha: "",
  password: "",
  rePasswd: ""
});
const renewalPwdFormRules = reactive<FormRules>({
  captcha: [
    {
      required: true,
      message: "请输入验证码",
      trigger: "blur"
    },
    { validator: FormValidatorsUtil.verifyCodeValidator(isCodeCorrect), trigger: "change" },
    { validator: FormValidatorsUtil.verifyCodeValidator(isCodeCorrect), trigger: "blur" }
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur"
    },
    { validator: FormValidatorsUtil.passwordValidator(isPasswordCorrect), trigger: "change" },
    { validator: FormValidatorsUtil.passwordValidator(isPasswordCorrect), trigger: "blur" }
  ],
  rePasswd: [
    {
      required: true,
      message: "请确认密码",
      trigger: "blur"
    },
    {
      validator: FormValidatorsUtil.rePasswdValidator(isRePasswdCorrect, renewalPwdFormData),
      trigger: "change"
    },
    {
      validator: FormValidatorsUtil.rePasswdValidator(isRePasswdCorrect, renewalPwdFormData),
      trigger: "blur"
    }
  ]
});

function getRenewalPwdCode() {
  FormValidatorsUtil.getVerifyCode(interval, coudButtonCount, codeButtonDisabled, callback => {
    UserApi.askMailCaptcha({ email: formData.value.email }, () => callback());
  });
}

function confirmRenewalPwd() {
  FormValidatorsUtil.validate(renewalPwdFormRef.value, () => {
    UserApi.verifyMailCaptcha(
      { captcha: renewalPwdFormData.captcha, email: formData.value.email },
      () => {
        UserApi.renewalByToken(
          {
            password: renewalPwdFormData.password
          },
          () => {
            UserApi.logout(() => {
              location.reload();
              ElMessage.success("请重新登录！");
            });
          }
        );
      }
    );
  });
}
</script>

<template>
  <div class="setting slim-slider h-100vh flow-auto bg-bg-page">
    <CommonHeader></CommonHeader>
    <div class="f-c-c mt-5">
      <div class="w-75vw">
        <div class="bg-bg-overlay rd-2 p-10">
          <div class="font-bold text-1.5rem pb-5 mb-10 b-b-1 b-b-solid b-border-primary">
            基本信息
          </div>
          <div class="f-s-b">
            <div class="w-50%">
              <el-form :model="formData" label-position="left" label-width="auto">
                <el-form-item label="昵称">
                  <EditInput v-model:text="formData.username" :base-modification="true"></EditInput>
                </el-form-item>
                <el-form-item label="行业">
                  <el-select v-model="formData.profession" placeholder="请选择您的行业">
                    <el-option
                      v-for="item in ElSelectData.professionList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value" />
                  </el-select>
                </el-form-item>
                <el-form-item label="公司">
                  <el-input v-model="formData.company" clearable />
                </el-form-item>
                <el-form-item label="职业（学历）">
                  <el-input v-model="formData.position" clearable />
                </el-form-item>
                <el-form-item label="头像地址">
                  <el-input v-model="formData.avatar" clearable />
                </el-form-item>
                <el-form-item label="个人简介">
                  <el-input
                    v-model="formData.signature"
                    :rows="4"
                    clearable
                    placeholder="请输入您的个性签名"
                    type="textarea" />
                </el-form-item>
              </el-form>
            </div>
            <div class="w-30% text-right">
              <img :src="formData.avatar" class="rd-50% h-40 w-40" />
            </div>
          </div>
          <div class="f-c-c mt-10">
            <el-button @click="resetBasicData">重置</el-button>
            <el-button type="primary" @click="renewalBasicData">保存资料</el-button>
          </div>
        </div>
        <div class="bg-bg-overlay rd-2 my-5 p-10">
          <div class="font-bold text-1.5rem pb-5 mb-10 b-b-1 b-b-solid b-border-primary">
            账号安全
          </div>
          <div>
            <div class="f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-phone mr-2"></div>
                <div>手机</div>
              </div>
              <div class="ml-10 f-c-s">
                <div class="text-text-secondary mr-10">{{ formData.phone }}</div>
                <el-button size="small" text bg>更换</el-button>
              </div>
            </div>
            <div class="mt-5 f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-at mr-2"></div>
                <div>邮箱</div>
              </div>
              <div class="ml-10 f-c-s">
                <div class="text-text-secondary mr-10">{{ formData.email }}</div>
                <el-button size="small" text bg>更换</el-button>
              </div>
            </div>
            <div class="mt-5 f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-key mr-2"></div>
                <div>密码</div>
              </div>
              <div class="ml-10 f-c-s">
                <div class="text-text-secondary mr-10">******</div>
                <el-button
                  size="small"
                  type="danger"
                  text
                  bg
                  @click="renewalPwdDialog = !renewalPwdDialog">
                  修改
                </el-button>
              </div>
            </div>
          </div>
        </div>
        <el-dialog v-model="renewalPwdDialog" title="修改密码" width="30%">
          <el-form
            ref="renewalPwdFormRef"
            label-width="80px"
            label-position="left"
            :model="renewalPwdFormData"
            :rules="renewalPwdFormRules">
            <el-form-item label="验证码" prop="captcha">
              <div class="f-c-b flex-wrap w-100%">
                <div class="w-45%">
                  <el-input
                    class="w-100%"
                    size="small"
                    :maxlength="6"
                    :minlength="6"
                    v-model="renewalPwdFormData.captcha"
                    clearable
                    placeholder="请输入6位验证码" />
                </div>
                <div class="w-50% f-c-s">
                  <el-button
                    plain
                    type="primary"
                    size="small"
                    :disabled="codeButtonDisabled"
                    @click="getRenewalPwdCode">
                    <span v-if="coudButtonCount < 60 && coudButtonCount >= 0">
                      请等待 {{ coudButtonCount }}s
                    </span>
                    <span v-else>获取验证码</span>
                  </el-button>
                </div>
              </div>
            </el-form-item>
            <el-form-item label="新的密码" prop="password">
              <el-input
                v-model="renewalPwdFormData.password"
                :maxlength="16"
                :minlength="8"
                clearable
                placeholder="设置密码：支持任何字符"
                show-password
                size="small" />
            </el-form-item>
            <el-form-item label="确认密码" prop="rePasswd">
              <el-input
                v-model="renewalPwdFormData.rePasswd"
                :maxlength="16"
                :minlength="8"
                clearable
                placeholder="确认密码：两次密码保持一致"
                show-password
                size="small" />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="renewalPwdDialog = false">取消</el-button>
              <el-button
                :disabled="!isCodeCorrect || !isPasswordCorrect || !isRePasswdCorrect"
                type="primary"
                @click="confirmRenewalPwd">
                确认
              </el-button>
            </span>
          </template>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
