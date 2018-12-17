import React, { Component } from "react";
import "./projectDashboard.css";
import "font-awesome/css/font-awesome.min.css";
import { Link } from "react-router-dom";
import axios from "axios";
import BacklogTicket from "./Tickets/BacklogTicket";

class ProjectDashboard extends Component {
  constructor(props) {
    super(props);
    const { projectIdentifier } = this.props.match.params;
    this.state = {
      allTickets: [],
      backlog: [],
      todo: [],
      inProgress: [],
      testing: [],
      projectIdentifier: projectIdentifier
    };
  }
  // axios request to the backend to fetch current data,  for update for ticket.
  componentDidMount() {
    axios
      .get(`http://localhost:8080/api/backlog/${this.state.projectIdentifier}`)
      .then(json => {
        json.data.forEach(item => {
          if (item.status === "BACKLOG") {
            this.setState({
              backlog: this.state.backlog.concat(item)
            });
          }
          if (item.status === "TO_DO") {
            this.setState({
              todo: this.state.todo.concat(item)
            });
          }
        });
      });
  }

  render() {
    console.log("all todo now: " + JSON.stringify(this.state.todo));
    const backlog = this.state.backlog;
    console.log("all backlog now: " + JSON.stringify(backlog));

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
        <div className="container-fluid">
          <div className="row">
            <div className="col-md-3">
              <h4 className="display-5 text-center title-backlog__border">
                Backlog
              </h4>
              <Link to={`/addProjectTask/${this.state.projectIdentifier}`}>
                <div className="card text-center">
                  <div className="card-header">Add Ticket &#x2b;</div>
                </div>
              </Link>
              {backlog.map(ticket => (
                <BacklogTicket key={ticket.id} ticket={ticket} />
              ))}
              <div className="card">
                <div className="card-body">
                  <a href="#">
                    <h6 className="card-title">brr-q23</h6>
                  </a>
                  <p className="card-text ticket-text">
                    With supporting text below as a natural lead-in to
                    additional content.
                  </p>
                </div>
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
            <divclassName="col-md-2">
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
