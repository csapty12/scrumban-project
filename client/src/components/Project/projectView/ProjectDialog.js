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
      isDialogActive: this.props.open,
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
    let slugify = require("slugify");
    const projectIdentifierSlug = slugify(this.state.project.projectName);
    const newProject = new Project();

    newProject.projectName = this.state.project.projectName;
    newProject.id = this.state.project.id;
    newProject.description = this.state.project.description;
    newProject.projectIdentifier = projectIdentifierSlug;

    axios
      .post("http://localhost:8080/api/project", newProject)
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
  };

  render() {
    const { classes } = this.props;
    const { errors } = this.state;

    return (
      <Dialog
        open={this.state.isDialogActive}
        onClose={this.props.onClose}
        aria-labelledby="form-dialog-title"
        scroll="paper"
        fullWidth={true}
        maxWidth={"md"}
        fullwidth="true"
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
              Create
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    );
  }
}
export default withStyles(styles)(ProjectDialog);
