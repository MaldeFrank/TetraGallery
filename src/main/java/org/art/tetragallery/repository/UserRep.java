package org.art.tetragallery.repository;

import org.art.tetragallery.model.entity.User;

import java.util.Optional;

public interface UserRep extends BaseRep<User,Long>{
    Optional<User> findByEmail(String email);

}
