package org.art.tetragallery.controller;

import org.art.tetragallery.model.dto.Bid.BidDtoGet;
import org.art.tetragallery.model.dto.Bid.BidDtoPost;
import org.art.tetragallery.model.dto.Customer.CustomerDtoGet;
import org.art.tetragallery.model.dto.Customer.CustomerDtoPost;
import org.art.tetragallery.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDtoGet> createCustomer(@RequestBody CustomerDtoPost dto) {
        CustomerDtoGet createdCustomer = customerService.createCustomer(dto);

        return ResponseEntity
                .created(URI.create("/api/customer/" + createdCustomer.getCustomerId()))
                .body(createdCustomer);
    }

    @PostMapping("/create/bid")
    public ResponseEntity<BidDtoGet> createBid(@RequestBody BidDtoPost dto) {
        BidDtoGet bid = customerService.createBid(dto);
        return ResponseEntity.created(URI.create("/api/customer"+bid.getId())).body(bid);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDtoGet> fetchCustomer(@PathVariable Long id) {
        CustomerDtoGet customer = customerService.fetchCustomer(id);
        return ResponseEntity.created(URI.create("/api/customer/"+id)).body(customer);
    }

    @GetMapping("/bids/{id}")
    public ResponseEntity<List<BidDtoGet>> fetchBid(@PathVariable Long id) {
        List<BidDtoGet> bids = customerService.fetchCustomerBids(id);
        return ResponseEntity.ok(bids);
    }
}
