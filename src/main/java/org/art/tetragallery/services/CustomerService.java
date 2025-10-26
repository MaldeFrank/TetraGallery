package org.art.tetragallery.services;


import org.art.tetragallery.model.entity.Customer;
import org.art.tetragallery.repository.CustomerRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CustomerService {

    private final CustomerRep customerRep;

    public CustomerService(CustomerRep customerRep) {
        this.customerRep = customerRep;
    }

    public List<Customer> getCustomers() {
        return customerRep.findAll();
    }

    public Customer getCustomerById(long id) {
        return customerRep.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

}
