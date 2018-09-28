package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/***
 * 代理商信息
 * @author  zhifang.xu
 */
@Data
@ToString
@Alias("agent")
public class AgentInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 代理商名称
     */
    private String agentName;
    /***
     * 代理商密码
     */
    private String pwd;
    /***
     * 代理商手机号
     */
    private String mobile;
    /***
     * 创建人
     */
    private String founder;
    /***
     * 上次登录时间
     */
    private Date lastLoginTime;
    /***
     * 代理商状态:正常00,到期04,冻结01
     */
    private String status;
    /***
     * 冻结时间
     */
    private Date freezeTime;
    /***
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}