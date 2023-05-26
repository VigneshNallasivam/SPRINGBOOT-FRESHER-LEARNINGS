package com.spring.crud.controller;

import com.spring.crud.dto.CrudDTO;
import com.spring.crud.dto.ResponseDTO;
import com.spring.crud.model.Crud;
import com.spring.crud.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class CrudController
{
    @Autowired
    ICrudService service;
    @GetMapping("/greet")
    public String greeting(CrudDTO crudDTO)
    {
        return "Hai "+" "+crudDTO.getFirstName()+" "+crudDTO.getLastName();
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseDTO> create(@RequestBody CrudDTO crudDTO)
    {
        Crud crud = service.create(crudDTO);
        ResponseDTO responseDTO = new ResponseDTO("Data Created Successfully",crud);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/put/{rollNo}")
    public ResponseEntity<ResponseDTO> update(@RequestBody CrudDTO crudDTO,@PathVariable int rollNo)
    {
        Crud crud = service.update(crudDTO,rollNo);
        ResponseDTO responseDTO = new ResponseDTO("Data Updated Successfully",crud);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> readAll()
    {
        List<Crud> crudDTOList = service.readAll();
        ResponseDTO responseDTO = new ResponseDTO("All Details Were Found..!!",crudDTOList);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @GetMapping("/getByRollNo/{rollNo}")
    public ResponseEntity<ResponseDTO> readAll(@PathVariable int rollNo)
    {
        Crud crud = service.readByRollNo(rollNo);
        ResponseDTO responseDTO = new ResponseDTO("Data Found for given Roll-Number..!!",crud);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{rollNo}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable int rollNo)
    {
        service.delete(rollNo);
        ResponseDTO responseDTO = new ResponseDTO("Data for "+rollNo+" was deleted..!!","Successfully..!!");
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

}
