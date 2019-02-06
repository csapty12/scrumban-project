import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Project from "../../../model/Project";
import axios from "axios";

const styles = theme => ({
  error: {
    color: "red",
    fontSize: 12
  },

  appBar: {
    position: "relative",
    backgroundColor: "#2196F3"
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
    if (!this.props.projectInfo) {
      projectInfo.projectName = this.state.project.projectName;
      projectInfo.description = this.state.project.description;
      projectInfo.createdAt = this.state.project.createdAt;
      projectInfo.projectIdentifier = this.slugifyProjectName(
        this.state.project.projectName
      );
      this.saveNewProject(projectInfo);
    } else {
      projectInfo.id = this.props.projectInfo.id;
      projectInfo.projectIdentifier = this.props.projectInfo.projectIdentifier;
      projectInfo.projectName = !this.state.project.projectName
        ? this.props.projectInfo.projectName
        : this.state.project.projectName;
      projectInfo.description = !this.state.project.description
        ? this.props.projectInfo.description
        : this.state.project.description;
      projectInfo.createdAt = !this.state.project.createdAt
        ? this.props.projectInfo.createdAt
        : this.state.project.createdAt;
      this.updateProjectInformation(projectInfo);
    }
  };

  slugifyProjectName(projectName) {
    let slugify = require("slugify");
    const projectIdentifierSlug = slugify(projectName);
    return projectIdentifierSlug;
  }

  updateProjectInformation(projectInfo) {
    axios
      .patch("http://localhost:8080/api/project", projectInfo)
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
      .post("http://localhost:8080/api/project", projectInfo)
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
        <AppBar className={classes.appBar}>
          <Toolbar>
            <Typography variant="h6" color="inherit" className={classes.flex}>
              {this.props.type} Project
            </Typography>
          </Toolbar>
        </AppBar>
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
