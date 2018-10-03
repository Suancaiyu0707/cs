package com.casaba.dao.mapper.auth;

import com.casaba.dao.entity.auth.AccountInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 账号mapper
 * @author zhifang.xu
 */
public interface AccountInfoMapper {
    /***
     * 根据id删除账号信息
     * @param id 主键id
     * @return
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 添加一条账号信息
     * @param accountInfo
     * @return
     */
    int insert(AccountInfo accountInfo);

    /***
     * 根据id查询账号信息
     * @param id
     * @return
     */
    AccountInfo selectById(@Param("id") Integer id);

    /***
     * 更新账号信息
     * @param accountInfo
     * @return
     */
    int updateById(AccountInfo accountInfo);
}