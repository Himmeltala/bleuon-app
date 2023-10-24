<script setup lang="ts">
/**
 * @description 角色查询
 * @author zheng
 * @since 2023/10/24
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { PermissionHttp } from "@common/requests";
import { DateUtil, FormValidatorsUtil } from "@common/utils";

const currPage = ref(1);
const pageSize = ref(10);

const mainList = ref<PageInfo<RoleModel>>();

async function fetchDataList() {
  mainList.value = await PermissionHttp.findAllRoleWithAuthorityList({
    pageSize: pageSize.value,
    currPage: currPage.value
  });
}

async function handleSizeChange() {
  await fetchDataList();
}

async function handleCurrentChange() {
  await fetchDataList();
}

const addRoleDialog = ref(false);
const roleFormEl = ref();
const roleFormData = reactive({
  name: "",
  remark: ""
});
const roleFormRules = reactive({
  name: [{ required: true, message: "角色名称必须填写！", trigger: "blur" }],
  remark: [{ required: true, message: "角色备注必须填写！", trigger: "blur" }]
});

function addRole() {
  FormValidatorsUtil.validate(roleFormEl.value, () => {
    PermissionHttp.addRole(roleFormData);
  });
}

await fetchDataList();
</script>

<template>
  <div>
    <div class="f-c-e mb-5">
      <div class="add-role">
        <el-button @click="addRoleDialog = true" type="primary">新增角色</el-button>
        <el-dialog v-model="addRoleDialog" width="40%" title="新增角色">
          <div>
            <el-form
              ref="roleFormEl"
              :rules="roleFormRules"
              :model="roleFormData"
              label-position="left"
              label-width="100px">
              <el-form-item prop="name" label="角色名称">
                <el-input placeholder="角色名称，如维护用户管理员" v-model="roleFormData.name" />
              </el-form-item>
              <el-form-item prop="remark" label="角色备注">
                <el-input
                  placeholder="角色备注，尽可能清晰描述该角色的主要工作"
                  v-model="roleFormData.remark" />
              </el-form-item>
            </el-form>
          </div>
          <template #footer>
            <el-button @click="addRoleDialog = false">取消</el-button>
            <el-button type="primary" @click="addRole">确定</el-button>
          </template>
        </el-dialog>
      </div>
    </div>
    <el-table stripe :data="mainList.list" border style="width: 100%">
      <el-table-column label="权限列表" type="expand" width="120">
        <template #default="scope">
          <div class="m-5 font-bold">权限列表</div>
          <div class="m-5">
            <el-table border :data="scope.row.authorities">
              <el-table-column prop="id" label="权限 ID"></el-table-column>
              <el-table-column prop="name" label="权限备注"></el-table-column>
              <el-table-column prop="value" label="权限值"></el-table-column>
              <el-table-column prop="createDate" label="创建日期">
                <template #default="scope">
                  {{ DateUtil.formatted(scope.row.createDate) }}
                </template>
              </el-table-column>
              <el-table-column prop="modifyDate" label="修改日期">
                <template #default="scope">
                  {{ DateUtil.formatted(scope.row.modifyDate) }}
                </template>
              </el-table-column>
            </el-table>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="角色备注"></el-table-column>
      <el-table-column label="角色名称">
        <template #default="scope">
          <el-tag v-if="scope.row.name === '超级管理员'" type="danger">
            {{ scope.row.name }}
          </el-tag>
          <el-tag v-else type="info">
            {{ scope.row.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="角色 ID" />
      <el-table-column prop="createDate" label="创建日期">
        <template #default="scope">
          {{ DateUtil.formatted(scope.row.createDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="modifyDate" label="修改日期">
        <template #default="scope">
          {{ DateUtil.formatted(scope.row.modifyDate) }}
        </template>
      </el-table-column>
    </el-table>
    <div class="mt-5 f-c-e">
      <el-pagination
        v-model:current-page="currPage"
        v-model:page-size="pageSize"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="sizes, prev, pager, next, jumper"
        :page-sizes="[5, 10, 15, 20]"
        background
        :total="mainList.total" />
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
