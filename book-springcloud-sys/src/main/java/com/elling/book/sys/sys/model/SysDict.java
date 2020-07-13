package com.elling.book.sys.sys.model;

import javax.persistence.*;

@Table(name = "sys_dict")
public class SysDict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典类型值
     */
    @Column(name = "type_code")
    private String typeCode;

    /**
     * 字典类型名称
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * 字典码
     */
    private String code;

    /**
     * 代码值
     */
    @Column(name = "code_value")
    private String codeValue;

    /**
     * 状态值
     */
    private String status;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 扩展字段
     */
    @Column(name = "extend_data1")
    private String extendData1;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取字典类型值
     *
     * @return type_code - 字典类型值
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 设置字典类型值
     *
     * @param typeCode 字典类型值
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取字典类型名称
     *
     * @return type_name - 字典类型名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置字典类型名称
     *
     * @param typeName 字典类型名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取字典码
     *
     * @return code - 字典码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置字典码
     *
     * @param code 字典码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取代码值
     *
     * @return code_value - 代码值
     */
    public String getCodeValue() {
        return codeValue;
    }

    /**
     * 设置代码值
     *
     * @param codeValue 代码值
     */
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    /**
     * 获取状态值
     *
     * @return status - 状态值
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态值
     *
     * @param status 状态值
     */
    public void setStatus(String status) {
        this.status = status;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取扩展字段
     *
     * @return extend_data1 - 扩展字段
     */
    public String getExtendData1() {
        return extendData1;
    }

    /**
     * 设置扩展字段
     *
     * @param extendData1 扩展字段
     */
    public void setExtendData1(String extendData1) {
        this.extendData1 = extendData1;
    }
}