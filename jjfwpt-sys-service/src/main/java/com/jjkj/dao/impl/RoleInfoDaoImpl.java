package com.jjkj.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjkj.dao.RoleInfoDao;
import com.jjkj.dao.mapper.TRoleInfoMapper;
import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TRoleInfo;
import com.jjkj.util.MybatisUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class RoleInfoDaoImpl extends ServiceImpl<TRoleInfoMapper, TRoleInfo> implements RoleInfoDao {

    @Override
    public TRoleInfo searchRoleById(Long id) {
        return super.getById(id);
    }

    @Override
    public List<TRoleInfo> searchRoleListByContion(Map contion) {
        return null;
    }

    @Override
    public IPage<TRoleInfo> searchRolePagesByParam(Map param) {
        return baseMapper.searchRolePagesByParam(MybatisUtil.getPage(param),MybatisUtil.getConditionMap(param));
    }

    @Override
    public List<Map> getRoleMenusByRoleId(Long id) {
        return baseMapper.getRoleMenusByRoleId(id);
    }

    @Override
    public List<Long> getRoleUserIdByRoleId(Long id) {
        return baseMapper.getRoleUserIdByRoleId(id);
    }

    @Override
    public boolean delRoleUserByRoleIdAndUserIds(TRoleInfo roleInfo) {
        return baseMapper.delRoleUserByRoleIdAndUserIds(roleInfo);
    }

    @Override
    public boolean addRoleUser(TRoleInfo roleInfo) {
        List<Long> userIds=roleInfo.getUserIds();
        List<Map<String,Object>> menuRoleList=new ArrayList<>();
        for(int i=0,len=userIds.size();i<len;i=i+3){
            Map<String,Object> userRole=new HashMap();
            userRole.put("role_id",roleInfo.getId());
            userRole.put("user_id",userIds.get(i));
            userRole.put("create_time",new Date());
            menuRoleList.add(userRole);
        }
        baseMapper.insertRoleUserBatch(menuRoleList);
        return true;
    }


    @Override
    @Transactional
    public boolean saveOrUpdateRoleMenu(Map param) {
        if(param.containsKey("menuIds")&&param.containsKey("id")){
            List<String> menuIds= (List<String>) param.get("menuIds");
            String id = (String) param.get("id");
            List<Map<String,Object>> menuRoleList=new ArrayList<>();
            baseMapper.deleteRoleMenuByRoleId(new String[]{id});
            if(menuIds!=null&&menuIds.size()>0){
                for(int i=0,len=menuIds.size();i<len;i=i+3){
                    Map<String,Object> menuRole=new HashMap();
                    menuRole.put("role_id",id);
                    menuRole.put("menu_id",menuIds.get(i));
                    menuRole.put("menu_type",menuIds.get(i+1));
                    menuRole.put("select_type",menuIds.get(i+2));
                    menuRole.put("create_time",new Date());
                    menuRoleList.add(menuRole);
                }
                baseMapper.insertRoleMenuBatch(menuRoleList);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateRoleInfo(TRoleInfo roleInfo) {
        return super.saveOrUpdate(roleInfo);
    }
}
