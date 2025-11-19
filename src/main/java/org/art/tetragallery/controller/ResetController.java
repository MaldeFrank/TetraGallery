package org.art.tetragallery.controller;

import lombok.RequiredArgsConstructor;
import org.art.tetragallery.repository.ArtistRep;
import org.art.tetragallery.repository.CustomerRep;
import org.art.tetragallery.repository.UserRep;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reset")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ResetController {

    private final UserRep userRep;
    private final CustomerRep customerRep;
    private final ArtistRep artistRep;

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteEverything() {

        artistRep.deleteAll();
        customerRep.deleteAll();
        userRep.deleteAll();

        return ResponseEntity.noContent().build();
    }
}