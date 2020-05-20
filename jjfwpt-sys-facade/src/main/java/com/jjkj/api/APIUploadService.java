package com.jjkj.api;

import com.jjkj.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface APIUploadService {
    ResponseDto uploadFile(@RequestParam("file")MultipartFile file, @RequestBody Map<String,String> fileParam);

    String[] upload(MultipartFile file, Map<String,String> fileParam);
    Boolean delFile(String filePath);
}
