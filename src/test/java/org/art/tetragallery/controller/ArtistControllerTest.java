package org.art.tetragallery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.art.tetragallery.model.dto.Artist.ArtistDtoPost;
import org.art.tetragallery.model.entity.*;
import org.art.tetragallery.repository.ArtistRep;
import org.art.tetragallery.repository.AuctionRep;
import org.art.tetragallery.repository.ProductRep;
import org.art.tetragallery.repository.UserRep;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArtistRep artistRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private ProductRep productRep;
    @Autowired
    private AuctionRep auctionRep;
    private User user;
    private Artist artist;
    private Product product;
    private Auction auction;

    private ArtistDtoPost createArtistEntity(String name, String email) {
        ArtistDtoPost dto = new ArtistDtoPost();
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

        artist = new Artist();
        artist.setUser(user);
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
        auctionRep.deleteAll();
        productRep.deleteAll();
        artistRep.deleteAll();
        userRep.deleteAll();
    }

    @Test
    void createArtist() throws Exception {
        ArtistDtoPost dto = createArtistEntity("han","han@gmail.com");

        String jsonDto = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/artist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/artist/")))
                .andExpect(jsonPath("$.name").value("han"))
                .andExpect(jsonPath("$.email").value("han@gmail.com"))
                .andExpect(jsonPath("$.artistId").exists());
    }

    @Test
    void createArtist_uniqueNameViolation() throws Exception {
        String existingName = "Rand Al'Thor";
        ArtistDtoPost seed = createArtistEntity(existingName, "unique_setup_email@test.com");
        mockMvc.perform(post("/api/artist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seed)))
                .andExpect(status().isCreated());

        ArtistDtoPost dto = createArtistEntity(existingName,"new_unique@gmail.com");

        String jsonDto = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/artist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createArtist_uniqueEmailViolation() throws Exception {
        String existingEmail = "Mat@gmail.com";
        ArtistDtoPost seed = createArtistEntity("unique setup name", existingEmail);
        mockMvc.perform(post("/api/artist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seed)))
                .andExpect(status().isCreated());

        ArtistDtoPost dto = createArtistEntity(existingEmail,"Mat@gmail.com");
        String jsonDto = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/artist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDto))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}