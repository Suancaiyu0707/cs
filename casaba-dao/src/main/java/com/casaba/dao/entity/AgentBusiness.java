package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/***
 * 代理商企业基本信息
 * @author zhifang.xu
 */
@Alias("agentBusiness")
@Data
@ToString
public class AgentBusiness implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    /***
     * 账号ID
     */
    private Integer agentId;
    /***
     * 公司名称
     */
    private String companyName;
    /***
     * 公司电话
     */
    private String companyPhone;
    /***
     * 省
     */
    private String province;
    /***
     * 市
     */
    private String city;
    /***
     * 区
     */
    private String area;
    /***
     * 公司地址
     */
    private String address;
    /***
     * 联系人
     */
    private String contacts;
    /***
     * 联系人手机号
     */
    private String contactMobile;
    /***
     * 联系人电话
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

    private Date createTime;

    private Date updateTime;
}