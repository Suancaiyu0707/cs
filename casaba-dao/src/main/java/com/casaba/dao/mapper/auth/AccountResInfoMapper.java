package com.casaba.dao.mapper.auth;

import com.casaba.dao.entity.auth.AccountResInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 账号权限mapper
 * @author zhifang.xu
 */
public interface AccountResInfoMapper {
    /***
     * 删除账号权限信息
     * @param id 主键id
     * @return
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 新增账号权限信息
     * @param accountResInfo
     * @return
     */
    int insert(AccountResInfo accountResInfo);

    /***
     * 根据id查询账号权限信息
     * @param id 主键id
     * @return
     */
    AccountResInfo selectById(@Param("id") Integer id);

    /***
     * 根据id更新账号权限信息
     * @param accountResInfo
     * @return
     */
    int updateById(AccountResInfo accountResInfo);
}