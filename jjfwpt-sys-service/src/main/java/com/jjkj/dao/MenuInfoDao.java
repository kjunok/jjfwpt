package com.jjkj.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjkj.model.TMenuInfo;

import java.util.List;
import java.util.Map;

public interface MenuInfoDao  extends IService<TMenuInfo> {
    TMenuInfo findMenuByContion(Map condition);

    List<TMenuInfo> findMenuListByContion(Map condition);

    List<TMenuInfo> findMenuTreeSortList(Map condition);

    List<TMenuInfo> findMenuTreeView(Long userId);

    List<TMenuInfo> findAllMenuTreeView();

    Boolean addMenu(TMenuInfo menuInfo);

    Boolean updateMenu(TMenuInfo menuInfo);

    Boolean delMenu(String[] ids);
}
