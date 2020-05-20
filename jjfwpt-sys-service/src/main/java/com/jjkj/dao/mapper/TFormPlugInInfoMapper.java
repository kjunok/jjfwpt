package com.jjkj.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jjkj.model.TFormPlugInInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author WangJun
 * @since 2020-04-18
 */
public interface TFormPlugInInfoMapper extends BaseMapper<TFormPlugInInfo> {
    Page<TFormPlugInInfo> getTFormPlugInInfoPage(Page page, @Param("condition") Map condition);
    TFormPlugInInfo getTFormPlugInInfo(@Param("condition") Map condition);
    List<TFormPlugInInfo> getTFormPlugInInfoList(@Param("condition") Map condition);
}
