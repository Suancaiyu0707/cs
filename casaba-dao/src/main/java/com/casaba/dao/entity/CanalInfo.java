package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/***
 * 渠道员信息
 * @author zhifang.xu
 */
@Data
@ToString
@Alias("canal   ")
public class CanalInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 渠道员名称
     */
    private String canalName;
    /***
     * 手机号
     */
    private String mobile;
    /***
     * 密码
     */
    private String passwd;
    /***
     * 管理区域
     */
    private String managerArea;

}