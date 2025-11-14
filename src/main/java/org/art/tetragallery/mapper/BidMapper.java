package org.art.tetragallery.mapper;

import org.art.tetragallery.model.dto.Bid.BidDtoGet;
import org.art.tetragallery.model.entity.Bid;
import org.art.tetragallery.model.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BidMapper {
    private final CustomerMapper customerMapper;
    private final AuctionMapper auctionMapper;

    public BidMapper(CustomerMapper customerMapper, AuctionMapper auctionMapper) {
        this.customerMapper = customerMapper;
        this.auctionMapper = auctionMapper;
    }

    public BidDtoGet toDto(Bid bid) {
        BidDtoGet dto = new BidDtoGet();
        dto.setId(bid.getId());
        dto.setCustomer(customerMapper.toDto(bid.getCustomer()));
        dto.setAuction(auctionMapper.toDto(bid.getAuction()));
        dto.setAmount(bid.getAmount());
        dto.setTimestamp(bid.getTimestamp());
        return dto;
    }

    public List<BidDtoGet> toDtoBids(Customer customer) {
        return customer.getBids().stream().map(this::toDto).toList();
    }
}
