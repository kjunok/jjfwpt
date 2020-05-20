package com.jjkj.dao.impl;

import com.jjkj.config.FastDfsConfig;
import com.jjkj.dao.FileDao;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FileDaoImpl implements FileDao {

    @Autowired
    private FastDfsConfig fastDfsConfig;

    @Override
    public String[] upload(MultipartFile file, Map<String,String> fileParam) {
        try{

            int pairNum =0;
            List<NameValuePair> pairList= new ArrayList<>();
            if(fileParam.size()>0){
                fileParam.forEach((k,v)->{
                    pairList.add(new NameValuePair( k,v));
                });
            }
            NameValuePair[] pairs = new NameValuePair[pairList.size()];
            pairList.toArray(pairs);
            byte[] fileByte = file.getBytes();
            String filename=file.getResource().getFilename();
            String suffix=filename.substring(filename.lastIndexOf(".")+1,filename.length());
            String[] fileIds = fastDfsConfig.getStorageClient().upload_file(fileByte, suffix, pairs);
            return fileIds;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean delFile(String filePath) {
        String groupName = filePath.substring(0,filePath.indexOf("/"));
        String storage = filePath.substring(filePath.indexOf("/")+1,filePath.length());
        try {
            return fastDfsConfig.getStorageClient().delete_file(groupName, storage)>0?true:false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return false;
    }
}
