package com.casaba.auth.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/***
 * 权限资源bean
 * @author zhifang.xu
 */
@Data
@ToString
public class ResInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 权限资源名称
     */
    private String resName;
    /***
     * 权限资源路径
     */
    private String resUrl;
    /***
     * 权限资源编码
     */
    private String resCode;
    /***
     * 父url
     */
    private Integer parentId;
    /***
     * 权限资源类型
     * url-url btn-按钮
     */
    private String resType;
    /***
     * 权限资源顺序
     */
    private String resOrder;
    /***
     * 权限资源描述信息
     */
    private String resDesc;

}
