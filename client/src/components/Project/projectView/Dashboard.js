import React, { Component } from "react";
import ProjectItem from "./ProjectItem";
import axios from "axios";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import Project from "../../../model/Project";
import ProjectDialog from "./ProjectDialog";

const styles = theme => ({
  createButton: {
    background: "linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",
    border: 0,
    borderRadius: 3,
    boxShadow: "0 3px 5px 2px rgba(255, 105, 135, .3)",
    color: "white",
    height: 35,
    padding: "0 30px"
  }
});

class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allProjects: [],
      project: new Project(),
      isProjectDialogActive: false
    };
  }
  handleIsProjectDialogActive = () => {
    this.setState({ isProjectDialogActive: true });
  };

  handleClose = () => {
    this.setState({
      isProjectDialogActive: false,
      errors: {},
      project: new Project()
    });
  };

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
      .then(() => {
        let filteredProjects = currentProjects.filter(
          item => item.projectIdentifier !== projectIdentifier
        );
        this.setState({ allProjects: filteredProjects });
      });
  };

  updateAllProjects = state => {
    this.setState({ allProjects: state });
  };

  render() {
    const { classes } = this.props;
    const allProjects = this.state.allProjects;
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">All Projects</h1>
              <br />
              <Button
                variant="contained"
                color="secondary"
                onClick={this.handleIsProjectDialogActive}
                className={classes.createButton}
                disableRipple
              >
                Create New project
              </Button>
              {this.state.isProjectDialogActive && (
                <ProjectDialog
                  type="Create Project"
                  onClose={this.handleClose}
                  allProjects={this.state.allProjects}
                  updateAllProjects={this.updateAllProjects}
                />
              )}
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

export default withStyles(styles)(Dashboard);
