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
                    <el-tag v-for="(subRole1) in subRole.roles" :key="subRole1.id" closable @close="removeRoleById(scope.row, subRole1.id)">{{subRole1.roleName}}</el-tag>
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
              <el-button type="warning" icon="el-icon-setting" size="mini" @click="showRoleDialog(scope.row)"></el-button>
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
      <el-tree :data="rolesTree" :props="roleProps" show-checkbox node-key="id" default-expand-all :default-checked-keys="defKeys"></el-tree>
      <!-- 底部两个按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="userDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRoles">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'users',
  data() {
    // 自定义的验证邮箱正则
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (regEmail.test(value)) {
        // 校验成功
        return callback()
      }
      callback(new Error('请输入合法的邮箱'))
    }
    // 自定义的验证电话正则
    var checkPhone = (rule, value, callback) => {
      const regPhone = /^1(3|4|5|7|8)\d{9}$/
      if (regPhone.test(value)) {
        // 校验成功
        return callback()
      }
      callback(new Error('请输入合法的电话'))
    }
    return {
      // 获取用户列表的参数对象
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      userList: [],
      total: 0,
      tableHeight: 580,
      userDialogVisible: false,
      roleDialogVisible: false,
      // 添加的用户
      userForm: {
        id: '',
        name: 'user1',
        age: 20,
        nickname: 'user1',
        cardno: '123453246532456434',
        email: 'demo@demo.com',
        phone: '13131313131',
        pwd: 'password',
        status: 0
      },
      defKeys: [1, 11],
      // 用户验证规则
      userFormRules: {
        // 验证用户名
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        age: [
          { type: 'number', required: true, min: 0, max: 100, message: '请输入年龄(1-100)' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 5, max: 15, message: '长度在 5 到 15 个字符', trigger: 'blur' }
        ],
        cardno: [
          { required: true, message: '请输入身份证号码', trigger: 'blur' },
          { min: 18, max: 18, message: '长度为 18 个字符', trigger: 'blur' }
        ],
        // 验证密码
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 15, message: '长度在 5 到 15 个字符', trigger: 'blur' }
        ],
        // 验证邮箱
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ],
        // 验证电话
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' }
        ],
        // 验证电话
        status: [
          { required: true, message: '请输入用户状态', trigger: 'blur' },
          { type: 'number', min: 0, max: 1, message: '0：可用，1：不可用', trigger: ['blur', 'change'] }
        ]
      },
      // 用户角色信息
      rolesTree: {},
      roleProps: {
        children: 'roles',
        label: 'roleName'
      }
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    async getUserList() {
      const { data: resultVo } = await this.$http.get('users', { params: this.queryInfo })
      if (resultVo.code !== 1) return this.$message.error(resultVo.description)
      this.userList = resultVo.data[0].contets
      this.userList.forEach(element => {
        element.status = (element.status === 0)
      })
      this.total = resultVo.data[0].total
    },
    // 监听pagesize改变的事件
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize
      this.getUserList()
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage
      this.getUserList()
    },
    // 监听用户状态的改变
    async userStatusChanged(userInfo) {
      var statusValue = userInfo.status === true ? '0' : '1'
      const { data: resultVo } = await this.$http.post('adduser', {
        id: userInfo.id,
        status: statusValue
      })
      if (resultVo.code !== 1) return this.$message.error(resultVo.description)
      this.$message.success('用户状态修改成功')
      this.userDialogVisible = false
      this.getUserList()
    },
    // 监听对话框关闭事件
    addDialogClosed() {
      this.$refs.userFormRef.resetFields()
    },
    // 监听对话框关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields()
    },
    // 添加用户
    addUser() {
      this.$refs.userFormRef.validate(async valid => {
        if (!valid) return
        const { data: resultVo } = await this.$http.post('adduser', this.userForm)
        if (resultVo.code !== 1) return this.$message.error(resultVo.description)
        this.$message.success('添加用户成功')
        this.userDialogVisible = false
        this.getUserList()
      })
    },
    // 显示修改对话框
    showEditDialog(userInfo) {
      this.userForm.id = userInfo.id
      this.userForm.age = userInfo.age
      this.userForm.name = userInfo.name
      this.userForm.email = userInfo.email
      this.userForm.phone = userInfo.phone
      this.userForm.nickname = userInfo.nickname
      this.userDialogVisible = true
    },
    // 显示角色修改对话框
    showRoleDialog(userInfo) {
      console.log(userInfo)
      this.rolesTree = userInfo.roles
      this.roleDialogVisible = true
    },
    // 关闭角色修改对话框
    roleDialogClosed() {
      this.roleDialogVisible = false
    },
    // 角色修改的确定
    updateRoles() {
      this.roleDialogVisible = false
    },
    // 修改用户信息
    editUser() {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return
        console.log('edit success')
        console.log(this.editForm)
        this.userDialogVisible = false
      })
    },
    // 删除用户信息
    async removeUser(userId) {
      const confirmResult = await this.$confirm('此操作将永久删除此用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('取消了删除')
      }
      // 实际用该调用接口删除
      const { data: resultVo } = await this.$http.delete('deleteuser', {
        params: {
          id: userId
        }
      })
      if (resultVo.code !== 1) return this.$message.error(resultVo.description)
      this.$message.success('删除用户成功')
      this.getUserList()
    },
    // 删除用户权限
    async removeRoleById(userInfo, roleId) {
      const confirmResult = await this.$confirm('删除用户权限, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('取消了删除')
      }
      // 实际应该调用借口
      console.log('删除了用户的权限')

      // 直接赋值防止界面刷新
      // userInfo.roles = userInfo.roles
    }
  }
}
</script>

<style lang="less" scoped>
.el-tag {
  margin: 10px;
}

.bdtop {
  border-top: 1px solid #eeeeee;
}

.bdbottom {
  border-bottom: 1px solid #eeeeee;
}
</style>
