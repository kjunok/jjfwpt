package com.jjkj.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jjkj.model.TBserviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjun
 * @since 2019-05-29
 */
public interface TBserviceInfoMapper extends BaseMapper<TBserviceInfo> {
    Page<TBserviceInfo> searchBserviceInfoPagesByParam(Page page, @Param("condition") Map condition, @Param("orderMap") Map orderMap);
    TBserviceInfo getBserviceInfo(@Param("condition") Map condition);
    List<TBserviceInfo> getBserviceInfoList(@Param("condition") Map condition);
}
