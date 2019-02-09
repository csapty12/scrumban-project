package com.scrumban.service.project;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.Tickets;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectTicketServiceTest {

    @MockBean
    private ProjectTicketRepository projectTicketRepository;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private SwimLaneService swimLaneService;

    @Autowired
    private ProjectTicketService projectTicketService;

    @DisplayName("get empty project dashboard when no tickets and no swimlanes exist")
    @Test
    void emptyDashboard() {
        ProjectEntity project = createProjectWithNoSwimLanesNorTickets();
        when(projectService.tryToFindProject(any(String.class))).thenReturn(Optional.of(project));
        Tickets projectDashboard = projectTicketService.getProjectDashboard("TEST-PROJECT");
        assertThat(projectDashboard.getTickets().size(), is(0));
        assertThat(projectDashboard.getSwimLanes().size(), is(0));
    }

    @DisplayName("when project does not exist, throw project not found exception")
    @Test
    void projectInvalid(){
        when(projectService.tryToFindProject(any(String.class))).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class,
                ()-> projectTicketService.getProjectDashboard(any(String.class)));
    }

    @DisplayName("Get project dashboard with swim lanes ano no tickets")
    @Test
    void swimLanesExist() {
        ProjectEntity projectWithSwimLanesAndNoTickets = createProjectWithSwimLanesAndNoTickets();
        when(projectService.tryToFindProject(any(String.class))).thenReturn(Optional.of(projectWithSwimLanesAndNoTickets));
        Tickets projectDashboard = projectTicketService.getProjectDashboard("TEST-PROJECT");
        assertThat(projectDashboard.getTickets().size(), is(0));
        assertThat(projectDashboard.getSwimLanes().size(), is(1));
    }

    @DisplayName("Get project dashboard with swim lanes ano tickets")
    @Test
    void fullProjectDashboard() {
        ProjectEntity projectWithSwimLanesAndTickets = createProjectWithSwimLanesAndTickets();
        when(projectService.tryToFindProject(any(String.class))).thenReturn(Optional.of(projectWithSwimLanesAndTickets));
        Tickets projectDashboard = projectTicketService.getProjectDashboard("TEST-PROJECT");
        assertThat(projectDashboard.getTickets().size(), is(1));
        assertThat(projectDashboard.getSwimLanes().size(), is(1));

    }

    private ProjectEntity createProjectWithNoSwimLanesNorTickets() {
        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .projectTickets(new ArrayList<>())
                .swimLaneEntities(new LinkedList<>())
                .build();
    }

    private ProjectEntity createProjectWithSwimLanesAndNoTickets() {

        SwimLaneEntity swimLaneEntity = SwimLaneEntity
                .builder()
                .id(1)
                .name("backlog")
                .projectTickets(new ArrayList<>())
                .build();

        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .projectTickets(new ArrayList<>())
                .swimLaneEntities(new LinkedList<>(asList(swimLaneEntity)))
                .build();
    }

    private ProjectEntity createProjectWithSwimLanesAndTickets() {

        ProjectTicket projectTicket= ProjectTicket
                .builder()
                .id(1L)
                .projectIdentifier("TEST-PROJECT")
                .projectSequence("TP-01")
                .build();
        SwimLaneEntity swimLaneEntity = SwimLaneEntity
                .builder()
                .id(1)
                .name("backlog")
                .projectTickets(new ArrayList<>())
                .build();

        projectTicket.setSwimLaneEntity(swimLaneEntity);
        swimLaneEntity.setProjectTickets(new ArrayList<>(Arrays.asList(projectTicket)));

        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .projectTickets(new ArrayList<>(asList(projectTicket)))
                .swimLaneEntities(new LinkedList<>(asList(swimLaneEntity)))
                .build();
    }
}