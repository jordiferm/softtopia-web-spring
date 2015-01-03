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
import com.softtopia.web.domain.Technology;
import com.softtopia.web.repository.TechnologyRepository;

/**
 * Test class for the TechnologyResource REST controller.
 *
 * @see TechnologyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
public class TechnologyResourceTest {
    
    private static final String DEFAULT_ID = "1";
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
        
    private static final String DEFAULT_POST_ID = "SAMPLE_TEXT";
    private static final String UPDATED_POST_ID = "UPDATED_TEXT";
        
    @Inject
    private TechnologyRepository technologyRepository;

    private MockMvc restTechnologyMockMvc;

    private Technology technology;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TechnologyResource technologyResource = new TechnologyResource();
        ReflectionTestUtils.setField(technologyResource, "technologyRepository", technologyRepository);

        this.restTechnologyMockMvc = MockMvcBuilders.standaloneSetup(technologyResource).build();

        technology = new Technology();
        technology.setId(DEFAULT_ID);

        technology.setDescription(DEFAULT_DESCRIPTION);
        technology.setPostId(DEFAULT_POST_ID);
    }

    @Test
    public void testCRUDTechnology() throws Exception {

        // Create Technology
        restTechnologyMockMvc.perform(post("/app/rest/technologys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technology)))
                .andExpect(status().isOk());

        // Read Technology
        restTechnologyMockMvc.perform(get("/app/rest/technologys/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.toString()));

        // Update Technology
        technology.setDescription(UPDATED_DESCRIPTION);
        technology.setPostId(UPDATED_POST_ID);

        restTechnologyMockMvc.perform(post("/app/rest/technologys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technology)))
                .andExpect(status().isOk());

        // Read updated Technology
        restTechnologyMockMvc.perform(get("/app/rest/technologys/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.description").value(UPDATED_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.postId").value(UPDATED_POST_ID.toString()));

        // Delete Technology
        restTechnologyMockMvc.perform(delete("/app/rest/technologys/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Read nonexisting Technology
        restTechnologyMockMvc.perform(get("/app/rest/technologys/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }
}
