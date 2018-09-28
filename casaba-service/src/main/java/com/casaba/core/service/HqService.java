package com.casaba.core.service;

import com.casaba.core.bean.HqInfoBean;
import com.casaba.dao.entity.HqInfo;

/**
 * 总部接口
 * @author  zhifang.xu
 */
public interface HqService {
    /***
     * 根据手机号和密码查询用户
     * @param mobile 手机号
     * @param passwd 密码
     * @return
     */
    HqInfoBean queryHqInfo(String mobile, String passwd);

    /***
     * 修改密码
     * @param mobile 手机号
     * @param passwd 旧密码
     * @param pwd 新密码
     */
    void modifyHqPasswd(String mobile, String passwd,String pwd);


    /***
     * 修改密码
     * @param mobile 手机号
     * @param passwd 新密码
     */
    void findHqPasswd(String mobile, String passwd);

    /**
     * 查询代理商账号
     * @param mobile 手机号
     * @return
     */
    HqInfoBean queryHqByMobile(String mobile);

    /***
     * 查询账号的详情
     * @param id 主键id
     * @return
     */
    HqInfoBean loadHqInfoById(Integer id);

    /***
     * 更新登录时间
     * @param mobile 手机
     * @param passwd 密码
     */
    void modifyLastLoginTime(String mobile,String passwd);

}
