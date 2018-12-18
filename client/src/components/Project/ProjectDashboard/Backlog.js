import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Ticket from "./Tickets/Ticket";
class Backlog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allTickets: [],
      backlog: [],
      todo: [],
      inProgress: [],
      testing: [],
      done: [],
      blocked: [],
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
          if (item.status === "IN_PROGRESS") {
            this.setState({
              inProgress: this.state.inProgress.concat(item)
            });
          }
          if (item.status === "TESTING") {
            this.setState({
              testing: this.state.testing.concat(item)
            });
          }
          if (item.status === "DONE") {
            this.setState({
              done: this.state.done.concat(item)
            });
          }
          if (item.status === "BLOCKED") {
            this.setState({
              blocked: this.state.blocked.concat(item)
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
    const { backlog, todo, inProgress, testing, done, blocked } = this.state;

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
                  <Ticket key={ticket.id} ticket={ticket} />
                ))}
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-todo__border">
                  To Do
                </h4>
                {todo.map(ticket => (
                  <Ticket key={ticket.id} ticket={ticket} />
                ))}
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-inprogress__border">
                  In Progress
                </h4>
                {inProgress.map(ticket => (
                  <Ticket key={ticket.id} ticket={ticket} />
                ))}
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-testing__border">
                  Testing
                </h4>
                {testing.map(ticket => (
                  <Ticket key={ticket.id} ticket={ticket} />
                ))}
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-done__border">
                  Done
                </h4>
                {done.map(ticket => (
                  <Ticket key={ticket.id} ticket={ticket} />
                ))}
              </div>
            </div>
            <div className="card--content col-10 col-lg-3">
              <div className="card-vertical-scroll-enabled">
                <h4 className="display-5 text-center title-blocked__border">
                  Blocked
                </h4>
                {blocked.map(ticket => (
                  <Ticket key={ticket.id} ticket={ticket} />
                ))}
              </div>
            </div>
          </section>
        </div>
      </div>
    );
  }
}

export default Backlog;
