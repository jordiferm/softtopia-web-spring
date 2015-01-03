package com.softtopia.web.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.softtopia.web.Application;
import com.softtopia.web.domain.Blog;
import com.softtopia.web.repository.BlogRepository;

/**
 * Test class for the BlogResource REST controller.
 *
 * @see BlogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
public class BlogResourceTest {
    
    private static final String DEFAULT_ID = "1";
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
        
    private static final String DEFAULT_POST_ID = "SAMPLE_TEXT";
    private static final String UPDATED_POST_ID = "UPDATED_TEXT";
        
    @Inject
    private BlogRepository blogRepository;

    private MockMvc restBlogMockMvc;

    private Blog blog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BlogResource blogResource = new BlogResource();
        ReflectionTestUtils.setField(blogResource, "blogRepository", blogRepository);

        this.restBlogMockMvc = MockMvcBuilders.standaloneSetup(blogResource).build();

        blog = new Blog();
        blog.setId(DEFAULT_ID);

        blog.setDescription(DEFAULT_DESCRIPTION);
        blog.setPostId(DEFAULT_POST_ID);
    }

    @Test
    public void testCRUDBlog() throws Exception {

        // Create Blog
        restBlogMockMvc.perform(post("/app/rest/blogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blog)))
                .andExpect(status().isOk());

        // Read Blog
        restBlogMockMvc.perform(get("/app/rest/blogs/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.toString()));

        // Update Blog
        blog.setDescription(UPDATED_DESCRIPTION);
        blog.setPostId(UPDATED_POST_ID);

        restBlogMockMvc.perform(post("/app/rest/blogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blog)))
                .andExpect(status().isOk());

        // Read updated Blog
        restBlogMockMvc.perform(get("/app/rest/blogs/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.description").value(UPDATED_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.postId").value(UPDATED_POST_ID.toString()));

        // Delete Blog
        restBlogMockMvc.perform(delete("/app/rest/blogs/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Read nonexisting Blog
        restBlogMockMvc.perform(get("/app/rest/blogs/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }
}
