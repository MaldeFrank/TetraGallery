package org.art.tetragallery.services;

import org.art.tetragallery.mapper.ArtistMapper;
import org.art.tetragallery.model.dto.Artist.ArtistDtoGet;
import org.art.tetragallery.model.dto.Artist.ArtistDtoPost;
import org.art.tetragallery.model.dto.Product.ProductDtoGet;
import org.art.tetragallery.model.entity.Artist;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.ArtistRep;
import org.art.tetragallery.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistService {
    private final ArtistRep artistRep;
    private final UserRep userRep;
    private final ArtistMapper artistMapper;

    public ArtistService(ArtistRep artistRep, UserRep userRep, ArtistMapper artistMapper) {
        this.artistRep = artistRep;
        this.userRep = userRep;
        this.artistMapper = artistMapper;
    }

    public ArtistDtoGet createArtist(ArtistDtoPost dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        User savedUser = userRep.save(user);

        Artist artist = new Artist();
        artist.setUser(savedUser);

        Artist savedArtist = artistRep.save(artist);
        return artistMapper.toDto(savedArtist);
    }

    public ArtistDtoGet fetchArtist(Long id) {
        Artist artist = artistRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return artistMapper.toDto(artist);
    }

    public List<ProductDtoGet> fetchArtistProducts(Long id) {
        Artist artist = artistRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return artistMapper.toDtoProducts(artist);
    }

}
