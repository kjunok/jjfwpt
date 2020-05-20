package com.jjkj.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjkj.dao.UserInfoDao;
import com.jjkj.dao.mapper.TUserInfoMapper;
import com.jjkj.model.TUserInfo;
import com.jjkj.util.MybatisUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Component
public class UserInfoDaoImpl extends ServiceImpl<TUserInfoMapper,TUserInfo> implements UserInfoDao {

    @Override
    public boolean saveOrUpdate(TUserInfo userInfo) {
        return super.saveOrUpdate(userInfo);
    }

    @Override
    public TUserInfo queryByAccount(String account) {
        TUserInfo tUserInfo =new TUserInfo();
        tUserInfo.setAccount(account);
        return baseMapper.queryByAccount(account);
    }

    @Override
    public TUserInfo searchUserById(Long id) {
        return super.getById(id);
    }

    @Override
    public List<TUserInfo> getUserListByContion(Map contion) {
        QueryWrapper queryWrapper=new QueryWrapper();
        contion.forEach((f,v)->queryWrapper.eq(f,v));
        List<TUserInfo> userList=this.list(queryWrapper);
        return userList;
    }

    @Override
    public TUserInfo getUserByContion(Map contion) {
        QueryWrapper queryWrapper=new QueryWrapper();
        contion.forEach((f,v)->queryWrapper.eq(f,v));
        TUserInfo user=this.getOne(queryWrapper,true);
        return user;
    }

    @Override
    public TUserInfo findUserByContion(Map condition) {
        QueryWrapper queryWrapper=new QueryWrapper();
        condition.forEach((f,v)->queryWrapper.eq(f,v));
        TUserInfo userInfo=this.getOne(queryWrapper);
        return userInfo;
    }

    @Override
    public List<TUserInfo> findUserListByContion(Map condition) {
        QueryWrapper queryWrapper=new QueryWrapper();
        condition.forEach((f,v)->queryWrapper.eq(f,v));
        List<TUserInfo> userInfoList=this.list(queryWrapper);
        return userInfoList;
    }

    @Override
    public IPage<TUserInfo> searchRoleUserListByParam(Map param){
        return baseMapper.searchRoleUserList(MybatisUtil.getPage(param),MybatisUtil.getConditionMap(param));
    }

    @Override
    public IPage<TUserInfo> searchNotRoleUserListByParam(Map param) {
        return baseMapper.searchNotRoleUserList(MybatisUtil.getPage(param),MybatisUtil.getConditionMap(param));
    }

    @Override
    public IPage<TUserInfo> findUserPageByContion(Map condition) {
        return baseMapper.selectPage(MybatisUtil.getPage(condition),MybatisUtil.getQueryWrapper(condition));
    }

    @Override
    public Boolean delUser(String[] ids) {
        return  this.removeByIds(Arrays.asList(ids));
    }
}
