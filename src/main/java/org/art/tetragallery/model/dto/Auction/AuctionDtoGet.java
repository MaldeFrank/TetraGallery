package org.art.tetragallery.model.dto.Auction;

import lombok.Data;
import org.art.tetragallery.model.dto.Artist.ArtistDtoGet;
import org.art.tetragallery.model.dto.Product.ProductDtoGet;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class AuctionDtoGet {
    private Long id;
    private ProductDtoGet product;
    private Timestamp timestamp;
    private BigDecimal secretPrice;
    private ArtistDtoGet artist;
}
