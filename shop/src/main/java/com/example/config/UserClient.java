package com.example.config;


import com.example.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;

@FeignClient("UserService")
public interface UserClient {
    @GetMapping("/user/{id}")
    User findById(@PathVariable Integer id);

}
