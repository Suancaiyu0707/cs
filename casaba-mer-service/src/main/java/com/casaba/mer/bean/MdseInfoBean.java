package com.casaba.mer.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 版本bean 标准，旗舰等
 * @author tqlei
 *
 */
public class MdseInfoBean  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Integer id;
    /**
     * 	商品名称
     */
    private String mdseName;
    /**
     * 	排序
     */
    private Integer mdseSort;
    
    /***
     * 	创建时间
     */
    private Date createTime;
    /***
     * 	更新时间
     */
    private Date updateTime;


    private List<SaleDetailBean> saleInfoList;
    
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

	public List<SaleDetailBean> getSaleInfoList() {
		return saleInfoList;
	}

	public void setSaleInfoList(List<SaleDetailBean> saleInfoList) {
		this.saleInfoList = saleInfoList;
	}
    
}