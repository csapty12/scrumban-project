package com.scrumban.service;

import com.scrumban.model.project.Project;
import com.scrumban.model.project.SwimLane;
import com.scrumban.repository.SwimLaneRepository;
import com.scrumban.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SwimLaneService {

    private ProjectService projectService;
    private SwimLaneRepository swimLaneRepository;

    public SwimLaneService(ProjectService projectService, SwimLaneRepository swimLaneRepository) {
        this.projectService = projectService;
        this.swimLaneRepository = swimLaneRepository;
    }

    public Project addSwimLaneToProject(String projectIdentifier, SwimLane swimLane) {
        System.out.println("adding swimlane to table");

            Project project = projectService.tryToFindProject(projectIdentifier);

            SwimLane foundSwimLand = swimLaneRepository.findByName(swimLane.getName());
            if(foundSwimLand ==null){
                System.out.println("saving new swimlane");
                SwimLane newSwimLane = swimLaneRepository.save(swimLane);
                Set<SwimLane> swimLanes = project.getSwimLanes();
                swimLanes.add(newSwimLane);

                projectService.updateProject(project);
                return  projectService.tryToFindProject(projectIdentifier);
            }
            else {
                System.out.println("swimlane already found");
                Set<SwimLane> swimLanes = project.getSwimLanes();
                swimLanes.add(foundSwimLand);
                projectService.updateProject(project);
                return projectService.tryToFindProject(projectIdentifier);
            }
    }

    public SwimLane findSwimLaneByName(String swimLaneName){
        return swimLaneRepository.findByName(swimLaneName);
    }

}
