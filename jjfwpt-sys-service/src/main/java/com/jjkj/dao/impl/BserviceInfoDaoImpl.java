package com.jjkj.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjkj.dao.BserviceInfoDao;
import com.jjkj.dao.mapper.TBserviceInfoMapper;
import com.jjkj.model.TBserviceInfo;
import com.jjkj.util.MybatisUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BserviceInfoDaoImpl extends ServiceImpl<TBserviceInfoMapper, TBserviceInfo> implements BserviceInfoDao {
    @Override
    public IPage<TBserviceInfo> searchBserviceInfoPagesByParam(Map param) {
        return baseMapper.searchBserviceInfoPagesByParam(MybatisUtil.getPage(param),MybatisUtil.getConditionMap(param),MybatisUtil.getOrderMap(param));
    }
}
