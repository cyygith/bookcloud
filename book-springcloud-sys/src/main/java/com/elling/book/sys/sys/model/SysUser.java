package com.elling.book.sys.sys.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 状态 0：禁用  1：启用
     */
    private Byte status;

    /**
     * 部门id
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 用户代码
     */
    @Column(name = "user_code")
    private String userCode;

    /**
     * 用户类型
     */
    @Column(name = "user_type")
    private Byte userType;
    
    /**
     * 所属部门
     */
    private List<SysUserDept> depts;
    /**
     * 所属角色
     */
    private List<SysUserRole> roles;
    
    
    public List<SysUserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysUserRole> roles) {
		this.roles = roles;
	}

	public List<SysUserDept> getDepts() {
		return depts;
	}

	public void setDepts(List<SysUserDept> depts) {
		this.depts = depts;
	}

	/**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取盐
     *
     * @return salt - 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐
     *
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取状态 0：禁用  1：启用
     *
     * @return status - 状态 0：禁用  1：启用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态 0：禁用  1：启用
     *
     * @param status 状态 0：禁用  1：启用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取部门id
     *
     * @return dept_id - 部门id
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置部门id
     *
     * @param deptId 部门id
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取用户代码
     *
     * @return user_code - 用户代码
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 设置用户代码
     *
     * @param userCode 用户代码
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取用户类型
     *
     * @return user_type - 用户类型
     */
    public Byte getUserType() {
        return userType;
    }

    /**
     * 设置用户类型
     *
     * @param userType 用户类型
     */
    public void setUserType(Byte userType) {
        this.userType = userType;
    }
}