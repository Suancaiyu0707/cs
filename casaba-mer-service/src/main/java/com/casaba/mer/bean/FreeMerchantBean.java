package com.casaba.mer.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 免费商户bean
 * @author tqlei
 *
 */
public class FreeMerchantBean  implements Serializable {
    private static final long serialVersionUID = 1L;
   
    /**
     * 	商户名称
     */
    private String merName;
    /**
     * 	商户编码
     */
    private String merCode;    
    /**
     * 	手机号
     */
    private String mobile;
    /**
     * 	商品名称
     */
    private String mdseName;
    /**
     * 	商品id
     */
    private Integer mdseId;
    /**
     *	 注册时间
     */
    private Date createTime;
    /**
     * 	开始时间
     */
    private Date startTime;
    /**
     * 	结束时间
     */
    private Date endTime;
    /**
     *	 省
     */
    private String province;        
    /**
     *	 市
     */
    private String city;
    /**
     *	 区
     */
    private String area;
    
    
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getMdseName() {
		return mdseName;
	}
	public void setMdseName(String mdseName) {
		this.mdseName = mdseName;
	}
	public Integer getMdseId() {
		return mdseId;
	}
	public void setMdseId(Integer mdseId) {
		this.mdseId = mdseId;
	}

    

}