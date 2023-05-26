package com.spring.basics.controller;

import com.spring.basics.model.Encryption;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")

public class EncryptionController
{
    @GetMapping("/greet")
    public String greet(Encryption encryption)
    {
        return "<h1>Welcome To The Encryption Program "+encryption.getFirstName()+encryption.getLastName();
    }


}
