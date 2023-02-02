package com.example.customerrest.controller;

import com.example.customerrest.dao.CustomerDao;
import com.example.customerrest.dao.CustomerDto;
import com.example.customerrest.ds.Customer;
import com.example.customerrest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /*CustomerDto(  Integer id,
                            String code,
                            @JsonProperty(value = "first_name") String firstName,
                            @JsonProperty(value = "last_name") String lastName,
                            String email){}
    */
    //curl -X POST -H "Content-Type: application/json" -d '{"id":null,"code":"AB","first_name":"Ashly","last_name":"Burn","email":"ashly@gmail.com"}' localhost:8080/customers
    @PostMapping(value = "customers",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDto){
        CustomerDto savedCustomer=customerService.saveCustomer(customerDto);
        return ResponseEntity
                .created(URI.create("http://localhost:8080/customers"))
                .body(customerDto);
    }

    @GetMapping("/creation")
    public String init(){
        List.of(
                new CustomerDto(null,"TH","Thomas","Shelby","thomas@gmail.com"),
                new CustomerDto(null,"AT","Arthur","Shelby","authur@gmail.com"),
                new CustomerDto(null,"JH","John","Shelby","john@gmail.com")
        )
                .forEach(customerService::saveCustomer);
        return "success";
    }

    //made by entity body
    @GetMapping(value = "/customers/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<CustomerDto> listAllCustomer(){
        return customerService.listCustomers();
    }

    @GetMapping("/customers")
    public ResponseEntity<Iterable<CustomerDto>> listCustomer(){
        /*return new ResponseEntity<>(
                customerService.listCustomers(),
                HttpStatus.valueOf(200));*/
        return ResponseEntity
                .accepted()
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(customerService.listCustomers());
    }

    @GetMapping("/customers/customer")
    public ResponseEntity<CustomerDto> customersById(@RequestParam("id")int id){
        return ResponseEntity
                .ok()
                .body(customerService.findCustomerById(id));
    }

}
