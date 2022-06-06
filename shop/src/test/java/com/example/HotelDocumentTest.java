package com.example;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pojo.Shop;
import com.example.service.IShopService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class HotelDocumentTest {
    private RestHighLevelClient client;
    @Autowired
    private IShopService iShopService;

    @Test
    void test() {
        System.out.println(client);
    }


    @Test//批量导入
    void testIndexDocumentById() throws IOException {
        QueryWrapper<Shop> queryWrapper=new QueryWrapper();
        queryWrapper.eq("trade_filed",0);
        List<Shop> shops = iShopService.list(queryWrapper);
        //创建BulkRequest请求
        BulkRequest request = new BulkRequest();
        for (Shop shop : shops) {
            //一条一条添加
            request.add(new IndexRequest("campus").id(shop.getId().toString())
                    .source(JSON.toJSONString(shop),XContentType.JSON));
        }
        client.bulk(request, RequestOptions.DEFAULT);
    }

    @Test//批量删除
    void testDeleteAllIndexDocument() throws IOException {
        //创建请求路径
        SearchRequest request=new SearchRequest("campus");
        //查询条件
        request.source().query(QueryBuilders.matchAllQuery());
        //发请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Object id = sourceAsMap.get("id");
            //创建请求路径
            DeleteRequest deleteRequest = new DeleteRequest("campus",  id.toString());
            //发请求
            DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        }
        /*//创建BulkRequest请求
        for (Shop shop : shops) {
            //创建请求路径
            DeleteRequest request = new DeleteRequest("campus", String.valueOf(shop.getId()));
            //发请求
            DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
            System.out.println(delete.getResult());
        }*/
    }

    @BeforeEach
    void setUp() {
        this.client=new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.5.129:9200")
        ));
    }


    @AfterEach
    void down() throws IOException {
        this.client.close();
    }
}
