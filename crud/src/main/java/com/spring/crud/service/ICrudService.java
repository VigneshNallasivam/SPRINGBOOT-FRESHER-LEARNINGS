package com.spring.crud.service;

import com.spring.crud.dto.CrudDTO;
import com.spring.crud.model.Crud;

import java.util.List;

public interface ICrudService {
    Crud create(CrudDTO crudDTO);

    Crud update(CrudDTO crudDTO,int rollNo);

    List<Crud> readAll();

    Crud readByRollNo(int rollNo);

    String delete(int rollNo);
}
