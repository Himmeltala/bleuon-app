<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/22
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { PermissionHttp } from "@common/requests";

const currPage = ref(1);
const pageSize = ref(10);

const mainList = ref<PageInfo<AdminModel>>();

async function fetchDataList() {
  mainList.value = await PermissionHttp.findAllAdminWithRoleAndAuthorityList({
    pageSize: pageSize.value,
    currPage: currPage.value
  });
}

await fetchDataList();
</script>

<template>
  <div>
    <RemarkText class="mb-4" title="当前页面备注" sub="维护后台管理系统的管理员的角色分组" />
    <div class="f-c-e mb-5"></div>
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
  </div>
</template>

<style scoped lang="scss"></style>
