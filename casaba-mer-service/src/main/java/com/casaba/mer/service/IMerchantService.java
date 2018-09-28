package com.casaba.mer.service;

import java.util.Date;

import com.casaba.common.paging.PageInfo;
import com.casaba.mer.bean.FreeMerchantBean;
import com.casaba.mer.bean.MerOrderBean;
import com.casaba.mer.bean.MerchantBean;
import com.casaba.mer.bean.QueryMerBean;

/**
 * 商户相关接口
 * @author tqlei
 *
 */
public interface IMerchantService {
	/**
	 * 添加付费商户
	 * Integer 商户id
	 */
	public Integer addMer(MerchantBean mer);
	

	/**
	 * 添加付费并开通商户
	 */
	public void addAndOpenMer(MerchantBean mer);
	
	/**
	 * 编辑商户信息
	 * @param mer
	 */
	public void updateMer(MerchantBean mer);
	
	/**
	 * 商户详情,包括联系信息
	 * @param merId
	 * @return
	 */
	public MerchantBean queryMerDetail(int merId);
	
	/**
	 * 查询商户列表包含全部详情
	 * @param query
	 * @return
	 */
	public PageInfo<MerchantBean> queryMerDtailList(QueryMerBean query);
	
	
	/**
	 * 商户列表（不包含详情）
	 * @param query
	 * @return
	 */
	public PageInfo<MerchantBean> queryMerList(QueryMerBean query);
	
	
	/**
	 * 开通商户
	 * @param merId
	 */
	public void openMer(int merId,String operator);

	/**
	 * 冻结商户
	 * @param merId
	 */
	public void freezeMer(int merId);

	/**
	 * 解冻商户
	 * @param merId
	 */
	public void thawMer(int merId);

	/**
	 * 商户升级
	 * @param order
	 */
	public void upgradeMer(MerOrderBean order);

	/**
	 * 商户续费
	 * @param order
	 */
	public void renewalMer(MerOrderBean order);

	

	/**
	 * 同步免费商户
	 */
	public void addFreeMerchant(FreeMerchantBean mer);

	
	/**
	 * 免费商户开单
	 */
	public void orderMer(MerOrderBean order);

	
	/**
	 * 免费商户续期
	 * @param merId 商户id
	 * @param buyYear 购买年限
	 * @param operator 操作人
	 */
	public void freeMerRenewal(Integer merId ,Date endTime,String operator);
	
	
	/**
	 * 定时处理已过期的商户
	 */
	public void exeExpireMer();
	
	
	
	/**
	 * 判断商户手机号是否已经存在
	 * @return
	 */
	public boolean isExistMerMobile(String mobile);
	
	
	/**
	 * 商户信息，不包括公司及联系人信息
	 * @param merCode 商户编号
	 * @return
	 */
	public MerchantBean queryMerByMerCode(String merCode);
	
	/**
	 * 商户信息，不包括公司及联系人信息
	 * @param merid 商户id
	 * @return
	 */
	public MerchantBean queryMerByMerId(Integer merId);
	
	
    /***
     *	 计算套餐升级费用
     * @param merId
     * @param mdseId
     * @param saleId
     * @return
     */
   
    public MerOrderBean  calcUpgradeMoney(int merId,int mdseId,int saleId);
    /***
     *	 计算套餐续期费用
     * @param merId
     * @param mdseId
     * @param saleId
     * @return
     */
    
    public MerOrderBean  calcRenewalMoney(int merId,int mdseId,int saleId);
    /***
     *	 计算商户开单费用
     * @param merId
     * @param mdseId
     * @param saleId
     * @return
     */
    
    public MerOrderBean  calcOrderMoney(int mdseId,int saleId,int agentId);
}
