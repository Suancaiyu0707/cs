package com.casaba.auth.core.service;

import com.casaba.auth.core.bean.AccountBean;

import java.util.List;

/***
 * 账号服务
 * @author zhifang.xu
 */
public interface AccountService {
    /***
     * 添加账号信息
     * @param accountBean
     */
    void addAccount(AccountBean accountBean);

    /***
     * 添加账号信息
     * @param accountBean
     */
    void modifyAccount(AccountBean accountBean);

    /**
     * 删除账号
     * @param accountId 账号id
     */
    void delAccount(Integer accountId);

    /***
     * 分页查询账号信息
     * @param page 页码
     * @param pageSize 每页大小
     * @param remark 手机号或姓名
     * @return
     */
    List<AccountBean> listAccounts(Integer page, Integer pageSize,String remark);

    /***
     * 根据账号名称查询账号信息
     * @param accountName
     * @return
     */
    AccountBean queryAccount(String accountName);
}
