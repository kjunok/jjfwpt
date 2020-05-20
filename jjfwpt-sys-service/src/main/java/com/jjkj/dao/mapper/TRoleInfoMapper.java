package com.jjkj.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jjkj.model.TRoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjun
 * @since 2019-05-12
 */
public interface TRoleInfoMapper extends BaseMapper<TRoleInfo> {
    Page<TRoleInfo> searchRolePagesByParam(Page page, @Param("param") Map param);
    boolean insertRoleMenuBatch(@Param("roleMenuList") List<Map<String, Object>> roleMenuList);
    boolean insertRoleUserBatch(@Param("roleUserList") List<Map<String, Object>> roleUserList);
    boolean deleteRoleMenuByRoleId(@Param("roleIds") String[] roleIds);
    boolean deleteRoleUserByRoleId(@Param("roleIds") String[] roleIds);
    boolean delRoleUserByRoleIdAndUserIds(TRoleInfo roleInfo);
    List<Map> getRoleMenusByRoleId(Long roleId);
    List<Long> getRoleUserIdByRoleId(Long roleId);
}
