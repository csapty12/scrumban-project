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
import { Link } from "react-router-dom";
import ProjectDialog from "./ProjectDialog";
import Project from "../../../model/Project";

const styles = theme => ({});

class ProjectItem extends Component {
  constructor(props) {
    super(props);

    const { project } = this.props;
    const currentProject = new Project();
    currentProject.id = project.id;
    currentProject.projectName = project.projectName;
    currentProject.projectIdentifier = project.projectIdentifier;
    currentProject.description = project.description;
    currentProject.createdAt = project.createdAt;
    this.state = {
      anchorEl: null,
      deleteProject: null,
      updateProject: null,
      project: currentProject
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

  handleDeleteProjectClose = () => {
    this.setState({ deleteProject: null });
  };

  handleUpdateProjectClick = event => {
    this.setState({ updateProject: event.currentTarget });
  };

  handleUpdateProjectClose = () => {
    this.handleCloseMenuClick(null);
    this.setState({ updateProject: null });
  };

  updateProjectInfo = projectInfo => {
    this.setState({ project: projectInfo });
  };

  render() {
    const { project } = this.props;

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

                    {updateOpen && (
                      <ProjectDialog
                        type="Update"
                        onClose={this.handleUpdateProjectClose}
                        projectInfo={this.state.project}
                        updateProjectInfo={this.updateProjectInfo}
                      />
                    )}
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
