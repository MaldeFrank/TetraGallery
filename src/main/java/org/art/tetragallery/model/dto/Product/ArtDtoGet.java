package org.art.tetragallery.model.dto.Product;
import lombok.Data;
import org.art.tetragallery.model.entity.Artist;

@Data
public class ArtDtoGet {
    private long artId;
    private String title;
    private Artist artist;
    private String imageUrl;
    private String description;
    private String category;
}