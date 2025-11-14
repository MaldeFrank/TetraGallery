package org.art.tetragallery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.art.tetragallery.model.dto.Auction.AuctionDtoPost;
import org.art.tetragallery.model.dto.Auction.AuctionUpdateDto;
import org.art.tetragallery.model.dto.Bid.BidDtoGet;
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
class AuctionControllerTest {
    @Autowired
    private AuctionRep auctionRep;
    @Autowired
    private ArtistRep artistRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private ProductRep productRep;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private User user2;
    private Artist artist;
    private Product product;
    private Product product2;
    private Auction auction;
    @Autowired
    private CustomerRep customerRep;

    @BeforeEach
    void beforeEach() throws Exception {
        user = new User();
        user.setName("Rand");
        user.setEmail("Rand@email");
        user = userRep.save(user);

        user2 = new User();
        user2.setName("Mand");
        user2.setEmail("Mand@email");
        user2 = userRep.save(user);


        artist = new Artist();
        artist.setUser(user);
        artist = artistRep.save(artist);

        product = new Product();
        product.setDescription("Product 1");
        product.setArtist(artist);
        product = productRep.save(product);

        product2 = new Product();
        product2.setDescription("Product 2");
        product2.setArtist(artist);
        product2 = productRep.save(product2);

        auction = new Auction();
        auction.setSecretPrice(new BigDecimal("100"));
        auction.setSeller(artist);
        auction.setProduct(product);
        auction = auctionRep.save(auction);
    }

    @AfterEach
    void afterEach() {
        auctionRep.deleteAll();
        productRep.deleteAll();
        artistRep.deleteAll();
        customerRep.deleteAll();
        userRep.deleteAll();
    }

    @Test
    void createAuction() throws Exception {
        AuctionDtoPost dto = new AuctionDtoPost();
        dto.setSecretPrice(new BigDecimal("200.00"));
        dto.setArtistId(artist.getId());
        dto.setProductId(product2.getId());
        String dtoString = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/auction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.product").exists());
    }

    @Test
    void updateAuction() throws Exception {
        AuctionUpdateDto dto = new AuctionUpdateDto();

        BigDecimal expectedSecretPrice = new BigDecimal("300.00");
        dto.setSecretPrice(expectedSecretPrice);

        String dtoString = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/api/auction/"+auction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").exists())
                .andExpect(jsonPath("$.secretPrice").value(expectedSecretPrice.doubleValue()));
    }
}