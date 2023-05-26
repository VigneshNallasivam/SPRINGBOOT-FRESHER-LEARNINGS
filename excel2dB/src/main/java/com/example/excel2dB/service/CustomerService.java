package com.example.excel2dB.service;

import com.example.excel2dB.model.Customer;
import com.example.excel2dB.repository.CustomerRepository;
import com.example.excel2dB.utility.ImportExcel2DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void saveCustomersToDatabase(MultipartFile file) {
        if (ImportExcel2DataBase.isValidExcelFile(file)) {
            try {
                List<Customer> customers = ImportExcel2DataBase.getCustomersDataFromExcel(file.getInputStream());
                this.customerRepository.saveAll(customers);
            } catch (IOException e) {
                throw new IllegalArgumentException("File is NOT a valid Excel file");
            }
        }

    }
    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }
}
