package com.casaba.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MerchantOrder {
	/**
	 *	 套餐订单号
	 */
    private Integer orderId;
    /**
	 *	 套餐订单时间
	 */
    private Date orderTime;
    /**
	 *	 商户id
	 */
    private Integer merchantId;
    /**
	 *	 套餐id
	 */
    private Integer mdseId;
    /**
	 *	 套餐名称
	 */
    private String mdseName;
    /**
	 *	 购买年限
	 */
    private Integer purchaseYear;
    /**
	 *	 赠送年限
	 */
    private Integer giveYear;
    /**
	 *	 售价
	 */
    private BigDecimal sellPrice;
    /**
	 *	 套餐开始时间
	 */
    private Date startTime;
    /**
	 *	 套餐结束时间
	 */
    private Date endTime;
    /**
	 *	 订单类型 详情参考 MerOrderType 枚举类
	 */
    private String orderType;
    /**
	 *	订单状态 详情参考 MerOrderStatus 枚举类
	 */
    private String orderStatus;
    /**
	 *	 操作人
	 */
    private String operator;
    /**
	 *	 消耗金额
	 */
    private BigDecimal consumePrice;
    /**
	 *	 创建时间
	 */
    private Date createTime;
    /**
	 *	 更新时间
	 */
    private Date updateTime;



    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getMdseId() {
        return mdseId;
    }

    public void setMdseId(Integer mdseId) {
        this.mdseId = mdseId;
    }

    public String getMdseName() {
        return mdseName;
    }

    public void setMdseName(String mdseName) {
        this.mdseName = mdseName == null ? null : mdseName.trim();
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public BigDecimal getConsumePrice() {
        return consumePrice;
    }

    public void setConsumePrice(BigDecimal consumePrice) {
        this.consumePrice = consumePrice;
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
    
    
}