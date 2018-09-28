package com.casaba.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SaleDetail {
    private Integer id;

    
    /**
     * 	销售名称
     */
    private String saleName;
    
    /**
     * 	商品id
     */
    private Integer mdseId;
    /**
     * 	使用年限
     */
    private Integer purchaseYear;
    /**
     * 	赠送年限
     */
    private Integer giveYear;
    /**
     * 	售价
     */
    private BigDecimal sellPrice;
    /**
     * 	创建时间
     */
    private Date createTime;
    /**
     * 	更新时间
     */
    private Date updateTime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMdseId() {
        return mdseId;
    }

    public void setMdseId(Integer mdseId) {
        this.mdseId = mdseId;
    }

    public Integer getPurchaseYear() {
        return purchaseYear;
    }

    public void setPurchaseYear(Integer purchaseYear) {
        this.purchaseYear = purchaseYear;
    }

    public Integer getGiveYear() {
        return giveYear;
    }

    public void setGiveYear(Integer giveYear) {
        this.giveYear = giveYear;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

    

}