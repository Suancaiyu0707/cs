package com.casaba.core.service;

import com.casaba.common.paging.PageInfo;
import com.casaba.core.bean.SalerBean;

/***
 * 销售员服务
 * @author zhifang.xu
 */
public interface SalerService {
    /***
     * 添加一个销售员
     * @param salerBean
     */
    void addSaler(SalerBean salerBean);

    /***
     * 编辑销售员信息
     * @param salerBean
     */
    void modifySaler(SalerBean salerBean);

    /**
     * 根据id删除销售员信息
     * @param id
     */
    void delSaler(Integer id);

    /**
     * 分页查询销售员信息
     * @param remark 名称或手机号
     * @param page 页码
     * @param pageSize 每页大小
     * @return
     */
    PageInfo<SalerBean> querySalers(String remark,Integer page,Integer pageSize);
}
