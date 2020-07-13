package com.elling.book.sys.accbook.model;

import javax.persistence.*;

@Table(name = "acc_book")
public class AccBook {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账本类型 0-收入 1-支出
     */
    private String type;

    /**
     * 金额
     */
    private String money;

    /**
     * 记账时间
     */
    private String time;

    /**
     * 备注
     */
    private String mark;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 类别 001-生活 002-蔬菜等
     */
    private String category;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取账本类型 0-收入 1-支出
     *
     * @return type - 账本类型 0-收入 1-支出
     */
    public String getType() {
        return type;
    }

    /**
     * 设置账本类型 0-收入 1-支出
     *
     * @param type 账本类型 0-收入 1-支出
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取金额
     *
     * @return money - 金额
     */
    public String getMoney() {
        return money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 获取记账时间
     *
     * @return time - 记账时间
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置记账时间
     *
     * @param time 记账时间
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取备注
     *
     * @return mark - 备注
     */
    public String getMark() {
        return mark;
    }

    /**
     * 设置备注
     *
     * @param mark 备注
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * 获取类别 001-生活 002-蔬菜等
     *
     * @return category - 类别 001-生活 002-蔬菜等
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置类别 001-生活 002-蔬菜等
     *
     * @param category 类别 001-生活 002-蔬菜等
     */
    public void setCategory(String category) {
        this.category = category;
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
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    
}