package com.casaba.dao.entity;

import java.util.Date;

public class MdseInfo {
    private Integer id;

    private String mdseName;

    private Integer mdseSort;

    /***
     * 	创建时间
     */
    private Date createTime;
    /***
     * 	更新时间
     */
    private Date updateTime;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMdseName() {
        return mdseName;
    }

    public void setMdseName(String mdseName) {
        this.mdseName = mdseName == null ? null : mdseName.trim();
    }

    public Integer getMdseSort() {
        return mdseSort;
    }

    public void setMdseSort(Integer mdseSort) {
        this.mdseSort = mdseSort;
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