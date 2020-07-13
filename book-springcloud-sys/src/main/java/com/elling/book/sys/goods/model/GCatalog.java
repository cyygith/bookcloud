package com.elling.book.sys.goods.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elling.book.sys.common.utils.UuidGen;

import tk.mybatis.mapper.annotation.KeySql;

@Table(name = "g_catalog")
public class GCatalog {
    @Id
    @Column(name = "ID")
    @KeySql(genId = UuidGen.class)
    private String id;

    /**
     * 商品中文名称
     */
    @Column(name = "CNAME")
    private String cname;

    /**
     * 商品英文名称
     */
    @Column(name = "ENAME")
    private String ename;

    /**
     * 类型
     */
    @Column(name = "C_TYPE")
    private String cType;

    /**
     * 父ID
     */
    @Column(name = "PID")
    private String pid;

    /**
     * 排序
     */
    @Column(name = "C_ORDER")
    private String cOrder;

    /**
     * URL地址
     */
    @Column(name = "C_URL")
    private String cUrl;

    @Column(name = "C_LEVEL")
    private String cLevel;
    
    @Column(name = "ICON")
    private String icon;
    
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private String createTime;

    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private String updateTime;
    
    @Transient
    private String pname;
    
    
    
    
    public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取商品中文名称
     *
     * @return CNAME - 商品中文名称
     */
    public String getCname() {
        return cname;
    }

    /**
     * 设置商品中文名称
     *
     * @param cname 商品中文名称
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * 获取商品英文名称
     *
     * @return ENAME - 商品英文名称
     */
    public String getEname() {
        return ename;
    }

    /**
     * 设置商品英文名称
     *
     * @param ename 商品英文名称
     */
    public void setEname(String ename) {
        this.ename = ename;
    }

    /**
     * 获取类型
     *
     * @return C_TYPE - 类型
     */
    public String getcType() {
        return cType;
    }

    /**
     * 设置类型
     *
     * @param cType 类型
     */
    public void setcType(String cType) {
        this.cType = cType;
    }

    /**
     * 获取父ID
     *
     * @return PID - 父ID
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置父ID
     *
     * @param pid 父ID
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 获取排序
     *
     * @return C_ORDER - 排序
     */
    public String getcOrder() {
        return cOrder;
    }

    /**
     * 设置排序
     *
     * @param cOrder 排序
     */
    public void setcOrder(String cOrder) {
        this.cOrder = cOrder;
    }

    /**
     * 获取URL地址
     *
     * @return C_URL - URL地址
     */
    public String getcUrl() {
        return cUrl;
    }

    /**
     * 设置URL地址
     *
     * @param cUrl URL地址
     */
    public void setcUrl(String cUrl) {
        this.cUrl = cUrl;
    }

    /**
     * @return C_LEVEL
     */
    public String getcLevel() {
        return cLevel;
    }

    /**
     * @param cLevel
     */
    public void setcLevel(String cLevel) {
        this.cLevel = cLevel;
    }
}