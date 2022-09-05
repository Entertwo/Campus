package com.example.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.Echarts;
import com.example.pojo.PageResult;
import com.example.pojo.Shop;
import com.example.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品控制器
 *
 * @author tians
 * @date 2022/09/05
 */
@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private IShopService shopService;

    /**
     * mutualhelp界面
     * 返回交易字段为0的
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/{currentPage}/{pageSize}")
    IPage<Shop> getAll(@PathVariable Integer currentPage, @PathVariable Integer pageSize,Shop shop){
        IPage<Shop> shops = shopService.getAll(currentPage, pageSize,shop);
        return shops;
    }
    /*//不用elasticsearch使用该方法
    @GetMapping("/{currentPage}/{pageSize}")
    IPage<Shop> getAll(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        IPage<Shop> shops = shopService.getALl(currentPage, pageSize);
        List<Shop> records = shops.getRecords();
        for (Shop record : records) {
            System.out.println(record);
        }
        return shops;
    }*/

    /**
     * 得到所有贸易filed0
     * mutualhelp界面
     * 查询交易字段为0的
     * 根据shop的userId和name查询不属于自己的商品
     *
     * @param currentPage 当前页
     * @param pageSize    页的大小
     * @param shop        商品名称
     * @return 商品全部信息
     */
    @GetMapping("/{currentPage}/{pageSize}/tradeFiled/0")
    IPage<Shop> getAllTradeFiled0(@PathVariable Integer currentPage, @PathVariable Integer pageSize, Shop shop){
        IPage<Shop> shops = shopService.getAllTradeFiled0(currentPage, pageSize,shop);
        return shops;
    }

    /**
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @param shop        商店
     * @return {@link PageResult}
     */
    @GetMapping("/{currentPage}/{pageSize}/tradeFiled/0/elasticseartch")
    PageResult getAllTradeFiledByelasticserch0(@PathVariable Integer currentPage, @PathVariable Integer pageSize,Shop shop){
        //第一版
        //IPage<Shop> shops = shopService.getAllTradeFiled0(currentPage, pageSize,shop);
        PageResult result = shopService.getAllTradeFiled0ByElasticsearch(currentPage, pageSize, shop);
        //System.out.println(result);
        return result;
    }

    /**
     * SendOrder界面
     * 根据代送id，查询代送的单子
     *
     * @param sendId      把身份证
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link IPage}<{@link Shop}>
     */
    @GetMapping("/sendId/{sendId}/{currentPage}/{pageSize}")
    IPage<Shop> getShopsBySendId(@PathVariable Integer sendId,@PathVariable Integer currentPage,
                                 @PathVariable Integer pageSize){
        IPage<Shop> shops=shopService.getShopsBySendId(sendId,currentPage,pageSize);
        return shops;
    }

    /**
     * 根据代送id，查询代送的单子
     *
     * @param userId      用户id
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link IPage}<{@link Shop}>
     */
    @GetMapping("/userId/{userId}/{currentPage}/{pageSize}")
    IPage<Shop> getShopsByUserId(@PathVariable Integer userId,@PathVariable Integer currentPage,
                                 @PathVariable Integer pageSize){
        IPage<Shop> shops=shopService.getShopsByUserId(userId,currentPage,pageSize);
        return shops;
    }

    /**
     * 查询商店数
     *
     * @return {@link Integer}
     */
    @GetMapping("/count")
    public Integer queryShopsCount(){
        return shopService.count();
    }

    /**
     * 查询交易字段为零的商品数
     *
     * @return {@link Integer}
     */
    @GetMapping("/tradeFiled/count")
    public Integer QueryShopsCountAndTradeFiled0(){
        return shopService.getShopsCountByTradeFiled0();
    }

    /**
     * 查询商品金额
     *
     * @return {@link Double}
     */
    @GetMapping("sum/price")
    public Double queryShopsPriceCount(){
        return shopService.getShopsPriceCount();
    }

    /**
     * 主页商品循环展示
     *
     * @param currentPage 当前页面
     * @return {@link IPage}<{@link Shop}>
     */
    @GetMapping("/cycle/{currentPage}")
    public IPage<Shop> getCycleShops(@PathVariable Integer currentPage){
        IPage<Shop> shops = shopService.getAllTradeFiled0(currentPage, 3);
        return shops;
    }

    /**
     * 得到echarts
     * 图形化界面数据传输
     *
     * @return {@link List}<{@link Echarts}>
     */
    @GetMapping("/echarts")
    public List<Echarts> getEcharts(){
        List<Echarts> echarts=shopService.getEchars();
        return echarts;
    }

    /**
     * 得到echarts2
     * 图形化界面数据传输
     *
     * @return {@link List}<{@link Echarts}>
     */
    @GetMapping("/echarts2")
    public List<Echarts> getEcharts2(){
        List<Echarts> echarts=shopService.getEchars2();
        return echarts;
    }

    /**通过设置请求头，实现跨域问题*/
   /* @PostMapping
    public void insertShop(@RequestBody Shop shop,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
    }*/

    /**
     * 插入商品数据
     *
     * @param shop 商店
     * @return {@link Boolean}
     */
    @PostMapping
    public Boolean insertShop(@RequestBody Shop shop) {
       Boolean flag = shopService.insertShop(shop);
       return flag;
    }

    /**
     * 更新商店
     * mutualhelp代送请求
     * 更新tradeFiled字段为1
     *
     * @param shop 商店
     * @return {@link Boolean}
     */
    @PutMapping("/tradeFiled")
    public Boolean updateShopAndFiled(@RequestBody Shop shop) {
        Boolean flag=shopService.updateShopAndFiled(shop);
       return flag;
    }

    /**
     * 更新商店
     * demanOrder,弹出框更新
     *
     * @param shop 商店
     * @return {@link Boolean}
     */
    @PutMapping
    public Boolean updateShop(@RequestBody Shop shop) {
        Boolean flag=shopService.updateShop(shop);
        return flag;
    }

    /**
     * 取消发送订单
     * 修改tradeFiled字段为3 问题件
     * SendOrder的界面请求
     * 取消代送请求
     *
     * @param id id
     * @return {@link Boolean}
     */
    @PutMapping("/tradeFiled/1/rowId/{id}")
    public Boolean cancleSendOrder(@PathVariable Integer id) {
        Boolean flag=shopService.updateTradeFiled(id);
        return flag;
    }

    /**
     * 发送订单
     * 修改tradeFiled字段为2
     * SendOrder的界面请求
     * 商品已送达
     *
     * @param id id
     * @return {@link Boolean}
     */
    @PutMapping("/tradeFiled/2/rowId/{id}")
    public Boolean SendOrder(@PathVariable Integer id) {
        System.out.println(id+"daohzadfsdf");
        Boolean flag=shopService.arrived(id);
        return flag;
    }

    /**
     * 同意取消发送订单
     * 同意取消
     * 修改tradeFiled字段3为0 解除问题件为初始件
     * 添加开始时间，为当前时间
     *
     * @param shopId 商店id
     * @return {@link Boolean}
     */
    @PutMapping("/tradeFiled/3/{shopId}")
    public Boolean agreeCancleSendOrder(@PathVariable Integer shopId) {
       Boolean flag=shopService.agreeCancleSendOrder(shopId);
        return flag;
    }

    /**
     * 同意接收货物
     * 确认收货demanOrder订单
     * 添加交易时间
     *
     * @param shop 商店
     * @return {@link Boolean}
     */
    @PutMapping("/tradeFiled/2")
    public Boolean AgreeToReceiveTheGoods(@RequestBody Shop  shop) {
         Boolean flag=shopService.AgreeToReceiveTheGoods(shop);
        return flag;
    }

    /**
     * 删除通过id
     * 删除shop
     *
     * @param id 商品id
     * @return boolean
     */
    @DeleteMapping("/{id}")
    public  Boolean deleteById(@PathVariable Integer id) {
        return  shopService.deleteById(id);
    }
}
