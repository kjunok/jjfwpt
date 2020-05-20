package com.jjkj.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjkj.model.TUserInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


public interface UserInfoDao extends IService<TUserInfo> {

    boolean saveOrUpdate(@RequestBody TUserInfo userInfo);

    TUserInfo queryByAccount(@RequestParam("account") String account);
    TUserInfo searchUserById(Long id);

    List<TUserInfo> getUserListByContion(Map contion);

    TUserInfo getUserByContion(Map contion);

    IPage<TUserInfo> searchRoleUserListByParam(Map param);

    IPage<TUserInfo> searchNotRoleUserListByParam(Map param);

    TUserInfo findUserByContion(Map condition);

    IPage<TUserInfo> findUserPageByContion(Map condition);

    List<TUserInfo> findUserListByContion(Map condition);



    Boolean delUser(String[] ids);
}
