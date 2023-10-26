<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/27
 * @link https://gitee.com/himmelbleu/bleuon-app
 */
import { AdminHttp, PermissionHttp } from "@common/requests";
import { DateUtil } from "@common/utils";

const adminList = ref<PageInfo<AdminModel>>();
const roleList = ref<PageInfo<RoleModel>>();

const adminCurrPage = ref(1);
const adminPageSize = ref(5);

async function fetchAdminList() {
  adminList.value = await PermissionHttp.findAllAdminWithRoleAndAuthorityList({
    pageSize: adminPageSize.value,
    currPage: adminCurrPage.value
  });
}

const roleCurrPage = ref(1);
const rolePageSize = ref(5);

async function fetchRoleList() {
  roleList.value = await PermissionHttp.findAllRoleWithAuthorityList({
    pageSize: rolePageSize.value,
    currPage: roleCurrPage.value
  });
}

const selectedCurrAdminRow = ref<AdminModel>();
const selectedCurrRoleRow = ref<RoleModel>();

function confirmAddRoleToAdmin() {
  PermissionHttp.addRoleToAdmin({
    roleId: selectedCurrRoleRow.value.id,
    adminId: selectedCurrAdminRow.value.id,
    username: selectedCurrAdminRow.value.username
  });
}

await fetchAdminList();
await fetchRoleList();
</script>

<template>
  <div>
    <RemarkText class="mb-4" title="当前页面备注" sub="给当前系统已存在的管理员分配角色" />
    <div class="f-c-e mb-10">
      <el-button size="small" type="primary" @click="confirmAddRoleToAdmin">确认分配角色</el-button>
    </div>
    <RemarkText class="mb-4" title="系统已存在的管理员列表" sub="单击选中下列某一个管理员为其分配角色" />
    <el-table
      class="mb-4"
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
      <el-table-column label="已有角色">
        <template #default="scope">
          <div class="f-c-s flex-wrap flex-gap-2">
            <el-tag v-for="item in scope.row.roles" v-if="scope.row.roles?.length > 0">
              {{ item.name }}
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="创建日期">
        <template #default="scope">
          {{ DateUtil.formatted(scope.row.createDate) }}
        </template>
      </el-table-column>
      <el-table-column sortable label="修改日期">
        <template #default="scope">
          {{ DateUtil.formatted(scope.row.modifyDate) }}
        </template>
      </el-table-column>
    </el-table>
    <div class="mb-10 f-c-e">
      <el-pagination
        small
        background
        v-model:current-page="adminCurrPage"
        v-model:page-size="adminPageSize"
        @size-change="async () => await fetchAdminList()"
        @current-change="async () => await fetchAdminList()"
        layout="sizes, prev, pager, next, jumper"
        :page-sizes="[5, 10, 15, 20]"
        :total="adminList.total" />
    </div>
    <RemarkText
      class="mb-4"
      title="系统已存在的角色列表"
      sub="单击选中下列某一个角色，以给选中的管理员分配角色" />
    <el-table
      class="mb-4"
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
    <div class="mb-10 f-c-e">
      <el-pagination
        small
        background
        v-model:current-page="roleCurrPage"
        v-model:page-size="rolePageSize"
        @size-change="async () => await fetchRoleList()"
        @current-change="async () => await fetchRoleList()"
        layout="sizes, prev, pager, next, jumper"
        :page-sizes="[5, 10, 15, 20]"
        :total="roleList.total" />
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
