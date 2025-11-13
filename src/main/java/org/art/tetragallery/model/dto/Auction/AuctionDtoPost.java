package org.art.tetragallery.model.dto.Auction;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class AuctionDtoPost {
    private Long artistId;
    private int productId;
    private BigDecimal secretPrice;
}
