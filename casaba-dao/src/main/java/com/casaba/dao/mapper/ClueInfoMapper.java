package com.casaba.dao.mapper;

import com.casaba.dao.entity.ClueInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 线索mapper
 * @author zhifang.xu
 */
public interface ClueInfoMapper {
    /**
     * 根据id删除线索
     * @param id
     * @return
     */
    int deleteById(@Param("id")Integer id);

    /**
     * 新增一条线索
     * @param record
     * @return
     */
    int insert(ClueInfo record);

    /**
     * 根据id查询线索
     * @param id
     * @return
     */
    ClueInfo selectById(@Param("id")Integer id);

    /***
     * 根据主键id更新线索
     * @param record
     * @return
     */
    int updateById(ClueInfo record);
}