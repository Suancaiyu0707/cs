package com.casaba.mer.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MerOrderBean implements Serializable {
    private static final long serialVersionUID = 1L;
	/**
	 * 商户id
	 */
    private Integer merId;
    
    /**
     	* 产品id
     */
    private Integer mdseId;
    /**
     *	 产品名称
     */
    private Integer mdseName;
    /**
     	* 销售id
     */
    private Integer saleId;
    /**
     *	 代理商id
     */
    private Integer agentId;
    
    
    /**
	 *	代理商 消耗金额
	 */
    private BigDecimal consumePrice;
    /**
     *	 套餐售价
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
     * 	操作人
     */
    private String operator;
    
    

	public Integer getMerId() {
		return merId;
	}

	public void setMerId(Integer merId) {
		this.merId = merId;
	}

	public Integer getMdseId() {
		return mdseId;
	}

	public void setMdseId(Integer mdseId) {
		this.mdseId = mdseId;
	}


	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
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

	public BigDecimal getConsumePrice() {
		return consumePrice;
	}

	public void setConsumePrice(BigDecimal consumePrice) {
		this.consumePrice = consumePrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getMdseName() {
		return mdseName;
	}

	public void setMdseName(Integer mdseName) {
		this.mdseName = mdseName;
	}

    
    
    
    

}