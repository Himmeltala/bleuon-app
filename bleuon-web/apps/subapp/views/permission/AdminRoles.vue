<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/22
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { PermissionHttp } from "@common/requests";
import { DateUtil } from "@common/utils";

const currPage = ref(1);
const pageSize = ref(10);

const mainList = ref<PageInfo<AdminModel>>();

async function fetchDataList() {
  mainList.value = await PermissionHttp.findAllAdminsWithAuthorityList({
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

// TODO: 1. 查询要分配角色的管理员用户 2. 查询当前角色列表。3. 传递角色id和管理员用户id、username即可分配角色。
// TODO: 可以删除当前的管理员用户的角色、不可以修改信息

await fetchDataList();
</script>

<template>
  <div>
    <div class="mb-5 text-text-secondary text-0.9rem">描述: 维护后台管理系统的管理员的角色分组</div>
    <div class="f-c-e mb-5">
      <div class="add-role">
        <el-button @click="addRoleDialog = true" size="small" type="primary">分配角色</el-button>
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
      <el-table-column label="角色名称">
        <template #default="scope">
          <el-tag>
            {{ scope.row.role.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="role.remark" label="角色备注"></el-table-column>
      <el-table-column prop="role.id" label="角色 ID" />
      <el-table-column prop="createDate" label="注册日期">
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
    <el-dialog
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      v-model="addRoleDialog"
      width="40%"
      title="分配角色">
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
