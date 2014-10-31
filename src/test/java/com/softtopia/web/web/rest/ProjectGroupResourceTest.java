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
import com.softtopia.web.domain.ProjectGroup;
import com.softtopia.web.repository.ProjectGroupRepository;

/**
 * Test class for the ProjectGroupResource REST controller.
 *
 * @see ProjectGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
public class ProjectGroupResourceTest {
    
    private static final String DEFAULT_ID = "1";
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
        
    @Inject
    private ProjectGroupRepository projectgroupRepository;

    private MockMvc restProjectGroupMockMvc;

    private ProjectGroup projectgroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectGroupResource projectgroupResource = new ProjectGroupResource();
        ReflectionTestUtils.setField(projectgroupResource, "projectgroupRepository", projectgroupRepository);

        this.restProjectGroupMockMvc = MockMvcBuilders.standaloneSetup(projectgroupResource).build();

        projectgroup = new ProjectGroup();
        projectgroup.setId(DEFAULT_ID);

        projectgroup.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    public void testCRUDProjectGroup() throws Exception {

        // Create ProjectGroup
        restProjectGroupMockMvc.perform(post("/app/rest/projectgroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectgroup)))
                .andExpect(status().isOk());

        // Read ProjectGroup
        restProjectGroupMockMvc.perform(get("/app/rest/projectgroups/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));

        // Update ProjectGroup
        projectgroup.setDescription(UPDATED_DESCRIPTION);

        restProjectGroupMockMvc.perform(post("/app/rest/projectgroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectgroup)))
                .andExpect(status().isOk());

        // Read updated ProjectGroup
        restProjectGroupMockMvc.perform(get("/app/rest/projectgroups/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.description").value(UPDATED_DESCRIPTION.toString()));

        // Delete ProjectGroup
        restProjectGroupMockMvc.perform(delete("/app/rest/projectgroups/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Read nonexisting ProjectGroup
        restProjectGroupMockMvc.perform(get("/app/rest/projectgroups/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }
}
