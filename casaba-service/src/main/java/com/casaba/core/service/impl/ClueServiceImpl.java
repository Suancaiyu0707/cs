package com.casaba.core.service.impl;

import com.casaba.common.paging.PageInfo;
import com.casaba.core.bean.ClueBean;
import com.casaba.core.bean.ClueQueryBean;
import com.casaba.core.service.ClueService;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 线索服务实现类
 * @author zhifang.xu
 */
@Service
public class ClueServiceImpl implements ClueService{
    @Override
    public void addClue(ClueBean clueBean) {

    }

    @Override
    public void modifyClue(ClueBean clueBean) {

    }

    @Override
    public ClueBean loadClueDetail(Integer id) {
        return null;
    }

    @Override
    public PageInfo<ClueBean> listClueInfos(ClueQueryBean queryBean, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public void updateClueWithAgent(List<Integer> ids, Integer agentId, String agentName) {

    }


}
