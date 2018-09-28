package com.casaba.core.service.impl;

import com.casaba.core.bean.HqInfoBean;
import com.casaba.core.service.HqService;
import com.casaba.dao.entity.HqInfo;
import com.casaba.dao.repository.HqRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HqServiceImpl implements HqService {
    @Autowired
    private HqRespository hqRespository;
    @Override
    public HqInfoBean queryHqInfo(String mobile, String passwd) {
        HqInfo hqInfo = hqRespository.queryHqInfo(mobile,passwd);
        if(hqInfo==null){
            return null;
        }
        HqInfoBean hqInfoBean = new HqInfoBean();
        BeanUtils.copyProperties(hqInfo,hqInfoBean);
        return hqInfoBean;
    }

    @Override
    public void modifyHqPasswd(String mobile, String passwd, String pwd) {
        hqRespository.modifyHqPasswd(mobile,passwd,pwd);
    }

    @Override
    public void findHqPasswd(String mobile, String passwd) {
        hqRespository.findHqPasswd(mobile,passwd);
    }

    @Override
    public HqInfoBean queryHqByMobile(String mobile) {
        HqInfo hqInfo = hqRespository.queryHqByMobile(mobile);
        if(hqInfo==null){
            return null;
        }
        HqInfoBean hqInfoBean = new HqInfoBean();
        BeanUtils.copyProperties(hqInfo,hqInfoBean);
        return hqInfoBean;
    }

    @Override
    public HqInfoBean loadHqInfoById(Integer id) {
        HqInfo hqInfo = hqRespository.loadHqInfoById(id);
        if(hqInfo==null){
            return null;
        }
        HqInfoBean hqInfoBean = new HqInfoBean();
        BeanUtils.copyProperties(hqInfo,hqInfoBean);
        return hqInfoBean;
    }

    @Override
    public void modifyLastLoginTime(String mobile, String passwd) {
        hqRespository.modifyLastLoginTime(mobile,passwd);
    }


}
