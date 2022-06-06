package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Echarts;
import com.example.pojo.PageResult;
import com.example.pojo.Shop;

import java.util.List;


public interface IShopService extends IService<Shop> {





    Boolean insertShop(Shop shop);

    Boolean updateShopAndFiled(Shop shop);

    IPage<Shop> getShopsBySendId(Integer sendId,Integer currentPage, Integer pageSize);

    Boolean updateTradeFiled(Integer id);

    IPage<Shop> getShopsByUserId(Integer userId, Integer currentPage, Integer pageSize);

    Boolean updateShop(Shop shop);

    Boolean agreeCancleSendOrder(Integer shopId);

    Boolean AgreeToReceiveTheGoods(Shop shop);

    Boolean arrived(Integer id);

    Double getShopsPriceCount();

    IPage<Shop> getAll(Integer currentPage, Integer pageSize);

    IPage<Shop> getAllTradeFiled0(Integer currentPage, Integer pageSize, Shop shop);

    IPage<Shop> getAllTradeFiled0(Integer currentPage, Integer pageSize);

    PageResult getAllTradeFiled0ByElasticsearch(Integer currentPage, Integer pageSize, Shop shop);

    Boolean deleteById(Integer id);

    List<Echarts> getEchars();

    List<Echarts> getEchars2();

    IPage<Shop> getAll(Integer currentPage, Integer pageSize, Shop shop);

    Integer getShopsCountByTradeFiled0();
}
