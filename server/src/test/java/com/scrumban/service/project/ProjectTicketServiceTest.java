package com.scrumban.service.project;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.exception.ProjectSwimLaneNotFoundException;
import com.scrumban.model.domain.ProjectDashboard;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProjectTicketServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Mock
    private SwimLaneService swimLaneService;

    @Mock
    private ProjectTicketRepository projectTicketRepository;

    @InjectMocks
    private ProjectTicketService projectTicketService;

    @BeforeEach
    void setUp() {
    }

    @Nested
    @DisplayName("Get project dashboard tests")
    class ProjectDashboardTest {

        @Test
        @DisplayName("when user does not exist, throw UsernameNotFoundException")
        void testUserDoesNotExist() {
            when(userService.getUser(anyString())).thenThrow(UsernameNotFoundException.class);
            assertThrows(UsernameNotFoundException.class, () -> projectTicketService.getProjectDashboard("test", "test@test.com"));
        }

        @Test
        @DisplayName("When user does exist, but project does not exist, throw ProjectNotFoundException")
        void testProjectNotFound() {
            User validUser = createValidUser("a@a.com", 1L);
            ProjectEntity project = createEmptyProject();
            when(userService.getUser(anyString())).thenReturn(validUser);
            when(projectService.getProject(any(), any())).thenReturn(Optional.empty());
            assertThrows(ProjectNotFoundException.class, () -> projectTicketService.getProjectDashboard(project.getProjectIdentifier(), validUser.getEmail()));
        }

        @Test
        @DisplayName("when user exists, but is not associated with project, throw exception")
        void userNotAssociatedWithProject() {
            User validUser = createValidUser("bob@bob.com", 5L);
            ProjectEntity project = createEmptyProject();
            when(userService.getUser(anyString())).thenReturn(validUser);
            when(projectService.getProject(any(), any())).thenThrow(ProjectNotFoundException.class);
            assertThrows(ProjectNotFoundException.class, () -> projectTicketService.getProjectDashboard(project.getProjectIdentifier(), validUser.getEmail()));
        }

        @Test
        @DisplayName("When user is associated with new project, get dashboard")
        void getFreshDashboard() {
            User validUser = createValidUser("a@a.com", 1L);
            ProjectEntity project = createEmptyProject();
            when(userService.getUser(anyString())).thenReturn(validUser);
            when(projectService.getProject(any(), any())).thenReturn(Optional.of(project));
            ProjectDashboard projectDashboard = projectTicketService.getProjectDashboard(project.getProjectIdentifier(), validUser.getEmail());
            assertThat(projectDashboard.getSwimLanes().size(), is(0));
            assertThat(projectDashboard.getTickets().size(), is(0));
        }
    }

    @Nested
    @DisplayName("Add new Ticket to Project test")
    class NewTicket {

        @Test
        @DisplayName("test ProjectSwimLaneNotFoundException thrown")
        void testSwimLaneException() {

            SwimLaneEntity.SwimLaneEntityBuilder swimLane = createSwimLane();
            SwimLaneEntity builtSwimLane = swimLane.build();
            ProjectTicket projectTicket = createProjectTicket();

            when(swimLaneService.findSwimLaneByName(anyString())).thenReturn(Optional.empty());
            assertThrows(ProjectSwimLaneNotFoundException.class, ()-> projectTicketService.addProjectTicketToProject("test", builtSwimLane.getName(), projectTicket, "a@a.com"));
        }

        @Test
        @DisplayName("test ProjectSwimLaneNotFoundException thrown")
        void testProjectNotFoundException() {

            SwimLaneEntity.SwimLaneEntityBuilder swimLane = createSwimLane();
            SwimLaneEntity builtSwimLane = swimLane.build();
            ProjectTicket projectTicket = createProjectTicket();

            when(swimLaneService.findSwimLaneByName(anyString())).thenReturn(Optional.of(builtSwimLane));
            when(projectService.getProject(any(), any())).thenReturn(Optional.empty());
            assertThrows(ProjectNotFoundException.class, ()-> projectTicketService.addProjectTicketToProject("test", builtSwimLane.getName(), projectTicket, "a@a.com"));
        }

        @Test
        @DisplayName("add new ticket to project successfully")
        void addNewTicket() {
            User validUser = createValidUser("a@a.com", 1L);
            ProjectEntity project = createEmptyProject();
            SwimLaneEntity.SwimLaneEntityBuilder swimLane = createSwimLane();
            Set<ProjectEntity> setOfProjectEntities = new HashSet<>();
            setOfProjectEntities.add(project);
            swimLane.projectEntities(setOfProjectEntities);
            SwimLaneEntity builtSwimLane = swimLane.build();
            project.setSwimLaneEntities(Arrays.asList(builtSwimLane));
            ProjectTicket projectTicket = createProjectTicket();

            when(swimLaneService.findSwimLaneByName(any())).thenReturn(Optional.of(builtSwimLane));
            when(userService.getUser(anyString())).thenReturn(validUser);
            when(projectService.getProject(any(), any())).thenReturn(Optional.of(project));
            when(projectTicketRepository.save(any())).thenReturn(projectTicket);

            LinkedHashMap<String, ProjectTicket> actualNewProjectTicket = projectTicketService.addProjectTicketToProject("test", builtSwimLane.getName(), projectTicket, "a@a.com");
            LinkedHashMap<String, ProjectTicket> expectedNewProjectTicket = new LinkedHashMap<>();
            expectedNewProjectTicket.put("T-1", projectTicket);

            assertThat(actualNewProjectTicket, is(expectedNewProjectTicket));
        }
    }

    @Nested
    @DisplayName("Remove ticket from Project")
    class RemoveTicket {
        @Test
        @DisplayName("when user does not exist, throw UsernameNotFoundException")
        void testUserDoesNotExist() {
            when(userService.getUser(anyString())).thenThrow(UsernameNotFoundException.class);
            ProjectTicket projectTicket = createProjectTicket();
            assertThrows(UsernameNotFoundException.class, () -> projectTicketService.removeTicketFromProject(projectTicket, "test@test.com"));
        }

        @Test
        @DisplayName("when project does not exist, throw ProjectNotFoundException")
        void testProjectDoesNotExist() {
            User validUser = createValidUser("a@a.com", 1L);
            when(userService.getUser(anyString())).thenReturn(validUser);
            ProjectTicket projectTicket = createProjectTicket();
            when(projectService.getProject(any(),any())).thenReturn(Optional.empty());
            assertThrows(ProjectNotFoundException.class, () -> projectTicketService.removeTicketFromProject(projectTicket, "test@test.com"));
        }

        @Test
        @DisplayName("Delete ticket successfully from project")
        void testDelete(){
            User validUser = createValidUser("a@a.com", 1L);
            ProjectEntity project = createEmptyProject();
            SwimLaneEntity.SwimLaneEntityBuilder swimLane = createSwimLane();
            Set<ProjectEntity> setOfProjectEntities = new HashSet<>();
            setOfProjectEntities.add(project);
            swimLane.projectEntities(setOfProjectEntities);
            SwimLaneEntity builtSwimLane = swimLane.build();
            project.setSwimLaneEntities(Arrays.asList(builtSwimLane));
            ProjectTicket projectTicket = createProjectTicket();
            projectTicket.setId(1L);

            when(userService.getUser(anyString())).thenReturn(validUser);
            when(projectService.getProject(any(), any())).thenReturn(Optional.of(project));
            projectTicketService.removeTicketFromProject(projectTicket, "a@a.com");
            verify(projectTicketRepository, times(1)).deleteProjectTicket(projectTicket.getId());
        }
    }

    private User createValidUser(String email, long id) {
        User user = new User();
        user.setEmail(email);
        user.setId(id);
        return user;
    }

    private ProjectEntity createEmptyProject() {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("test");
        project.setProjectIdentifier("test");
        project.setUser(createValidUser("a@a.com", 1L));
        project.setSwimLaneEntities(new ArrayList<>());
        project.setProjectTickets(new ArrayList<>());
        return project;
    }

    private SwimLaneEntity.SwimLaneEntityBuilder createSwimLane() {
        return SwimLaneEntity.builder().id(1).name("swimlane");
    }

    private ProjectTicket createProjectTicket() {
        return ProjectTicket.builder()
                .summary("")
                .acceptanceCriteria("")
                .complexity(1)
                .priority("HIGH")
                .build();
    }


}