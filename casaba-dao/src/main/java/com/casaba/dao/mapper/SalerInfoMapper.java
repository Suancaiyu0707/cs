package com.casaba.dao.mapper;

import com.casaba.dao.entity.SalerInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 销售员mapper
 * @author zhifang.xu
 */
public interface SalerInfoMapper {
    int deleteById(@Param("id") Integer id);

    /**
     * 新增一个销售员
     * @param salerInfo
     * @return
     */
    int insert(SalerInfo salerInfo);

    /**
     * 根据id查询销售员
     * @param id
     * @return
     */
    SalerInfo selectById(@Param("id")Integer id);

    /***
     * 根据id更新销售员信息
     * @param salerInfo
     * @return
     */
    int updateById(SalerInfo salerInfo);
}