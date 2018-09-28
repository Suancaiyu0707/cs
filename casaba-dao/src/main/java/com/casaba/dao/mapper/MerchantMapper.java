package com.casaba.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.casaba.dao.bean.QueryMer;
import com.casaba.dao.entity.Merchant;
import com.github.pagehelper.Page;

public interface MerchantMapper {
	
	
    int deleteById(Integer id);

    int insert(Merchant record);

    /**
     * 根据商户id查询商户
     * @param merId 商户id
     * @return
     */
    Merchant selectById(Integer id);
    /**
     * 根据商户id查询商户
     * @param merCode 商户编号
     * @return
     */
    Merchant queryMerByMerCode(@Param("merCode")String merCode);

    
    /**
     *	 更新商户状态
     * @param merId 商户id
     * @param merStatus 当前状态
     * @param target 更新状态
     * @param freezeTime 冻结时间  (解冻时状态为空)
     * @return
     */
    int updateFreezeStatusById(@Param("merId")Integer merId,@Param("curStatus")String curStatus,
    		@Param("target")String target,@Param("freezeTime") Date freezeTime);
    
    
    /**
     *	 更新商户信息 名称，区域，所属代理商
     * @param merId 商户id
     * @param merStatus 当前状态
     * @param target 更新状态
     * @return
     */
    int updateMerInfoById(Merchant mer);
    
    
    /**
     * 更新商户状态及有效时间和套餐名
     * @param merId
     * @param curStatus 当前状态
     * @param target 更新状态
     * @param mdseName  套餐名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    int updateStatusAndTimeById(@Param("merId")Integer merId,@Param("curStatus")String curStatus,@Param("target")String target,    		
    		@Param("startTime")Date startTime,@Param("endTime")Date endTime);
    
    
    /**
     * 更新商户状态及有效时间和套餐名
     * @param merId
     * @param curStatus 当前状态
     * @param target 更新状态
     * @param mdseName  套餐名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    int updateStatusAndTimeMdseNameById(@Param("merId")Integer merId,@Param("target")String target,
    		@Param("mdseName")String mdseName,@Param("mdseId")Integer mdseId,
    		@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("orderId")Integer orderId);
    
    /**
     * 	更新套餐
     * @param merId
     * @param orderId
     * @return
     */
    int updateOrderIdById(@Param("merId")Integer merId,@Param("orderId")Integer orderId);
    
    /**
       *  将商户状态修改成为进行中 ，并将新套餐id 更新
     * @param merId
     * @param orderId
     * @param target
     * @return
     */
    int updateStatusAndOrderIdById(@Param("merId")Integer merId,@Param("orderId")Integer orderId,@Param("target")String target);
    
    /**
     *  将商户状态修改成为进行中 ，并将新套餐id 更新
     * @param merId
     * @param orderId
     * @param target
     * @return
     */
    int updateStaTypeAndOrderIdById(@Param("merId")Integer merId,@Param("target")String target,
    		@Param("merType")String merType,@Param("agentId")Integer agentId);


  
    
    /**
     * 根据query条件查询商户详情列表 包括当前套餐信息
     * @param query
     * @return
     */
    Page<Map<String, Object>> queryMerDetailList(QueryMer query);
    
    
    /**
     * 根据query条件查询商户详情列表
     * @param query
     * @return
     */
    Page<Merchant> queryMerList(QueryMer query);
    
    

    /***
     * 批量过期商户
     * @param ids 批量商户id
     * @return
     */
    void batchMerEnd(@Param("merIds")List<Integer> merIds,@Param("merStatus")String merStatus);
    
    /***
     * 根据手机号查询商户
     * @param mobile 手机号
     * @return
     */
   
   Merchant  queryMerByMobile(@Param("mobile")String mobile);
   
   
   
   
   /**
    * 查询有效期到了但状态是正常的商户
    * @param merStatus 状态
    * @param curTime 当前时间
    * @return
    */
   List<Merchant> queryExpireMer(@Param("curTime")Date curTime,@Param("merStatus")String merStatus);
   
}