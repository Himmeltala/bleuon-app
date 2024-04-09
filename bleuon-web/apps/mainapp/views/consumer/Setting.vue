<script lang="ts" setup>
/**
 * @description 设置
 * @author zheng
 * @since 2023/8/23
 */

import { Requests } from "@common/requests";
import { ElSelectData } from "@common/data";
import { ElFormUtil, DateUtil } from "@common/utils";

import CommonHeader from "@mainapp/fragments/CommonHeader.vue";

const token = localStorage.getToken(Consts.MAINAPP_TOKEN_KEY);
const mainData = ref(await Requests.Consumer.findBy({ id: token.id }));

function upgradeBasicData() {
  Requests.Consumer.upgrade({
    id: token.id,
    username: mainData.value.username,
    profession: mainData.value.profession,
    company: mainData.value.company,
    position: mainData.value.position,
    degree: mainData.value.degree,
    signature: mainData.value.signature,
    sex: mainData.value.sex
  });
}

let interval: number;
const resetPwdDialog = ref(false);
const resetButtonCount = ref(60);
const resetButtonDisabled = ref(false);
const isCodeCorrect = ref(false);
const isPasswordCorrect = ref(false);
const isRePasswdCorrect = ref(false);

const resetPwdFormRef = ref();
const resetPwdFormData = reactive({
  captcha: "",
  password: "",
  rePasswd: ""
});
const resetPwdFormRules = reactive<FormRules>({
  captcha: [
    {
      required: true,
      message: "请输入验证码",
      trigger: "blur"
    },
    { validator: ElFormUtil.verifyCaptchaValidator(isCodeCorrect), trigger: "change" },
    { validator: ElFormUtil.verifyCaptchaValidator(isCodeCorrect), trigger: "blur" }
  ],
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
      validator: ElFormUtil.rePasswdValidator(isRePasswdCorrect, resetPwdFormData),
      trigger: "change"
    },
    {
      validator: ElFormUtil.rePasswdValidator(isRePasswdCorrect, resetPwdFormData),
      trigger: "blur"
    }
  ]
});

function getResetPwdCode() {
  ElFormUtil.askVerifyCaptcha(interval, resetButtonCount, resetButtonDisabled, callback => {
    Requests.Consumer.askResetEmailCaptcha({ email: mainData.value.email }, () => callback());
  });
}

function confirmResetPwd() {
  ElFormUtil.validate(resetPwdFormRef.value, () => {
    Requests.Consumer.verifyEmailCaptcha(
      { captcha: resetPwdFormData.captcha, email: mainData.value.email },
      () => {
        Requests.Consumer.upgrade(
          {
            id: token.id,
            password: resetPwdFormData.password
          },
          () => {
            Requests.Consumer.authLogout();
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
              <el-form :model="mainData" label-position="left" label-width="90px">
                <el-form-item label="昵称">
                  <EditInput v-model:text="mainData.username" :base-modification="true"></EditInput>
                </el-form-item>
                <el-form-item label="行业">
                  <el-select v-model="mainData.position" placeholder="请选择您的行业">
                    <el-option
                      v-for="item in ElSelectData.positionList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value" />
                  </el-select>
                </el-form-item>
                <el-form-item label="职业">
                  <el-select v-model="mainData.profession" placeholder="请选择您的职业">
                    <el-option
                      v-for="item in ElSelectData.professionList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value" />
                  </el-select>
                </el-form-item>
                <el-form-item label="公司">
                  <el-input v-model="mainData.company" clearable />
                </el-form-item>
                <el-form-item label="学历">
                  <el-select v-model="mainData.degree" placeholder="请选择您的学历">
                    <el-option
                      v-for="item in ElSelectData.degreeList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value" />
                  </el-select>
                </el-form-item>
                <el-form-item label="性别">
                  <el-select v-model="mainData.sex" placeholder="请选择您的性别">
                    <el-option
                      v-for="item in ElSelectData.sexList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value" />
                  </el-select>
                </el-form-item>
                <el-form-item label="个人简介">
                  <el-input
                    v-model="mainData.signature"
                    :rows="4"
                    clearable
                    placeholder="请输入您的个性签名"
                    type="textarea" />
                </el-form-item>
              </el-form>
            </div>
            <div class="w-30% f-c-e cursor-pointer">
              <AvatarUpload
                v-model:img-url="mainData.avatar"
                :start-upload="formData => Requests.Consumer.upgradeAvatar(formData, token.id)" />
            </div>
          </div>
          <div class="mt-5 f-c-e text-0.8rem text-text-secondary">
            <div class="f-c-c">
              <div class="i-ep-clock mr-1"></div>
              上次更新：{{ DateUtil.formatted(mainData.modifyDate) }}
            </div>
          </div>
          <div class="f-c-c mt-10">
            <el-button type="primary" @click="upgradeBasicData">保存资料</el-button>
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
                <div class="text-text-secondary mr-10">{{ mainData.phone }}</div>
                <el-button bg size="small" text>更换</el-button>
              </div>
            </div>
            <div class="mt-5 f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-at mr-2"></div>
                <div>邮箱</div>
              </div>
              <div class="ml-10 f-c-s">
                <div class="text-text-secondary mr-10">{{ mainData.email }}</div>
                <el-button bg size="small" text>更换</el-button>
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
                  bg
                  size="small"
                  text
                  type="danger"
                  @click="resetPwdDialog = !resetPwdDialog">
                  修改
                </el-button>
              </div>
            </div>
          </div>
        </div>
        <el-dialog v-model="resetPwdDialog" title="修改密码" width="25%">
          <el-form
            ref="resetPwdFormRef"
            :model="resetPwdFormData"
            :rules="resetPwdFormRules"
            label-position="left"
            label-width="80px">
            <el-form-item label="验证码" prop="captcha">
              <div class="f-c-b flex-wrap w-100%">
                <div class="w-45%">
                  <el-input
                    v-model="resetPwdFormData.captcha"
                    :maxlength="6"
                    :minlength="6"
                    class="w-100%"
                    clearable
                    placeholder="请输入6位验证码"
                    size="small" />
                </div>
                <div class="w-50% f-c-s">
                  <el-button
                    :disabled="resetButtonDisabled"
                    plain
                    size="small"
                    type="primary"
                    @click="getResetPwdCode">
                    <span v-if="resetButtonCount < 60 && resetButtonCount >= 0">
                      请等待 {{ resetButtonCount }}s
                    </span>
                    <span v-else>获取验证码</span>
                  </el-button>
                </div>
              </div>
            </el-form-item>
            <el-form-item label="新的密码" prop="password">
              <el-input
                v-model="resetPwdFormData.password"
                :maxlength="16"
                :minlength="8"
                clearable
                placeholder="设置密码：支持任何字符"
                show-password
                size="small" />
            </el-form-item>
            <el-form-item label="确认密码" prop="rePasswd">
              <el-input
                v-model="resetPwdFormData.rePasswd"
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
              <el-button @click="resetPwdDialog = false">取消</el-button>
              <el-button
                :disabled="!isCodeCorrect || !isPasswordCorrect || !isRePasswdCorrect"
                type="primary"
                @click="confirmResetPwd">
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
