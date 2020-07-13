package com.elling.book.sys.sys.model;

import javax.persistence.*;

@Table(name = "sys_config")
public class SysConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 键
     */
    @Column(name = "param_key")
    private String paramKey;

    /**
     * 值
     */
    @Column(name = "param_value")
    private String paramValue;

    /**
     * 状态  0：隐藏  1：显示
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

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
     * 获取键
     *
     * @return param_key - 键
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * 设置键
     *
     * @param paramKey 键
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * 获取值
     *
     * @return param_value - 值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 设置值
     *
     * @param paramValue 值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 获取状态  0：隐藏  1：显示
     *
     * @return status - 状态  0：隐藏  1：显示
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态  0：隐藏  1：显示
     *
     * @param status 状态  0：隐藏  1：显示
     */
    public void setStatus(Byte status) {
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