import React, { Component } from "react";
import "./projectDashboard.css";
import "font-awesome/css/font-awesome.min.css";
import TicketBoard from "./TicketBoard";

class ProjectDashboard extends Component {
  render() {
    const activeProject = this.props.match.params.projectIdentifier;
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
        {<TicketBoard projectIdentifier={activeProject} />}
      </div>
    );
  }
}

export default ProjectDashboard;
