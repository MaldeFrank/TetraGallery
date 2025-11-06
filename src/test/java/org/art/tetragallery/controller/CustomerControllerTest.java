package org.art.tetragallery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.art.tetragallery.model.dto.Customer.CustomerDtoPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(jsonPath("$.name", "Rand").value("Rand"))
                .andExpect(jsonPath("$.email", "Rand@gmail.com").value("Rand@gmail.com"))
                .andExpect(jsonPath("$.customerId").exists());
    }
}