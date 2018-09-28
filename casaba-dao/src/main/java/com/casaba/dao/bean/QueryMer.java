package com.casaba.dao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商户信息查询bean
 * @author tqlei
 *
 */
public class QueryMer implements Serializable {
    private static final long serialVersionUID = 1L;
   
	 /**
     * 省
     */
    private List<String> provinceList;
    /**
     * 市
     */
    private List<String> cityList;
    /**
     * 区
     */
    private List<String> areaList;
    
    /**
     * 版本
     */
	private Integer mdseId;
	
    /**
     * 版本
     */
	private Integer agentId;
	
    /**
     * 商户名称
     */
    private String merStatus;
    /**
     * 商户类型 免费，付费
     */
    private String merType;
	
    /**
     	* 商户名称
     */
    private String merName;

    /**
     	* 手机号
     */
    private String mobile;
    /**
     * 	手机号或名称
     */
    private String mobileOrName;

    /**
     * 当前页
     */
	private Integer page;
    
    /**
     * 一页多少条数据
     */
	private Integer pageSize;
	
	

	public List<String> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<String> provinceList) {
		this.provinceList = provinceList;
	}

	public List<String> getCityList() {
		return cityList;
	}

	public void setCityList(List<String> cityList) {
		this.cityList = cityList;
	}

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
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