package com.example;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mapper.ShopMapper;
import com.example.pojo.Echarts;
import com.example.pojo.Shop;
import com.example.service.IShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class ShopApplicationTests {
    @Autowired
    private IShopService shopService;
    @Autowired
    private ShopMapper shopMapper;

    @Test
    void contextLoads() {
        List<Shop> list = shopService.list();
        System.out.println(list);
    }

    //    @Test
//    void contextLoads2() {
//        Shop shop1 = shopMapper.findById(1l);
//        System.out.println(shop1);
//    }
    @Test
    void testTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UpdateWrapper<Shop> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 2)
                .set("start_time", formatter.format(date));
        shopService.update(updateWrapper);
    }

    @Test
    void testTime2() {
          List<Shop> list = shopService.list();
        for (Shop shop : list) {
            System.out.println(shop);
        }
    }
    @Test
    void testadd() {
        Shop shop=new Shop();
        shop.setName("haohao");
        shopService.save(shop);

    }
    @Test
    void testEcharts() {
        List<Echarts> echars = shopService.getEchars2();
        for (Echarts echar : echars) {
            System.out.println(echar);
        }
    }
    @Test
    void agreeCancleSendOrder(){
            UpdateWrapper<Shop> updateWrapper =new UpdateWrapper<>();
            updateWrapper.eq("trade_filed",3)
                    .set("send_id",null)
                    .set("start_time", null)
                    .eq("id",10)
                    .set("trade_filed",0);
            boolean update = shopService.update(updateWrapper);
        System.out.println(update);
    }
    @Test
    void testDateTime() throws ParseException {
       /* List<Shop> list = shopService.list();
        for (Shop shop : list) {
            Date time = shop.getTradeTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date date = new Date();
            if ("".equals(time)) {
                date = sdf.parse(String.valueOf(time));
                String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                shop.setTradeTime(formatStr);
            }
            System.err.println(formatStr);
        }*/
    }
}
