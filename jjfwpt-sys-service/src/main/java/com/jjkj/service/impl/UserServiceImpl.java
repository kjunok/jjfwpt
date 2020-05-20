package com.jjkj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jjkj.api.APIUserService;
import com.jjkj.dao.RoleInfoDao;
import com.jjkj.dao.UserInfoDao;
import com.jjkj.dao.elasticsearch.TUserInfoEsSearchDao;
import com.jjkj.dto.ResponseDto;
import com.jjkj.exception.BusinessException;
import com.jjkj.exception.DataParseException;
import com.jjkj.exception.IllegalParameterException;
import com.jjkj.model.TUserInfo;
import com.jjkj.util.ElasticUtil;
import com.jjkj.util.SessionUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author wangjun
 */
@RestController
public class UserServiceImpl implements APIUserService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RoleInfoDao roleInfoDao;

    @Autowired
    private TUserInfoEsSearchDao tUserInfoEsSearchDao;

    @Override
    public TUserInfo queryByAccount(String account) {
        TUserInfo tUserInfo =new TUserInfo();
        tUserInfo.setAccount(account);
        tUserInfo=userInfoDao.queryByAccount(account);
        return tUserInfo;
    }

    @Override
    public ResponseDto searchUserList(String token, Map param) {
        ResponseDto resultDto =new ResponseDto<>();
        IPage<TUserInfo> userInfoList=null;
        if(param.containsKey("keyword")){
            Page<TUserInfo> userInfos = tUserInfoEsSearchDao.search(ElasticUtil.searchQuery(param));
            return resultDto.success(userInfos);
        }else {
            userInfoList=userInfoDao.findUserPageByContion(param);
        }
        if(userInfoList==null){
            resultDto = resultDto.error("信息不存在!");
        }else {
            resultDto.success(userInfoList);
        }
        return resultDto;
    }


    @Override
    public ResponseDto deleteUserById(String token, Long id) {

        return null;
    }

    @Override
    public ResponseDto searchSingleUserByParam(Map condition) {
        ResponseDto<TUserInfo> resultDto =new ResponseDto<>();
        TUserInfo user=userInfoDao.getUserByContion(condition);
        if(null==user){
            resultDto = resultDto.error("用户不存在!");
        }else{
            resultDto.success(user);
        }
        return resultDto;
    }

    @Override
    public ResponseDto<List<TUserInfo>> findUserListByContion(Map condition) {
        ResponseDto<List<TUserInfo>> resultDto =new ResponseDto<>();
        List<TUserInfo> userlist=userInfoDao.getUserListByContion(condition);
        if(null==userlist){
            resultDto = resultDto.error("没有复合条件的用户!");
        }else{
            resultDto.success(userlist);
        }
        return resultDto;
    }

    @Override
    public ResponseDto register(TUserInfo userInfo) {
        boolean flag=false;
        ResponseDto resultDto=new ResponseDto();
        try{
            flag = userInfoDao.save(userInfo);
        }catch (Exception e){
            resultDto=resultDto.error("注册失败!");
        }
        if(flag){
            return resultDto.success("注册成功");
        }else {
            return resultDto;
        }
    }
    @Override
    public ResponseDto findUserByCondition(String token,Map condition) {
        ResponseDto resultDto =new ResponseDto<>();
        TUserInfo userInfo=userInfoDao.findUserByContion(condition);
        if(null==userInfo){
            resultDto = resultDto.error("信息不存在!");
        }else{
            resultDto.success(userInfo);
        }
        return resultDto;
    }

    @Override
    public ResponseDto<List<TUserInfo>> findUserListByCondition(String token,Map condition) {
        ResponseDto<List<TUserInfo>> resultDto =new ResponseDto<>();
        List<TUserInfo> userInfoList=userInfoDao.findUserListByContion(condition);
        if(userInfoList==null){
            resultDto = resultDto.error("信息不存在!");
        }else {
            resultDto.success(userInfoList);
        }
        return resultDto;
    }

    @Override
    public ResponseDto searchRoleUserPageByParam(String token, Map param) {
       ResponseDto resultDto =new ResponseDto<>();
       if(param.containsKey("keyword")){
           Long roleId = Long.valueOf(String.valueOf(param.get("roleId")));
           List<Long> roleUserIds = roleInfoDao.getRoleUserIdByRoleId(roleId);
           QueryBuilder filterBuilder =
                   QueryBuilders.boolQuery()
                            .must(QueryBuilders.termsQuery("id",roleUserIds.toArray()))
                            .must(QueryBuilders.termsQuery("enable",true));
           SearchQuery searchQuery = new NativeSearchQueryBuilder()
                   .withQuery(ElasticUtil.searchBuilder(param))
                   .withPageable(ElasticUtil.searchPageable(param))
                   .withFilter(filterBuilder)
                   .build();
           Page<TUserInfo> userInfos = tUserInfoEsSearchDao.search(searchQuery);
           resultDto.success(userInfos);
       }else {
           IPage<TUserInfo> userInfoList=userInfoDao.searchRoleUserListByParam(param);
           resultDto.success(userInfoList);
       }
        return resultDto;
    }

    @Override
    public ResponseDto searchNotRoleUserPageByParam(String token, Map param) {
        ResponseDto resultDto =new ResponseDto<>();
        if(param.containsKey("keyword")){
            Long roleId = Long.valueOf(String.valueOf(param.get("roleId")));
            List<Long> roleUserIds = roleInfoDao.getRoleUserIdByRoleId(roleId);
            QueryBuilder filterBuilder =
                    QueryBuilders.boolQuery()
                            .mustNot(QueryBuilders.termsQuery("id",roleUserIds.toArray()))
                            .mustNot(QueryBuilders.termsQuery("enable",false));
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(ElasticUtil.searchBuilder(param))
                    .withPageable(ElasticUtil.searchPageable(param))
                    .withFilter(filterBuilder)
                    .build();
            Page<TUserInfo> userInfos = tUserInfoEsSearchDao.search(searchQuery);
            resultDto.success(userInfos);
        }else {
            IPage<TUserInfo> userInfoList=userInfoDao.searchNotRoleUserListByParam(param);
            resultDto.success(userInfoList);
        }
        return resultDto;
    }

    @Override
    public ResponseDto saveOrUpdateUser(String token,TUserInfo userInfo){
        ResponseDto resultDto =new ResponseDto<>();
        if(userInfo.getId()==null){
            userInfo.setCreateTime(new Date());
        }else{
            userInfo.setUpdateTime(new Date());
        }
        if(userInfoDao.saveOrUpdate(userInfo)){
            if(tUserInfoEsSearchDao.existsById(Long.valueOf(userInfo.getId()))){
                tUserInfoEsSearchDao.delete(userInfo);
            }
            tUserInfoEsSearchDao.index(userInfo);
            resultDto=resultDto.success(userInfo);
        }else {
            resultDto = resultDto.error("添加或修改失败!");
        }
        return resultDto;
    }

    @Override
    public ResponseDto delUser(String token, String[] ids) {
        ResponseDto resultDto =new ResponseDto<>();
        if(!userInfoDao.delUser(ids)){
            resultDto = resultDto.error("删除失败!");
        }else {
            resultDto=resultDto.success("删除成功");
        }
        return resultDto;
    }
}
