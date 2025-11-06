package org.art.tetragallery.model.dto.Customer;


import lombok.Data;

@Data
public class CustomerDtoGet {
    private Long customerId;
    private Long userId;
    private String name;
    private String email;
}
