package org.art.tetragallery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.art.tetragallery.model.dto.Bid.BidDtoGet;
import org.art.tetragallery.model.dto.Bid.BidDtoPost;
import org.art.tetragallery.model.dto.Customer.CustomerDtoPost;
import org.art.tetragallery.model.entity.*;
import org.art.tetragallery.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

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
    private AuctionRep auctionRep;
    @Autowired
    private ArtistRep artistRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private ProductRep productRep;
    private User user2;
    private User user;
    private Artist artist;
    private Product product;
    private Auction auction;
    private Customer customer;
    @Autowired
    private BidRep bidRep;

    private CustomerDtoPost createCustomerEntity(String name, String email) {
        CustomerDtoPost dto = new CustomerDtoPost();
        dto.setName(name);
        dto.setEmail(email);
        return dto;
    }

    @BeforeEach
    void beforeEach() throws Exception {
        user = new User();
        user.setName("Rand");
        user.setEmail("Rand@email");
        user = userRep.save(user);

        user2 = new User();
        user2.setName("Mand");
        user2.setEmail("Mand@email");
        user2 = userRep.save(user2);

        customer = new Customer();
        customer.setUser(user);
        customer = customerRep.save(customer);

        artist = new Artist();
        artist.setUser(user2);
        artist = artistRep.save(artist);

        product = new Product();
        product.setDescription("Product 1");
        product.setArtist(artist);
        product = productRep.save(product);


        auction = new Auction();
        auction.setSecretPrice(new BigDecimal("100"));
        auction.setSeller(artist);
        auction.setProduct(product);
        auction = auctionRep.save(auction);
    }

    @AfterEach
    void afterEach() {
        bidRep.deleteAll();
        auctionRep.deleteAll();
        productRep.deleteAll();
        artistRep.deleteAll();
        customerRep.deleteAll();
        userRep.deleteAll();
    }

    @Test
    void createCustomer() throws Exception {
        CustomerDtoPost dto = new CustomerDtoPost();
        dto.setName("han");
        dto.setEmail("han@gmail.com");

        String jsonDto = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/customer/")))
                .andExpect(jsonPath("$.name").value("han"))
                .andExpect(jsonPath("$.email").value("han@gmail.com"))
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


    @Test
    void fetchCustomerBids_success() throws Exception {
        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("550.00"));
        bid.setCustomer(customer);
        bid.setAuction(auction);
        bid = bidRep.save(bid);

        Bid bid2 = new Bid();
        bid2.setAmount(new BigDecimal("600.00"));
        bid2.setCustomer(customer);
        bid2.setAuction(auction);
        bid2 = bidRep.save(bid2);

        mockMvc.perform(get("/api/customer/bids/"+customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(bid.getId()))
                .andExpect(jsonPath("$[0].amount").value(bid.getAmount().doubleValue()));
    }

    @Test
    void placeBid_success() throws Exception {
        BidDtoPost bid1 = new BidDtoPost();
        bid1.setAmount(new BigDecimal("550.00"));
        bid1.setCustomerId(customer.getId());
        bid1.setAuctionId(auction.getId());

        mockMvc.perform(post("/api/customer/create/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}