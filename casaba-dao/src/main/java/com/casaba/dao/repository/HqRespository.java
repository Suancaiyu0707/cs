package com.casaba.dao.repository;

import com.casaba.dao.entity.AgentInfo;
import com.casaba.dao.entity.HqInfo;
import com.casaba.dao.mapper.HqInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/***
 * 总部 mapper处理类
 * @author zhifang.xu
 */
@Repository
public class HqRespository {
    @Autowired
    private HqInfoMapper hqInfoMapper;

    /***
     * 根据手机号和密码查询总部信息
     * @param mobile 手机号
     * @param passwd 密码
     * @return
     */
    public HqInfo queryHqInfo(String mobile, String passwd) {
        return hqInfoMapper.queryHqInfo(mobile,passwd);
    }

    /***
     * 修改密码
     * @param mobile 手机号
     * @param passwd 旧密码
     * @param pwd 新密码
     */
    public void modifyHqPasswd(String mobile, String passwd, String pwd) {
        hqInfoMapper.modifyHqPasswd(mobile,passwd,pwd);
    }

    /***
     * 找回密码
     * @param mobile 手机号
     * @param passwd 新密码
     */
    public void findHqPasswd(String mobile, String passwd) {
        hqInfoMapper.findHqPasswd(mobile,passwd);
    }

    /***
     * 根据手机号查询
     * @param mobile 手机号
     * @return
     */
    public HqInfo queryHqByMobile(String mobile) {
        return hqInfoMapper.queryHqByMobile(mobile);
    }

    /***
     * 查询账号详情
     * @param id 主键Id
     * @return
     */
    public HqInfo loadHqInfoById(Integer id){
        return hqInfoMapper.selectById(id);
    }

    /***
     * 更新登录时间
     * @param mobile
     * @param passwd
     */
    public void modifyLastLoginTime(String mobile,String passwd){
        hqInfoMapper.modifyLastLoginTime(mobile,passwd,new Date());
    }

}
