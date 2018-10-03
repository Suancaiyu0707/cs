package com.casaba.auth.core.service;

import com.casaba.auth.core.bean.RoleResInfoBean;

import java.util.List;

/***
 * 角色service
 */
public interface RoleService {
    /**
     * 添加角色信息
     * @param roleResInfoBean
     */
    void addRole(RoleResInfoBean roleResInfoBean);

    /**
     * 分页查询角色列表
     * @param page 页码
     * @param pagesize 页面大小
     * @return
     */
    List<RoleResInfoBean> listRoles(Integer page,Integer pagesize);

    /**
     * 修改角色信息
     * @param roleResInfoBean
     */
    void modifyRole(RoleResInfoBean roleResInfoBean);

    /***
     * 删除角色信息
     * @param roleId 角色id
     */
    void delRole(Integer roleId);

    /***
     * 根据名字查询角色信息
     * @return
     */
    RoleResInfoBean queryRoleResInfoByRoleName(String roleName);

    /***
     * 根据手机号查询角色信息
     * @param mobile
     * @return
     */
    RoleResInfoBean queryRoleResInfoByMobile(String mobile);

}
