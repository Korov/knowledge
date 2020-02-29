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
          { min: 5, max: 15, message: '长度在 5 到 15 个字x符', trigger: 'blur' }
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
