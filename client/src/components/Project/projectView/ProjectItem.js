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
import ProjectDialog from "./ProjectDialog";
import DeleteItem from "./DeleteItem";
import store from "../../../store";
import { SET_ACTIVE_PROJECT } from "../../../actions/Types";
import { Link } from "react-router-dom";
import { Redirect } from "react-router-dom";

const styles = theme => ({});

class ProjectItem extends Component {
  constructor(props) {
    super(props);

    const { project } = this.props;

    this.state = {
      anchorEl: null,
      deleteProject: null,
      updateProject: null,
      project: project
    };
  }

  handleOpenMenuClick = event => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleCloseMenuClick = option => {
    this.setState({ anchorEl: null });
  };

  handleDeleteProject = project => {
    this.props.deleteProject(project);
  };

  handleDeleteProjectClick = event => {
    this.setState({ deleteProject: event.currentTarget });
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

  activateProject = projectIdentifier => {
    console.log("am in here, with projectID: " + projectIdentifier);
    store.dispatch({
      type: SET_ACTIVE_PROJECT,
      payload: { activeProject: projectIdentifier }
    });
    const serializedState = projectIdentifier;
    localStorage.setItem("activeProject", serializedState);

    return;
  };

  render() {
    const { project } = this.props;

    const { anchorEl, deleteProject, updateProject } = this.state;
    const menuOpen = Boolean(anchorEl);
    const deleteOpen = Boolean(deleteProject);
    const updateOpen = Boolean(updateProject);
    return (
      <div className="col-md-6 col-lg-4 item">
        <Card onClick={() => this.activateProject(project.projectIdentifier)}>
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

                    {deleteOpen && (
                      <DeleteItem
                        project={project}
                        handleDeleteProject={this.handleDeleteProject}
                      />
                    )}
                  </MenuItem>
                </Menu>
              </Fragment>
            }
            title={
              <Link to={`/dashboard/${project.projectIdentifier}`}>
                {project.projectName}
              </Link>
            }
            subheader={"created: " + project.createdAt}
          />
        </Card>
      </div>
    );
  }
}
export default withStyles(styles)(ProjectItem);
