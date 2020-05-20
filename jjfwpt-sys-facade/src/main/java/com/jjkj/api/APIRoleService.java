package com.jjkj.api;

import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TRoleInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roleService")
public interface APIRoleService {

    @RequestMapping("/searchRolePagesByParam")
    ResponseDto searchRolePagesByParam(@RequestBody Map param);

    @RequestMapping("/searchRoleById")
    ResponseDto searchRoleById(Long id);

    @RequestMapping("/getRoleMenusByRoleId")
    ResponseDto getRoleMenusByRoleId(@RequestParam Long id);

    @RequestMapping("/saveOrUpdateRoleMenu")
    ResponseDto saveOrUpdateRoleMenu(@RequestBody Map param);

    @RequestMapping("/saveOrUpdateRoleInfo")
    ResponseDto saveOrUpdateRoleInfo(@RequestBody TRoleInfo roleInfo);

    @RequestMapping("/delRoleUserByRoleIdAndUserIds")
    ResponseDto delRoleUserByRoleIdAndUserIds(@RequestBody TRoleInfo roleInfo);

    @RequestMapping("/addRoleUser")
    ResponseDto addRoleUser(@RequestBody TRoleInfo roleInfo);
}