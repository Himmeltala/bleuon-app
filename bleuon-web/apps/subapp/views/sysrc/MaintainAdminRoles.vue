<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/22
 */

import { Requests } from "@common/requests";
import { DateUtil } from "@common/utils";

const currPage = ref(1);
const pageSize = ref(10);

const mainList = ref<PageInfo<AdminModel>>();

async function fetchDataList() {
  mainList.value = await Requests.Permission.findAdminsWithRoleListWithoutAuthorityList({
    pageSize: pageSize.value,
    currPage: currPage.value
  });
}

async function onExpandAuthorityListOfRoleItem(item: any) {
  item.loading = true;
  if (!item.hasGetAuthorities) {
    Requests.Permission.findAuthoritiesOfRole({ adminId: item.id, pageSize: 10, currPage: 1 }, data => {
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

async function fetchAuthorityListOfRole(item: any) {
  item.loading = true;
  Requests.Permission.findAuthoritiesOfRole(
    {
      adminId: item.id,
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

function handleDelete(item: AdminModel) {
  Requests.Permission.deleteRoleOfAdmin({ username: item.username }, async () => {
    await fetchDataList();
  });
}

const editAdminDialog = ref(false);
const currEditRole = ref<AdminModel>();

function openEditAdminDialog(item: AdminModel) {
  if (!item.roles) {
    ElMessage.warning("该管理员没有分配角色！");
  } else {
    editAdminDialog.value = true;
    currEditRole.value = item;
  }
}

function handleRemoveRole(item: RoleModel) {
  Requests.Permission.deleteRoleOfAdmin(
    { roleId: item.id, adminId: currEditRole.value.id, username: currEditRole.value.username },
    async () => {
      await fetchDataList();
      editAdminDialog.value = false;
    }
  );
}

await fetchDataList();
</script>

<template>
  <div>
    <RemarkText class="mb-4" sub="备注: 维护管理员角色" />
    <div class="f-c-e mb-5"></div>
    <el-table
      @expand-change="onExpandAuthorityListOfRoleItem"
      row-key="id"
      stripe
      border
      :data="mainList.list"
      style="width: 100%">
      <el-table-column label="权限列表" type="expand" width="100">
        <template #default="scope">
          <div class="mx-20 my-5">
            <RemarkText class="mb-4" title="其余信息" sub="该管理员的其余信息" />
            <div class="mb-10">
              <div class="mb-2">ID：{{ scope.row.id }}</div>
              <div class="mb-2">手机号：{{ scope.row.phone }}</div>
              <div class="mb-2">邮箱：{{ scope.row.email }}</div>
              <div class="mb-2">注册日期： {{ DateUtil.formatted(scope.row.createDate) }}</div>
              <div>修改日期： {{ DateUtil.formatted(scope.row.modifyDate) }}</div>
            </div>
            <div v-if="scope.row.authorities?.list">
              <RemarkText class="mb-4" title="权限列表" sub="该角色所拥有的权限" />
              <el-table border :data="scope.row.authorities.list">
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
      <el-table-column label="头像" width="100">
        <template #default="scope">
          <img :src="scope.row.avatar" class="rd-50% w-10 h-10" />
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" />
      <el-table-column label="角色名称">
        <template #default="scope">
          <div class="f-c-s flex-wrap flex-gap-2">
            <el-tag v-for="item in scope.row.roles" v-if="scope.row.roles?.length > 0">
              {{ item.name }}
            </el-tag>
            <div v-else>该用户或管理员没有分配角色</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="角色备注">
        <template #default="scope">
          <div v-for="(item, index) in scope.row.roles" v-if="scope.row.roles?.length > 0">
            {{ index + 1 }}. {{ item.remark }}
          </div>
          <div v-else>无</div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-popconfirm title="确定是否要删除该管理员？" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button size="small" type="danger" plain>删除</el-button>
            </template>
          </el-popconfirm>
          <el-button size="small" type="primary" plain @click="openEditAdminDialog(scope.row)">
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="mt-5 f-c-e">
      <el-pagination
        v-model:current-page="currPage"
        v-model:page-size="pageSize"
        @size-change="async () => await fetchDataList()"
        @current-change="async () => await fetchDataList()"
        layout="sizes, prev, pager, next, jumper"
        :page-sizes="[5, 10, 15, 20]"
        background
        :total="mainList.total" />
    </div>
    <el-dialog v-model="editAdminDialog" title="编辑管理员的角色" width="35%">
      <RemarkText class="mb-6" :title="currEditRole.username" sub="该管理员目前拥有的角色" />
      <div class="f-c-s flex-gap-2 flex-wrap">
        <el-tag
          v-for="item in currEditRole.roles"
          closable
          size="large"
          :key="item.id"
          @close="handleRemoveRole(item)">
          {{ item.name }}
        </el-tag>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
