package org.art.tetragallery.mapper;

import org.art.tetragallery.model.dto.Artist.ArtistDtoGet;
import org.art.tetragallery.model.dto.Product.ProductDtoGet;
import org.art.tetragallery.model.entity.Artist;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtistMapper {
    private final ProductMapper productMapper;

    public ArtistMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ArtistDtoGet toDto(Artist artist) {
        ArtistDtoGet dto = new ArtistDtoGet();
        dto.setArtistId(artist.getId());
        dto.setName(artist.getUser().getName());
        dto.setEmail(artist.getUser().getEmail());
        return dto;
    }

    public List<ProductDtoGet> toDtoProducts(Artist artist) {
        return artist.getProducts().stream().map(p -> productMapper.toDto(p)).toList();
    }
}
