package com.casaba.dao.mapper;

import java.util.List;

import com.casaba.dao.entity.MerSetmealChange;

public interface MerSetmealChangeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MerSetmealChange record);

    MerSetmealChange selectByPrimaryKey(Integer id);
 
    /**
     * 根据商户id 查询操作记录
     * @param merId
     * @return
     */
    List<MerSetmealChange> queryListByMerId(Integer merId);
}