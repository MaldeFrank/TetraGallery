package org.art.tetragallery.services;


import org.art.tetragallery.mapper.BidMapper;
import org.art.tetragallery.mapper.CustomerMapper;
import org.art.tetragallery.model.dto.Bid.BidDtoGet;
import org.art.tetragallery.model.dto.Bid.BidDtoPost;
import org.art.tetragallery.model.dto.Customer.CustomerDtoGet;
import org.art.tetragallery.model.dto.Customer.CustomerDtoPost;
import org.art.tetragallery.model.entity.Auction;
import org.art.tetragallery.model.entity.Bid;
import org.art.tetragallery.model.entity.Customer;
import org.art.tetragallery.model.entity.User;
import org.art.tetragallery.repository.AuctionRep;
import org.art.tetragallery.repository.BidRep;
import org.art.tetragallery.repository.CustomerRep;
import org.art.tetragallery.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.art.tetragallery.model.entity.Role;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRep customerRep;
    private final UserRep userRep;
    private final AuctionRep auctionRep;
    private final CustomerMapper customerMapper;
    private final BidRep bidRep;
    private final BidMapper bidMapper;

    public CustomerService(CustomerRep customerRep, UserRep userRep, AuctionRep auctionRep, CustomerMapper customerMapper, BidRep bidRep, BidMapper bidMapper) {
        this.customerRep = customerRep;
        this.userRep = userRep;
        this.auctionRep = auctionRep;
        this.customerMapper = customerMapper;
        this.bidRep = bidRep;
        this.bidMapper = bidMapper;
    }

    public CustomerDtoGet createCustomer(CustomerDtoPost dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.COSTUMER);

        User savedUser = userRep.save(user);

        Customer customer = new Customer();
        customer.setUser(savedUser);
        Customer savedCustomer = customerRep.save(customer);

        return customerMapper.toDto(savedCustomer);
    }

    public CustomerDtoGet fetchCustomer(Long id) {
        Customer customer = customerRep.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return customerMapper.toDto(customer);
    }

    public List<BidDtoGet> fetchCustomerBids(Long id) {
        Customer customer = customerRep.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return bidMapper.toDtoBids(customer);
    }

    @Transactional
    public BidDtoGet createBid(BidDtoPost dto) {
        Customer customer = customerRep.findById(dto.getCustomerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Auction auction = auctionRep.findById(dto.getAuctionId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        Bid bid = new Bid();
        bid.setCustomer(customer);
        bid.setAuction(auction);

        Bid persisted = bidRep.save(bid);
        return bidMapper.toDto(persisted);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRep.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        User user = customer.getUser();

        customerRep.delete(customer);

        if (user != null && user.getId() != null) {
            userRep.deleteById(user.getId());
        }
    }
}
