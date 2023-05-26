package com.java.basics.controller;

import com.java.basics.dto.ResponseDTO;
import com.java.basics.dto.ResponseHandler;
import com.java.basics.entity.AOP;
import com.java.basics.service.AopService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class AopController
{

    @Autowired
    AopService service;


    @GetMapping("/greet")
    public String greeting(AOP aop)
    {
        return "Welcome"+aop.getFirstName()+aop.getLastName();
    }

    @PostMapping("/post")
    public ResponseEntity<Object> insert(@RequestBody AOP aop)
    {
        AOP aop1 = service.insertion(aop);
        ResponseDTO responseDTO = new ResponseDTO("Data Inserted Successfully",aop1);
        return ResponseHandler.generateResponse("Actual Realization Retrived Successfully", true, HttpStatus.OK,responseDTO);
    }


}
