<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/28
 */

import { Requests } from "@common/requests";
import { DateUtil, ElFormUtil } from "@common/utils";

const authList = ref<PageInfo<AuthorityModel>>();

const pageSize = ref(10);
const currPage = ref(1);

async function fetchDataList() {
  authList.value = await Requests.Permission.findAuthorityList({
    pageSize: pageSize.value,
    currPage: currPage.value,
    ...findFormData
  });
}

function handleDeleteAuth(item: AuthorityModel) {
  Requests.Permission.dropAuthority({ id: item.id }, async () => {
    await fetchDataList();
  });
}

function handleEdit(item: any) {
  Requests.Permission.upgradeAuthority(item, () => {
    item.editRow = false;
  });
}

const addAuthDialog = ref(false);
const addAuthFormEl = ref();
const addAuthFormData = reactive({
  name: "",
  value: ""
});
const addAuthFormRules = reactive({
  name: [
    {
      required: true,
      message: "必须填入权限备注",
      trigger: "blur"
    }
  ],
  value: [
    {
      required: true,
      message: "必须填入权限值",
      trigger: "blur"
    }
  ]
});

function addAuth() {
  ElFormUtil.validate(addAuthFormEl.value, () => {
    Requests.Permission.addAuthority(addAuthFormData, async () => {
      await fetchDataList();
      addAuthDialog.value = false;
    });
  });
}

const findFormData = reactive({
  keyword: ""
});

await fetchDataList();
</script>

<template>
  <div>
    <RemarkText class="mb-4" sub="备注: 管理权限。例如，新增权限、查询权限、编辑权限等。" />
    <div class="mb-5 f-c-s">
      <el-button @click="addAuthDialog = true" type="primary" size="small">新增权限</el-button>
    </div>
    <div class="mb-4">
      <el-form>
        <el-form-item label="搜索">
          <el-input
            @keydown.enter="async () => await fetchDataList()"
            v-model="findFormData.keyword"
            placeholder="输入权限备注或权限值关键字以搜索" />
        </el-form-item>
      </el-form>
    </div>
    <el-table border stripe row-key="id" :data="authList.list">
      <el-table-column sortable prop="id" label="权限 ID"></el-table-column>
      <el-table-column label="权限备注">
        <template #default="scope">
          <span v-if="!scope.row.editRow">
            {{ scope.row.name }}
          </span>
          <el-input v-else v-model="scope.row.name" />
        </template>
      </el-table-column>
      <el-table-column label="权限值" width="400">
        <template #default="scope">
          <span v-if="!scope.row.editRow">
            {{ scope.row.value }}
          </span>
          <el-input v-else v-model="scope.row.value" />
        </template>
      </el-table-column>
      <el-table-column sortable label="创建日期">
        <template #default="scope">
          {{ DateUtil.formatted(scope.row.createDate) }}
        </template>
      </el-table-column>
      <el-table-column sortable label="修改日期">
        <template #default="authScope">
          {{ DateUtil.formatted(authScope.row.modifyDate) }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-popconfirm title="确定是否要删除该权限？" @confirm="handleDeleteAuth(scope.row)">
            <template #reference>
              <el-button size="small" type="danger" plain>删除</el-button>
            </template>
          </el-popconfirm>
          <el-button
            v-if="!scope.row.editRow"
            type="primary"
            size="small"
            plain
            @click="scope.row.editRow = true">
            编辑
          </el-button>
          <el-button v-else type="success" size="small" plain @click="handleEdit(scope.row)">
            完成编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="mt-5 f-c-e">
      <el-pagination
        background
        v-model:current-page="currPage"
        v-model:page-size="pageSize"
        @size-change="async () => await fetchDataList()"
        @current-change="async () => await fetchDataList()"
        layout="sizes, prev, pager, next, jumper"
        :page-sizes="[10, 15, 20, 25, 30]"
        :total="authList.total" />
    </div>
    <el-dialog
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      v-model="addAuthDialog"
      title="添加权限"
      width="35%">
      <el-form
        label-position="left"
        label-width="100px"
        :rules="addAuthFormRules"
        :model="addAuthFormData"
        ref="addAuthFormEl">
        <el-form-item label="权限备注" prop="name">
          <el-input placeholder="请输入权限备注" v-model="addAuthFormData.name" />
        </el-form-item>
        <el-form-item label="权限值" prop="value">
          <el-input
            placeholder="请输入权限值，例如 sys:find:permission:upgrade"
            v-model="addAuthFormData.value" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="danger" plain @click="addAuthDialog = false">取消</el-button>
        <el-button type="primary" @click="addAuth">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
