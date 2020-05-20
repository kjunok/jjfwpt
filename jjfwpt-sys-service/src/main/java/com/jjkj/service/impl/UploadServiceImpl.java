package com.jjkj.service.impl;

import com.jjkj.api.APIUploadService;
import com.jjkj.dto.ResponseDto;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UploadServiceImpl implements APIUploadService {

    @Autowired
    private StorageClient storageClient;


    @Override
    public ResponseDto uploadFile(MultipartFile file, Map<String, String> fileParam) {
        return null;
    }

    @Override
    public String[] upload(MultipartFile file, Map<String, String> fileParam) {
        return new String[0];
    }

    @Override
    public Boolean delFile(String filePath) {
        return null;
    }
}