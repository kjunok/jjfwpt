package com.jjkj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jjkj.api.APIBserviceService;
import com.jjkj.dao.BserviceInfoDao;
import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TBserviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BserviceServiceImpl implements APIBserviceService {

    @Autowired
    private BserviceInfoDao bserviceInfoDao;

    @Override
    public ResponseDto searchBserviceInfoPagesByParam(Map param) {
        IPage<TBserviceInfo> tBserviceInfoList =bserviceInfoDao.searchBserviceInfoPagesByParam(param);
        return new ResponseDto().success(tBserviceInfoList);
    }
}
