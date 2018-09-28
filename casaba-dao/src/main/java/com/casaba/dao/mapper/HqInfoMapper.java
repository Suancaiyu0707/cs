package com.casaba.dao.mapper;

import com.casaba.dao.entity.HqInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/***
 * 总部 mapper
 * @author zhifang.xu
 */
public interface HqInfoMapper {
    /***
     * 根据id 删除
     * @param id 主键id
     * @return
     */
    void deleteById(@Param("id") Integer id);

    /***
     * 新增一条总部信息
     * @param hqInfo
     * @return
     */
    void insert(HqInfo hqInfo);

    /***
     * 根据id 查询总部信息
     * @param id 主键id
     * @return
     */
    HqInfo selectById(@Param("id")Integer id);

    /***
     * 根据id更新总部信息
     * @param hqInfo
     * @return
     */
    void updateById(HqInfo hqInfo);

    /***
     * 根据手机号和密码查询用户
     * @param mobile 手机号
     * @param passwd 密码
     * @return
     */
    HqInfo queryHqInfo(@Param("mobile")String mobile, @Param("passwd")String passwd);

    /***
     * 修改密码
     * @param mobile 手机号
     * @param passwd 旧密码
     * @param pwd 新密码
     */
    void modifyHqPasswd(@Param("mobile")String mobile, @Param("passwd")String passwd,@Param("pwd")String pwd);


    /***
     * 找回密码
     * @param mobile 手机号
     * @param passwd 新密码
     */
    void findHqPasswd(@Param("mobile")String mobile,@Param("passwd") String passwd);

    /**
     * 查询代理商账号
     * @param mobile 手机号
     * @return
     */
    HqInfo queryHqByMobile(@Param("mobile")String mobile);

    /***
     * 更新上一次登录时间
     * @param mobile 手机号
     * @param passwd 密码
     * @param loginTime 登录时间
     */
    void modifyLastLoginTime(@Param("mobile") String mobile,@Param("passwd") String passwd,@Param("loginTime") Date loginTime);
}