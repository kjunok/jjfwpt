package com.jjkj.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjkj.dao.FormInfoDao;
import com.jjkj.dao.mapper.TFormInfoMapper;
import com.jjkj.model.TFormInfo;
import com.jjkj.util.MybatisUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FormInfoDaoImpl  extends ServiceImpl<TFormInfoMapper, TFormInfo> implements FormInfoDao {

    @Override
    public IPage<TFormInfo> searchFormPagesByParam(Map param) {
        return baseMapper.searchFormInfoPage(MybatisUtil.getPage(param),MybatisUtil.getConditionMap(param));
    }

    @Override
    public TFormInfo searchFormById(String id) {
        return super.getById(id);
    }

    @Override
    public TFormInfo searchFormInfo(Map param) {
        return baseMapper.searchFormInfo(param);
    }


    @Override
    public List<TFormInfo> searchFormInfoList(Map param) {
        return baseMapper.searchFormInfoList(param);
    }

    @Override
    public Boolean deleteFormById(String id) {
        return baseMapper.deleteById(id)>0;
    }

    @Override
    public Boolean saveOrUpdateForm(TFormInfo tFormInfo) {
        return super.saveOrUpdate(tFormInfo);
    }
}
