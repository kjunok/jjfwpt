package com.jjkj.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@Component
public class FastDfsProperties {
    @Value("${fastdfs.connect_timeout_in_seconds:}")
    private  String connect_timeout_in_seconds;

    @Value("${fastdfs.network_timeout_in_seconds:}")
    private  String network_timeout_in_seconds;

    @Value("${fastdfs.charset:}")
    private  String charset;

    @Value("${fastdfs.http_anti_steal_token:}")
    private  String http_anti_steal_token;

    @Value("${fastdfs.http_secret_key:}")
    private  String http_secret_key;

    @Value("${fastdfs.http_tracker_http_port:}")
    private  String http_tracker_http_port;

    @Value("${fastdfs.tracker_servers:}")
    private  String tracker_servers;

    public Properties getProp(){
        Properties properties =new Properties();
        properties.setProperty("fastdfs.connect_timeout_in_seconds",connect_timeout_in_seconds);
        properties.setProperty("fastdfs.network_timeout_in_seconds",network_timeout_in_seconds);
        properties.setProperty("fastdfs.charset",charset);
        properties.setProperty("fastdfs.http_anti_steal_token",http_anti_steal_token);
        properties.setProperty("fastdfs.http_secret_key",http_secret_key);
        properties.setProperty("fastdfs.http_tracker_http_port",http_tracker_http_port);
        properties.setProperty("fastdfs.tracker_servers",tracker_servers);
        return properties;
    }
}
