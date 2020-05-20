package com.jjkj.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjkj.api.APIMenuService;
import com.jjkj.dao.FileDao;
import com.jjkj.dao.MenuInfoDao;
import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TMenuInfo;
import com.jjkj.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuServiceImpl implements APIMenuService {
    @Autowired
    private MenuInfoDao menuInfoDao;

    @Autowired
    private FileDao fileDao;

    @Override
    public ResponseDto searchMenuTreeSortList(String token, Map condition) {
        ResponseDto<List<TMenuInfo>> responseDto =new ResponseDto<>();
        List<TMenuInfo> menuPage = menuInfoDao.findMenuTreeSortList(condition);
        if(null==menuPage){
            responseDto = responseDto.error("数据不存在");
        }else{
            responseDto = responseDto.success(menuPage);
        }
        return responseDto;
    }

    @Override
    public ResponseDto searchMenuTreeNavList(String token) {
        ResponseDto<List<TMenuInfo>> resultDto =new ResponseDto<>();
        Long userId=SessionUtil.getUserID(token);
        List<TMenuInfo> menuInfos = menuInfoDao.findMenuTreeView(userId);
        if(null==menuInfos){
            resultDto = resultDto.error("菜单不存在!");
        }else{
            resultDto = resultDto.success(menuInfos);
        }
        return resultDto;
    }

    @Override
    public ResponseDto getTestData(String a, String b, Boolean c, String[] d) {
        JSONArray jsonArray = new JSONArray();
        for(int i =0,len  =10;i<10;i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text","text"+i);
            jsonObject.put("key","key"+i);
            jsonArray.add(jsonObject);
        }
        System.out.println(a+b+c+d);
        return new ResponseDto().success(jsonArray);
    }

    @Override
    public ResponseDto getTestData1(String aaa, String bbb, Boolean ccc) {
        JSONArray jsonArray = new JSONArray();
        for(int i =0,len  =10;i<10;i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("label","label"+i);
            jsonObject.put("value","value"+i);
            jsonArray.add(jsonObject);
        }
        return new ResponseDto().success(jsonArray);
    }

    @Override
    public ResponseDto routes(String token) {
        Map condition = new HashMap();
        condition.put("type",2);
        List<TMenuInfo> menuList = menuInfoDao.findMenuListByContion(condition);
        return new ResponseDto().success(menuList);
    }

    @Override
    public ResponseDto searchSingleMenuByParam(Map condition) {
        ResponseDto<TMenuInfo> resultDto =new ResponseDto<>();
        TMenuInfo menuInfo= menuInfoDao.findMenuByContion(condition);
        if(null==menuInfo){
            resultDto = resultDto.error("菜单不存在!");
        }else{
            resultDto.success(menuInfo);
        }
        return resultDto;
    }

    @Override
    public ResponseDto addOrUpdateMenu(String token, MultipartFile file, String tMenuInfo1) {
        ResponseDto resultDto=new ResponseDto();
        TMenuInfo tMenuInfo = JSON.parseObject(tMenuInfo1,TMenuInfo.class);
        try{
            if(tMenuInfo.getId()!=null){
                Map condition =new HashMap();
                condition.put("id",tMenuInfo.getId());
                TMenuInfo oldMenuInfo = menuInfoDao.findMenuByContion(condition);
                if(oldMenuInfo.getIcon().indexOf("/")>-1){
                    fileDao.delFile(oldMenuInfo.getIcon());
                }
            }
            if(file!=null&&!file.isEmpty()){
                Map<String,String> pair = new HashMap<>();
                pair.put("menuId", String.valueOf(tMenuInfo.getId()));
                String[] fileInfo = fileDao.upload(file,pair);
                tMenuInfo.setIcon(fileInfo[0]+"/"+fileInfo[1]);
            }
            String userId=null;
            if(token!=null){
                userId=SessionUtil.getUserIDstr(token);
            }
            if(tMenuInfo.getId()!=null){
                tMenuInfo.setUpdateUserId(userId);
                tMenuInfo.setUpdateTime(new Date());
            }else {
                tMenuInfo.setId(null);
                tMenuInfo.setCreateUserId(userId);
                tMenuInfo.setCreateTime(new Date());
            }
            menuInfoDao.saveOrUpdate(tMenuInfo);
            return resultDto.success("修改菜单成功！");
        }catch (Exception e){
            return resultDto.error("修改菜单失败!");
        }
    }

    @Override
    public ResponseDto deleteMenuById(String token, String id) {
        return null;
    }
}
