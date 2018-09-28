package com.casaba.agent.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/***
 * 代理商企业信息
 * @author zhifang.xu
 */
@Data
@ToString
public class AgentBusinessBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 代理商账号id
     */
    private Integer agentId;
    /***
     * 公司名称
     */
    private String companyName;
    /***
     *公司电话
     */
    private String companyPhone;
    /***
     * 省
     */
    private String province;
    /***
     * 城市
     */
    private String city;

    /***
     * 区
     */
    private String area;
    /***
     * 详细地址
     */
    private String address;
    /***
     * 联系人
     */
    private String contacts;
    /***
     * 手机
     */
    private String contactMobile;
    /***
     * 固话
     */
    private String contactTelephone;
    /***
     * 邮箱
     */
    private String email;
    /***
     * QQ
     */
    private String qq;
}
