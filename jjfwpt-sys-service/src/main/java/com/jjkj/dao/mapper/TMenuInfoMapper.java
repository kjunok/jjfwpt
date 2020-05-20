package com.jjkj.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjkj.model.TMenuInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TMenuInfoMapper extends BaseMapper<TMenuInfo> {

   public List<TMenuInfo> selectRoleMenus(@Param("userId") Long userId);

}