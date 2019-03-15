import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Project from "../../../model/Project";
import axios from "axios";
import DialogHeader from "./DialogHeader";

const styles = theme => ({
  error: {
    color: "red",
    fontSize: 12
  }
});

class ProjectDialog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isDialogActive: true,
      project: new Project(),
      errors: {}
    };
  }

  handleChange = event => {
    const projectState = { ...this.state.project };
    projectState[event.target.name] = event.target.value;
    this.setState({
      project: projectState
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    const projectInfo = new Project();
    const { projectName, description, createdAt } = this.state.project;
    if (this.props.type === "Create") {
      projectInfo.projectName = projectName;
      projectInfo.description = description;
      projectInfo.createdAt = createdAt;
      projectInfo.projectIdentifier = this.slugifyProjectName(projectName);
      this.saveNewProject(projectInfo);
    } else {
      const {
        id,
        projectIdentifier,
        projectName,
        description,
        createdAt
      } = this.props.projectInfo;
      projectInfo.id = id;
      projectInfo.projectIdentifier = projectIdentifier;
      projectInfo.projectName = !this.state.project.projectName
        ? projectName
        : this.state.project.projectName;
      projectInfo.description = !this.state.project.description
        ? description
        : this.state.project.description;
      projectInfo.createdAt = !this.state.project.createdAt
        ? createdAt
        : this.state.project.createdAt;
      this.updateProjectInformation(projectInfo);
    }
  };

  slugifyProjectName(projectName) {
    const splitString = projectName.split(" ");
    const projectIdentifierSlug = splitString.join("-");
    return projectIdentifierSlug.toUpperCase();
  }

  updateProjectInformation(projectInfo) {
    console.log("udpated project information: " + JSON.stringify(projectInfo));
    axios
      .patch("/api/project", projectInfo)
      .then(json => {
        this.props.updateProjectInfo(json.data);
        this.props.onClose();
      })
      .catch(json => {
        console.log("json response : " + JSON.stringify(json.response.data));
        this.setState({
          errors: {
            projectName: json.response.data.projectName,
            description: json.response.data.description
          }
        });
        return;
      });
  }

  saveNewProject(projectInfo) {
    axios
      .post("/api/project", projectInfo)
      .then(json => {
        this.props.updateAllProjects([...this.props.allProjects, json.data]);
      })
      .then(() => {
        this.props.onClose();
      })
      .catch(json => {
        const { description, projectName } = json.response.data;
        this.setState({
          errors: {
            projectName: projectName,
            description: description
          }
        });
        return;
      });
  }

  render() {
    const { classes, projectInfo } = this.props;
    const { errors } = this.state;

    return (
      <Dialog
        open={this.state.isDialogActive}
        onClose={this.props.onClose}
        aria-labelledby="form-dialog-title"
        scroll="paper"
        fullWidth={true}
        maxWidth={"md"}
      >
        <DialogHeader type={this.props.type} />
        <form onSubmit={this.handleSubmit}>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              name="projectName"
              label="Project Name"
              type="text"
              fullWidth
              onChange={this.handleChange}
              defaultValue={
                projectInfo === undefined ? "" : projectInfo.projectName
              }
            />
            {errors.projectName && (
              <span className={classes.error}>{errors.projectName}</span>
            )}
            {
              <TextField
                id="standard-multiline-static"
                name="description"
                label="Project Description"
                multiline
                rows="4"
                margin="normal"
                fullWidth
                onChange={this.handleChange}
                defaultValue={
                  projectInfo === undefined ? "" : projectInfo.description
                }
              />
            }
            {errors.description && (
              <span className={classes.error}>{errors.description}</span>
            )}
          </DialogContent>
          <DialogActions>
            <Button onClick={this.props.onClose} color="primary">
              Cancel
            </Button>
            <Button color="primary" type="submit">
              {this.props.type}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    );
  }
}
export default withStyles(styles)(ProjectDialog);
