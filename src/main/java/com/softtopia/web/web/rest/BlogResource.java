package com.softtopia.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softtopia.web.domain.Blog;
import com.softtopia.web.domain.Technology;
import com.softtopia.web.repository.BlogRepository;
import com.softtopia.web.security.AuthoritiesConstants;
import com.softtopia.web.service.WordpresService;
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
 * REST controller for managing Blog.
 */
@RestController
@RequestMapping("/app")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    @Inject
    private BlogRepository blogRepository;

    /**
     * POST  /rest/blogs -> Create a new blog.
     */
    @RequestMapping(value = "/rest/blogs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    @Timed
    public void create(@RequestBody Blog blog) {
        log.debug("REST request to save Blog : {}", blog);
        blogRepository.save(blog);
    }

    /**
     * GET  /rest/blogs -> get all the blogs.
     */
    @RequestMapping(value = "/rest/blogs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Blog> getAll() {
        log.debug("REST request to get all Blogs");
        return blogRepository.findAll();
    }

    /**
     * GET  /rest/blogs/:id -> get the "id" blog.
     */
    @RequestMapping(value = "/rest/blogs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Blog> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Blog : {}", id);
        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/blogs/:id -> delete the "id" blog.
     */
    @RequestMapping(value = "/rest/blogs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Blog : {}", id);
        blogRepository.delete(id);
    }

    @RequestMapping(value="/rest/blog-body/{id}",
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WordpressPost> getBody(@PathVariable String id) {
        Blog blog = blogRepository.findOne(id);
        String postId = blog.getPostId();
        return new ResponseEntity<WordpressPost>(WordpresService.getPost(postId), HttpStatus.OK);
    }

}
