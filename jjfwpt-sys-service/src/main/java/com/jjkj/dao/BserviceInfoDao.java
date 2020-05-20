package com.jjkj.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjkj.model.TBserviceInfo;

import java.util.Map;

public interface BserviceInfoDao extends IService<TBserviceInfo> {
    IPage<TBserviceInfo> searchBserviceInfoPagesByParam(Map param);
}
