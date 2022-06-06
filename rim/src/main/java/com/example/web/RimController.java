package com.example.web;


import com.example.pojo.Rim;
import com.example.service.IRimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rims")
public class RimController {

    @Autowired
    private IRimService rimService;


    @GetMapping()
    public List<Rim> getAll(){
        return rimService.list();
    }


}
