package com.java.basics.repository;

import com.java.basics.entity.AOP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AopRepository extends JpaRepository<AOP,Integer>
{

}
