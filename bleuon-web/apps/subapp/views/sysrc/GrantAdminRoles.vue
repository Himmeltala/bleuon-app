<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/27
 */

import { Requests } from "@common/requests";
import { DateUtil } from "@common/utils";

const adminList = ref<PageInfo<AdminModel>>();
const roleList = ref<PageInfo<RoleModel>>();

const adminCurrPage = ref(1);
const adminPageSize = ref(5);

async function fetchAdminList() {
  adminList.value = await Requests.Permission.findAdminsWithRoleAndAuthorityList({
    pageSize: adminPageSize.value,
    currPage: adminCurrPage.value
  });
}

const roleCurrPage = ref(1);
const rolePageSize = ref(5);

async function fetchRoleListButNoAuthorityList() {
  roleList.value = await Requests.Permission.findRolesWithoutAuthorityList({
    pageSize: rolePageSize.value,
    currPage: roleCurrPage.value
  });
}

function onExpandAuthorityListOfRoleItem(item: any) {
  item.loading = true;
  if (!item.hasGetAuthorities) {
    Requests.Permission.findAuthoritiesOfRole({ roleId: item.id, pageSize: 10, currPage: 1 }, data => {
      item.authorities = data;
      item.pageSize = 10;
      item.currPage = 1;
      item.hasGetAuthorities = true;
      item.loading = false;
    });
  } else {
    item.loading = false;
  }
}

function fetchAuthorityListOfRole(item: any) {
  item.loading = true;
  Requests.Permission.findAuthoritiesOfRole(
    {
      roleId: item.id,
      pageSize: item.pageSize,
      currPage: item.currPage
    },
    data => {
      item.authorities = data;
      item.hasGetAuthorities = true;
      item.loading = false;
    }
  );
}

const currAdminRow = ref<AdminModel>();
const currRoleRow = ref<RoleModel>();

function confirmAddRoleToAdmin() {
  Requests.Permission.addRoleToAdmin(
    {
      roleId: currRoleRow.value.id,
      adminId: currAdminRow.value.id,
      username: currAdminRow.value.username
    },
    async () => {
      await fetchAdminList();
    }
  );
}

await fetchAdminList();
await fetchRoleListButNoAuthorityList();
</script>

<template>
  <div>
    <RemarkText class="mb-4" sub="备注: 给当前存在的管理员分配角色" />
    <div class="f-c-e mb-10">
      <el-button
        :disabled="!currAdminRow?.id || !currRoleRow?.id"
        size="small"
        type="primary"
        @click="confirmAddRoleToAdmin">
        确认分配角色
      </el-button>
    </div>
    <RemarkText
      class="mb-4"
      title="系统已存在的管理员列表"
      sub="单击选中下列某一个管理员为其分配角色" />
    <el-table
      class="mb-4"
      stripe
      border
      row-key="id"
      highlight-current-row
      @current-change="(val: AdminModel) => currAdminRow = val"
      :data="adminList.list">
      <el-table-column label="头像" width="100">
        <template #default="scope">
          <img :src="scope.row.avatar" class="rd-50% w-10 h-10" />
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column label="已有角色">
        <template #default="scope">
          <div class="f-c-s flex-wrap flex-gap-2">
            <el-tag v-for="item in scope.row.roles" v-if="scope.row.roles?.length > 0">
              {{ item.name }}
            </el-tag>
            <div v-else>可以为该管理员分配角色</div>
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
      row-key="id"
      highlight-current-row
      :data="roleList.list"
      @expand-change="onExpandAuthorityListOfRoleItem"
      @current-change="(val: RoleModel) =>  currRoleRow = val">
      <el-table-column label="权限列表" type="expand" width="120">
        <template #default="scope">
          <div class="mx-20 my-5">
            <RemarkText class="mb-4" title="权限列表" sub="该角色所拥有的权限" />
            <div v-if="scope.row.authorities?.list">
              <el-table v-loading="scope.row.loading" border :data="scope.row.authorities.list">
                <el-table-column sortable prop="id" label="权限 ID"></el-table-column>
                <el-table-column prop="name" label="权限备注"></el-table-column>
                <el-table-column prop="value" label="权限值"></el-table-column>
                <el-table-column sortable label="创建日期">
                  <template #default="authScope">
                    {{ DateUtil.formatted(authScope.row.createDate) }}
                  </template>
                </el-table-column>
                <el-table-column sortable label="修改日期">
                  <template #default="authScope">
                    {{ DateUtil.formatted(authScope.row.modifyDate) }}
                  </template>
                </el-table-column>
              </el-table>
              <div class="mt-5 f-c-e">
                <el-pagination
                  small
                  v-model:current-page="scope.row.currPage"
                  v-model:page-size="scope.row.pageSize"
                  @size-change="fetchAuthorityListOfRole(scope.row)"
                  @current-change="fetchAuthorityListOfRole(scope.row)"
                  layout="prev, pager, next"
                  :page-sizes="[5, 10, 15, 20]"
                  :total="scope.row.authorities.total" />
              </div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="角色 ID"> </el-table-column>
      <el-table-column prop="name" label="角色名称"> </el-table-column>
      <el-table-column prop="remark" label="角色备注"> </el-table-column>
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
        v-model:current-page="roleCurrPage"
        v-model:page-size="rolePageSize"
        @size-change="async () => await fetchRoleListButNoAuthorityList()"
        @current-change="async () => await fetchRoleListButNoAuthorityList()"
        layout="sizes, prev, pager, next, jumper"
        :page-sizes="[5, 10, 15, 20]"
        :total="roleList.total" />
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
