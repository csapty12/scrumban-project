package com.scrumban.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrumban.model.project.entity.ProjectEntity;
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

import java.util.LinkedList;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @MockBean
    private ProjectService projectService;

    @Mock
    private BindingResult mockBindingResult;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST request to save new project - /api/project/")
    void saveProject() throws Exception {
        ProjectEntity projectEntity = createProject();

        ProjectEntity newEntity = createProject();

        when(projectService.saveProject(any())).thenReturn(newEntity);
        mockMvc.perform(post("/api/project")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(projectEntity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.projectName", is("test")))
                .andExpect(jsonPath("$.projectIdentifier", is("TEST")))
                .andExpect(jsonPath("$.description", is("test description")));

    }

    @Test
    @DisplayName("POST failure to save new project - /api/project")
    void cannotSaveProject() throws Exception {
        ProjectEntity projectEntity = ProjectEntity.builder().projectName("failure").build();

        when(mockBindingResult.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/api/project")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(projectEntity)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.description", is("Project description is needed")))
                .andDo(print());
    }

    @Test
    @DisplayName("GET request to load one specific project - /api/project/test - Found")
    void getSingleProject() throws Exception {
        ProjectEntity projectEntity = createProject();
        projectEntity.setId(1L);

        when(projectService.tryToFindProject("TEST")).thenReturn(Optional.of(projectEntity));
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
        when(projectService.tryToFindProject("FAILURE")).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/project/{projectIdentifier}", "failure"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.projectIdentifier", is("No project found with identifier: FAILURE")));
    }

    @Test
    @DisplayName("PATCH request to update a specific project - /api/project")
    void updateProject() throws Exception {
        ProjectEntity projectEntity = createProject();
        projectEntity.setId(1L);

        ProjectEntity newEntity = createProject();


        when(projectService.tryToFindProject(anyString())).thenReturn(Optional.of(projectEntity));
        when(projectService.updateProject(any())).thenReturn(newEntity);
        mockMvc.perform(patch("/api/project")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(projectEntity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.projectName", is("test")))
                .andExpect(jsonPath("$.projectIdentifier", is("TEST")))
                .andExpect(jsonPath("$.description", is("test description")));


    }

    @Test
    @DisplayName("DELETE request to delete a project - /api/project/test")
    void deleteProject() throws Exception {
        ProjectEntity projectEntity = createProject();
        when(projectService.tryToFindProject("TEST")).thenReturn(Optional.of(projectEntity));
        mockMvc.perform(delete("/api/project/{projectIdentifier}", "test"))
                .andExpect(status().isOk());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ProjectEntity createProject() {
        return ProjectEntity
                .builder()
                .projectName("test")
                .description("test description")
                .projectIdentifier("TEST")
                .swimLaneEntities(new LinkedList<>())
                .build();
    }
}
