package com.scrumban.service.project;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.ProjectDashboard;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectTicketServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectTicketService projectTicketService;


    @BeforeEach
    void setUp() {
    }

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


}