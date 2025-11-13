package org.art.tetragallery.model.dto.Product;
import lombok.Data;

@Data
public class ArtDtoGet {
    private int artId;
    private String title;
    private String artist;
    private String imageUrl;
    private String description;
    private String category;
}