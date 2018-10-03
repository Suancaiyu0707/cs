package com.casaba.dao.mapper.auth;

import com.casaba.dao.entity.auth.RoleResInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 角色权限信息
 * @author zhifang.xu
 */
public interface RoleResInfoMapper {
    /**
     * 根据主键删除角色权限
     * @param id 主键id
     * @return
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 新增一条角色权限信息
     * @param roleResInfo
     * @return
     */
    int insert(RoleResInfo roleResInfo);

    /***
     * 根据id查询一条角色权限信息
     * @param id
     * @return
     */
    RoleResInfo selectById(@Param("id")Integer id);

    /**
     * 根据id更新一条权限信息
     * @param roleResInfo
     * @return
     */
    int updateById(RoleResInfo roleResInfo);
}