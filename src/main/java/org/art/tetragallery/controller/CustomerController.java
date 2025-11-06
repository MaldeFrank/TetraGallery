package org.art.tetragallery.controller;

import org.art.tetragallery.model.dto.Customer.CustomerDtoGet;
import org.art.tetragallery.model.dto.Customer.CustomerDtoPost;
import org.art.tetragallery.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDtoGet> createCustomer(@RequestBody CustomerDtoPost dto) {
        CustomerDtoGet createdCustomer = customerService.createCustomer(dto);

        return ResponseEntity
                .created(URI.create("/api/customer/" + createdCustomer.getCustomerId()))
                .body(createdCustomer);
    }
}
