package com.softtopia.web.repository;

import com.softtopia.web.domain.ProjectGroup;
        import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProjectGroup entity.
 */
public interface ProjectGroupRepository extends MongoRepository<ProjectGroup, String> {

}
