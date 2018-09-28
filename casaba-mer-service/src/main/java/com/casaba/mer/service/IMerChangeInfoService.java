package com.casaba.mer.service;

import java.util.List;

import com.casaba.mer.bean.MerInfoChangeBean;
import com.casaba.mer.bean.MerSetmealChangeBean;

/**
 * 商户变更记录相关接口
 * @author tqlei
 *
 */
public interface IMerChangeInfoService {
	
	
	/**
	 * 查询所有版本信息及所属的销售信息
	 * 
	 * @return List<MdseInfoBean>
	 */
	public List<MerInfoChangeBean> queryMerChangeList(Integer merId);
	

	
	/**
	 * 查询销售信息
	 * 
	 * @return SaleDetail
	 */
	public List<MerSetmealChangeBean> querySetmealInfoList(Integer merId);
}
