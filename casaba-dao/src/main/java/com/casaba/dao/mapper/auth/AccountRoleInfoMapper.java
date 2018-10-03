package com.casaba.dao.mapper.auth;

import com.casaba.dao.entity.auth.AccountRoleInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 账号角色mapper
 * @author zhifang.xu
 */
public interface AccountRoleInfoMapper {
    /**
     * 根据id删除一条账号角色
     * @param id
     * @return
     */
    int deleteById(@Param("id") Integer id);

    /***
     * 新增账号角色信息
     * @param accountRoleInfo
     * @return
     */
    int insert(AccountRoleInfo accountRoleInfo);

    /***
     * 根据id查询账号角色信息
     * @param id
     * @return
     */
    AccountRoleInfo selectById(@Param("id") Integer id);

    /***
     * 根据id更新账号角色信息
     * @param accountRoleInfo
     * @return
     */
    int updateById(AccountRoleInfo accountRoleInfo);
}