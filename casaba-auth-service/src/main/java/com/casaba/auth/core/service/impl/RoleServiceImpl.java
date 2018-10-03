package com.casaba.auth.core.service.impl;

import com.casaba.auth.core.bean.RoleResInfoBean;
import com.casaba.auth.core.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public void addRole(RoleResInfoBean roleResInfoBean) {
        
    }

    @Override
    public List<RoleResInfoBean> listRoles(Integer page, Integer pagesize) {
        return null;
    }

    @Override
    public void modifyRole(RoleResInfoBean roleResInfoBean) {

    }

    @Override
    public void delRole(Integer roleId) {

    }

    @Override
    public RoleResInfoBean queryRoleResInfoByRoleName(String roleName) {
        return null;
    }
}
