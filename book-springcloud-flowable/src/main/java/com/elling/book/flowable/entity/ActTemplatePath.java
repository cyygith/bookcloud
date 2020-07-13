package com.elling.book.flowable.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Table(name = "act_template_path")
public class ActTemplatePath {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模板key
     */
    @Column(name = "TEMPLATE_KEY_")
    private String templateKey;

    /**
     * 模板路径
     */
    @Column(name = "TEMPLATE_PATH_")
    private String templatePath;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private String createTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取模板key
     *
     * @return TEMPLATE_KEY_ - 模板key
     */
    public String getTemplateKey() {
        return templateKey;
    }

    /**
     * 设置模板key
     *
     * @param templateKey 模板key
     */
    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    /**
     * 获取模板路径
     *
     * @return TEMPLATE_PATH_ - 模板路径
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * 设置模板路径
     *
     * @param templatePath 模板路径
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 获取状态
     *
     * @return STATUS - 状态
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
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
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
}