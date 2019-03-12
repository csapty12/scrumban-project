package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectTicketRepository projectTicketRepository;

    @InjectMocks
    private ProjectService projectService;

    @Nested
    @DisplayName("Create new project tests")
    class Create {

        @Test
        @DisplayName("Test when Project already exists, a  new ProjectIdentifierException is thrown")
        void saveProjectThrowsException() {

            User user = createValidUser();
            ProjectEntity project = createProject();

            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.of(project));

            assertThrows(ProjectIdentifierException.class, () -> projectService.saveProject(project, user.getEmail()));
        }

        @Test
        @DisplayName("Test when project does not exist, the new project is saved")
        void saveProject() {
            User user = createValidUser();
            ProjectEntity project = createProject();

            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.empty());
            when(projectRepository.save(any())).thenReturn(project);

            ProjectEntity actualSavedProject = projectService.saveProject(project, user.getEmail());
            ProjectEntity expectedSavedProject = ProjectEntity.builder().projectIdentifier("test").projectName("test").build();
            assertEquals(actualSavedProject.getProjectIdentifier(), expectedSavedProject.getProjectIdentifier());
        }

        @Test
        @DisplayName("Test when user does not exist, throw usernameNotFoundException")
        void userNotExistThrowException() {
            User user = createInvalidUser();
            ProjectEntity project = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
            assertThrows(UsernameNotFoundException.class, () -> projectService.saveProject(project, user.getEmail()));
        }

    }

    @Nested
    @DisplayName("Get all projects tests")
    class Get {

        @Test
        @DisplayName("Test when find all projects, throws UsernameNotFound excpetion")
        void testUserDoesNotExist() {
            User user = createInvalidUser();
            when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
            assertThrows(UsernameNotFoundException.class, () -> projectService.findAllProjects(user.getEmail()));
        }

        @Test
        @DisplayName("Test when find all projects, throws ProjectNotFoundException excpetion")
        void testNoProjectsFound() {
            User user = createValidUser();
            when(projectRepository.findAllByUser(user)).thenReturn(new ArrayList<>());
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            assertThrows(ProjectNotFoundException.class, () -> projectService.findAllProjects(user.getEmail()));
        }

        @Test
        @DisplayName("Test when find all projects, returns projects")
        void testAllProjectsFound() {
            User user = createValidUser();
            ProjectEntity projectEntity = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            List<ProjectEntity> listOfProjects = new ArrayList<>(asList(projectEntity));
            when(projectRepository.findAllByUser(user)).thenReturn(listOfProjects);
            Iterable<ProjectEntity> allProjects = projectService.findAllProjects(user.getEmail());

            List<ProjectEntity> target = new ArrayList<>();
            allProjects.forEach(target::add);
            assertThat(target.size(), is(1));
        }
    }

    @Nested
    @DisplayName("Update project tests")
    class Update {

        @Test
        @DisplayName("Test when update project, project is updated")
        void testUpdate() {
            User user = createValidUser();
            ProjectEntity projectEntity = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.of(projectEntity));
            projectService.updateProject(projectEntity, user.getEmail());
            verify(projectRepository, times(1)).save(any());
        }

        @Test
        @DisplayName("Test when update project, throws UsernameNotFound exception")
        void testUserDoesNotExist() {
            User user = createInvalidUser();
            ProjectEntity projectEntity = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
            assertThrows(UsernameNotFoundException.class, () -> projectService.updateProject(projectEntity, user.getEmail()));
        }

        @Test
        @DisplayName("Test when update project, throws ProjectIdentifierException when project not associated with user")
        void testProjectNotFound() {
            User user = createInvalidUser();
            ProjectEntity projectEntity = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.empty());
            assertThrows(ProjectIdentifierException.class, () -> projectService.updateProject(projectEntity, user.getEmail()));
        }

        @Test
        @DisplayName("Test when update project, throws ProjectNotFoundException when project not associated with user")
        void testProjectNotAssociatedWithUser() {
            User user = createInvalidUser();
            ProjectEntity projectEntity = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.of(projectEntity));
            assertThrows(ProjectNotFoundException.class, () -> projectService.updateProject(projectEntity, user.getEmail()));
        }

    }

    @Nested
    @DisplayName("Delete project tests")
    class Delete {
        @Test
        @DisplayName("Test when user does not exist, throw usernameNotFoundException")
        void userNotExistThrowException() {
            User user = createInvalidUser();
            ProjectEntity project = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
            assertThrows(UsernameNotFoundException.class, () -> projectService.deleteProject(project.getProjectIdentifier(), user.getEmail()));
        }

        @Test
        @DisplayName("Test when delete project, throws ProjectIdentifierException when project not associated with user")
        void testProjectNotFound() {
            User user = createInvalidUser();
            ProjectEntity projectEntity = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.empty());
            assertThrows(ProjectIdentifierException.class, () -> projectService.deleteProject(projectEntity.getProjectIdentifier(), user.getEmail()));
        }

        @Test
        @DisplayName("Test when delete project, throws ProjectNotFoundException when project not associated with user")
        void testProjectNotAssociatedWithUser() {
            User user = createInvalidUser();
            ProjectEntity projectEntity = createProject();
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.of(projectEntity));
            assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProject(projectEntity.getProjectIdentifier(), user.getEmail()));
        }

        @Test
        @DisplayName("test when delete project, verify delete function is called")
        void testDeleteProjectWipesProject() {
            User user = createValidUser();
            ProjectEntity projectEntity = createProject();

            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
            when(projectRepository.findProjectEntityByProjectIdentifier(any())).thenReturn(Optional.of(projectEntity));
            projectService.deleteProject(projectEntity.getProjectIdentifier(), user.getEmail());
            verify(projectRepository, times(1)).delete(projectEntity);

        }
    }

    private User createInvalidUser() {
        return new User();
    }

    private User createValidUser() {
        User user = new User();
        user.setEmail("a@a.com");
        user.setId(1L);
        return user;
    }

    private ProjectEntity createProject() {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("test");
        project.setProjectIdentifier("test");
        project.setUser(createValidUser());

        ProjectTicket projectTicket = new ProjectTicket();
        projectTicket.setId(1L);

        project.setProjectTickets(Arrays.asList(projectTicket));
        return project;
    }

}