package org.art.tetragallery.mapper;

import org.art.tetragallery.model.dto.Auction.AuctionDtoGet;
import org.art.tetragallery.model.entity.Auction;
import org.springframework.stereotype.Component;

@Component
public class AuctionMapper {
    private final ProductMapper productMapper;
    private final ArtistMapper artistMapper;

    public AuctionMapper(ProductMapper productMapper, ArtistMapper artistMapper) {
        this.productMapper = productMapper;
        this.artistMapper = artistMapper;
    }

    public AuctionDtoGet toDto(Auction auction) {
        AuctionDtoGet dto = new AuctionDtoGet();
        dto.setId(auction.getId());
        dto.setProduct(productMapper.toDto(auction.getProduct()));
        dto.setArtist(artistMapper.toDto(auction.getSeller()));
        dto.setSecretPrice(auction.getSecretPrice());
        return dto;
    }
}
