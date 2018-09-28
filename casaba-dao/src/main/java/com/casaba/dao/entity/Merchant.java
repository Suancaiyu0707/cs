package com.casaba.dao.entity;

import java.util.Date;

public class Merchant {
    private Integer id;

    /***
     * 	商户名称
     */
    private String merName;
    /***
     * 	商户编码
     */
    private String merCode;
    /***
     * 	商户密码
     */
    private String pwd;
    /***
     * 	手机号
     */
    private String mobile;
    /***
     * 	商品Id（套餐）
     */
    private Integer mdseId;
    /***
     * 	商品名称
     */
    private String mdseName;
    /***
     * 	开始时间
     */
    private Date startTime;
    /***
     * 	结束时间
     */
    private Date endTime;
    /***
     * 	创建时间
     */
    private Date createTime;
    /***
     * 	更新时间
     */
    private Date updateTime;

    /***
     * 	商户名称
     */
    private Date freezeTime;
    /***
     * 	商户类型     详情参考 MerType
     */
    private String merType;
    /***
     * 	商户名称
     */
    private Integer agentId;

    /***
     * 	省  所在区域
     */
    private String province;
    /***
     * 	市    所在区域
     */
    private String city;
    /***
     * 	区   所在区域
     */
    private String area;
    /***
     * 	商户名称  详情MerStatus 枚举类
     */
    private String merStatus;
    /***
     * 	商户名称
     */
    private String operator;
    /***
     * 	商户名称
     */
    private Integer orderId;
    /***
     * 	商户名称
     */
    private Date lastLoginTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType == null ? null : merType.trim();
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getMerStatus() {
        return merStatus;
    }

    public void setMerStatus(String merStatus) {
        this.merStatus = merStatus;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	public String getMdseName() {
		return mdseName;
	}

	public void setMdseName(String mdseName) {
		this.mdseName = mdseName;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
    
    public Date getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}

	public Integer getMdseId() {
		return mdseId;
	}

	public void setMdseId(Integer mdseId) {
		this.mdseId = mdseId;
	}

    
}