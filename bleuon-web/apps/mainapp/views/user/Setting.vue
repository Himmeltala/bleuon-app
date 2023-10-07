<script lang="ts" setup>
/**
 * @description 设置
 * @author zheng
 * @since 2023/8/23
 * @link https://github.com/himmelbleu/bleuon-app
 */

import { UserApi } from "@mainapp/apis";
import { ElSelectData } from "@common/data";

// components
import CommonHeader from "@mainapp/components/CommonHeader.vue";

const formData = reactive(await UserApi.fineOneByToken());

function saveBasicData() {
  UserApi.updateOneByToken({
    username: formData.username,
    profession: formData.profession,
    company: formData.company,
    position: formData.position,
    avatar: formData.avatar,
    signature: formData.signature
  });
}

function resetBasicData() {}
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
            <el-button type="primary" @click="saveBasicData">保存资料</el-button>
          </div>
        </div>
        <div class="bg-bg-overlay rd-2 my-5 p-10">
          <div class="font-bold text-1.5rem pb-5 mb-10 b-b-1 b-b-solid b-border-primary">
            账号安全
          </div>
          <div>
            <!-- phone -->
            <div class="mb-10 f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-phone mr-2"></div>
                <div>手机号</div>
              </div>
              <div class="ml-10 f-c-c">
                <div class="text-text-secondary">{{ formData.phone }}</div>
                <div class="ml-10 text-primary cursor-pointer">更换手机号</div>
              </div>
            </div>
            <!-- email -->
            <div class="mb-10 f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-phone mr-2"></div>
                <div>邮箱号</div>
              </div>
              <div class="ml-10 f-c-c">
                <div class="text-text-secondary">{{ formData.email }}</div>
                <div class="ml-10 text-primary cursor-pointer">更换邮箱号</div>
              </div>
            </div>
            <div class="f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-phone mr-2"></div>
                <div>密码</div>
              </div>
              <div class="ml-10 f-c-c">
                <div class="text-text-secondary">******</div>
                <div class="ml-10 text-primary cursor-pointer">更换密码</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
