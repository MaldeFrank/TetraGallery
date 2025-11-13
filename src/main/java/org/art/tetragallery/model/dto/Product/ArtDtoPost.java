package org.art.tetragallery.model.dto.Product;
import lombok.Data;


@Data
public class ArtDtoPost {
    private String title;
    private String artist;
    private String imageUrl;
    private String description;
    private String category;
}