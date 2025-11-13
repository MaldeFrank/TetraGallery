package org.art.tetragallery.model.dto.Product;

import lombok.Data;

import java.util.List;

@Data
public class ProductDtoGet {
    private int id;
    private String description;
    private String artistName;
    private List<String> tags;
}