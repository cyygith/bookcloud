package com.elling.book.sys.sys.model;

import javax.persistence.*;

@Table(name = "sys_menu")
public class SysMenu {
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 授权（多个用逗号分隔，如，user:list,user:create)
     */
    private String perms;

    /**
     * 类型  0：目录  1：菜单  2：按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 状态
     */
    private String status;
    
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
     * @return menu_id
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取父菜单ID，一级菜单为0
     *
     * @return parent_id - 父菜单ID，一级菜单为0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单ID，一级菜单为0
     *
     * @param parentId 父菜单ID，一级菜单为0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单名称
     *
     * @return name - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取菜单url
     *
     * @return url - 菜单url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单url
     *
     * @param url 菜单url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取授权（多个用逗号分隔，如，user:list,user:create)
     *
     * @return perms - 授权（多个用逗号分隔，如，user:list,user:create)
     */
    public String getPerms() {
        return perms;
    }

    /**
     * 设置授权（多个用逗号分隔，如，user:list,user:create)
     *
     * @param perms 授权（多个用逗号分隔，如，user:list,user:create)
     */
    public void setPerms(String perms) {
        this.perms = perms;
    }

    /**
     * 获取类型  0：目录  1：菜单  2：按钮
     *
     * @return type - 类型  0：目录  1：菜单  2：按钮
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型  0：目录  1：菜单  2：按钮
     *
     * @param type 类型  0：目录  1：菜单  2：按钮
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取菜单图标
     *
     * @return icon - 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
}