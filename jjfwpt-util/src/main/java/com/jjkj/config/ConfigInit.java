//package com.jjkj.config;
//
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ConfigInit {
//    @Value("${jjfwpt.use.redis:false}")
//    private boolean useRedis;
//
//    @Value("${jjfwpt.use.mybatis:false}")
//    private boolean useMybatis;
//
//    @Value("${jjfwpt.use.fastdfs:false}")
//    private boolean useFastdfs;
//
////    @Bean
////    public RedisConfig initRedisConfig(){
////        if(useRedis){
////            return new RedisConfig();
////        }else {
////            return null;
////        }
////    }
//
//
//    @Bean
//    public MybatisPlusConfig initMybatisConfig(){
//        if(useMybatis){
//            return new MybatisPlusConfig();
//        }else {
//            return null;
//        }
//    }
//    @Bean
//    public FastDfsConfig initFastDfsConfig(){
//        if(useFastdfs){
//            return new FastDfsConfig();
//        }else {
//            return null;
//        }
//    }
//}
