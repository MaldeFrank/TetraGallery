package org.art.tetragallery.model.dto.Bid;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BidDtoPost {
    private Long customerId;
    private Long auctionId;
    private BigDecimal amount;
}
