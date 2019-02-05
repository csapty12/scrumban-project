import React, { Component, Fragment } from "react";
import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import IconButton from "@material-ui/core/IconButton";
import MoreVertIcon from "@material-ui/icons/MoreVert";
import Button from "@material-ui/core/Button";
import CardActions from "@material-ui/core/CardActions";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContentText from "@material-ui/core/DialogContentText";
import TextField from "@material-ui/core/TextField";
import { Link } from "react-router-dom";
import axios from "axios";

const styles = theme => ({
  error: {
    color: "red",
    fontSize: 12
  },

  appBar: {
    position: "relative",
    backgroundColor: "#2196F3"
  },
  flex: {
    flex: 1
  }
});

class ProjectItem extends Component {
  constructor(props) {
    super(props);

    const { project } = this.props;
    this.state = {
      anchorEl: null,
      deleteProject: null,
      updateProject: null,
      projectName: project.projectName,
      id: project.id,
      projectIdentifier: project.projectIdentifier,
      description: project.description,
      createdAt: project.createdAt,
      errors: {}
    };
  }

  handleOpenMenuClick = event => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleCloseMenuClick = option => {
    this.setState({ anchorEl: null });
  };

  handleDeleteProject = project => {
    this.handleDeleteProjectClose(null);
    this.props.deleteProject(project);
  };

  handleDeleteProjectClick = event => {
    this.setState({ deleteProject: event.currentTarget });
  };

  handleDeleteProjectClose = option => {
    this.setState({ deleteProject: null });
  };

  handleUpdateProjectClick = event => {
    this.setState({ updateProject: event.currentTarget });
  };

  handleUpdateProjectClose = option => {
    this.handleCloseMenuClick(null);
    this.setState({ updateProject: null });
  };

  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    const updatedProject = {
      id: this.state.id,
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description
    };
    axios
      .patch("http://localhost:8080/api/project", updatedProject)
      .then(() => {
        this.handleUpdateProjectClose(null);
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
  };

  render() {
    const { classes, project } = this.props;

    const { anchorEl, deleteProject, updateProject, errors } = this.state;
    const menuOpen = Boolean(anchorEl);
    const deleteOpen = Boolean(deleteProject);
    const updateOpen = Boolean(updateProject);
    // console.log("errors: " + JSON.stringify(errors));

    return (
      <div className="col-md-6 col-lg-4 item">
        <Card>
          <CardHeader
            action={
              <Fragment>
                <IconButton
                  aria-label="More"
                  aria-owns={menuOpen ? "long-menu" : undefined}
                  aria-haspopup="true"
                  onClick={this.handleOpenMenuClick}
                >
                  <MoreVertIcon />
                </IconButton>
                <Menu
                  id="long-menu"
                  anchorEl={anchorEl}
                  open={menuOpen}
                  onClose={this.handleCloseMenuClick}
                >
                  <MenuItem key="updateItem">
                    <span onClick={this.handleUpdateProjectClick}>
                      Update Project
                    </span>
                    <Dialog
                      open={updateOpen}
                      onClose={this.handleUpdateProjectClose}
                      aria-labelledby="alert-dialog-title"
                      aria-describedby="alert-dialog-description"
                    >
                      <DialogTitle id="alert-dialog-title">
                        {"Update Project"}
                      </DialogTitle>
                      <form onSubmit={this.handleSubmit}>
                        <DialogContent>
                          <TextField
                            autoFocus
                            margin="dense"
                            id="projectName"
                            name="projectName"
                            label="Project Name"
                            type="text"
                            fullWidth
                            onChange={this.handleChange}
                            value={this.state.projectName}
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
                          />
                          {errors.description && (
                            <span className={classes.error}>
                              {errors.description}
                            </span>
                          )}
                        </DialogContent>
                        <DialogActions>
                          <Button
                            onClick={this.handleUpdateProjectClose}
                            color="primary"
                          >
                            Cancel
                          </Button>
                          <Button color="primary" type="submit">
                            Update
                          </Button>
                        </DialogActions>
                      </form>
                    </Dialog>
                  </MenuItem>

                  <MenuItem key="deleteItem">
                    <span onClick={this.handleDeleteProjectClick}>
                      Delete Project
                    </span>
                    <Dialog
                      open={deleteOpen}
                      onClose={this.handleDeleteProjectClose}
                      aria-labelledby="alert-dialog-title"
                      aria-describedby="alert-dialog-description"
                    >
                      <DialogTitle id="alert-dialog-title">
                        {"Delete Project"}
                      </DialogTitle>
                      <DialogContent>
                        <DialogContentText id="alert-dialog-description">
                          Are you sure you want to delete:
                          {" " + project.projectName}
                        </DialogContentText>
                      </DialogContent>
                      <DialogActions>
                        <Button
                          onClick={this.handleDeleteProjectClose}
                          color="primary"
                        >
                          No
                        </Button>
                        <Button
                          onClick={this.handleDeleteProject.bind(this, project)}
                          color="primary"
                          autoFocus
                        >
                          Yes
                        </Button>
                      </DialogActions>
                    </Dialog>
                  </MenuItem>
                </Menu>
              </Fragment>
            }
            title={project.projectName}
            subheader={"created: " + project.createdAt}
          />
          <CardActions>
            <Link to={`/dashboard/${project.projectIdentifier}`}>
              <Button size="small" color="primary">
                Dashboard
              </Button>
            </Link>
          </CardActions>
        </Card>
      </div>
    );
  }
}
export default withStyles(styles)(ProjectItem);
