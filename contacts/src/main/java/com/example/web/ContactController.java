package com.example.web;


import com.example.pojo.Contact;
import com.example.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 留言控制器
 *
 * @author tians
 * @date 2022/09/05
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private IContactService contactService;

    /**
     * 插入留言
     *
     * @param contact 联系
     * @return {@link Boolean}
     */
    @PostMapping()
    public Boolean insertContact(@RequestBody Contact contact){
        return contactService.save(contact);
    }

    /**
     * 得到接留言数
     *
     * @return {@link Integer}
     */
    @GetMapping("count")
    public Integer getContactCount(){
        return contactService.count();
    }

    /**
     * 获取联系人
     *
     * @return {@link List}<{@link Contact}>
     */
    @GetMapping
    public List<Contact> getContacts(){
        return contactService.list();
    }

}
