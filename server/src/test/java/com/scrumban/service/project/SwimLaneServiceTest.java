package com.scrumban.service.project;

import com.scrumban.exception.DuplicateProjectSwimLaneException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.SwimLaneRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SwimLaneServiceTest {

    @MockBean
    private ProjectService projectService;
    @MockBean
    private SwimLaneRepository swimLaneRepository;

    @Autowired
    private SwimLaneService swimLaneService;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("Add new swim lane to specific project successfully")
    @Test
    void addNewSwimLane(){
        ProjectEntity project = createProjectWithNoSwimLanesNorTickets();
        SwimLaneEntity swimLane = newSwimLane();

        when(swimLaneRepository.findByName(swimLane.getName())).thenReturn(Optional.empty());
        when(swimLaneRepository.save(swimLane)).thenReturn(swimLane);

        swimLaneService.addSwimLaneToProject(project, swimLane);
        verify(swimLaneRepository,times(1)).save(swimLane);
        verify(projectService, times(1)).updateProject(project);
    }

    @DisplayName("If swim lane already exists, do not save it again but add it to project")
    @Test
    void addExistingSwimLane(){
        ProjectEntity project = createProjectWithNoSwimLanesNorTickets();
        SwimLaneEntity swimLane = newSwimLaneWithId();

        when(swimLaneRepository.findByName(swimLane.getName())).thenReturn(Optional.of(swimLane));
        swimLaneService.addSwimLaneToProject(project, swimLane);
        verify(swimLaneRepository,times(0)).save(swimLane);
        assertThat(project.getSwimLaneEntities().size(), is(1));
    }

    @DisplayName("if swim lane already exists, then do not add it to the project")
    @Test
    void noDuplicateInProject(){
        ProjectEntity project = createProjectWithSwimLane();
        SwimLaneEntity swimLane = project.getSwimLaneEntities().get(0);
        when(swimLaneRepository.findByName(swimLane.getName())).thenReturn(Optional.of(swimLane));
        assertThrows(DuplicateProjectSwimLaneException.class, ()->
                swimLaneService.addSwimLaneToProject(project, swimLane));
    }

    private ProjectEntity createProjectWithSwimLane() {
        return ProjectEntity
                .builder()
                .id(1L)
                .projectName("test project")
                .description("test description")
                .projectIdentifier("TEST-PROJECT")
                .projectTickets(new ArrayList<>())
                .swimLaneEntities(new LinkedList<>(Arrays.asList(newSwimLaneWithId())))
                .build();
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

    private SwimLaneEntity newSwimLane(){
        return SwimLaneEntity
                .builder()
                .name("backlog")
                .projectTickets(new ArrayList<>())
                .build();
    }

    private SwimLaneEntity newSwimLaneWithId(){
        return SwimLaneEntity
                .builder()
                .id(1)
                .name("backlog")
                .projectTickets(new ArrayList<>())
                .build();
    }

}