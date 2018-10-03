package com.casaba.auth.core.service.impl;

import com.casaba.auth.core.bean.AccountBean;
import com.casaba.auth.core.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public void addAccount(AccountBean accountBean) {

    }

    @Override
    public void modifyAccount(AccountBean accountBean) {

    }

    @Override
    public void delAccount(Integer accountId) {

    }

    @Override
    public List<AccountBean> listAccounts(Integer page, Integer pageSize, String remark) {
        return null;
    }
}
