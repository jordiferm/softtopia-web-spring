package com.softtopia.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softtopia.web.domain.Project;
import com.softtopia.web.domain.Technology;
import com.softtopia.web.repository.TechnologyRepository;
import com.softtopia.web.security.AuthoritiesConstants;
import com.softtopia.web.service.WordpresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Technology.
 */
@RestController
@RequestMapping("/app")
public class TechnologyResource {

    private final Logger log = LoggerFactory.getLogger(TechnologyResource.class);

    @Inject
    private TechnologyRepository technologyRepository;

    /**
     * POST  /rest/technologys -> Create a new technology.
     */
    @RequestMapping(value = "/rest/technologys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    @Timed
    public void create(@RequestBody Technology technology) {
        log.debug("REST request to save Technology : {}", technology);
        technologyRepository.save(technology);
    }

    /**
     * GET  /rest/technologys -> get all the technologys.
     */
    @RequestMapping(value = "/rest/technologys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Technology> getAll() {
        log.debug("REST request to get all Technologys");
        return technologyRepository.findAll();
    }

    /**
     * GET  /rest/technologys/:id -> get the "id" technology.
     */
    @RequestMapping(value = "/rest/technologys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Technology> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Technology : {}", id);
        Technology technology = technologyRepository.findOne(id);
        if (technology == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(technology, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/technologys/:id -> delete the "id" technology.
     */
    @RequestMapping(value = "/rest/technologys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Technology : {}", id);
        technologyRepository.delete(id);
    }

    @RequestMapping(value="/rest/technology-body/{id}",
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WordpressPost> getBody(@PathVariable String id) {
        Technology technology = technologyRepository.findOne(id);
        String postId = technology.getPostId();
        return new ResponseEntity<WordpressPost>(WordpresService.getPost(postId), HttpStatus.OK);
    }

}
