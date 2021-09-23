package org.korov.springboot_security.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author korov
 * @since 2021-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String id;

    /**
     * 用户账户
     */
    private String username;

    /**
     * 用户密码
     */
    @TableField("`password`")
    private String password;

    /**
     * 用户状态（0：正常 1：禁用）
     */
    @TableField("`enable`")
    private Boolean enable;

    /**
     * 姓名
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别 (0: 未知 1: 男 2: 女)
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 锁定状态
     */
    private Boolean locked;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 部门编号
     */
    private String deptId;

    /**
     * 岗位编号
     */
    private String postId;

    /**
     * 签名
     */
    private String sign;


    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String ENABLE = "enable";

    public static final String NICKNAME = "nickname";

    public static final String AVATAR = "avatar";

    public static final String GENDER = "gender";

    public static final String EMAIL = "email";

    public static final String PHONE = "phone";

    public static final String DELETED = "deleted";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    public static final String LOCKED = "locked";

    public static final String REMARK = "remark";

    public static final String TENANT_ID = "tenant_id";

    public static final String DEPT_ID = "dept_id";

    public static final String POST_ID = "post_id";

    public static final String SIGN = "sign";

}
