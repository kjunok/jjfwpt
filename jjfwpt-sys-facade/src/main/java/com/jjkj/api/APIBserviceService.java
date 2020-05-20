package com.jjkj.api;

import com.jjkj.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/bserviceService")
public interface APIBserviceService {

    @RequestMapping("/searchBserviceInfoPagesByParam")
    ResponseDto searchBserviceInfoPagesByParam(@RequestBody Map param);
}
