package com.casaba.dao.repository;

import com.casaba.dao.entity.auth.RoleResInfo;
import com.casaba.dao.mapper.auth.RoleResInfoMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 角色数据库处理类
 * @author zhifang.xu
 */
@Repository
public class RoleRespository {
    private static final Logger logger = LoggerFactory.getLogger(RoleRespository.class);
    @Autowired
    private RoleResInfoMapper roleResInfoMapper;
    /**
     * 添加角色信息
     * @param roleResInfo
     */
    public void addRole(RoleResInfo roleResInfo){

    }

    /**
     * 分页查询角色列表
     * @param page 页码
     * @param pagesize 页面大小
     * @return
     */
    public List<RoleResInfo> listRoles(Integer page, Integer pagesize){
        PageHelper.startPage(page,pagesize);
        return null;
    }

    /**
     * 修改角色信息
     * @param roleResInfo
     */
    public void modifyRole(RoleResInfo roleResInfo){

    }

    /***
     * 删除角色信息
     * @param roleId 角色id
     */
    public void delRole(Integer roleId){

    }

    /***
     * 根据名字查询角色信息
     * @return
     */
    public RoleResInfo queryRoleResInfoByName(){
        return null;
    }
}
