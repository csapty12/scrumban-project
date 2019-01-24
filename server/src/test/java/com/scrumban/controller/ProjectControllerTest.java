package com.scrumban.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.project.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @MockBean
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @MockBean
    private ValidationErrorService validationErrorService;

    @Mock
    private BindingResult mockBindingResult;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST request to save new project - /api/project/")
    void saveProject() throws Exception {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectName("test");
        projectEntity.setDescription("test description");
        projectEntity.setProjectIdentifier("TEST");

        ProjectEntity newEntity = new ProjectEntity();
        newEntity.setId(1L);
        newEntity.setProjectName("test");
        newEntity.setDescription("test description");
        newEntity.setProjectIdentifier("TEST");

        when(projectService.saveProject(any())).thenReturn(newEntity);
        mockMvc.perform(post("/api/project")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(projectEntity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.projectName", is("test")))
                .andExpect(jsonPath("$.projectIdentifier", is("TEST")))
                .andExpect(jsonPath("$.description", is("test description")));

    }

    @Test
    @DisplayName("GET request to load one specific project - /api/project/test - Found")
    void getSingleProject() throws Exception {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setProjectName("test");
        projectEntity.setDescription("test description");
        projectEntity.setProjectIdentifier("TEST");
        when(projectService.tryToFindProject("TEST")).thenReturn(projectEntity);
        mockMvc.perform(get("/api/project/{projectIdentifier}", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.projectName", is("test")))
                .andExpect(jsonPath("$.projectIdentifier", is("TEST")))
                .andExpect(jsonPath("$.description", is("test description")));


    }

    @Test
    @DisplayName("GET request to load one specific project - /api/project/failure - Bad Request")
    void cannotFindProject() throws Exception {

        when(mockBindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(get("/api/project/{projectIdentifier}", "failure"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.projectIdentifier", is("no projectEntity found with identifier: FAILURE")));
    }

    @Test
    @DisplayName("PATCH request to update a specific project - /api/project")
    void updateProject() throws Exception {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setProjectName("test");
        projectEntity.setDescription("test description");
        projectEntity.setProjectIdentifier("TEST");

        ProjectEntity updatedEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setProjectName("test project name");
        projectEntity.setDescription("test description");
        projectEntity.setProjectIdentifier("TEST");
        mockMvc.perform(patch("/api/project")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(projectEntity)));


    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
