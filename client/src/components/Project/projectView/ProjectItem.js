//this is what a project object will look like

import React, { Component } from "react";
import { Link } from "react-router-dom";

class ProjectItem extends Component {
  handleDelete = project => {
    this.props.deleteProject(project);
  };
  render() {
    const { project } = this.props;
    return (
      <div className="col-md-6 col-lg-4 item">
        <div className="project-item">
          <div className="projectTitle text-center">
            <h1>{project.projectName}</h1>
            <h6>{project.projectIdentifier}</h6>
          </div>
          <div className="list-group list-of-actions">
            <a href="#" className="list-group-item list-group-item-action">
              Project Dashboard
            </a>
            <Link
              to={`/updateProject/${project.projectIdentifier}`}
              className="list-group-item list-group-item-action"
            >
              Update Project Info
            </Link>
            <li
              className="list-group-item list-group-item-action"
              onClick={this.handleDelete.bind(this, project)}
            >
              Delete Project
            </li>
          </div>
        </div>
      </div>
    );
  }
}
export default ProjectItem;
