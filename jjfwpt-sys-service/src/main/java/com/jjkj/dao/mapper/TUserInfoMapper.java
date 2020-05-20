package com.jjkj.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jjkj.model.TUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TUserInfoMapper extends BaseMapper<TUserInfo> {

    TUserInfo queryByAccount(@Param("account") String account);

    Page<TUserInfo> searchRoleUserList(Page<TUserInfo> page, @Param("condition") Map condition);

    Page<TUserInfo> searchNotRoleUserList(Page<TUserInfo> page, @Param("condition") Map condition);
}