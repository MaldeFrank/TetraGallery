package org.art.tetragallery.controller;

import lombok.RequiredArgsConstructor;
import org.art.tetragallery.model.entity.Artist;
import org.art.tetragallery.model.entity.Role;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.ArtistRep;
import org.art.tetragallery.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final UserRep userRep;

    public AdminController(UserRep userRep) {
        this.userRep = userRep;
    }

    @PostMapping("/promote/{userId}")
    public void promoteToArtist(@PathVariable Long userId) {
        User user = userRep.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setRole(Role.ARTIST);
        userRep.save(user);
    }
}