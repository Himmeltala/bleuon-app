<script setup lang="ts">
/**
 * @description
 * @author zheng
 * @since 2023/10/24
 */

import { Requests } from "@common/requests";
import { DateUtil, ElFormUtil, PagerUtil } from "@common/utils";

const currPage = ref(1);
const pageSize = ref(10);
const mainList = shallowRef<PageInfo<RoleModel>>();

async function fetchDataList() {
  mainList.value = await Requests.Permission.findRolesWithoutAuthorityList({
    pageSize: pageSize.value,
    currPage: currPage.value
  });
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
    Requests.Permission.addRole(addRoleFormData.value, async () => {
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

function openEditDialog(item: RoleModel) {
  editRoleDialog.value = true;
  editRoleFormData.value = item;
}

function handleEditRole() {
  ElFormUtil.validate(editRoleFormEl.value, () => {
    console.log(editRoleFormData.value);
    Requests.Permission.upgradeRole(editRoleFormData.value, async () => {
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

function handleDeleteRole(item: RoleModel) {
  Requests.Permission.deleteRole({ id: item.id }, async () => {
    await fetchDataList();
  });
}

function onExpandAuthChange(item: any) {
  item.loading = true;
  if (!item.hasGetAuthorities) {
    Requests.Permission.findAuthoritiesOfRole(
      { roleId: item.id, pageSize: 10, currPage: 1 },
      data => {
        item.authorities = data;
        item.pageSize = 10;
        item.currPage = 1;
        item.hasGetAuthorities = true;
        item.loading = false;
      }
    );
  } else {
    item.loading = false;
  }
}

function fetchAuthorityListOfRole(item: any) {
  item.loading = true;
  Requests.Permission.findAuthoritiesOfRole(
    { roleId: item.id, pageSize: item.pageSize, currPage: item.currPage },
    data => {
      item.authorities = data;
      item.hasGetAuthorities = true;
      item.loading = false;
    }
  );
}

const addRoleAuthItem = ref();
const addRoleAuthDialog = ref(false);

const authIdList = ref([]);
const roleAuthList = ref([]);

const addRoleAuthCurrPage = ref(1);
const addRoleAuthPageSize = ref(5);
const calcRoleAuthList = PagerUtil.paginate(roleAuthList, addRoleAuthCurrPage, addRoleAuthPageSize);

function openAddRoleAuthDialog(item: any) {
  addRoleAuthItem.value = item;
  Requests.Permission.findNoRepeatAuthorityListOfRole({ roleId: item.id }, data => {
    roleAuthList.value = data;
    addRoleAuthDialog.value = true;
  });
}

function handleAddRoleAuthSelectionChange(val: AuthorityModel[]) {
  authIdList.value = val.map(i => i.id);
}

function handleAddRoleAuth() {
  Requests.Permission.addAuthorityListToRole(
    { roleId: addRoleAuthItem.value.id, authIds: authIdList.value },
    () => {
      addRoleAuthItem.value.loading = true;
      Requests.Permission.findAuthoritiesOfRole(
        {
          roleId: addRoleAuthItem.value.id,
          pageSize: addRoleAuthItem.value.pageSize,
          currPage: addRoleAuthItem.value.currPage
        },
        data => {
          addRoleAuthItem.value.authorities = data;
          addRoleAuthItem.value.hasGetAuthorities = true;
          addRoleAuthItem.value.loading = false;
          addRoleAuthDialog.value = false;
        }
      );
    }
  );
}

function handleDeleteAuth(authItem: any, roleItem: any) {
  Requests.Permission.deleteRoleAuthority({ roleId: roleItem.id, authId: authItem.id }, () => {
    fetchAuthorityListOfRole(roleItem);
  });
}

await fetchDataList();
</script>

<template>
  <div>
    <RemarkText class="mb-4" sub="备注: 管理角色和权限。例如，新增角色、维护角色下的权限列表。" />
    <div class="f-c-e mb-5">
      <div class="add-role">
        <el-button @click="addRoleDialog = true" size="small" type="primary">新增角色</el-button>
      </div>
    </div>
    <el-table stripe border row-key="id" @expand-change="onExpandAuthChange" :data="mainList.list">
      <el-table-column label="权限列表" type="expand" width="120">
        <template #default="scope">
          <div class="mx-20 my-5">
            <RemarkText class="mb-4" title="权限列表" sub="该角色所拥有的权限" />
            <div class="f-c-e mb-4">
              <el-button
                plain
                type="primary"
                size="small"
                @click="openAddRoleAuthDialog(scope.row)">
                添加权限
              </el-button>
            </div>
            <div v-if="scope.row.authorities?.list">
              <el-table
                border
                row-key="id"
                v-loading="scope.row.loading"
                :data="scope.row.authorities.list">
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
                <el-table-column label="操作">
                  <template #default="authScope">
                    <el-popconfirm
                      title="确定是否要删除该权限？"
                      @confirm="handleDeleteAuth(authScope.row, scope.row)">
                      <template #reference>
                        <el-button size="small" type="danger" plain>删除</el-button>
                      </template>
                    </el-popconfirm>
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
                  :total="scope.row.authorities.total" />
              </div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="id" label="角色 ID" />
      <el-table-column label="角色名称">
        <template #default="scope">
          <el-tag>
            {{ scope.row.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="角色备注"></el-table-column>
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
    <div class="mt-5 f-c-e">
      <el-pagination
        small
        background
        v-model:current-page="currPage"
        v-model:page-size="pageSize"
        @size-change="async () => await fetchDataList()"
        @current-change="async () => await fetchDataList()"
        layout="sizes, prev, pager, next, jumper"
        :page-sizes="[10, 15, 20, 25, 30]"
        :total="mainList.total" />
    </div>
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
      width="60%"
      title="添加权限">
      <el-table
        stripe
        row-key="id"
        border
        :data="calcRoleAuthList"
        @selection-change="handleAddRoleAuthSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column sortable label="权限 ID" prop="id" />
        <el-table-column label="权限名称" prop="name" />
        <el-table-column label="权限值" prop="value" />
      </el-table>
      <div class="mt-5 f-c-e">
        <el-pagination
          small
          background
          v-model:current-page="addRoleAuthCurrPage"
          v-model:page-size="addRoleAuthPageSize"
          layout="sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 15, 20, 25, 30]"
          :total="roleAuthList.length" />
      </div>
      <template #footer>
        <el-button @click="addRoleAuthDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAddRoleAuth">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
