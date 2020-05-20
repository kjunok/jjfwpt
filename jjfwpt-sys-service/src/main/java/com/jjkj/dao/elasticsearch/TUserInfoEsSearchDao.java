package com.jjkj.dao.elasticsearch;


import com.jjkj.model.TUserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TUserInfoEsSearchDao extends ElasticsearchRepository<TUserInfo, Long> {

}