package com.scrumban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.repository.SwimLaneRepository;
import com.scrumban.service.project.ProjectService;
import com.scrumban.service.project.SwimLaneService;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectDashboardControllerTest {

    @MockBean
    private ProjectService projectService;

    @MockBean
    private SwimLaneRepository swimLaneRepository;

    @MockBean
    private SwimLaneService swimLaneService;

    @MockBean
    ProjectTicketRepository projectTicketRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("GET request to get all ticket with invalid project throws exception -   /dashboard/{projectID}n")
    void getNoProject() throws Exception {
        when(projectService.tryToFindProject("FAILURE")).thenReturn(Optional.empty());
        mockMvc.perform(get("/dashboard/{projectIdentifier}", "FAILURE"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("{'project':'No project found with identifier: FAILURE'}"));
    }

    @Test
    @DisplayName("GET request to get an empty dashboard (with neither swim lanes nor tickets) - /dashboard/{projectID}")
    void emptyDashboard() throws Exception {
        ProjectEntity project = createDefaultProject();
        project.setId(1L);

        when(projectService.tryToFindProject("TEST-PROJECT")).thenReturn(Optional.of(project));
        mockMvc.perform(get("/dashboard/{projectIdentifier}", "TEST-PROJECT"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'tickets':[],'swimLanes':[],'swimLaneOrder':[]}"));

    }

    @Test
    @DisplayName("GET request to get an dashboard with swim lanes but not tickets) - /dashboard/{projectID}")
    void dashboardWithSwimLanes() throws Exception {
        ProjectEntity project = createProjectWithSwimLane();
        when(projectService.tryToFindProject("TEST-PROJECT")).thenReturn(Optional.of(project));
        mockMvc.perform(get("/dashboard/{projectIdentifier}", "TEST-PROJECT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("{'tickets':[],'swimLanes':[{'backlog':{'title':'backlog','ticketIds':[]}}],'swimLaneOrder':['backlog']}"));
    }

    @Test
    @DisplayName("GET request to get an dashboard with swim lanes and tickets) - /dashboard/{projectID}")
    void fullDashboard() throws Exception {
        ProjectEntity project = createProjectWithSwimLanesAndTickets();
        when(projectService.tryToFindProject("TEST-PROJECT")).thenReturn(Optional.of(project));
        mockMvc.perform(get("/dashboard/{projectIdentifier}", "TEST-PROJECT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("{'tickets':[{'TP-01':{'id':1,'projectSequence':'TP-01','summary':null,'acceptanceCriteria':null,'complexity':0,'priority':null,'createdAt':null,'ticketNumberPosition':0,'projectIdentifier':'TEST-PROJECT'}}],"
                                + "'swimLanes':[{'backlog':{'title':'backlog','ticketIds':['TP-01']}}],"
                                + "'swimLaneOrder':['backlog']}"));
    }


    @Test
    @DisplayName("POST request to add new swimLane fails when no project exists -  /dashboard/{projectID}")
    void projectNotFoundPost() throws Exception {
        SwimLaneEntity newSwimLane = createDefaultSwimLane();
        mockMvc.perform(post("/dashboard/{projectIdentifier}", "FAILURE")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(newSwimLane)))
                .andDo(print())
                .andExpect(content().json("{'project':'No project found with identifier: FAILURE'}"));

    }
    @Test
    @DisplayName("POST request to add new swimLane fails when no errors in json -  /dashboard/{projectID}")
    void jsonErrorsInPost() throws Exception {
        ProjectEntity project = createDefaultProject();
        SwimLaneEntity swimLaneEntity = SwimLaneEntity.builder().build();
        when(projectService.tryToFindProject("TEST-PROJECT")).thenReturn(Optional.of(project));
        mockMvc.perform(post("/dashboard/{projectIdentifier}", "FAILURE")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(swimLaneEntity)))
                .andDo(print())
                .andExpect(content().json("{'name':'Please provide a swim lane name.'}"));

    }

//    @Test
//    @DisplayName("POST request to add new swimLane fails when project already has swimlane  -  /dashboard/{projectID}")
//    void duplicateSwimLane() throws Exception {
//        ProjectEntity project = createProjectWithSwimLane();
//        SwimLaneEntity swimLaneEntity = createDefaultSwimLane();
//        when(projectService.tryToFindProject("TEST-PROJECT")).thenReturn(Optional.of(project));
//        when(swimLaneRepository.findByName(swimLaneEntity.getName())).thenReturn(Optional.of(project.getSwimLaneEntities().get(0)));
//
//        mockMvc.perform(post("/dashboard/{projectIdentifier}", "TEST-PROJECT")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(asJsonString(swimLaneEntity)))
//                .andDo(print())
//                .andExpect(content().json("{'name':'Swim lane already exists in this project'}"));
//    }


    @DisplayName("POST request to add a new ticket to swim lane fails with invalid JSON - /{projectIdentifier}/{swimLaneId} ")
    @Test
    void invalidJsonAddTicket() throws Exception {
        ProjectTicket projectTicket = ProjectTicket.builder().build();
        mockMvc.perform(post("/dashboard/{projectIdentifier}/{swimlaneId}", "TEST-PROJECT","backlog")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(projectTicket)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'summary':'please include ticket summary', 'acceptanceCriteria':'please include at least one acceptance criteria'}"));
    }

    @DisplayName("POST request to add a new ticket to swim lane succeeds -  /{projectIdentifier}/{swimLaneId}")
    @Test
    void test() throws Exception {
        ProjectTicket projectTicket = ProjectTicket.builder().summary("test summary").acceptanceCriteria("test AC").id(1L).build();
        ProjectEntity project =  createProjectWithSwimLane();
        when(projectService.tryToFindProject(project.getProjectIdentifier())).thenReturn(Optional.of(project));
        when(swimLaneService.findSwimLaneByName("backlog")).thenReturn(Optional.of(project.getSwimLaneEntities().get(0)));
        when(projectTicketRepository.save(any())).thenReturn(projectTicket);

        mockMvc.perform(post("/dashboard/{projectIdentifier}/{swimlaneId}", "TEST-PROJECT","backlog")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(asJsonString(projectTicket)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("{'TP-1':{'id':1,'projectSequence':'TP-1','summary':'test summary','acceptanceCriteria':'test AC','complexity':0,'priority':null,'createdAt':null,'ticketNumberPosition':0,'projectIdentifier':'TEST-PROJECT'}}"));
    }

//    @DisplayName("POST request to add a new ticket to swim lane fails when sent to invalid swim lane -  /{projectIdentifier}/{swimLaneId}")
//    @Test
//    void invalidSwimLane() throws Exception {
//        ProjectTicket projectTicket = ProjectTicket.builder().summary("test summary").acceptanceCriteria("test AC").id(1L).build();
//        ProjectEntity project =  createProjectWithSwimLane();
//        when(projectService.tryToFindProject(project.getProjectIdentifier())).thenReturn(Optional.of(project));
//        when(swimLaneService.findSwimLaneByName("failure")).thenReturn(Optional.empty());
//        when(projectTicketRepository.save(any())).thenReturn(projectTicket);
//
//        mockMvc.perform(post("/dashboard/{projectIdentifier}/{swimlaneId}", "TEST-PROJECT","failure")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(asJsonString(projectTicket)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(content().json("{'name':'Swim lane with name: failure not found'}"));
//
//    }



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

    private ProjectEntity createProjectWithSwimLanesAndTickets() {

        ProjectTicket projectTicket = ProjectTicket
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
        swimLaneEntity.setProjectTickets(new ArrayList<>(asList(projectTicket)));

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

    private SwimLaneEntity createDefaultSwimLane() {
        return SwimLaneEntity
                .builder()
                .name("backlog")
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