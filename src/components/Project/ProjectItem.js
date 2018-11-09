//this is what a project object will look like

import React, { Component } from "react";

class ProjectItem extends Component {
  render() {
    return (
      <section className="gallery-block grid-gallery">
      <div className="container">
          <div className="row">
              <div className="col-md-6 col-lg-4 item">
                <div className="project-item">
                  <div className="projectTitle text-center">
                      <h1>Scrumban Project</h1>
                  </div>
                  <div className="list-group list-of-actions">
                    <a href="#" className="list-group-item list-group-item-action">Project Dashboard</a>
                    <a href="#" className="list-group-item list-group-item-action">Update Project Info</a>
                    <a href="#" className="list-group-item list-group-item-action">Delete Project</a>
                  </div>
                  </div>
              </div>
              <div className="col-md-6 col-lg-4 item">
                <div className="project-item">
                  <div className="list-group list-of-actions">
                    <a href="#" className="list-group-item list-group-item-action">Project Dashboard</a>
                    <a href="#" className="list-group-item list-group-item-action">Update Project Info</a>
                    <a href="#" className="list-group-item list-group-item-action">Delete Project</a>
                  </div>
                  </div>
              </div>
              <div className="col-md-6 col-lg-4 item">
                <div className="project-item">
                  <div className="list-group list-of-actions">
                    <a href="#" className="list-group-item list-group-item-action">Project Dashboard</a>
                    <a href="#" className="list-group-item list-group-item-action">Update Project Info</a>
                    <a href="#" className="list-group-item list-group-item-action">Delete Project</a>
                  </div>
                  </div>
              </div>
          </div>
      </div>
    </section>
    );
  }
}
export default ProjectItem;
