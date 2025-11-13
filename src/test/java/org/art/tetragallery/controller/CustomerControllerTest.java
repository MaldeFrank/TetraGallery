package org.art.tetragallery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.art.tetragallery.model.dto.Customer.CustomerDtoPost;
import org.art.tetragallery.repository.CustomerRep;
import org.art.tetragallery.repository.UserRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRep customerRep;
    @Autowired
    private UserRep userRep;

    private CustomerDtoPost createCustomerEntity(String name, String email) {
        CustomerDtoPost dto = new CustomerDtoPost();
        dto.setName(name);
        dto.setEmail(email);
        return dto;
    }
    @AfterEach
    void afterEach() {
        customerRep.deleteAll();
        userRep.deleteAll();
    }

    @Test
    void createCustomer() throws Exception {
        CustomerDtoPost dto = new CustomerDtoPost();
        dto.setName("Rand");
        dto.setEmail("Rand@gmail.com");

        String jsonDto = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/customer/")))
                .andExpect(jsonPath("$.name").value("Rand"))
                .andExpect(jsonPath("$.email").value("Rand@gmail.com"))
                .andExpect(jsonPath("$.customerId").exists());
    }


    @Test
    void createCustomer_uniqueNameViolation() throws Exception {
        String existingName = "Rand Al'Thor";
        CustomerDtoPost seed = createCustomerEntity(existingName, "unique_setup_email@test.com");
        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seed)))
                .andExpect(status().isCreated());

        CustomerDtoPost dto = new CustomerDtoPost();
        dto.setName(existingName);
        dto.setEmail("new_unique@gmail.com");

        String jsonDto = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createCustomer_uniqueEmailViolation() throws Exception {
        String existingEmail = "Mat@gmail.com";
        CustomerDtoPost seed = createCustomerEntity("unique setup name", existingEmail);
        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seed)))
                .andExpect(status().isCreated());

        CustomerDtoPost dto = new CustomerDtoPost();
        dto.setName("Mat Cauthon");
        dto.setEmail(existingEmail);

        String jsonDto = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}