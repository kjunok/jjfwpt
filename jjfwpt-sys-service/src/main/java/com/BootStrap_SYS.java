package com;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
/**
 * @author wangjun
 */

@CrossOrigin
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(value = {"com.jjkj.dao.mapper"})
@EnableElasticsearchRepositories(basePackages = "com.jjkj.model")
public class BootStrap_SYS/* implements CommandLineRunner*/ {

    /*mybatis-plus分页插件*/
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
    /*mybatis-plus执行性分析插件*/
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    public static void main(String[] args) {
        SpringApplication.run(BootStrap_SYS.class, args);
    }

}
