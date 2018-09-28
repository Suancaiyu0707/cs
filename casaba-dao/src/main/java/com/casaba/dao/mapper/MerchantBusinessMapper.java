package com.casaba.dao.mapper;

import com.casaba.dao.entity.MerchantBusiness;

public interface MerchantBusinessMapper {
    int insert(MerchantBusiness record);

    int insertSelective(MerchantBusiness record);
    
    int updateSelective(MerchantBusiness record);
    
    MerchantBusiness queryByMerId(Integer merId);
    
    
}