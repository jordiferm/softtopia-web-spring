package com.softtopia.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softtopia.web.domain.Project;
import com.softtopia.web.domain.ProjectGroup;
import com.softtopia.web.repository.ProjectGroupRepository;
import com.softtopia.web.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/app")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectGroupRepository projectGroupRepository;

    /**
     * POST  /rest/projects -> Create a new project.
     */
    @RequestMapping(value = "/rest/projects",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Project project) {
        //project.setProjectGroup(projectGroupRepository.findAll().get(0));
        //log.debug("REST request to save Project : {}", project);

        projectRepository.save(project);
    }

    /**
     * GET  /rest/projects -> get all the projects.
     */
    @RequestMapping(value = "/rest/projects",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Project> getAll() {
        List<Project> projects = projectRepository.findAll();
        log.debug("REST request to get all Projects");
        List<Project> projects2 = projectRepository.findByProjectGroup(projectGroupRepository.findAll().get(0));
        for (Project project : projects2) {
            log.debug("Projects of first project group ---- ");
            log.debug(project.toString());
        }

        return projects;
    }

    /**
     * GET  /rest/projects/:id -> get the "id" project.
     */
    @RequestMapping(value = "/rest/projects/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Project : {}", id);
        Project project = projectRepository.findOne(id);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/projects/:id -> delete the "id" project.
     */
    @RequestMapping(value = "/rest/projects/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Project : {}", id);
        projectRepository.delete(id);
    }

    @RequestMapping(value="/rest/project-body/{id}",
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WordpressPost> getBody(@PathVariable String id) {
        RestTemplate restTemplate = new RestTemplate();
        WordpressPost post = restTemplate.getForObject("https://public-api.wordpress.com/rest/v1/sites/jordiferm.wordpress.com/posts/4", WordpressPost.class);
        //TODO: Get postId from project, and return it.
        return new ResponseEntity<WordpressPost>(post, HttpStatus.OK);
    }

    @RequestMapping(value="/rest/project-body-html/{id}",
            method= RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    @Timed
    public ResponseEntity<String> getBodyHtml(@PathVariable String id) {
        RestTemplate restTemplate = new RestTemplate();
        WordpressPost post = restTemplate.getForObject("https://public-api.wordpress.com/rest/v1/sites/jordiferm.wordpress.com/posts/4", WordpressPost.class);

        return new ResponseEntity<>(post.getContent(), HttpStatus.OK);
    }


}
