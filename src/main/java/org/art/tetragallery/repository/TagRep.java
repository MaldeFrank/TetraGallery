package org.art.tetragallery.repository;

import org.art.tetragallery.model.entity.Tag;
import java.util.Optional;

public interface TagRep extends BaseRep<Tag, Long> {
    Optional<Tag> findByName(String name);
}
