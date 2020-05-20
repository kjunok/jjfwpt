package com;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.jjkj.auth.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
* OAuth2认证授权服务
* @ EnableDiscoveryClient 启用服务注册发现
*/
@SpringBootApplication(
   exclude={ DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class})
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
public class BootStrap_AUTH_GETEWAY {
   public static void main(String[] args) {
      SpringApplication.run(BootStrap_AUTH_GETEWAY.class, args);
   }
   /**
    * 资源过滤器
    * @return 资源过滤器
    */
   @Bean
   public AccessFilter accessFilter(){
      return new AccessFilter();
   }
}