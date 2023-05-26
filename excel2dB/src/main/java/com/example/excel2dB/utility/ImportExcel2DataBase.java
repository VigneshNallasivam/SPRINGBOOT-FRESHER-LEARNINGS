package com.example.excel2dB.utility;

import com.example.excel2dB.model.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ImportExcel2DataBase {
    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<Customer> getCustomersDataFromExcel(InputStream inputStream) {
        List<Customer> customers = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Customer");
            int rowIndex = 0;
            for(Row row : sheet)
            {
                if(rowIndex == 0)
                {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex =0;
                Customer customer = new Customer();
                while(cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch(cellIndex)
                    {
                        case 0 -> customer.setId((int) cell.getNumericCellValue());
                        case 1 -> customer.setCustomerName(cell.getStringCellValue());
                        case 2 -> customer.setCountry(cell.getStringCellValue());
                        case 3 -> customer.setCode((int) cell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                customers.add(customer);
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return customers;
    }

}
