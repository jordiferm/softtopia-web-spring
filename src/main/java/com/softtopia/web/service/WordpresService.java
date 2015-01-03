package com.softtopia.web.service;

import com.softtopia.web.web.rest.WordpressPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jordi on 1/2/15.
 */
public class WordpresService {
    public static WordpressPost getPost(String postId) {
        RestTemplate restTemplate = new RestTemplate();
        WordpressPost post = restTemplate.getForObject("https://public-api.wordpress.com/rest/v1/sites/jordiferm.wordpress.com/posts/" + postId, WordpressPost.class);
        return post;
    }

}
