package com.spring.crud.service;

import com.spring.crud.dto.CrudDTO;
import com.spring.crud.exception.CrudException;
import com.spring.crud.model.Crud;
import com.spring.crud.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CrudService implements ICrudService
{
    @Autowired
    CrudRepository repo;
    @Override
    public Crud create(CrudDTO crudDTO)
    {
        Crud crud = new Crud(crudDTO);
        repo.save(crud);
        return crud;
    }

    @Override
    public Crud update(CrudDTO crudDTO,int rollNo)
    {
        Crud crud = repo.findById(rollNo).get();
        if(repo.findById(rollNo).isPresent())
        {
            crud.setFirstName(crudDTO.getFirstName());
            crud.setLastName(crudDTO.getLastName());
            repo.save(crud);
            return crud;
        }
        else
        {
            throw new CrudException("Roll Number NOT-Found..!!");
        }
    }

    @Override
    public List<Crud> readAll()
    {
        List<Crud> list = repo.findAll();
        if(repo.findAll().isEmpty())
        {
            throw new CrudException("OOPS..!!No Data Found in Data-Base!!");
        }
        else
        {
            return list;
        }
    }

    @Override
    public Crud readByRollNo(int rollNo)
    {
        Crud list = repo.findById(rollNo).get();
        if(repo.findById(rollNo).isPresent())
        {
            return list;
        }
        else
        {
            throw new CrudException("Roll Number is not available..!!");
        }
    }

    @Override
    public String delete(int rollNo)
    {
        if(repo.existsById(rollNo))
        {
            repo.deleteById(rollNo);
            return "Data Deleted!!";
        }
        else
        {
            throw new CrudException("Roll Number is Not Found to be deleted..!!");
        }
    }
}
