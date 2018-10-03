package com.casaba.dao.mapper.auth;

import com.casaba.dao.entity.auth.ResInfo;
import org.apache.ibatis.annotations.Param;

/***
 * 权限信息
 * @author zhifang.xu
 */
public interface ResInfoMapper {
    /***
     * 根据主键id删除一条权限信息
     * @param id
     * @return
     */
    int deleteById(@Param("id")Integer id);

    /***
     * 新增一条权限信息
     * @param resInfo
     * @return
     */
    int insert(ResInfo resInfo);

    /***
     * 根据id查询一条权限信息
     * @param id 主键id
     * @return
     */
    ResInfo selectById(@Param("id")Integer id);

    /***
     * 根据id更新一条权限信息
     * @param resInfo
     * @return
     */
    int updateById(ResInfo resInfo);
}