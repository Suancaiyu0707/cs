package com.casaba.dao.mapper;

import com.casaba.dao.entity.CanalInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 渠道员mapper
 * @author zhifang.xu
 */
public interface CanalInfoMapper {
    /***
     * 根据id删除渠道员信息
     * @param id
     * @return
     */
    int deleteById(@Param("id") Integer id);

    /***
     * 新增一条渠道员信息
     * @param canalInfo
     * @return
     */
    int insert(CanalInfo canalInfo);

    /***
     * 根据id查询渠道员信息
     * @param id
     * @return
     */
    CanalInfo selectById(@Param("id") Integer id);

    /***
     * 根据id更新渠道员信息
     * @param canalInfo
     * @return
     */
    int updateById(CanalInfo canalInfo);
}