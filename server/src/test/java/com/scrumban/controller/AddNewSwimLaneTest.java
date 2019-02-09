package com.scrumban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrumban.model.ProjectDashboardColumn;
import com.scrumban.model.Tickets;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.service.project.ProjectService;
import com.scrumban.service.project.ProjectTicketService;
import com.scrumban.service.project.SwimLaneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddNewSwimLaneTest {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SwimLaneService swimLaneService;

    @MockBean
    private ProjectTicketService projectTicketService;


    @Test
    @DisplayName("POST request to add new swimLane fails if project not found -  /dashboard/{projectID}")
    void addNewSwimLane() throws Exception {
        ProjectEntity defaultProject = createDefaultProject();
        SwimLaneEntity newSwimLane = createDefaultSwimLane();
        ProjectEntity projectWithSwimLane = createProjectWithSwimLane();
        when(projectService.tryToFindProject("TEST-PROJECT")).thenReturn(Optional.of(defaultProject));
        when(swimLaneService.addSwimLaneToProject(defaultProject, newSwimLane)).thenReturn(projectWithSwimLane);

        List<Map<String, ProjectDashboardColumn>> swimLanes = new ArrayList<>();
        Map<String, ProjectDashboardColumn> projectDashboardColumnMap = new HashMap<>();
        ProjectDashboardColumn projectDashboardColumn = ProjectDashboardColumn.builder().title("backlog").ticketIds(new ArrayList<>()).build();
        projectDashboardColumnMap.put("backlog",projectDashboardColumn);
        swimLanes.add(projectDashboardColumnMap);
        Tickets tickets = new Tickets();
        tickets.setTickets(new ArrayList<>());
        tickets.setSwimLanes(swimLanes);
        tickets.setSwimLaneOrder(new ArrayList<>(Arrays.asList("backlog")));

        when(projectTicketService.getProjectDashboard(any())).thenReturn(tickets);

        mockMvc.perform(post("/dashboard/{projectIdentifier}", "TEST-PROJECT")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(newSwimLane)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content()
                        .json("{'tickets':[],'swimLanes':[{'backlog':{'title':'backlog','ticketIds':[]}}],'swimLaneOrder':['backlog']}"));
    }

    private ProjectEntity createDefaultProject() {
        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .projectTickets(new ArrayList<>())
                .swimLaneEntities(new LinkedList<>())
                .build();
    }

    private SwimLaneEntity createDefaultSwimLane() {
        return SwimLaneEntity
                .builder()
                .name("backlog")
                .projectTickets(new ArrayList<>())
                .build();
    }

    private ProjectEntity createProjectWithSwimLane() {
        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .projectTickets(new ArrayList<>())
                .swimLaneEntities(new LinkedList<>(asList(newSwimLaneWithId())))
                .build();
    }

    private SwimLaneEntity newSwimLaneWithId() {
        return SwimLaneEntity
                .builder()
                .id(1)
                .name("backlog")
                .projectTickets(new ArrayList<>())
                .build();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
