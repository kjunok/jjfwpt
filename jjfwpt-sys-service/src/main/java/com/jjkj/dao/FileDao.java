package com.jjkj.dao;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileDao {
     String[] upload(MultipartFile file, Map<String,String> fileParam);

     Boolean delFile(String filePath);
}
