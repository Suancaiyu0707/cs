package com.casaba.core.service;

import com.casaba.core.bean.CanalBean;
import com.casaba.core.bean.ClueBean;

import java.util.List;

/***
 * 渠道管理员服务
 * @author zhifang.xu
 */
public interface CanalService {
    /***
     * 新增渠道员信息
     * @param canalBean
     */
    void addCanal(CanalBean canalBean);

    /***
     * 编辑渠道员信息
     * @param canalBean
     */
    void modifyCanal(CanalBean canalBean);

    /***
     * 删除渠道员信息
     * @param id
     */
    void delCanal(Integer id);

    /***
     * 冻结渠道员
     * @param id
     */
    void frezeeCanal(Integer id);

    /***
     * 解冻渠道员
     * @param id
     */
    void unfrezeeCanal(Integer id);

    /**
     * 分页查询销售员信息
     * @param remark
     * @param page
     * @param pageSize
     * @return
     */
    List<CanalBean> queryCanals(String remark, Integer page, Integer pageSize);

    /**
     * 加载渠道员详情
     * @param id
     * @return
     */
    CanalBean loadCanalDetail(Integer id);

    /**
     * 查询渠道员的列表
     * @return
     */
    List<ClueBean> listClues();
}
