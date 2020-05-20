package com.jjkj.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
public class RedisProperties {
    @Value("${redis.host}")
    public String host;

    @Value("${redis.port}")
    public int port;

    @Value("${redis.password}")
    public String password;

    @Value("${redis.minIdle}")
    public int minIdle;

    @Value("${redis.maxIdle}")
    public int maxIdle;

    @Value("${redis.maxTotal}")
    public int maxTotal;

    @Value("${redis.maxWaitMillis}")
    public int maxWaitMillis;

    @Value("${redis.timeout}")
    public int timeout;

    @Value("${redis.commandTimeout}")
    public int commandTimeout;

    @Value("${redis.shutdownTimeout}")
    public int shutdownTimeout;

    @Value("${redis.testOnBorrow}")
    public boolean testOnBorrow;

    @Value("${redis.expiration}")
    public int expiration;

    @Value("${redis.enableTransaction}")
    public boolean enableTransaction;

    @Value("${redis.cluster.nodes}")
    public String clusterNodes;

    @Value("${redis.master}")
    public String master;

    @Value("${redis.sentinels}")
    public String sentinels;


}
