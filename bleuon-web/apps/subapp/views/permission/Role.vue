<script setup lang="ts">
/**
 * @description 角色查询
 * @author zheng
 * @since 2023/10/24
 * @link https://gitee.com/himmelbleu/bleuon-app
 */

import { PermissionHttp } from "@common/requests";
import { DateUtil, ElFormUtil } from "@common/utils";

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

const roleFormRules = reactive({
  name: [{ required: true, message: "角色名称必须填写！", trigger: "blur" }],
  remark: [{ required: true, message: "角色备注必须填写！", trigger: "blur" }]
});

const addRoleDialog = ref(false);
const addRoleFormEl = ref();
const addRoleFormData = ref<RoleModel>({
  name: "",
  remark: ""
});

function handleAddRole() {
  ElFormUtil.validate(addRoleFormEl.value, () => {
    PermissionHttp.addRole(addRoleFormData.value, async () => {
      ElFormUtil.reset(addRoleFormEl.value);
      addRoleDialog.value = false;
      await fetchDataList();
    });
  });
}

function closeAddDialog() {
  ElFormUtil.reset(addRoleFormEl.value);
  addRoleDialog.value = false;
}

const editRoleDialog = ref(false);
const editRoleFormEl = ref();
const editRoleFormData = ref<RoleModel>({
  name: "",
  remark: ""
});

function openEditDialog(row: RoleModel) {
  editRoleDialog.value = true;
  editRoleFormData.value = row;
}

function handleEditRole() {
  ElFormUtil.validate(editRoleFormEl.value, () => {
    console.log(editRoleFormData.value);
    PermissionHttp.upgradeRole(editRoleFormData.value, async () => {
      ElFormUtil.reset(editRoleFormEl.value);
      editRoleDialog.value = false;
      await fetchDataList();
    });
  });
}

function closeEditDialog() {
  ElFormUtil.reset(editRoleFormEl.value);
  editRoleDialog.value = false;
}

function handleDeleteRole(index: number, row: RoleModel) {
  PermissionHttp.deleteRole({ id: row.id }, () => {
    mainList.value.list.splice(index, 1);
  });
}

await fetchDataList();
</script>

<template>
  <div>
    <div class="f-c-e mb-5">
      <div class="add-role">
        <el-button @click="addRoleDialog = true" type="primary">新增角色</el-button>
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
          <el-tag>
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
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" plain @click="openEditDialog(scope.row)"> 修改 </el-button>
          <el-popconfirm
            title="确定是否要删除该角色？"
            @confirm="handleDeleteRole(scope.$index, scope.row)">
            <template #reference>
              <el-button size="small" type="danger" plain> 删除 </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      v-model="addRoleDialog"
      width="40%"
      title="新增角色">
      <el-form
        ref="addRoleFormEl"
        :rules="roleFormRules"
        :model="addRoleFormData"
        label-position="left"
        label-width="100px">
        <el-form-item prop="name" label="角色名称">
          <el-input placeholder="角色名称，如维护用户管理员" v-model="addRoleFormData.name" />
        </el-form-item>
        <el-form-item prop="remark" label="角色备注">
          <el-input
            type="textarea"
            placeholder="角色备注，尽可能清晰描述该角色的主要工作"
            v-model="addRoleFormData.remark" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="closeAddDialog">取消</el-button>
        <el-button type="primary" @click="handleAddRole">确定</el-button>
      </template>
    </el-dialog>
    <el-dialog
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      v-model="editRoleDialog"
      width="40%"
      title="修改角色">
      <el-form
        ref="editRoleFormEl"
        :rules="roleFormRules"
        :model="editRoleFormData"
        label-position="left"
        label-width="100px">
        <el-form-item prop="name" label="角色名称">
          <el-input placeholder="角色名称，如维护用户管理员" v-model="editRoleFormData.name" />
        </el-form-item>
        <el-form-item prop="remark" label="角色备注">
          <el-input
            type="textarea"
            placeholder="角色备注，尽可能清晰描述该角色的主要工作"
            v-model="editRoleFormData.remark" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="closeEditDialog">取消</el-button>
        <el-button type="primary" @click="handleEditRole">确定</el-button>
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
