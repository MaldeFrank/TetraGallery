package org.art.tetragallery.controller;

import org.art.tetragallery.model.dto.Product.*;
import org.art.tetragallery.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/art")
public class ArtController {
    private ProductService artService;

    public ArtController(ProductService artService){
        this.artService = artService;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtDtoGet> createArt(@RequestBody ArtDtoPost dto){
        ArtDtoGet createdArt = artService.createArt(dto);

        return ResponseEntity.created(URI.create("/api/art" + createdArt.getArtId())).body(createdArt);
    }

    @GetMapping
    public ResponseEntity<List<ProductDtoGet>> getArtList(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size
    ){
        List<ProductDtoGet> artList = artService.getProducts(page, size);
        return ResponseEntity.ok(artList);
    }

}
