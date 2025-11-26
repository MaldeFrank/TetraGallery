package org.art.tetragallery.controller;

import lombok.RequiredArgsConstructor;
import org.art.tetragallery.model.entity.Artist;
import org.art.tetragallery.model.entity.Role;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.ArtistRep;
import org.art.tetragallery.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final UserRep userRep;

    public AdminController(UserRep userRep) {
        this.userRep = userRep;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSummary>> getAllUsers() {
        List<UserSummary> users = userRep.findAll().stream()
                .map(u -> new UserSummary(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole().name()
                ))
                .toList();

        return ResponseEntity.ok(users);
    }

    public record UserSummary(Long id, String name, String email, String role) {}

    // Grant ARTIST privileges
    @PostMapping("/promote/{userId}")
    public ResponseEntity<Void> promoteToArtist(@PathVariable Long userId) {
        User user = userRep.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setRole(Role.ARTIST);
        userRep.save(user);
        return ResponseEntity.ok().build();
    }

    // Revoke ARTIST privileges, back to COSTUMER
    @PostMapping("/revoke/{userId}")
    public ResponseEntity<Void> revokeArtist(@PathVariable Long userId) {
        User user = userRep.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setRole(Role.COSTUMER);
        userRep.save(user);
        return ResponseEntity.ok().build();
    }

}