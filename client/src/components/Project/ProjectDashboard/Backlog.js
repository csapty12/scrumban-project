import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import BacklogTicket from "./Tickets/Ticket";
class Backlog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allTickets: [],
      backlog: [],
      todo: [],
      inProgress: [],
      testing: [],
      projectIdentifier: props.projectIdentifier,
      errors: {}
    };
  }

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
      })
      .catch(json => {
        this.setState({
          errors: {
            projectIdentifier: json.response.data.projectIdentifier
          }
        });
      });
  }

  render() {
    const backlog = this.state.backlog;
    const todo = this.state.todo;

    if (this.state.errors.projectIdentifier) {
      return (
        <div className="container alert alert-danger text-center" role="alert">
          {this.state.errors.projectIdentifier}
        </div>
      );
    }
    return (
      <div>
        <div className="container-fluid">
          <section className="card-horizontal-scrollable-container">
            <div className="card--content  col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
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
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-todo__border">
                  To Do
                </h4>
                {todo.map(ticket => (
                  <BacklogTicket key={ticket.id} ticket={ticket} />
                ))}
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-inprogress__border">
                  In Progress
                </h4>
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-testing__border">
                  Testing
                </h4>
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-done__border">
                  Done
                </h4>
              </div>
            </div>
          </section>
        </div>
      </div>
    );
  }
}

export default Backlog;
