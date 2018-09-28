package com.casaba.dao.mapper;

import java.util.List;

import com.casaba.dao.entity.MdseInfo;

public interface MdseInfoMapper {
    int deleteById(Integer id);

    int insert(MdseInfo record);


    MdseInfo selectById(Integer id);

    int updateByIdSelective(MdseInfo record);

    int updateById(MdseInfo record);
    
    /**
     * 查询所有商品信息，根据sort 升序排序
     * @return
     */
    List<MdseInfo> queryMdseList();
}