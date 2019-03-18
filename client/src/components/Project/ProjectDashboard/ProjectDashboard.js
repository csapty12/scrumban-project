import React, { Component } from "react";
import "./projectDashboard.css";
import "font-awesome/css/font-awesome.min.css";
import TicketBoard from "./TicketBoard";

class ProjectDashboard extends Component {
  render() {
    const activeProject = this.props.match.params.projectIdentifier;
    return (
      <div className="project">
        {<TicketBoard projectIdentifier={activeProject} />}
      </div>
    );
  }
}

export default ProjectDashboard;
