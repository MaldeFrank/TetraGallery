package org.art.tetragallery.model.dto.Auction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AuctionUpdateDto {
    private BigDecimal secretPrice;
}
