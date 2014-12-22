package com.softtopia.web.repository;

import com.softtopia.web.domain.Project;
import com.softtopia.web.domain.ProjectGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Project entity.
 */
public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findByProjectGroup(ProjectGroup projectGroup);
}
