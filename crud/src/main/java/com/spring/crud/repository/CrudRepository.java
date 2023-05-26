package com.spring.crud.repository;

import com.spring.crud.model.Crud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudRepository extends JpaRepository<Crud,Integer>
{

}
