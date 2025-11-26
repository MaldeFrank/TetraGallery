package org.art.tetragallery.controller;

import lombok.RequiredArgsConstructor;
import org.art.tetragallery.model.dto.Auth.LoginRequest;
import org.art.tetragallery.model.dto.Auth.LoginResponse;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.UserRep;
import org.art.tetragallery.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRep userRep;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userRep.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId(), user.getRole(), user.getEmail());

        LoginResponse resp = new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
        return ResponseEntity.ok(resp);
    }
}
