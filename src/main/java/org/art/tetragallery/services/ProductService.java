package org.art.tetragallery.services;

import org.art.tetragallery.model.dto.Product.ArtDtoGet;
import org.art.tetragallery.model.dto.Product.ArtDtoPost;
import org.art.tetragallery.model.dto.Product.ProductDtoGet;
import org.art.tetragallery.model.entity.Artist;
import org.art.tetragallery.model.entity.Auction;
import org.art.tetragallery.model.entity.Product;
import org.art.tetragallery.repository.AuctionRep;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.art.tetragallery.repository.ArtistRep;
import org.art.tetragallery.repository.TagRep;
import org.art.tetragallery.repository.ProductRep;
import org.art.tetragallery.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ProductService {
    private final AuctionRep auctionRep;
    private final ArtistRep artistRep;
    private final TagRep tagRep;
    private final ProductRep productRep;

    public ProductService(AuctionRep auctionRep, ArtistRep artistRep, TagRep tagRep, ProductRep productRep){
        this.auctionRep = auctionRep;
        this.artistRep = artistRep;
        this.tagRep = tagRep;
        this.productRep = productRep;
    }

    public ArtDtoGet createArt(ArtDtoPost dto){
        Artist artist = artistRep.findById(dto.getArtistId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setArtist(artist);
        List<Tag> tags = new ArrayList<Tag>();
        for (String tagName : dto.getTagNames()) {
            Tag tag = tagRep.findByName(tagName).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
            tags.add(tag);
        }
        product.setTags(tags);
        product = productRep.save(product);
        Auction auction = new Auction();
        auction.setProduct(product);
        auction.setSeller(artist);
        auction.setSecretPrice(dto.getSecretAmount());
        auction.setBids(List.of());
        auctionRep.save(auction);

        ArtDtoGet response = new ArtDtoGet();
        response.setArtId(auction.getId());
        response.setTitle(dto.getTitle()); //Not in use for some reason? Check Entity classes TODO
        response.setArtist(artist);
        response.setImageUrl(dto.getImageUrl()); //Dont remember what we said to do here.
        response.setDescription(product.getDescription()); 
        response.setCategory(dto.getCategory()); //Dont think this is in use either check Entity classes TODO

    return response;

    }


    public List<ProductDtoGet> getProducts(int page, int size){ //Returns static products, should query the database and set the list from that.
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRep.findAll(pageable);

        return productPage.getContent().stream()
        .map(product -> {
                ProductDtoGet dto = new ProductDtoGet();
                dto.setId(product.getId());
                dto.setDescription(product.getDescription());
                
                if (product.getArtist() != null && product.getArtist().getUser() != null){
                    dto.setArtistName(product.getArtist().getUser().getName());
                }

                List<String> tagNames = product.getTags() != null ? product.getTags().stream().map(Tag::getName).toList() : List.of();
                //hent tags hvis ikke null stream dem fra getname to list og ellers tom
                dto.setTags(tagNames);

                return dto;
            }
        ).toList();
    }

}
