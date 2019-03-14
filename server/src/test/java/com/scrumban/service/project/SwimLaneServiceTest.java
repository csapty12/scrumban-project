package com.scrumban.service.project;

import com.scrumban.exception.DuplicateProjectSwimLaneException;
import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.SwimLane;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.SwimLaneRepository;
import com.scrumban.validator.UserProjectValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwimLaneServiceTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private SwimLaneRepository swimLaneRepository;

    @Mock
    private UserProjectValidator userProjectValidator;

    @InjectMocks
    private SwimLaneService swimLaneService;

    @Test
    @DisplayName("test that new swimlane is added to project successfully")
    void newSwimLane() {
        User user = createValidUser();
        ProjectEntity projectEntity = createProject();
        SwimLaneEntity swimLaneEntity = createSwimLaneEntity();
        when(userProjectValidator.getUserProject(any(), any())).thenReturn(projectEntity);
        when(swimLaneRepository.findByName(any())).thenReturn(Optional.empty());

        when(swimLaneRepository.save(swimLaneEntity)).thenReturn(swimLaneEntity);

        Map<String, SwimLane> actualSwimLaneToProject = swimLaneService.addSwimLaneToProject("test", swimLaneEntity, user.getEmail());

        Map<String, SwimLane> expectedAddNewSwimLaneToProject = new HashMap<>();
        SwimLane expectedSwimLane = SwimLane.builder().title("testSwimLane").ticketIds(new ArrayList<>()).build();
        expectedAddNewSwimLaneToProject.put("testSwimLane", expectedSwimLane);
        assertThat(actualSwimLaneToProject, is(expectedAddNewSwimLaneToProject));
    }

    @Test
    @DisplayName("test user does not exist when trying to add new swimLane")
    void userDoesNotExist() {

        when(userProjectValidator.getUserProject(any(), any())).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> swimLaneService.addSwimLaneToProject("test", new SwimLaneEntity(), "test@test.com"));
    }


    @Test
    @DisplayName("test that ProjectNotFoundException thrown when add swimlane to project that does not exist")
    void projectDoesNotExist() {
        when(userProjectValidator.getUserProject(any(), any())).thenThrow(ProjectNotFoundException.class);
        assertThrows(ProjectNotFoundException.class, () -> swimLaneService.addSwimLaneToProject("test", new SwimLaneEntity(), "test@test.com"));
    }

    @Test
    @DisplayName("test that ProjectNotFoundException thrown when add swimlane to project that does not exist")
    void swimLaneAlreadyExists() {
        User user = createValidUser();
        SwimLaneEntity swimLaneEntity = createSwimLaneEntity();
        ProjectEntity projectEntityWithSwimLane = createProject();

        Set<ProjectEntity> setOfProjects = new HashSet<>();
        setOfProjects.add(projectEntityWithSwimLane);
        swimLaneEntity.setProjectEntities(setOfProjects);
        projectEntityWithSwimLane.setSwimLaneEntities(singletonList(swimLaneEntity));

        when(userProjectValidator.getUserProject(any(), any())).thenReturn(projectEntityWithSwimLane);
        when(swimLaneRepository.findByName(any())).thenReturn(Optional.of(swimLaneEntity));

        assertThrows(DuplicateProjectSwimLaneException.class, () -> swimLaneService.addSwimLaneToProject("test", swimLaneEntity, "test@test.com"));
    }

    @Test
    @DisplayName("test findSwimLaneByName returns a swimLane entity")
    void findSwimLaneByName() {
        SwimLaneEntity swimLaneEntity = createSwimLaneEntity();
        when(swimLaneRepository.findByName(anyString())).thenReturn(Optional.of(swimLaneEntity));
        Optional<SwimLaneEntity> actualSwimLane = swimLaneService.findSwimLaneByName("testSwimLane");
        verify(swimLaneRepository, times(1)).findByName(any());
        assertThat(actualSwimLane, is(Optional.of(swimLaneEntity)));
    }

    @Test
    @DisplayName("test findSwimLaneByName is empty")
    void swimLaneDoesNotExist() {
        when(swimLaneRepository.findByName(anyString())).thenReturn(Optional.empty());
        Optional<SwimLaneEntity> actualFoundSwimLane = swimLaneService.findSwimLaneByName("testSwimLane");
        verify(swimLaneRepository, times(1)).findByName(any());
        assertThat(actualFoundSwimLane, is(Optional.empty()));
    }

    private User createValidUser() {
        User user = new User();
        user.setEmail("a@a.com");
        user.setId(1L);
        return user;
    }

    private SwimLaneEntity createSwimLaneEntity() {
        return SwimLaneEntity.builder()
                .name("testSwimLane")
                .id(1)
                .build();
    }

    private ProjectEntity createProject() {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("test");
        project.setProjectIdentifier("test");
        project.setUser(createValidUser());
        project.setSwimLaneEntities(new ArrayList<>());
        return project;
    }

}