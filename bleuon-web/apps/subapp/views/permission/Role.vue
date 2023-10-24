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

const mainList = shallowRef<PageInfo<RoleModel>>();

async function fetchDataList() {
  mainList.value = await PermissionHttp.findAllRole({
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

function handleDeleteRole(row: RoleModel) {
  PermissionHttp.deleteRole({ id: row.id }, async () => {
    await fetchDataList();
  });
}

const authPageSize = ref(10);
const authCurrPage = ref(1);

function onExpandChange(item: any) {
  authPageSize.value = 10;
  authCurrPage.value = 1;
  item.loading = true;
  if (!item.hasGetAuthorities) {
    PermissionHttp.findRoleAuthorityList({ roleId: item.id }, data => {
      item.authorities = data;
      item.hasGetAuthorities = true;
      item.loading = false;
    });
  } else {
    item.loading = false;
  }
}

function handleAuthSizeChange(item: any) {
  item.loading = true;
  PermissionHttp.findRoleAuthorityList(
    { roleId: item.id, pageSize: authPageSize.value, currPage: authCurrPage.value },
    data => {
      item.authorities = data;
      item.hasGetAuthorities = true;
      item.loading = false;
    }
  );
}

function handleAuthCurrentChange(item: any) {
  item.loading = true;
  PermissionHttp.findRoleAuthorityList(
    { roleId: item.id, pageSize: authPageSize.value, currPage: authCurrPage.value },
    data => {
      item.authorities = data;
      item.hasGetAuthorities = true;
      item.loading = false;
    }
  );
}

const addRoleAuthDialog = ref(false);
const addRoleAuthFormData = ref({
  values: []
});
const addRoleAuthFormRules = ref({
  values: [{ required: true, message: "请选择权限！", trigger: "blur" }]
});
const addRoleAuthFormEl = ref();
const addRoleAuthsList = ref<AuthorityModel[]>([]);
const addRoleAuthItem = ref();
const addRoleAuthIndex = ref(0);

function openAddRoleDialog(index: number, item: any) {
  addRoleAuthItem.value = item;
  addRoleAuthIndex.value = index;
  PermissionHttp.findAllAuthorityList({ roleId: item.id }, data => {
    addRoleAuthsList.value = data;
    addRoleAuthDialog.value = true;
  });
}

function handleAddRoleAuth() {
  ElFormUtil.validate(addRoleAuthFormEl.value, () => {
    PermissionHttp.addAuthorityListToRole(
      { roleId: addRoleAuthItem.value.id, authIds: addRoleAuthFormData.value.values },
      () => {
        const item = mainList.value.list[addRoleAuthIndex.value] as any;
        item.loading = true;
        PermissionHttp.findRoleAuthorityList(
          {
            roleId: addRoleAuthItem.value.id,
            pageSize: authPageSize.value,
            currPage: authCurrPage.value
          },
          data => {
            item.authorities = data;
            item.hasGetAuthorities = true;
            item.loading = false;
            addRoleAuthDialog.value = false;
            addRoleAuthFormData.value.values = [];
          }
        );
      }
    );
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
    <el-table
      stripe
      border
      @expand-change="onExpandChange"
      :data="mainList.list"
      style="width: 100%">
      <el-table-column label="权限列表" type="expand" width="120">
        <template #default="scope">
          <div class="m-5 f-c-e">
            <div>
              <el-button
                type="primary"
                size="small"
                plain
                @click="openAddRoleDialog(scope.$index, scope.row)">
                添加权限
              </el-button>
            </div>
          </div>
          <div class="m-5 font-bold">权限列表</div>
          <div class="m-5" v-if="scope.row.authorities?.list">
            <el-table v-loading="scope.row.loading" border :data="scope.row.authorities.list">
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
            <div class="mt-5 f-c-e">
              <el-pagination
                small
                v-model:current-page="authCurrPage"
                v-model:page-size="authPageSize"
                @size-change="handleAuthSizeChange(scope.row)"
                @current-change="handleAuthCurrentChange(scope.row)"
                layout="sizes, prev, pager, next, jumper"
                :page-sizes="[5, 10]"
                :total="scope.row.authorities.total" />
            </div>
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
          <el-popconfirm title="确定是否要删除该角色？" @confirm="handleDeleteRole(scope.row)">
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
    <el-dialog
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      v-model="addRoleAuthDialog"
      width="40%"
      title="添加权限">
      <el-form
        ref="addRoleAuthFormEl"
        :rules="addRoleAuthFormRules"
        :model="addRoleAuthFormData"
        label-position="left"
        label-width="100px">
        <el-form-item prop="values" label="选择权限">
          <el-select v-model="addRoleAuthFormData.values" multiple placeholder="请选择权限">
            <el-option
              v-for="item in addRoleAuthsList"
              :key="item.value"
              :label="item.name"
              :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addRoleAuthDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAddRoleAuth">确定</el-button>
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
