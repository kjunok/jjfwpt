package com.jjkj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jjkj.api.APIFormService;
import com.jjkj.dao.FormInfoDao;
import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TFormInfo;
import com.jjkj.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class FormServiceImpl implements APIFormService {

    @Autowired
    private FormInfoDao formInfoDao;

    @Override
    public ResponseDto searchFormPagesByParam(Map param) {
        ResponseDto responseDto = new ResponseDto();
        IPage<TFormInfo> formInfoPage = formInfoDao.searchFormPagesByParam(param);
        if(formInfoPage==null){
            responseDto.error("数据不存在!");
        }else{
            responseDto.success(formInfoPage);
        }
        return responseDto;
    }

    @Override
    public ResponseDto searchFormById(String id) {
        ResponseDto responseDto = new ResponseDto();
        TFormInfo tFormInfo = formInfoDao.searchFormById(id);
        if(tFormInfo==null){
            responseDto.error("数据不存在!");
        }else{
            responseDto.success(tFormInfo);
        }
        return responseDto;
    }

    @Override
    public ResponseDto saveOrUpdateForm(String token, TFormInfo formInfo) {
        ResponseDto responseDto = new ResponseDto();
        Boolean result = formInfoDao.saveOrUpdateForm(formInfo);
        String userId=null;
        if(token!=null){
            userId=SessionUtil.getUserIDstr(token);
        }
        if(formInfo.getId()==null){
            formInfo.setCreateUserId(userId);
            formInfo.setCreateTime(new Date());
        }else{
            formInfo.setUpdateUserId(userId);
            formInfo.setUpdateTime(new Date());
        }
        if(result==null){
            responseDto.error("添加或修改失败!");
        }else{
            responseDto.success("添加或修改成功！");
        }
        return responseDto;
    }

    @Override
    public ResponseDto deleteFormById(String id) {
        ResponseDto responseDto = new ResponseDto();
        Boolean result = formInfoDao.deleteFormById(id);
        if(result){
            responseDto.success("删除成功！");
        }else{
            responseDto.error("删除失败！");
        }
        return responseDto;
    }
}
