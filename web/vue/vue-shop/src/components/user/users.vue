<template>
  <div>
    <!-- 面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 搜索框 -->
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="userDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表区域 -->
      <el-table :data="userList" stripe border :height="tableHeight">
        <el-table-column type="expand" label="展开">
          <template slot-scope="scope">
            <!-- 一级权限-->
            <el-row :class="['bdbottom', i1 === 0 ? 'bdtop' : '']" v-for="(role, i1) in scope.row.roles" :key="role.id">
              <el-col :span="5">
                <el-tag type="success">{{role.roleName}}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>
              <el-col :span="19">
                <!-- 二级权限-->
                <el-row :class="[i2 === 0 ? '' : 'bdtop']" v-for="(subRole, i2) in role.roles" :key="subRole.id">
                  <el-col :span="6">
                    <el-tag type="danger">{{subRole.roleName}}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <el-col :span="18">
                    <el-tag v-for="(subRole1) in subRole.roles" :key="subRole1.id" closable
                            @close="removeRoleById(scope.row, subRole1.id)">{{subRole1.roleName}}
                    </el-tag>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>
        </el-table-column>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="name" label="用户名" width="80px"></el-table-column>
        <el-table-column prop="age" label="年龄" width="60px"></el-table-column>
        <el-table-column prop="nickname" label="用户昵称" width="80px"></el-table-column>
        <el-table-column prop="cardno" label="身份证号码" width="150px"></el-table-column>
        <el-table-column prop="email" label="邮件" width="150px"></el-table-column>
        <el-table-column prop="phone" label="电话" width="100px"></el-table-column>
        <el-table-column prop="createtime" label="创建时间" width="220px"></el-table-column>
        <el-table-column prop="updatetime" label="更新时间" width="220px"></el-table-column>
        <el-table-column prop="status" label="状态" width="70px">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" @change="userStatusChanged(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeUser(scope.row.id)"></el-button>
            <el-tooltip effect="dark" content="修改用户信息" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini"
                         @click="showRoleDialog(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="queryInfo.pagenum"
                     :page-sizes="[10, 20, 50, 100]"
                     :page-size="queryInfo.pagesize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </el-card>
    <!-- add user dialog -->
    <el-dialog title="用户信息" :visible.sync="userDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 主体内容 -->
      <el-form :model="userForm" :rules="userFormRules" ref="userFormRef" label-width="95px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="userForm.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="userForm.age"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="userForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="身份证号码" prop="cardno">
          <el-input v-model="userForm.cardno"></el-input>
        </el-form-item>
        <el-form-item label="邮件" prop="email">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="userForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pwd">
          <el-input v-model="userForm.pwd" show-password></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-input v-model.number="userForm.status"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部两个按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="userDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- roles dialog -->
    <el-dialog title="角色分配" :visible.sync="roleDialogVisible" width="50%" @close="roleDialogClosed">
      <!-- 主体内容 -->
      <el-tree :data="rolesTree" :props="roleProps" show-checkbox node-key="id" default-expand-all
               :default-checked-keys="defKeys"></el-tree>
      <!-- 底部两个按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="userDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRoles">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script src="./users.js"></script>

<style src="./users.less" lang="less" scoped></style>
