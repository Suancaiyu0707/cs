package com.casaba.dao.mapper;

import java.util.List;

import com.casaba.dao.entity.MerInfoChangeDectail;

public interface MerInfoChangeDectailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MerInfoChangeDectail record);

    MerInfoChangeDectail selectByPrimaryKey(Integer id);
    
    /**
     * 根据商户id 查询操作记录
     * @param merId
     * @return
     */
    List<MerInfoChangeDectail> queryListByMerId(Integer merId);

}