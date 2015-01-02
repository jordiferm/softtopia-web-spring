package com.softtopia.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softtopia.web.domain.Project;
import com.softtopia.web.domain.ProjectGroup;
import com.softtopia.web.repository.ProjectGroupRepository;
import com.softtopia.web.repository.ProjectRepository;
import com.softtopia.web.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing ProjectGroup.
 */
@RestController
@RequestMapping("/app")
public class ProjectGroupResource {

    private final Logger log = LoggerFactory.getLogger(ProjectGroupResource.class);

    @Inject
    private ProjectGroupRepository projectgroupRepository;

    @Inject
    private ProjectRepository projectRepository;

    /**
     * POST  /rest/projectgroups -> Create a new projectgroup.
     */
    @RequestMapping(value = "/rest/projectgroups",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    @Timed
    public void create(@RequestBody ProjectGroup projectgroup) {
        log.debug("REST request to save ProjectGroup : {}", projectgroup);
        projectgroupRepository.save(projectgroup);
    }

    /**
     * GET  /rest/projectgroups -> get all the projectgroups.
     */
    @RequestMapping(value = "/rest/projectgroups",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProjectGroup> getAll() {
        log.debug("REST request to get all ProjectGroups");
        return projectgroupRepository.findAll();
    }

    /**
     * GET  /rest/projectgroups/project -> get all the projectgroups by project id.
     */
    @RequestMapping(value = "/rest/projectgroups/{id}/projects",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Project> getByProject(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get all ProjectGroups of project : {}", id);
        return projectRepository.findByProjectGroup(projectgroupRepository.findOne(id));
    }


    /**
     * GET  /rest/projectgroups/:id -> get the "id" projectgroup.
     */
    @RequestMapping(value = "/rest/projectgroups/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectGroup> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get ProjectGroup : {}", id);
        ProjectGroup projectgroup = projectgroupRepository.findOne(id);
        if (projectgroup == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectgroup, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/projectgroups/:id -> delete the "id" projectgroup.
     */
    @RequestMapping(value = "/rest/projectgroups/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete ProjectGroup : {}", id);
        projectgroupRepository.delete(id);
    }
}
