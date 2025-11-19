package org.art.tetragallery.services;

import java.math.BigDecimal;

import org.art.tetragallery.mapper.AuctionMapper;
import org.art.tetragallery.model.dto.Auction.AuctionDtoGet;
import org.art.tetragallery.model.dto.Auction.AuctionDtoPost;
import org.art.tetragallery.model.dto.Auction.AuctionUpdateDto;
import org.art.tetragallery.model.entity.Auction;
import org.art.tetragallery.model.entity.Artist;
import org.art.tetragallery.model.entity.Bid;
import org.art.tetragallery.model.entity.Customer;
import org.art.tetragallery.model.entity.Product;
import org.art.tetragallery.repository.AuctionRep;
import org.art.tetragallery.repository.ArtistRep;
import org.art.tetragallery.repository.ProductRep;
import org.art.tetragallery.repository.CustomerRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class AuctionService {

    private final AuctionRep auctionRep;
    private final ArtistRep artistRep;
    private final ProductRep productRep;
    private final AuctionMapper auctionMapper;
    private final CustomerRep customerRep;

    public AuctionService(AuctionRep auctionRep, ArtistRep artistRep, ProductRep productRep, AuctionMapper auctionMapper, CustomerRep customerRep) {
        this.auctionRep = auctionRep;
        this.artistRep = artistRep;
        this.productRep = productRep;
        this.auctionMapper = auctionMapper;
        this.customerRep = customerRep;
    }

    @Transactional
    public AuctionDtoGet createAuction(AuctionDtoPost dto) {
        Long artistId = dto.getArtistId();
        int productId = dto.getProductId();

        Artist artist = artistRep.findById(artistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        Product product = productRep.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Auction auction = new Auction();
        auction.setProduct(product);
        auction.setSeller(artist);
        auction.setSecretPrice(dto.getSecretPrice());

        Auction persisted = auctionRep.save(auction);
        return auctionMapper.toDto(persisted);
    }

    public AuctionDtoGet updateAuction(Long id, AuctionUpdateDto dto) {
        Auction existingAuction = auctionRep.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (dto.getSecretPrice() != null) {
            existingAuction.setSecretPrice(dto.getSecretPrice());
        }

        Auction auction = auctionRep.save(existingAuction);
        return auctionMapper.toDto(auction);
    }

    @Transactional
    public String bid(long id, BigDecimal bid, long userId){
        Auction existingAuction = auctionRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Bid> existingBids = existingAuction.getBids();
        Customer biddingUser = customerRep.getReferenceById(userId);
        Bid currentBid = new Bid();
        currentBid.setAmount(bid);
        currentBid.setCustomer(biddingUser);
        currentBid.setAuction(existingAuction);
        existingBids.add(currentBid);
        existingAuction.setBids(existingBids);
        if (existingAuction.getSecretPrice().compareTo(bid) <= 0){ //Accept bid
            return "Bid accepted";
        }else{
            return "Bid too low";
        }
    }
}