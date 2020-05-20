package com.jjkj.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jjkj.model.TFormInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TFormInfoMapper extends BaseMapper<TFormInfo> {
    Page<TFormInfo> searchFormInfoPage(Page page, @Param("param") Map param);
    TFormInfo searchFormInfo(@Param("param") Map param);
    List<TFormInfo> searchFormInfoList(@Param("param") Map param);
}
