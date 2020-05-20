package com.jjkj.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
@Data
public class MybatisProperties {
    @Value("${mybatis.datasource.url:}")
    public String dbUrl;

    @Value("${mybatis.datasource.username:}")
    public String username;

    @Value("${mybatis.datasource.password:}")
    public String password;

    @Value("${mybatis.datasource.driverClassName:}")
    public String driverClassName;

    @Value("${mybatis.datasource.initialsize:10}")
    public int initialSize;

    @Value("${mybatis.datasource.minIdle:10}")
    public int minIdle;

    @Value("${mybatis.datasource.maxactive:10}")
    public int maxActive;

    @Value("${mybatis.datasource.maxWait:10}")
    public int maxWait;

    @Value("${mybatis.datasource.timeBetweenEvictionRunsMillis:}")
    public int timeBetweenEvictionRunsMillis;

    @Value("${mybatis.datasource.minEvictableIdleTimeMillis:}")
    public int minEvictableIdleTimeMillis;

    @Value("${mybatis.datasource.validationQuery:}")
    public String validationQuery;

    @Value("${mybatis.datasource.testWhileIdle:}")
    public boolean testWhileIdle;

    @Value("${mybatis.datasource.testOnBorrow:}")
    public boolean testOnBorrow;

    @Value("${mybatis.datasource.testOnReturn:}")
    public boolean testOnReturn;

    @Value("${mybatis.datasource.filters:}")
    public String filters;

    @Value("${mybatis.datasource.logSlowSql:}")
    public String logSlowSql;

    @Value("${mybatis.mapperPath:}")
    public String mapperPath;
}
