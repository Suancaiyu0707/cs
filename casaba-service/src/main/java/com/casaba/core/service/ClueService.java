package com.casaba.core.service;

import com.casaba.common.paging.PageInfo;
import com.casaba.core.bean.ClueBean;
import com.casaba.core.bean.ClueQueryBean;

import java.util.List;

/***
 * 线索service
 * @author zhifang.xu
 */
public interface ClueService {
    /***
     * 新增一条线索
     * @param clueBean
     */
    void addClue(ClueBean clueBean);

    /***
     * 编辑一条线索
     * @param clueBean
     */
    void modifyClue(ClueBean clueBean);

    /**
     * 根据id加载线索详情
     * @param id
     * @return
     */
    ClueBean loadClueDetail(Integer id);

    /***
     * 查询线索列表
     * @param queryBean
     * @param page 页码
     * @param pageSize 每页大小
     * @return
     */
    PageInfo<ClueBean> listClueInfos(ClueQueryBean queryBean,Integer page,Integer pageSize);

    /**
     * 批量更新所属代理商信息
     * @param ids 主键id
     * @param agentId 代理商id
     * @param agentName 代理商名称
     */
    void updateClueWithAgent(List<Integer> ids, Integer agentId, String agentName);

    /**
     * 批量更新所属代理商信息
     * @param ids 主键id
     * @param salerId 销售员id
     * @param salerName 销售员名称
     */
    void updateClueWithSaler(List<Integer> ids, Integer salerId, String salerName);

    /**
     * 根据销售员id查询线索列表
     * @param salerId
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo<ClueBean> queryCluesBySalerId(Integer salerId,Integer page,Integer pageSize);
}
