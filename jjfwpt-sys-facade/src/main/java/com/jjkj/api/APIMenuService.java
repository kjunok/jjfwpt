package com.jjkj.api;


import com.jjkj.annotation.JJKJAPI;
import com.jjkj.annotation.JJKJParamDesc;
import com.jjkj.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RequestMapping("/menuService")
public interface APIMenuService {

    @RequestMapping("/getTestData")
    @JJKJAPI(name="测试api",desc = "",response = "{text:'标题',key:'值'}")
    ResponseDto getTestData(
            @JJKJParamDesc(name = "a",desc = "参数测试a") String a,
            @JJKJParamDesc(name = "b",desc = "参数测试b") String b,
            @JJKJParamDesc(name = "c",desc = "参数测试c") Boolean c,
            @JJKJParamDesc(name = "d",desc = "参数测试d")
            String[] d);

    @RequestMapping("/getTestData1")
    @JJKJAPI(name="测试api111111111",desc = "",response = "{label:'标题aaa',value:'值bbb'}")
    ResponseDto getTestData1(
            @JJKJParamDesc(name = "aaa",desc = "参数测试aaa") String aaa,
            @JJKJParamDesc(name = "bbb",desc = "参数测试bbb") String bbb,
            @JJKJParamDesc(name = "ccc",desc = "参数测试ccc") Boolean ccc);

    @RequestMapping("/routes")
    ResponseDto routes(String token);

    @RequestMapping("/searchSingleMenuByParam")
    ResponseDto searchSingleMenuByParam(@RequestBody Map condition);

    @RequestMapping("/searchMenuTreeSortList")
    ResponseDto searchMenuTreeSortList(@RequestHeader("token") String token,@RequestBody Map condition);

    @RequestMapping("/searchMenuTreeNavList")
    ResponseDto searchMenuTreeNavList(@RequestHeader("token") String token);

    @RequestMapping("/addOrUpdateMenu")
    ResponseDto addOrUpdateMenu(@RequestHeader("token") String token,
                                @RequestParam(name = "iconFile",required = false) MultipartFile file,
                                @RequestParam("tMenuInfo") String tMenuInfo);

    @RequestMapping("/deleteMenuById")
    ResponseDto deleteMenuById(@RequestHeader("token") String token,String id);
}
