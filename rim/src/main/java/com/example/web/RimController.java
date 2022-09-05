package com.example.web;


import com.example.pojo.Rim;
import com.example.service.IRimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * rim控制器
 *
 * @author tians
 * @date 2022/09/05
 */
@RestController
@RequestMapping("/rims")
public class RimController {

    @Autowired
    private IRimService rimService;


    /**
     * 得到所有
     *
     * @return {@link List}<{@link Rim}>
     */
    @GetMapping()
    public List<Rim> getAll(){
        return rimService.list();
    }


}
