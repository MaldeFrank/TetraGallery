package org.art.tetragallery.services;


import org.art.tetragallery.mapper.CustomerMapper;
import org.art.tetragallery.model.dto.Customer.CustomerDtoGet;
import org.art.tetragallery.model.dto.Customer.CustomerDtoPost;
import org.art.tetragallery.model.entity.Customer;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.CustomerRep;
import org.art.tetragallery.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerService {

    private final CustomerRep customerRep;
    private final UserRep userRep;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRep customerRep, UserRep userRep, CustomerMapper customerMapper) {
        this.customerRep = customerRep;
        this.userRep = userRep;
        this.customerMapper = customerMapper;
    }

    public CustomerDtoGet createCustomer(CustomerDtoPost dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User savedUser = userRep.save(user);

        Customer customer = new Customer();
        customer.setUser(savedUser);
        Customer savedCustomer = customerRep.save(customer);

        return customerMapper.toDto(savedCustomer);
    }

    public CustomerDtoGet fetchCustomer(Long id) {
        Customer customer = customerRep.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return customerMapper.toDto(customer);
    }

}
