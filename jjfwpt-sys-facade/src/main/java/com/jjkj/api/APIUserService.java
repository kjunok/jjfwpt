package com.jjkj.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TUserInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@RequestMapping("/userService")
public interface APIUserService {

    @RequestMapping("/saveOrUpdateUser")
    ResponseDto saveOrUpdateUser(@RequestHeader("token") String token, @RequestBody TUserInfo userInfo);

    @RequestMapping("/queryByAccount")
    TUserInfo queryByAccount(@RequestParam("account") String account);

    @RequestMapping("/searchUserList")
    ResponseDto searchUserList(@RequestHeader("token") String token,@RequestBody Map condition);

    @RequestMapping("/deleteUserById")
    ResponseDto deleteUserById(@RequestHeader("token") String token,Long id);


    @RequestMapping("searchSingleUserByParam")
    ResponseDto searchSingleUserByParam(@RequestBody Map condition);

    @RequestMapping("/searchRoleUserPageByParam")
    ResponseDto<IPage<TUserInfo>> searchRoleUserPageByParam(@RequestHeader("token") String token,@RequestBody Map param);

    @RequestMapping("/searchNotRoleUserPageByParam")
    ResponseDto searchNotRoleUserPageByParam(@RequestHeader("token") String token,@RequestBody Map param);

    @RequestMapping("/findUserListByContion")
    ResponseDto<List<TUserInfo>> findUserListByContion(Map condition);

    @RequestMapping("/register")
    ResponseDto register(TUserInfo userInfo);

    @RequestMapping("/findUserByCondition")
    ResponseDto<TUserInfo> findUserByCondition(@RequestHeader("token") String token, Map condition);

    @RequestMapping("/findUserListByCondition")
    ResponseDto<List<TUserInfo>> findUserListByCondition(@RequestHeader("token") String token,Map condition);



    @RequestMapping("/delUser")
    ResponseDto delUser(@RequestHeader("token") String token,String[] ids);

}
