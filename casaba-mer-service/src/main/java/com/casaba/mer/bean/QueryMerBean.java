package com.casaba.mer.bean;

import java.io.Serializable;

/**
 * 商户信息查询bean
 * @author tqlei
 *
 */
public class QueryMerBean implements Serializable {
    private static final long serialVersionUID = 1L;
   
	 /**
     *	 省
     */
    private String province;
    /**
     *	 市
     */
    private String city;
    /**
     * 	区
     */
    private String area;
    
    /**
     * 	版本
     */
	private Integer mdseId;
	
    /**
     * 版本
     */
	private Integer agentId;
	
    /**
     *	 商户名称   参考 MerStatus 类
     */
    private String merStatus;
    /**
     *	 商户类型 免费，付费
     */
    private String merType;
	
    /**
     * 	商户名称
     */
    private String merName;

    /**
     * 	手机号
     */
    private String mobile;
    /**
     * 	手机号或名称
     */
    private String mobileOrName;

    /**
     * 	当前页
     */
	private Integer page=1;
    
    /**
     *	 一页多少条数据
     */
	private Integer pageSize=20;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getMdseId() {
		return mdseId;
	}

	public void setMdseId(Integer mdseId) {
		this.mdseId = mdseId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getMerStatus() {
		return merStatus;
	}

	public void setMerStatus(String merStatus) {
		this.merStatus = merStatus;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getMerType() {
		return merType;
	}

	public void setMerType(String merType) {
		this.merType = merType;
	}

	public String getMobileOrName() {
		return mobileOrName;
	}

	public void setMobileOrName(String mobileOrName) {
		this.mobileOrName = mobileOrName;
	}
	
	

}