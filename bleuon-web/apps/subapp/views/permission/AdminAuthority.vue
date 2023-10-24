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

await fetchDataList();
</script>

<template>
  <div>
    <el-table stripe :data="mainList.list" border style="width: 100%">
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
          <el-tag v-if="scope.row.role.name === '超级管理员'" type="danger">
            {{ scope.row.role.name }}
          </el-tag>
          <el-tag v-else type="info">
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
