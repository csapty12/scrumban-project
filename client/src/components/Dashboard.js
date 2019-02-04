import React, { Component } from "react";
import ProjectItem from "./Project/projectView/ProjectItem";
import axios from "axios";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import FormHelperText from "@material-ui/core/FormHelperText";
import { withStyles } from "@material-ui/core/styles";
import { red } from "@material-ui/core/colors";

const styles = theme => ({
  createButton: {
    background: "linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",
    border: 0,
    borderRadius: 3,
    boxShadow: "0 3px 5px 2px rgba(255, 105, 135, .3)",
    color: "white",
    height: 48,
    padding: "0 30px"
  },
  error: {
    color: "red",
    fontSize: 12
  }
});

class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allProjects: [],
      projectName: "",
      projectIdentifier: "",
      description: "",
      errors: {},
      open: false
    };
  }
  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false, errors: {} });
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

  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    let slugify = require("slugify");
    const projectIdentifierSlug = slugify(this.state.projectName);

    const newProject = {
      id: this.state.id,
      projectName: this.state.projectName,
      projectIdentifier: projectIdentifierSlug,
      description: this.state.description
    };
    axios
      .post("http://localhost:8080/api/project", newProject)
      .then(json => {
        this.setState({ allProjects: [...this.state.allProjects, json.data] });
      })
      .then(() => {
        this.handleClose();
      })
      .catch(json => {
        const {
          description,
          projectIdentifier,
          projectName
        } = json.response.data;
        this.setState({
          errors: {
            description: description,
            projectIdentifier: projectIdentifier,
            projectName: projectName
          }
        });
        return;
      });
  };
  render() {
    const { classes } = this.props;
    const allProjects = this.state.allProjects;
    const { errors } = this.state;
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
                onClick={this.handleClickOpen}
                className={classes.createButton}
                disableRipple
              >
                Create New project
              </Button>
              <Dialog
                open={this.state.open}
                onClose={this.handleClose}
                aria-labelledby="form-dialog-title"
                scroll="paper"
              >
                <DialogTitle id="form-dialog-title">
                  Create New Project
                </DialogTitle>
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
                      value={this.state.projectName}
                      // helperText={errors.projectName}
                    />
                    {errors.projectName && (
                      <span className={classes.error}>
                        {errors.projectName}
                      </span>
                    )}
                    <TextField
                      id="standard-multiline-static"
                      name="description"
                      label="Project Description"
                      multiline
                      rows="4"
                      margin="normal"
                      fullWidth
                      onChange={this.handleChange}
                      value={this.state.description}
                      // helperText={errors.description}
                    />
                    {errors.description && (
                      <span className={classes.error}>
                        {errors.description}
                      </span>
                    )}
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={this.handleClose} color="primary">
                      Cancel
                    </Button>
                    <Button
                      // onClick={this.handleClose}
                      color="primary"
                      type="submit"
                    >
                      Create
                    </Button>
                  </DialogActions>
                </form>
              </Dialog>
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
