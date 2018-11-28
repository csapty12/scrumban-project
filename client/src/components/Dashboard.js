/*this is where you can access all projects
this is where you can click the form to create a new project.*/
import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import CreateProjectButton from "./Project/CreateProjectButton";
import axios from "axios";

class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allProjects: []
    };
  }
  componentDidMount() {
    axios.get("http://localhost:8080/api/project").then(json => {
      this.setState({
        allProjects: json.data
      });
    });
  }

  handleProjectDelete = project => {
    const currentProjects = this.state.allProjects;
    const { projectIdentifier } = project;
    axios
      .delete(`http://localhost:8080/api/project/${projectIdentifier}`)
      .then(() => alert(`project: ${projectIdentifier} has been deleted`))
      .then(() => {
        let filteredProjects = currentProjects.filter(
          item => item.projectIdentifier !== projectIdentifier
        );
        this.setState({ allProjects: filteredProjects });
      });
  };

  render() {
    const allProjects = this.state.allProjects;
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">All Projects</h1>
              <br />
              <CreateProjectButton />
              <br />
              <hr />
              <section className="gallery-block grid-gallery">
                <div className="container">
                  <div className="row">
                    {allProjects.map(project => (
                      <ProjectItem
                        key={project.id}
                        project={project}
                        deleteProject={this.handleProjectDelete}
                      />
                    ))}
                  </div>
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Dashboard;
