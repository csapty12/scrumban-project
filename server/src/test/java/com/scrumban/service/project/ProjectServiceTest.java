package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.repository.ProjectRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectServiceTest {

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;


    @Test
    @DisplayName("successfully save new project")
    void saveNewProject() {
        ProjectEntity projectToSave = createFakeProject();
        ProjectEntity savedProject = createFakeProjectWithId();
        when(projectRepository.findProjectByProjectIdentifier(projectToSave.getProjectIdentifier())).thenReturn(Optional.empty());
        when(projectRepository.save(projectToSave)).thenReturn(savedProject);
        ProjectEntity projectEntity = projectService.saveProject(projectToSave);

        assertThat(projectEntity.getSwimLaneEntities().size(), is(0));
        assertThat(projectEntity.getId(), is(1L));
        assertThat(projectEntity.getProjectIdentifier(), is("TEST-PROJECT"));
        assertThat(projectEntity.getProjectName(), is("test project"));
        assertThat(projectEntity.getDescription(), is("test description"));

        verify(projectRepository, times(1)).save(any());
        verify(projectRepository, times(1)).findProjectByProjectIdentifier(any());
    }

    @Test
    @DisplayName("when project already exists, throw a projectId exception")
    void doNotSaveProject() {
        ProjectEntity projectToSave = createFakeProject();
        ProjectEntity savedProject = createFakeProjectWithId();
        when(projectRepository.findProjectByProjectIdentifier(projectToSave.getProjectIdentifier())).thenReturn(Optional.of(savedProject));
        assertThrows(ProjectIdentifierException.class, () -> projectService.saveProject(projectToSave));

    }

    @Test
    @DisplayName("get single project successfully")
    void getSingleProject() {
        ProjectEntity project = createFakeProjectWithId();
        when(projectService.tryToFindProject(project.getProjectIdentifier())).thenReturn(Optional.of(project));
        Optional<ProjectEntity> foundProject = projectService.tryToFindProject("TEST-PROJECT");
        assertThat(foundProject.get().getProjectName(), is("test project"));
    }

    @Test
    @DisplayName("When find all projects, return 2 projects")
    void findAllProjects() {

        ProjectEntity firstProject = ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project 1")
                .description("test description 1")
                .projectIdentifier("TEST-PROJECT-1")
                .swimLaneEntities(new LinkedList<>())
                .build();

        ProjectEntity secondProject = ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project 2")
                .description("test description 2")
                .projectIdentifier("TEST-PROJECT-2")
                .swimLaneEntities(new LinkedList<>())
                .build();

        List<ProjectEntity> allProjectEntities = new ArrayList<>(Arrays.asList(firstProject, secondProject));
        when(projectRepository.findAll()).thenReturn(allProjectEntities);

        Iterable<ProjectEntity> allProjects = projectService.findAllProjects();
        int numberOfProjects = 0;
        for (ProjectEntity p : allProjects) {
            numberOfProjects++;
        }
        assertThat(numberOfProjects, is(2));
    }

    @Test
    @DisplayName("When find all projects, throw exception")
    void findNoProjects() {
        List<ProjectEntity> allProjectEntities = new ArrayList<>();
        when(projectRepository.findAll()).thenReturn(allProjectEntities);
        assertThrows(ProjectNotFoundException.class, () -> projectService.findAllProjects());
    }

    @Test
    @DisplayName("When updating an existing project, save new details successfully")
    void updateProject() {
        ProjectEntity existingProject = createFakeProjectWithId();
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
    void doNotUpdateProject() {
        ProjectEntity projectEntity = createFakeProjectWithId();
        when(projectRepository.findProjectByProjectIdentifier(projectEntity.getProjectIdentifier())).thenReturn(Optional.empty());
        assertThrows(ProjectIdentifierException.class, () -> projectService.updateProject(projectEntity));
    }

    @Test
    @DisplayName("Delete a project successfully")
    void deleteProject() {
        ProjectEntity existingProject = createFakeProjectWithTickets();
        when(projectRepository.findProjectByProjectIdentifier(existingProject.getProjectIdentifier())).thenReturn(Optional.of(existingProject));
        projectService.deleteProject(existingProject.getProjectIdentifier());
        verify(projectRepository, times(1)).delete(existingProject);
    }

    @Test
    @DisplayName("when deleting project that does not exist, throw exception")
    void cannotDeleteProject() {
        ProjectEntity existingProject = createFakeProjectWithId();
        when(projectRepository.findProjectByProjectIdentifier(existingProject.getProjectIdentifier())).thenReturn(Optional.empty());
        assertThrows(ProjectIdentifierException.class, () -> projectService.deleteProject(existingProject.getProjectIdentifier()));
    }

    private ProjectEntity getUpdatedProject() {
        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project updated")
                .description("test description updated")
                .projectIdentifier("TEST-PROJECT")
                .swimLaneEntities(new LinkedList<>())
                .build();
    }

    private ProjectEntity createFakeProject() {
        return ProjectEntity
                .builder()
                .projectName("test project")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .build();
    }

    private ProjectEntity createFakeProjectWithId() {

        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .swimLaneEntities(new LinkedList<>())
                .build();
    }

    private ProjectEntity createFakeProjectWithTickets() {
        ProjectEntity project = createFakeProject();
        ProjectTicket projectTicket = ProjectTicket
                .builder()
                .id(1L)
                .build();

        project.setProjectTickets(Arrays.asList(projectTicket));
        return project;
    }
}