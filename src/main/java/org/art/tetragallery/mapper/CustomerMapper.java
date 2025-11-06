package org.art.tetragallery.mapper;

import org.art.tetragallery.model.dto.Customer.CustomerDtoGet;
import org.art.tetragallery.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDtoGet toDto(Customer customer) {
        CustomerDtoGet dto = new CustomerDtoGet();
        dto.setCustomerId(customer.getId());
        dto.setUserId(customer.getUser().getId());
        dto.setName(customer.getUser().getName());
        dto.setEmail(customer.getUser().getEmail());
        return dto;
    }

}