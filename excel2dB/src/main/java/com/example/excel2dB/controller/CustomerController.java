package com.example.excel2dB.controller;

import com.example.excel2dB.model.Customer;
import com.example.excel2dB.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/home")
public class CustomerController
{
    private CustomerService customerService;
    @PostMapping("/upload-customers-data")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file")MultipartFile file)
    {
        this.customerService.saveCustomersToDatabase(file);
        return ResponseEntity.ok(Map.of("Message","Customers Data Uploaded & Saved to Database Successfully"));
    }
    @GetMapping()
    public ResponseEntity<List<Customer>> getCustomers()
    {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.FOUND);
    }

}
