package org.art.tetragallery.model.dto.Artist;

import lombok.Data;

@Data
public class ArtistDtoGet {
    private Long artistId;
    private Long userId;
    private String name;
    private String email;
}
