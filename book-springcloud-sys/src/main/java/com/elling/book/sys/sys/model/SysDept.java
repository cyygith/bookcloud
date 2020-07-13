package com.elling.book.sys.sys.model;

import javax.persistence.*;

@Table(name = "sys_dept")
public class SysDept {
    /**
     * 部门id
     */
    @Id
    @Column(name = "dept_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    /**
     * 上级部门ID，一级部门为0
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    @Column(name = "del_flag")
    private Byte delFlag;

    /**
     * 部门编号
     */
    @Column(name = "dept_code")
    private String deptCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有子元素
     */
    private boolean hasChildren;
    
    
    public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
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
     * 获取上级部门ID，一级部门为0
     *
     * @return parent_id - 上级部门ID，一级部门为0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置上级部门ID，一级部门为0
     *
     * @param parentId 上级部门ID，一级部门为0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取排序
     *
     * @return order_num - 排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置排序
     *
     * @param orderNum 排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取是否删除  -1：已删除  0：正常
     *
     * @return del_flag - 是否删除  -1：已删除  0：正常
     */
    public Byte getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除  -1：已删除  0：正常
     *
     * @param delFlag 是否删除  -1：已删除  0：正常
     */
    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取部门编号
     *
     * @return dept_code - 部门编号
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置部门编号
     *
     * @param deptCode 部门编号
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}