package com.softtopia.web.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import com.softtopia.web.domain.ProjectGroup;
import org.joda.time.LocalDate;

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
import com.softtopia.web.domain.Project;
import com.softtopia.web.repository.ProjectRepository;

/**
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
public class ProjectResourceTest {
    
    private static final String DEFAULT_ID = "1";
    
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
        
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
        
    private static final String DEFAULT_PICTURE = "SAMPLE_TEXT";
    private static final String UPDATED_PICTURE = "UPDATED_TEXT";
        
    private static final LocalDate DEFAULT_DATE_CREATED = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_CREATED = new LocalDate();
        
    private static final LocalDate DEFAULT_DATE_FINISHED = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_FINISHED = new LocalDate();
        
    private static final String DEFAULT_LONG_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_LONG_DESCRIPTION = "UPDATED_TEXT";
        
    @Inject
    private ProjectRepository projectRepository;

    private MockMvc restProjectMockMvc;

    private Project project;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectResource projectResource = new ProjectResource();
        ReflectionTestUtils.setField(projectResource, "projectRepository", projectRepository);

        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource).build();

        project = new Project();
        project.setId(DEFAULT_ID);

        project.setName(DEFAULT_NAME);
        project.setDescription(DEFAULT_DESCRIPTION);
        project.setPicture(DEFAULT_PICTURE);
        project.setDateCreated(DEFAULT_DATE_CREATED);
        project.setDateFinished(DEFAULT_DATE_FINISHED);
        project.setLongDescription(DEFAULT_LONG_DESCRIPTION);
    }

    @Test
    public void testCRUDProject() throws Exception {

        // Create Project
        restProjectMockMvc.perform(post("/app/rest/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isOk());

        // Read Project
        restProjectMockMvc.perform(get("/app/rest/projects/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()))
                .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
                .andExpect(jsonPath("$.dateFinished").value(DEFAULT_DATE_FINISHED.toString()))
                .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()));

        // Update Project
        project.setName(UPDATED_NAME);
        project.setDescription(UPDATED_DESCRIPTION);
        project.setPicture(UPDATED_PICTURE);
        project.setDateCreated(UPDATED_DATE_CREATED);
        project.setDateFinished(UPDATED_DATE_FINISHED);
        project.setLongDescription(UPDATED_LONG_DESCRIPTION);

        ProjectGroup projectGroup = new ProjectGroup();
        projectGroup.setId("1");
        projectGroup.setDescription("Test Group");
        project.setProjectGroup(projectGroup);

        restProjectMockMvc.perform(post("/app/rest/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isOk());

        // Read updated Project
        restProjectMockMvc.perform(get("/app/rest/projects/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.name").value(UPDATED_NAME.toString()))
                .andExpect(jsonPath("$.description").value(UPDATED_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.picture").value(UPDATED_PICTURE.toString()))
                .andExpect(jsonPath("$.dateCreated").value(UPDATED_DATE_CREATED.toString()))
                .andExpect(jsonPath("$.dateFinished").value(UPDATED_DATE_FINISHED.toString()))
                .andExpect(jsonPath("$.longDescription").value(UPDATED_LONG_DESCRIPTION.toString()));

        // Delete Project
        restProjectMockMvc.perform(delete("/app/rest/projects/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Read nonexisting Project
        restProjectMockMvc.perform(get("/app/rest/projects/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetProjectBody() throws  Exception {
        restProjectMockMvc.perform(get("/app/rest/project-body/{id}", DEFAULT_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
