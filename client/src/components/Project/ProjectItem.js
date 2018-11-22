//this is what a project object will look like

import React, { Component } from "react";

class ProjectItem extends Component {
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
            <a href="#" className="list-group-item list-group-item-action">
              Update Project Info
            </a>
            <a href="#" className="list-group-item list-group-item-action">
              Delete Project
            </a>
          </div>
        </div>
      </div>
    );
  }
}
export default ProjectItem;
