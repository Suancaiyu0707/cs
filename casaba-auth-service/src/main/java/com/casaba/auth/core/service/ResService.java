package com.casaba.auth.core.service;

import com.casaba.auth.core.bean.ResInfoBean;

import java.util.List;

/***
 * 权限资源服务
 * @author  zhifang.xu
 */
public interface ResService {
    /***
     * 查询所有有效的权限资源
     * @return
     */
    List<ResInfoBean> queryAllResInfos();

    /***
     * 查询父url下的所有的权限配置
     * @param parantId 父url
     * @return
     */
    List<ResInfoBean> queryAllResInfos(int parantId);


}
