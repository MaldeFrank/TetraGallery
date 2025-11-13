package org.art.tetragallery.mapper;

import org.art.tetragallery.model.dto.Product.ProductDtoGet;
import org.art.tetragallery.model.entity.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {
    public ProductDtoGet toDto(Product product) {
        ProductDtoGet dto = new ProductDtoGet();
        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setArtistName(product.getArtist().getUser().getName());
        return dto;
    }
}
