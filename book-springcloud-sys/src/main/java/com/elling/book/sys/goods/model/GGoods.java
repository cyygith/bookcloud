package com.elling.book.sys.goods.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elling.book.sys.common.utils.UuidGen;
import com.fasterxml.jackson.annotation.JsonAlias;

import tk.mybatis.mapper.annotation.KeySql;

@Table(name = "g_goods")
public class GGoods {
	@Column(name = "ID")
    @Id
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
    @Column(name = "C_URL")
    private String cUrl;

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

    /**
     * 父id
     */
    @Column(name = "PID")
    private String pid;

    @Column(name = "T_PRICE")
    private String tPrice;

    @Column(name = "D_PRICE")
    private String dPrice;

    @Column(name = "SELL_COUNT")
    private String sellCount;

    @Column(name = "TICKET")
    private String ticket;

    private String descr;

    @Column(name = "img_url")
    private String imgUrl;
    
    @Column(name = "img_dir")
    private String imgDir;
    
    @Transient
    private String pname;
    
    
    public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getImgDir() {
		return imgDir;
	}

	public void setImgDir(String imgDir) {
		this.imgDir = imgDir;
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
     * @return C_URL - 类型
     */
    public String getcUrl() {
        return cUrl;
    }

    /**
     * 设置类型
     *
     * @param cUrl 类型
     */
    public void setcUrl(String cUrl) {
        this.cUrl = cUrl;
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

    /**
     * 获取修改时间
     *
     * @return UPDATE_TIME - 修改时间
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
     * 获取父id
     *
     * @return PID - 父id
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置父id
     *
     * @param pid 父id
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return T_PRICE
     */
    public String gettPrice() {
        return tPrice;
    }

    /**
     * @param tPrice
     */
    public void settPrice(String tPrice) {
        this.tPrice = tPrice;
    }

    /**
     * @return D_PRICE
     */
    public String getdPrice() {
        return dPrice;
    }

    /**
     * @param dPrice
     */
    public void setdPrice(String dPrice) {
        this.dPrice = dPrice;
    }

    /**
     * @return SELL_COUNT
     */
    public String getSellCount() {
        return sellCount;
    }

    /**
     * @param sellCount
     */
    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    /**
     * @return TICKET
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * @param ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * @return descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @param descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * @return img_url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}