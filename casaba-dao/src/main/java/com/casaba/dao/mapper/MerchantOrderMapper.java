package com.casaba.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.casaba.dao.entity.MerchantOrder;

public interface MerchantOrderMapper {
    int deleteById(Integer orderId);

    int insert(MerchantOrder record);

    MerchantOrder selectById(Integer orderId);

    /**
     	* 开通套餐，修改开始结束时间，代理商消耗金额
     * @param record
     * @return
     */
   int updateOpenById(MerchantOrder record);

    
    /**
       * 将订单状态修改为结束
     * @param orderId
     * @return
     */
    int updateEndById(@Param("orderId")Integer orderId,@Param("orderStatus")String orderStatus);
    


    /**
     * 根据商户id List ， 类型  状态  查询商户未生效的续费订单 list
     * @param orderId
     * @return
     */
    List<MerchantOrder> queryRenewalOrderList(@Param("merIds")List<Integer> merIds,@Param("orderType")String orderType,@Param("orderStatus")String orderStatus);

    
    /**
     * 根据商户id ， 类型  状态 查询商户未生效的续费订单
     * @param orderId
     * @return
     */
    MerchantOrder queryRenewalOrder(@Param("merId")Integer merId,@Param("orderType")String orderType,@Param("orderStatus")String orderStatus);
    
    

    /***
     * 批量过期订单
     * @param ids 批量id
     * @return
     */
    void batchOrderStatus(@Param("orderIds")List<Integer> orderIds,@Param("orderStatus")String orderStatus);

    /**
     * 根据商户id 查询当前商户最后一条购买信息 
     * @param merId 商户id
     * @return
     */
    MerchantOrder selectLastBymerId(Integer merId);

}