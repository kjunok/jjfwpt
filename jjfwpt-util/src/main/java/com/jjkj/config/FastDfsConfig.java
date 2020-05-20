package com.jjkj.config;

import com.jjkj.config.properties.FastDfsProperties;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class FastDfsConfig {

    @Autowired
    private FastDfsProperties fastFdfsProperties;


    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    public static StorageClient storageClient = null;

    public StorageClient getStorageClient() throws IOException, MyException {
        if(storageClient==null){
            Properties properties = fastFdfsProperties.getProp();
            ClientGlobal.initByProperties(properties);
            System.out.println("ClientGlobal.configInfo():" + ClientGlobal.configInfo());
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        }
        return storageClient;
    }
}
