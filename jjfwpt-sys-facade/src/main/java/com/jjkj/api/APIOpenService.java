package com.jjkj.api;

import com.jjkj.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/apiOpenService")
public interface APIOpenService {
    @RequestMapping("/getAllApi")
    ResponseDto getAllApi();
}
