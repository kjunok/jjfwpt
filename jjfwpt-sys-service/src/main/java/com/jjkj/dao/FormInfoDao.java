package com.jjkj.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjkj.model.TFormInfo;

import java.util.List;
import java.util.Map;

public interface FormInfoDao extends IService<TFormInfo> {

    IPage<TFormInfo> searchFormPagesByParam(Map param);

    List<TFormInfo> searchFormInfoList(Map param);

    TFormInfo searchFormById(String id);

    TFormInfo searchFormInfo(Map param);

    Boolean deleteFormById(String id);

    Boolean saveOrUpdateForm(TFormInfo tFormInfo);

}
