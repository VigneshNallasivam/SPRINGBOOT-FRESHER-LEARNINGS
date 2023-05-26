package com.java.basics.service;


import com.java.basics.entity.AOP;
import com.java.basics.repository.AopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AopService
{
    @Autowired
    AopRepository repo;

    public AOP insertion(AOP aop)
    {
       return repo.save(aop);
    }

}
