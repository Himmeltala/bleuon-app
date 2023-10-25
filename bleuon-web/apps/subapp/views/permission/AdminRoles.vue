<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/22
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { AdminHttp, PermissionHttp } from "@common/requests";
import { DateUtil } from "@common/utils";

const currPage = ref(1);
const pageSize = ref(10);

const mainList = ref<PageInfo<AdminModel>>();

async function fetchDataList() {
  mainList.value = await PermissionHttp.findAllAdminWithRoleAndAuthorityList({
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

const adminList = ref<PageInfo<AdminModel>>();
const roleList = ref<PageInfo<RoleModel>>();

async function fetchAdminList() {
  adminList.value = await AdminHttp.findListBy({
    pageSize: pageSize.value,
    currPage: currPage.value
  });
  roleList.value = await PermissionHttp.findAllRoleWithAuthorityList({});
}

const addRoleDialog = ref(false);
const roleCurrPage = ref(1);
const rolePageSize = ref(5);

async function openAddRoleDialog() {
  await fetchAdminList();
  addRoleDialog.value = true;
}

async function handleRoleSizeChange() {
  await fetchAdminList();
}

async function handleRoleCurrentChange() {
  await fetchAdminList();
}

const selectedCurrAdminRow = ref<AdminModel>();
const selectedCurrRoleRow = ref<RoleModel>();

function confirmAddRoleToAdmin() {
  PermissionHttp.addRoleToAdmin(
    {
      roleId: selectedCurrRoleRow.value.id,
      adminId: selectedCurrAdminRow.value.id,
      username: selectedCurrAdminRow.value.username
    },
    async () => {
      addRoleDialog.value = false;
      await fetchDataList();
    }
  );
}

await fetchDataList();
</script>

<template>
  <div>
    <div class="mb-5 text-text-secondary text-0.9rem">描述: 维护后台管理系统的管理员的角色分组</div>
    <div class="f-c-e mb-5">
      <div class="add-role">
        <el-button @click="openAddRoleDialog" size="small" type="primary">分配角色</el-button>
      </div>
    </div>
    <el-table stripe border :data="mainList.list" style="width: 100%">
      <el-table-column label="权限列表" type="expand" width="120">
        <template #default="scope">
          <div class="m-5 font-bold">权限列表</div>
          <div class="m-5">
            <el-table border :data="scope.row.authorities">
              <el-table-column prop="id" label="权限 ID"></el-table-column>
              <el-table-column prop="name" label="权限备注"></el-table-column>
              <el-table-column prop="value" label="权限值"></el-table-column>
            </el-table>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" />
      <el-table-column label="头像">
        <template #default="scope">
          <img :src="scope.row.avatar" class="rd-50% w-10 h-10" />
        </template>
      </el-table-column>
      <el-table-column label="角色名称">
        <template #default="scope">
          <div class="f-c-s flex-wrap flex-gap-2">
            <el-tag v-for="item in scope.row.roles" v-if="scope.row.roles?.length > 0">
              {{ item.name }}
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="角色备注">
        <template #default="scope">
          <div v-for="(item, index) in scope.row.roles" v-if="scope.row.roles?.length > 0">
            {{ index + 1 }}. {{ item.remark }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机" />
      <el-table-column prop="email" label="邮箱" />
    </el-table>
    <el-dialog
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      v-model="addRoleDialog"
      width="40%"
      title="为管理员分配角色">
      <RemarkText class="mb-4" title="管理员列表" sub="单击选中下列某一个管理员为其分配角色" />
      <el-table
        highlight-current-row
        stripe
        border
        @current-change="(val: AdminModel) => selectedCurrAdminRow = val"
        :data="adminList.list">
        <el-table-column label="头像">
          <template #default="scope">
            <img :src="scope.row.avatar" class="rd-50% w-10 h-10" />
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column sortable prop="createDate" label="创建日期">
          <template #default="scope">
            {{ DateUtil.formatted(scope.row.createDate) }}
          </template>
        </el-table-column>
      </el-table>
      <div class="mt-5 f-c-e">
        <el-pagination
          small
          background
          v-model:current-page="roleCurrPage"
          v-model:page-size="rolePageSize"
          @size-change="handleRoleSizeChange"
          @current-change="handleRoleCurrentChange"
          layout="sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 15, 20]"
          :total="adminList.total" />
      </div>
      <div class="mt-5">
        <RemarkText
          class="mb-4"
          title="角色列表"
          sub="单击选中下列某一个角色，以给选中的管理员分配角色" />
        <el-table
          highlight-current-row
          @current-change="(val: RoleModel) =>  selectedCurrRoleRow = val"
          :data="roleList.list">
          <el-table-column label="权限列表" type="expand" width="120">
            <template #default="scope">
              <div class="m-5 font-bold">权限列表</div>
              <div class="m-5">
                <el-table border :data="scope.row.authorities">
                  <el-table-column prop="id" label="权限 ID"></el-table-column>
                  <el-table-column prop="name" label="权限备注"></el-table-column>
                  <el-table-column prop="value" label="权限值"></el-table-column>
                </el-table>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="角色 ID"> </el-table-column>
          <el-table-column prop="remark" label="角色备注"> </el-table-column>
          <el-table-column prop="name" label="角色名称"> </el-table-column>
        </el-table>
      </div>
      <template #footer>
        <el-button size="small" @click="addRoleDialog = false">取消</el-button>
        <el-button size="small" type="primary" @click="confirmAddRoleToAdmin">确认</el-button>
      </template>
    </el-dialog>
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
