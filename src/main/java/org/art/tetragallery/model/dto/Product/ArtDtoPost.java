package org.art.tetragallery.model.dto.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class ArtDtoPost {
    private String title;
    private long artistId;
    private String imageUrl;
    private String description;
    private String category;
    private List<String> tagNames;
    private BigDecimal secretAmount;
}