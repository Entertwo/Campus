package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Echarts;
import com.example.pojo.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
    @Select("select count(a.id) as startCount,s.date from \n" +
            "(select date_add(CURDATE(),interval @i:=@i-1 day) as date \n" +
            "from (\n" +
            "select 1 \n" +
            "union all select 1 \n" +
            "union all select 1\n" +
            "union all select 1 \n" +
            "union all select 1 \n" +
            "union all select 1 \n" +
            "union all select 1 \n" +
            "union all select 1) as tmp,\n" +
            " (select @i:= +1) t\n" +
            ") s left join shop a on s.date = date_format(a.start_time,'%Y-%m-%d') \n" +
            "GROUP BY s.date")
    List<Echarts> getEcharts();

    @Select("select count(a.id) as endCount,s.date from \n" +
            "(select date_add(CURDATE(),interval @i:=@i-1 day) as date \n" +
            "from (\n" +
            "select 1 \n" +
            "union all select 1 \n" +
            "union all select 1\n" +
            "union all select 1 \n" +
            "union all select 1 \n" +
            "union all select 1 \n" +
            "union all select 1 \n" +
            "union all select 1) as tmp,\n" +
            " (select @i:= +1) t\n" +
            ") s left join shop a on s.date = date_format(a.end_time,'%Y-%m-%d') \n" +
            "GROUP BY s.date")
    List<Echarts> getEcharts2();
}

