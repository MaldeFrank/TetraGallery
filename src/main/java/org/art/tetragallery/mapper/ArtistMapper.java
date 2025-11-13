package org.art.tetragallery.mapper;

import org.art.tetragallery.model.dto.Artist.ArtistDtoGet;
import org.art.tetragallery.model.entity.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    public ArtistDtoGet toDto(Artist artist) {
        ArtistDtoGet dto = new ArtistDtoGet();
        dto.setArtistId(artist.getId());
        dto.setName(artist.getUser().getName());
        dto.setEmail(artist.getUser().getEmail());
        return dto;
    }
}
