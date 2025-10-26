package org.art.tetragallery.controller;

import org.art.tetragallery.model.entity.Customer;
import org.art.tetragallery.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomers(@PathVariable long id){
        return customerService.getCustomerById(id);
    }
}
