package org.art.tetragallery.controller;

import org.art.tetragallery.model.dto.Artist.ArtistDtoGet;
import org.art.tetragallery.model.dto.Artist.ArtistDtoPost;
import org.art.tetragallery.services.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    public ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtistDtoGet> createCustomer(@RequestBody ArtistDtoPost dto) {
        ArtistDtoGet artist = artistService.createArtist(dto);
        return ResponseEntity
                .created(URI.create("/api/artist/" + artist.getArtistId()))
                .body(artist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDtoGet> fetchArtist(@PathVariable Long id) {
        ArtistDtoGet artistDtoGet = artistService.fetchArtist(id);
        return ResponseEntity.created(URI.create("/api/artist"))
                .body(artistDtoGet);
    }
}
