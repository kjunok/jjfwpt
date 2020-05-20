package com.jjkj.util;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.Map;

public class ElasticUtil {
    public static QueryBuilder searchBuilder(Map param){
        QueryBuilder queryBuilder = null;
        if(param.containsKey("field")){
            queryBuilder = QueryBuilders.multiMatchQuery(param.get("keyword"), (String) param.get("field"));
        }else {
            queryBuilder = QueryBuilders.multiMatchQuery(param.get("keyword"));
        }

        return  queryBuilder;
    }

    public static Pageable searchPageable(Map param){
        int start = 0,size=10;
        if(param.containsKey("start")&&param.containsKey("size")){
            start = (int) param.get("start")-1;
            size = (int) param.get("size");
        }
        Pageable pageable = PageRequest.of(start,size);
        return  pageable;
    }

    public static SearchQuery searchQuery(Map param){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(searchBuilder(param))
                .withPageable(searchPageable(param))
                .build();
        return searchQuery;
    }
}
