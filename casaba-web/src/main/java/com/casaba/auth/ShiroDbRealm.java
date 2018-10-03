package com.casaba.auth;

import com.casaba.auth.core.bean.AccountBean;
import com.casaba.auth.core.bean.ResInfoBean;
import com.casaba.auth.core.bean.RoleResInfoBean;
import com.casaba.auth.core.service.AccountService;

import com.casaba.auth.core.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;


import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService ;
    @Autowired
    private RoleService roleService;
    public ShiroDbRealm() {
        super();
    }

    /**
     * 验证登陆
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        AccountBean accountBean = accountService.queryAccount(token.getUsername()) ;
        //根据登录名获取用户信息
        if (accountBean != null) {
            return new SimpleAuthenticationInfo(accountBean.getId(), accountBean.getPwd(), accountBean.getAccountName());
        } else {
            throw new AuthenticationException();
        }
    }

    /**
     * 登陆成功之后，进行角色和权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userNo = (String) getAvailablePrincipal(principals);
        // 列举此用户所有的权限
        //List<Permission> permissions = userService.findUserPermissionByName(username);
        RoleResInfoBean roleResInfoBean = roleService.queryRoleResInfoByRoleName(null);
        List<ResInfoBean> listRes = roleResInfoBean.getResInfos();
        Set<String> strs=new HashSet<String>();
        Iterator<ResInfoBean> it = listRes.iterator();
        while (it.hasNext()) {
            ResInfoBean re=it.next();
            strs.add(re.getResUrl());
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(strs);
        return authorizationInfo;
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
    /**
     *
     * @Title: clearAuthz
     * @Description: TODO 清楚缓存的授权信息
     * @return void    返回类型
     */
    public void clearAuthz(){
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
