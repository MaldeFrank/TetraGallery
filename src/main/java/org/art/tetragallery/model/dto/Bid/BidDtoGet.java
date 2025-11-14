package org.art.tetragallery.model.dto.Bid;

import lombok.Data;
import org.art.tetragallery.model.dto.Auction.AuctionDtoGet;
import org.art.tetragallery.model.dto.Customer.CustomerDtoGet;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BidDtoGet {
    private Long id;
    private CustomerDtoGet customer;
    private AuctionDtoGet auction;
    private BigDecimal amount;
    private Timestamp timestamp;
}
