package com.casaba.dao.mapper.auth;

import com.casaba.dao.entity.auth.RoleInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 角色信息mapper
 * @author zhifang.xu
 */
public interface RoleInfoMapper {
    /***
     * 根据id删除某一角色
     * @param roleId
     * @return
     */
    int deleteById(@Param("roleId") Integer roleId);

    /***
     * 新增一种角色
     * @param roleInfo
     * @return
     */
    int insert(RoleInfo roleInfo);

    /***
     * 根据角色id 查询角色
     * @param roleId
     * @return
     */
    RoleInfo selectById(@Param("roleId")Integer roleId);

    /***
     * 根据角色id更新角色信息
     * @param roleInfo
     * @return
     */
    int updateById(RoleInfo roleInfo);
}