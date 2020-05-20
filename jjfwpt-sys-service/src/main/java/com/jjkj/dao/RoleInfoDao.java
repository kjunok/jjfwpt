package com.jjkj.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjkj.model.TRoleInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


public interface RoleInfoDao extends IService<TRoleInfo> {

    boolean saveOrUpdate(@RequestBody TRoleInfo userInfo);

    TRoleInfo searchRoleById(Long id);

    List<TRoleInfo> searchRoleListByContion(Map condition);

    IPage<TRoleInfo> searchRolePagesByParam(Map param);

    List<Map> getRoleMenusByRoleId(Long id);

    List<Long> getRoleUserIdByRoleId(Long id);

    boolean delRoleUserByRoleIdAndUserIds(TRoleInfo roleInfo);

    boolean addRoleUser(TRoleInfo roleInfo);

    boolean saveOrUpdateRoleMenu(Map param);

    boolean saveOrUpdateRoleInfo(TRoleInfo roleInfo);
}
