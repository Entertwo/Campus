package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ContactMapper;
import com.example.pojo.Contact;
import com.example.service.IContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactService extends ServiceImpl<ContactMapper, Contact> implements IContactService{



}
