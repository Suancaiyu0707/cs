package com.casaba.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.casaba.dao.entity.SaleDetail;

public interface SaleDetailMapper {
    int deleteById(Integer id);

    int insert(SaleDetail record);

    SaleDetail selectById(Integer id);
    /**
     * 	根据商品id  销售id  查询销售 信息
     * @param id
     * @param mdseId
     * @return
     */
    SaleDetail selectByMdseIdAndId(@Param("id")Integer id,@Param("mdseId")Integer mdseId);
    
    /**
     * 根据商品id 查询销售信息 购买年限字段排序 升序
     * @param mdseId
     * @return
     */
    List<SaleDetail> selectByMdseId(Integer mdseId);

}