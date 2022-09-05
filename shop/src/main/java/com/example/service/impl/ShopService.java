package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.UserClient;
import com.example.mapper.ShopMapper;
import com.example.pojo.Echarts;
import com.example.pojo.PageResult;
import com.example.pojo.Shop;
import com.example.pojo.User;
import com.example.service.IShopService;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShopService extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private RestHighLevelClient client;

    /**
     * 根据传来的页码进行查询
     * 返回交易字段为0的
     *
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @param shop        商店
     * @return 返回Ipage
     */
    @Override
    public IPage<Shop> getAllTradeFiled0(Integer currentPage, Integer pageSize, Shop shop) {
        IPage<Shop> page = new Page<Shop>(currentPage, pageSize);
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(shop.getName()),"name",shop.getName());
        queryWrapper.eq("trade_filed",0)
                    .ne(shop.getUserId()!=null,"user_id",shop.getUserId())
        ;
        IPage<Shop> shopIPage = shopMapper.selectPage(page, queryWrapper);
        List<Shop> records = shopIPage.getRecords();
        for (Shop record : records) {
            Integer userId = record.getUserId();
            if (userId != null) {
                User user = userClient.findById(userId);
                record.setUser(user);
            }
        }
        return shopIPage;
    }

    /**
     * 返回交易字段为0的
     *
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @return 返回Ipage
     */
    @Override
    public IPage<Shop> getAllTradeFiled0(Integer currentPage, Integer pageSize) {
        IPage<Shop> page = new Page(currentPage, pageSize);
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("trade_filed",0)
        ;
        IPage<Shop> shopIPage = shopMapper.selectPage(page, queryWrapper);
        List<Shop> records = shopIPage.getRecords();
        for (Shop record : records) {
            Integer userId = record.getUserId();
            if (userId != null) {
                User user = userClient.findById(userId);
                record.setUser(user);
            }
        }
        return shopIPage;
    }

    /**
     * 在elasticsearch中查找
     *
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @param shop        查的参数
     * @return list集合
     */
    @Override
    public PageResult getAllTradeFiled0ByElasticsearch(Integer currentPage, Integer pageSize, Shop shop) {
        try {
            //创建请求
            SearchRequest request = new SearchRequest("campus");
            //查询条件
            buildQuery(shop, request);
            //数据分页
            int page = currentPage;
            int size = pageSize;
            request.source().from((page-1)*size).size(size);

            //发起请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            SearchHits hits = response.getHits();
            //获取总条数
            long total = hits.getTotalHits().value;
            SearchHit[] hitsHits = hits.getHits();
            List<Shop> shops=new ArrayList<>();
            for (SearchHit hitsHit : hitsHits) {
                String source = hitsHit.getSourceAsString();
                Shop isShop = JSON.parseObject(source, Shop.class);
                //添加user
                Integer userId = isShop.getUserId();
                if (userId != null) {
                    User user = userClient.findById(userId);
                    isShop.setUser(user);
                }
                shops.add(isShop);
            }
            return new PageResult(total,shops);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

    }

    /**
     * 删除商品通过id
     *
     * @param id 商品id
     * @return boolean
     */
    @Override
    public Boolean deleteById(Integer id) {
        return this.removeById(id);
    }

    /**
     * 得到echars
     * 查询echarts数据
     *
     * @return {@link List}<{@link Echarts}>
     */
    @Override
    public List<Echarts> getEchars() {
        List<Echarts> echarts=shopMapper.getEcharts();
        return echarts;
    }

    /**
     * 得到echars2
     * 查询echarts数据
     *
     * @return {@link List}<{@link Echarts}>
     */
    @Override
    public List<Echarts> getEchars2() {
        List<Echarts> echarts2=shopMapper.getEcharts2();
        return echarts2;
    }

    private void buildQuery(Shop shop, SearchRequest request) {
        //创建dsl
        //改造成bool请求
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if("".equals(shop.getName()) || shop.getName()==null){
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }else {
            boolQueryBuilder.must(QueryBuilders.matchQuery("all", shop.getName()));

           /* boolQueryBuilder.filter(QueryBuilders.termQuery("city",key));

            boolQueryBuilder.filter(QueryBuilders.termQuery("brand",key));

            boolQueryBuilder.filter(QueryBuilders.termQuery("starName",key));*/
        }
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("userId",shop.getUserId()));
        //价格
       /* if( requestPrams.getMinPrice() != null ){
            boolQueryBuilder.filter(QueryBuilders
                    .rangeQuery("price")
                    .gte(requestPrams.getMinPrice())
                    .lte(requestPrams.getMaxPrice()));
        }*/
        //request.source().query(boolQueryBuilder);
        //查询数据
        request.source().query(boolQueryBuilder);
    }

    /**
     * 查询全部
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link IPage}<{@link Shop}>
     */
    @Override
    public IPage<Shop> getAll(Integer currentPage, Integer pageSize) {
        IPage<Shop> page = new Page<Shop>(currentPage, pageSize);
        IPage<Shop> shopIPage = shopMapper.selectPage(page, null);
        List<Shop> records = shopIPage.getRecords();
        for (Shop record : records) {
            Integer userId = record.getUserId();
            if (userId != null) {
                User user = userClient.findById(userId);
                record.setUser(user);
            }
        }
        return shopIPage;
    }

    /**
     * 得到所有
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @param shop        商店
     * @return {@link IPage}<{@link Shop}>
     */
    @Override
    public IPage<Shop> getAll(Integer currentPage, Integer pageSize, Shop shop) {
        IPage<Shop> page = new Page<Shop>(currentPage, pageSize);
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(shop.getId()!=null,"id",shop.getId());
        IPage<Shop> shopIPage = shopMapper.selectPage(page, queryWrapper);
        List<Shop> records = shopIPage.getRecords();
        for (Shop record : records) {
            Integer userId = record.getUserId();
            if (userId != null) {
                User user = userClient.findById(userId);
                record.setUser(user);
            }
        }
        return shopIPage;
    }

    /**
     * 被贸易filed0商店数
     *
     * @return {@link Integer}
     */
    @Override
    public Integer getShopsCountByTradeFiled0() {
        QueryWrapper<Shop> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("trade_filed",0);
        return shopMapper.selectCount(queryWrapper);
    }

    /**
     * 被发送商店id
     * SendOrder界面
     * 获取代送商品,代送id要相等
     * 发送feign远端调用，添加user信息
     * 添加user信息
     *
     * @param sendId      把身份证
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link IPage}<{@link Shop}>
     */
    @Override
    public IPage<Shop> getShopsBySendId(Integer sendId, Integer currentPage, Integer pageSize) {
        IPage<Shop> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("send_id", sendId);
        IPage<Shop> shopIPage = shopMapper.selectPage(page, queryWrapper);
        List<Shop> records = shopIPage.getRecords();
        for (Shop record : records) {
            Integer userId = record.getUserId();
            User user = userClient.findById(userId);
            record.setUser(user);
        }
        return shopIPage;
    }

    /**
     * 被用户id商店
     * 根据代送id，查询代送的单子
     *
     * @param userId      用户id
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link IPage}<{@link Shop}>
     */
    @Override
    public IPage<Shop> getShopsByUserId(Integer userId, Integer currentPage, Integer pageSize) {
        IPage<Shop> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        IPage<Shop> shopIPage = shopMapper.selectPage(page, queryWrapper);
        List<Shop> records = shopIPage.getRecords();
        for (Shop record : records) {
            User user = userClient.findById(userId);
            record.setUser(user);
        }
        return shopIPage;
    }

    /**
     * 更新商店
     * DemanOrder的弹出框，修改商品
     *
     * @param shop 商店
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateShop(Shop shop) {
        UpdateWrapper<Shop> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",shop.getId());
        boolean update = this.update(shop, updateWrapper);
        if(update){
            saveDocument(shop.getId());
        }
        return update;
    }

    /**
     * 同意取消发送订单
     * 同意取消
     * 修改交易字段为0
     * 删除代送人信息
     * 设置时间为当前时间
     *
     * @param shopId 商店id
     * @return {@link Boolean}
     */
    @Override
    public Boolean agreeCancleSendOrder(Integer shopId) {
        UpdateWrapper<Shop> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("trade_filed",3)
                .set("send_id",null)
                .set("start_time", null)
                .eq("id",shopId)
                .set("trade_filed",0);
        boolean update = this.update(updateWrapper);
        if (update) {
            saveDocument(shopId);
        }
        return update;
    }

    /**
     * 同意接收货物
     * 添加end_time时间，
     *
     * @param shop 商店
     * @return {@link Boolean}
     */
    @Override
    public Boolean AgreeToReceiveTheGoods(Shop shop) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UpdateWrapper<Shop> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",shop.getId())
                .set("trade_filed",4)
                .set("end_time",formatter.format(date));
        return this.update(updateWrapper);
    }

    /**
     * 到达
     * 修改tradeFiled字段为2
     * 商品已送达
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Override
    public Boolean arrived(Integer id) {
        UpdateWrapper<Shop> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                .set("trade_filed",2);
        return this.update(updateWrapper);
    }

    /**
     * 让商店价格计算
     * 查询交易时间存在的价格
     *
     * @return {@link Double}
     */
    @Override
    public Double getShopsPriceCount() {
        Double sum = Double.valueOf(0);
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.isNotNull("end_time").select("sum(price) as sumAll");
        Map<String, Object> map = this.getMap(queryWrapper);
        for (Object value : map.values()) {
            sum= (Double) value;
        }
        return sum;
    }


    /**
     * 更新贸易了
     * 代送之后1-3---取消交易
     * 根据shopid修改tradeFiled3
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateTradeFiled(Integer id) {
        UpdateWrapper<Shop> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("trade_filed",3);
        return this.update(updateWrapper);
    }


    /**
     * 插入商店
     * 插入商品数据
     *
     * @param shop 商品数据
     * @return boolean
     */
    @Override
    public Boolean insertShop(Shop shop) {
        boolean flag = this.save(shop);
        if(flag){
            //更新elasticSearch
            saveDocument(shop.getId());
        }
        return flag;
    }

    /**
     * 更新商店和提交
     * 更新交易字段
     * 设置时间
     * 添加sendId代送人信息
     *
     * @param shop 商品信息
     * @return boolean
     */
    @Override
    public Boolean updateShopAndFiled(Shop shop) {
        //查询用户的代送状态是否激活
        Integer sendId = shop.getSendId();
        User user = userClient.findById(sendId);
        if(user.getStatus()) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            UpdateWrapper<Shop> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("send_id", shop.getSendId())
                    .set("trade_filed", 1)
                    .set("start_time", formatter.format(date))
                    .eq("id", shop.getId())
                    .eq("trade_filed", 0)
            ;
            boolean flag = this.update(shop, updateWrapper);
            if(flag){
                deleteDocument(shop.getId());
            }
            return flag;
        }
        return false;
    }

    /**
     * 保存文档
     *保存elasticSearch
     * @param id id
     */
    public void  saveDocument(int id){
        try {
            //查数据库
            Shop shop = this.getById(id);
            //创建请求路径
            IndexRequest request = new IndexRequest("campus").id(shop.getId().toString());
            //请求参数,序列化为json
            request.source(JSON.toJSONString(shop), XContentType.JSON);
            //发请求
            IndexResponse index = client.index(request, RequestOptions.DEFAULT);
            //System.out.println(index.getResult()+"添加elastic的"+id+"文档");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除elasticSearch文档
     *
     * @param id id
     */
    public void  deleteDocument(int id){
        try {
            //创建请求路径
            DeleteRequest request = new DeleteRequest("campus", String.valueOf(id));

            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            //发请求
            DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);

            //System.out.println(delete.getResult()+"删除elastic的"+id+"文档");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
