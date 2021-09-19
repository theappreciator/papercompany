package com.jesstucker.papercompany.controllers;

import com.jesstucker.papercompany.models.DTO.CustomerDTO;
import com.jesstucker.papercompany.services.CustomerService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }



    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        }
        catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) {
        return new ResponseEntity<>(service.create(customer), HttpStatus.OK);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customer) {
        try {
            return new ResponseEntity<>(service.update(id, customer), HttpStatus.OK);
        }
        catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable UUID id) {
        try {
            service.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EmptyResultDataAccessException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
