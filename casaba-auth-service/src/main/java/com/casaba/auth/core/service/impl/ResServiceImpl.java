package com.casaba.auth.core.service.impl;

import com.casaba.auth.core.bean.ResInfoBean;
import com.casaba.auth.core.service.ResService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResServiceImpl implements ResService {

    @Override
    public List<ResInfoBean> queryAllResInfos() {
        return null;
    }

    @Override
    public List <ResInfoBean> queryAllResInfos(int parantId) {
        return null;
    }
}
