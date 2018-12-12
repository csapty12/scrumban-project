import React, { Component } from "react";
import "./projectDashboard.css";
import "font-awesome/css/font-awesome.min.css";

class ProjectDashboard extends Component {
  render() {
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Project Dashboard</h5>
              <hr />
            </div>
          </div>
        </div>
        <div class="container-fluid">
          <div class="row">
            <div className="col-md-3">
              <h4 className="display-5 text-center title-backlog__border">
                Backlog
              </h4>
              <div class="text-center button__add-ticket">
                Add Ticket &#x2b;
              </div>
            </div>

            <div className="col-md-3">
              <h4 className="display-5 text-center title-todo__border">
                To Do
              </h4>
            </div>
            <div className="col-md-3">
              <h4 className="display-5 text-center title-inprogress__border">
                In Progress
              </h4>
            </div>
            <div className="col-md-3">
              <h4 className="display-5 text-center title-testing__border">
                Testing
              </h4>
            </div>
            {/* 
          <div className="col-md-2">
              <h4 className="display-5 text-center title-done__border">Done</h4>
            </div>
            <div className="col-md-2">
              <h4 className="display-5 text-center title-blocked__border">
                 Blocked
               </h4>
            </div> 
           */}
          </div>
        </div>
      </div>
    );
  }
}

export default ProjectDashboard;
