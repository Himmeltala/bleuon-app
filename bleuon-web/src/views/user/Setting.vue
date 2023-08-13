<script setup lang="ts">
import Header from "./Header.vue";

const formData = reactive({
  username: "Himmelble",
  profession: "计算机软件",
  company: "西南财经大学天府学院",
  position: "本科",
  avatar:
    "https://img2.baidu.com/it/u=1397727792,1861968739&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
  signature: "Time tick away, dream faded away!"
});

const isEditUsername = ref(false);
const professionOps = reactive([
  {
    value: "国防和航空",
    label: "国防和航空"
  },
  {
    value: "计算机硬件",
    label: "计算机硬件"
  },
  {
    value: "计算机软件",
    label: "计算机软件"
  },
  {
    value: "计算机网络",
    label: "计算机网络"
  },
  {
    value: "互联网",
    label: "互联网"
  },
  {
    value: "半导体",
    label: "半导体"
  },
  {
    value: "电信",
    label: "电信"
  },
  {
    value: "律师",
    label: "律师"
  },
  {
    value: "法律服务",
    label: "法律服务"
  },
  {
    value: "生物技术",
    label: "生物技术"
  }
]);

function saveBasicData() {}

function resetBasicData() {}
</script>

<template>
  <div class="setting min-h-100vh">
    <Header active-name="personal"></Header>
    <div class="f-c-c mt-5">
      <div class="w-75vw">
        <div class="bg-white rd-2 p-10">
          <div class="font-bold text-1.5rem pb-5 mb-10 b-b-1 b-b-solid b-b-#e4e4e4 bg-white">
            基本信息
          </div>
          <div class="f-s-b">
            <div class="w-50%">
              <el-form :model="formData" label-width="auto" label-position="left">
                <el-form-item label="昵称">
                  <div class="f-c-c" v-if="!isEditUsername">
                    <div class="mr-5 text-b">{{ formData.username }}</div>
                    <div
                      class="i-tabler-pencil-minus hover"
                      @click="isEditUsername = !isEditUsername"></div>
                  </div>
                  <div class="f-c-c" v-else>
                    <div class="mr-5">
                      <el-input
                        placeholder="请输入您的用户名"
                        clearable
                        v-model="formData.username" />
                    </div>
                    <div
                      class="i-tabler-edit-off hover"
                      @click="isEditUsername = !isEditUsername"></div>
                  </div>
                </el-form-item>
                <el-form-item label="行业">
                  <el-select v-model="formData.profession" placeholder="请选择您的行业">
                    <el-option
                      v-for="item in professionOps"
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
                    :rows="4"
                    clearable
                    type="textarea"
                    placeholder="请输入您的个性签名"
                    v-model="formData.signature" />
                </el-form-item>
              </el-form>
            </div>
            <div class="w-30% text-right">
              <img :src="formData.avatar" class="rd-50% h-40 w-40" />
            </div>
          </div>
          <div class="f-c-c mt-10">
            <el-button @click="resetBasicData">重置</el-button>
            <el-button @click="saveBasicData" type="primary">保存资料</el-button>
          </div>
        </div>
        <div class="bg-white rd-2 my-5 p-10">
          <div class="font-bold text-1.5rem pb-5 mb-10 b-b-1 b-b-solid b-b-#e4e4e4 bg-white">
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
                <div class="text-c">18508153489</div>
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
                <div class="text-c">zhengrenfu@outlook.com</div>
                <div class="ml-10 text-primary cursor-pointer">更换邮箱号</div>
              </div>
            </div>
            <div class="f-c-s">
              <div class="f-c-c">
                <div class="i-tabler-phone mr-2"></div>
                <div>密码</div>
              </div>
              <div class="ml-10 f-c-c">
                <div class="text-c">******</div>
                <div class="ml-10 text-primary cursor-pointer">更换密码</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.setting {
  background-color: rgb(244, 244, 244);
}
</style>
