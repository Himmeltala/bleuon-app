<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/22
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { AuthorityHttp } from "@common/requests";
import { DateUtil } from "@common/utils";

const mainList = ref(await AuthorityHttp.findAllAdmin({ pageSize: 1, currPage: 10 }));
</script>

<template>
  <div>
    <div>
      <el-table stripe :data="mainList.list" border style="width: 100%">
        <el-table-column label="用户权限" type="expand" width="120">
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
        <el-table-column prop="createDate" label="注册日期">
          <template #default="scope">
            {{ DateUtil.formatted(scope.row.createDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="modifyDate" label="修改日期">
          <template #default="scope">
            <el-popover placement="top" width="auto">
              <template #default>
                <div>该用户距离上一次更新日期</div>
              </template>
              <template #reference>
                {{ DateUtil.formatted(scope.row.modifyDate) }}
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="角色备注">
          <template #default="scope">
            <el-tag v-if="scope.row.role.name === '超级管理员'" type="danger">
              {{ scope.row.role.name }}
            </el-tag>
            <el-tag v-else-if="scope.row.role.name === '管理员'">
              {{ scope.row.role.name }}
            </el-tag>
            <el-tag v-else type="success">
              {{ scope.row.role.name }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="role.value" label="角色值"></el-table-column>
        <el-table-column prop="role.id" label="角色 ID" />
      </el-table>
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
