package com.jjkj.auth.service;

import com.jjkj.api.APIUserService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("jjfwpt-sys-service")
public interface UserService extends APIUserService {
}
