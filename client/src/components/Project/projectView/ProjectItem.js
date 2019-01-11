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

const styles = theme => ({});

class ProjectItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      anchorEl: null,
      deleteProject: null,
      updateProject: null
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
    this.setState({ updateProject: null });
  };

  render() {
    const { classes, project } = this.props;
    console.log("project: " + JSON.stringify(project));
    const { anchorEl, deleteProject, updateProject } = this.state;
    const menuOpen = Boolean(anchorEl);
    const deleteOpen = Boolean(deleteProject);
    const updateOpen = Boolean(updateProject);
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
                      <DialogContent>
                        <TextField
                          autoFocus
                          margin="dense"
                          id="name"
                          name="projectName"
                          label="Project Name"
                          type="text"
                          value={project.projectName}
                          fullWidth
                          onChange={this.handleChange}
                        />
                        <TextField
                          id="standard-multiline-static"
                          name="description"
                          label="Project Description"
                          multiline
                          rows="4"
                          margin="normal"
                          value={project.description}
                          fullWidth
                          onChange={this.handleChange}
                        />
                        <TextField
                          margin="dense"
                          id="name"
                          name="startDate"
                          label="Start Date"
                          type="date"
                          value={project.startDate}
                          fullWidth
                          InputLabelProps={{
                            shrink: true
                          }}
                          onChange={this.handleChange}
                        />
                      </DialogContent>
                      <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                          Cancel
                        </Button>
                        <Button
                          onClick={this.handleClose}
                          color="primary"
                          type="submit"
                        >
                          Create
                        </Button>
                      </DialogActions>
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
            subheader={"created: " + project.startDate}
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
