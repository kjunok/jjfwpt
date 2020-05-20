package com.jjkj.api;

import com.jjkj.dto.ResponseDto;
import com.jjkj.model.TFormInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/formService")
public interface APIFormService {

    @RequestMapping("/searchFormPagesByParam")
    ResponseDto searchFormPagesByParam(@RequestBody Map param);

    @RequestMapping("/searchFormById")
    ResponseDto searchFormById(String id);

    @RequestMapping("/saveOrUpdateForm")
    ResponseDto saveOrUpdateForm(@RequestHeader("token") String token,@RequestBody TFormInfo formInfo);

    @RequestMapping("/deleteFormById")
    ResponseDto deleteFormById(String id);
}
