package com.casaba.mer.service;

import java.util.List;

import com.casaba.mer.bean.MdseInfoBean;
import com.casaba.mer.bean.SaleDetailBean;

/**
 * 商户相关接口
 * @author tqlei
 *
 */
public interface IMdseService {
	
	
	/**
	 * 查询所有版本信息不包含所属的销售信息
	 * 
	 * @return List<MdseInfoBean>
	 */
	public List<MdseInfoBean> queryMdseList();
	
	/**
	 * 查询所有版本信息及所属的销售信息
	 * 
	 * @return List<MdseInfoBean>
	 */
	public List<MdseInfoBean> queryMdseSaleList();
	
	/**
	 * 查询当前商户能可升级版本信息及所属的销售信息
	 * 排除当前低于商户已经购买过的套餐及年限
	 * merId 商户id
	 * @return List<MdseInfoBean>
	 */
	public List<MdseInfoBean> queryUpgradeMdseList(Integer merId);
	
	
	/**
	 * 查询当前商户能可续期版本信息及所属的销售信息
	 * 排除当前低于商户已经购买过的套餐及年限
	 * @return List<MdseInfoBean>
	 */
	public List<MdseInfoBean> queryRenewalMdseList(Integer merId);
	/**
	 * 查询销售信息
	 * 
	 * @return SaleDetail
	 */
	public SaleDetailBean querySaleInfo(Integer saleId);
	
	
	
}
