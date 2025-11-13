package org.art.tetragallery.controller;

import org.art.tetragallery.model.dto.Auction.AuctionDtoGet;
import org.art.tetragallery.model.dto.Auction.AuctionDtoPost;
import org.art.tetragallery.model.dto.Auction.AuctionUpdateDto;
import org.art.tetragallery.services.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/auction/")
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/create")
    public ResponseEntity<AuctionDtoGet> createAuction(@RequestBody AuctionDtoPost dto) {
        AuctionDtoGet auctionDtoGet = auctionService.createAuction(dto);
        return ResponseEntity.created(URI.create("/api/auction" + auctionDtoGet.getId()))
                .body(auctionDtoGet);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuctionDtoGet> updateAuction(@PathVariable Long id, @RequestBody AuctionUpdateDto dto) {
        AuctionDtoGet auctionDtoGet = auctionService.updateAuction(id, dto);
        return ResponseEntity.ok(auctionDtoGet);
    }
}
