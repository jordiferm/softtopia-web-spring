package com.softtopia.web.repository;

import com.softtopia.web.domain.Technology;
        import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Technology entity.
 */
public interface TechnologyRepository extends MongoRepository<Technology, String> {

}
