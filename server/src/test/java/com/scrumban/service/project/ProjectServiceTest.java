package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectServiceTest {

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("successfully save new project")
    void saveNewProject(){
        ProjectEntity projectToSave = createProjectEntityObject();
        ProjectEntity savedProject = createProjectEntityWithId();
        when(projectRepository.findProjectByProjectIdentifier(projectToSave.getProjectIdentifier())).thenReturn(Optional.empty());
        when(projectRepository.save(projectToSave)).thenReturn(savedProject);
        ProjectEntity projectEntity = projectService.saveProject(projectToSave);
        assertThat(projectEntity.getSwimLaneEntities().size(), is(0));
        assertThat(projectEntity.getId(), is(1L));
        assertThat(projectEntity.getProjectIdentifier(), is("TEST"));
        assertThat(projectEntity.getProjectName(), is("tes project"));
        assertThat(projectEntity.getDescription(), is("test description"));

        verify(projectRepository,times(1)).save(any());
        verify(projectRepository,times(1)).findProjectByProjectIdentifier(any());
    }

    @Test
    @DisplayName("when project already exists, throw a projectId exception")
    void doNotSaveProject(){
        ProjectEntity projectToSave = createProjectEntityObject();
        ProjectEntity savedProject = createProjectEntityWithId();
        when(projectRepository.findProjectByProjectIdentifier(projectToSave.getProjectIdentifier())).thenReturn(Optional.of(savedProject));
        assertThrows(ProjectIdentifierException.class,() -> projectService.saveProject(projectToSave));

    }

    @Test
    @DisplayName("When update project, save new details successfully")
    void updateProject(){
        ProjectEntity existingProject = createProjectEntityWithId();
        ProjectEntity updatedProject = getUpdatedProject();
        when(projectRepository.findProjectByProjectIdentifier(existingProject.getProjectIdentifier())).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any())).thenReturn(updatedProject);
        ProjectEntity projectWithUpdatedValues = projectService.updateProject(updatedProject);
        verify(projectRepository, times(1)).save(any());
        assertThat(projectWithUpdatedValues.getId(), is(1L));
        assertThat(projectWithUpdatedValues.getDescription(), is("test description updated"));
    }

    @Test
    @DisplayName("when update project that does not exist, throw projectId exception")
    void doNotUpdateProject(){
        ProjectEntity projectEntity = createProjectEntityWithId();
        when(projectRepository.findProjectByProjectIdentifier(projectEntity.getProjectIdentifier())).thenReturn(Optional.empty());
        assertThrows(ProjectIdentifierException.class,() -> projectService.updateProject(projectEntity));
    }

    @Test
    @DisplayName("When find all projects, return 2 projects")
    void findAllProjects(){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setProjectName("test project 1");
        projectEntity.setDescription("test description 1");
        projectEntity.setProjectIdentifier("TS");

        ProjectEntity secondProjectEntity = new ProjectEntity();
        secondProjectEntity.setId(2L);
        projectEntity.setProjectName("test project 2");
        projectEntity.setDescription("test description 2");
        projectEntity.setProjectIdentifier("TSNEW");

        List<ProjectEntity> allProjectEntities = new ArrayList<>(Arrays.asList(projectEntity,secondProjectEntity));
        when(projectRepository.findAll()).thenReturn(allProjectEntities);

        Iterable<ProjectEntity> allProjects = projectService.findAllProjects();
        int numberOfProjects = 0;
        for(ProjectEntity p: allProjects){
            numberOfProjects++;
        }
        assertThat(numberOfProjects, is(2));
    }

    @Test
    @DisplayName("when deleting project that does not exist, throw exception")
    void cannotDeleteProject(){
        ProjectEntity existingProject = createProjectEntityWithId();
        when(projectRepository.findProjectByProjectIdentifier(existingProject.getProjectIdentifier())).thenReturn(Optional.empty());
        assertThrows(ProjectIdentifierException.class, ()-> projectService.deleteProject(existingProject.getProjectIdentifier()));
    }


    private ProjectEntity getUpdatedProject() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setProjectName("tes project updated");
        projectEntity.setDescription("test description updated");
        projectEntity.setProjectIdentifier("TEST");
        projectEntity.setSwimLaneEntities(new LinkedList<>());
        return projectEntity;
    }

    private ProjectEntity createProjectEntityObject() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectName("tes project");
        projectEntity.setDescription("test description");
        projectEntity.setProjectIdentifier("TEST");
        return projectEntity;
    }

    private ProjectEntity createProjectEntityWithId() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setProjectName("tes project");
        projectEntity.setDescription("test description");
        projectEntity.setProjectIdentifier("TEST");
        projectEntity.setSwimLaneEntities(new LinkedList<>());
        return projectEntity;
    }
}