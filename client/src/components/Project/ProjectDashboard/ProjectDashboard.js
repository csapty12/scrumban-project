import React, { Component } from "react";
import "./projectDashboard.css";
import "font-awesome/css/font-awesome.min.css";
import Backlog from "./Backlog";

class ProjectDashboard extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { projectIdentifier } = this.props.match.params;
    return (
      <div className="project">
        <div className="container">
          <div className="row ">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Project Dashboard</h5>
              <hr />
            </div>
          </div>
        </div>
        <Backlog projectIdentifier={projectIdentifier} />
      </div>
    );
  }
}

export default ProjectDashboard;
