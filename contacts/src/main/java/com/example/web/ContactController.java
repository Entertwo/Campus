package com.example.web;


import com.example.pojo.Contact;
import com.example.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private IContactService contactService;

    @PostMapping()
    public Boolean insertContact(@RequestBody Contact contact){
        return contactService.save(contact);
    }
    @GetMapping("count")
    public Integer getContactCount(){
        return contactService.count();
    }

    @GetMapping
    public List<Contact> getContacts(){
        return contactService.list();
    }

}
