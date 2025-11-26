package org.art.tetragallery.controller;

import lombok.RequiredArgsConstructor;
import org.art.tetragallery.model.entity.Role;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DevController {

    private final UserRep userRep;

    // For testing: make a user ADMIN by id
    @PostMapping("/make-admin/{userId}")
    public ResponseEntity<Void> makeAdmin(@PathVariable Long userId) {
        User user = userRep.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setRole(Role.ADMIN);
        userRep.save(user);
        return ResponseEntity.ok().build();
    }
}
