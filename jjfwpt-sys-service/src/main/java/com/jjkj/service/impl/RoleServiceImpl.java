package com.jjkj.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jjkj.api.APIRoleService;
import com.jjkj.dao.RoleInfoDao;
import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TRoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleServiceImpl implements APIRoleService {
    @Autowired
    private RoleInfoDao roleInfoDao;


    @Override
    public ResponseDto searchRolePagesByParam(Map param) {
        IPage<TRoleInfo> roleInfoList = roleInfoDao.searchRolePagesByParam(param);
        return new ResponseDto().success(roleInfoList);
    }

    @Override
    public ResponseDto searchRoleById(Long id) {
        TRoleInfo tRoleInfo = roleInfoDao.searchRoleById(id);
        ResponseDto responseDto = new ResponseDto();
        if(tRoleInfo!=null){
           return responseDto.success(tRoleInfo);
        }
        return  responseDto.error("所查询角色不存在");
    }

    @Override
    public ResponseDto getRoleMenusByRoleId(Long id) {
        List<Map> roleMenuList = roleInfoDao.getRoleMenusByRoleId(id);
        return new ResponseDto().success(roleMenuList);
    }

    @Override
    public ResponseDto saveOrUpdateRoleMenu(Map param) {
        roleInfoDao.saveOrUpdateRoleMenu(param);
        return new ResponseDto().success("添加或更细角色菜单成功!");
    }

    @Override
    public ResponseDto saveOrUpdateRoleInfo(TRoleInfo roleInfo) {
        roleInfoDao.saveOrUpdateRoleInfo(roleInfo);
        return new ResponseDto().success("添加或更新角色成功！");
    }

    @Override
    public ResponseDto delRoleUserByRoleIdAndUserIds(TRoleInfo roleInfo) {
        roleInfoDao.delRoleUserByRoleIdAndUserIds(roleInfo);
        return new ResponseDto().success("删除角色用户成功！");
    }

    @Override
    public ResponseDto addRoleUser(TRoleInfo roleInfo) {
        roleInfoDao.addRoleUser(roleInfo);
        return new ResponseDto().success("添加角色用户成功！");
    }
}
