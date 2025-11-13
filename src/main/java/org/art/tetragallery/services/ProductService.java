package org.art.tetragallery.services;

import org.art.tetragallery.model.dto.Product.ArtDtoGet;
import org.art.tetragallery.model.dto.Product.ArtDtoPost;
import org.art.tetragallery.model.dto.Product.ProductDtoGet;
import org.art.tetragallery.model.entity.Artist;
import org.art.tetragallery.model.entity.Product;
import org.art.tetragallery.repository.ArtRepository;

import java.util.List;
import java.util.Arrays;

public class ProductService {
    private int nextId = 1;
    private final ArtRepository artRepository;

    public ProductService(ArtRepository artRepository){
        this.artRepository = artRepository;
    }

    public ArtDtoGet createArt(ArtDtoPost dto){
        Product product = new Product();
        product.setId(nextId++); //TODO Replace with database id
        product.setDescription(dto.getDescription());

        Artist artist = new Artist();
        artist.setId((long) nextId); //TODO replace with database id, should query the database for available ids first etc.
        artist.setUser(null);
        product.setArtist(artist);

        product.setTags(List.of());

        ArtDtoGet response = new ArtDtoGet();
        response.setArtId(product.getId());
        response.setTitle(dto.getTitle());
        response.setArtist(dto.getArtist());
        response.setImageUrl(dto.getImageUrl());
        response.setDescription(dto.getDescription());
        response.setCategory(dto.getCategory());

    return response;

    }


    public List<ProductDtoGet> getProducts(int page, int size){ //Returns static products, should query the database and set the list from that.
        ProductDtoGet p1 = new ProductDtoGet();
        p1.setId(1);
        p1.setDescription("Sunset over the lake");
        p1.setArtistName("Alice Rivera");
        p1.setTags(Arrays.asList("Landscape", "Oil", "Nature"));

        ProductDtoGet p2 = new ProductDtoGet();
        p2.setId(2);
        p2.setDescription("Abstract Composition #5");
        p2.setArtistName("Liam Chen");
        p2.setTags(Arrays.asList("Abstract", "Modern", "Colorful"));

        ProductDtoGet p3 = new ProductDtoGet();
        p3.setId(3);
        p3.setDescription("Portrait of a Dream");
        p3.setArtistName("Nina Patel");
        p3.setTags(Arrays.asList("Portrait", "Acrylic", "Fantasy"));

        return Arrays.asList(p1, p2, p3);
    }

}
