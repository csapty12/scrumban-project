import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Ticket from "./Tickets/Ticket";
class Backlog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      backlog: {},

      projectIdentifier: props.projectIdentifier,
      errors: {}
    };
  }

  componentDidMount() {
    axios
      .get(`http://localhost:8080/api/backlog/${this.state.projectIdentifier}`)
      .then(json => {
        console.log("json for ticket: " + JSON.stringify(json));
        // json.data.forEach(item => {
        //   if (item.status === "BACKLOG") {
        //     this.setState({
        //       backlog: this.state.backlog.concat(item)
        //     });
        //   }
        // });
      })
      .catch(json => {
        this.setState({
          errors: {
            projectIdentifier: json.response.data.projectIdentifier
          }
        });
      });
  }

  handleTicketDelete = project => {
    const backlogTickets = this.state.backlog;
    const { projectIdentifier, projectSequence } = project;
    if (
      window.confirm(
        `Are you sure you want to delete ticket: ${projectSequence}`
      )
    ) {
      axios
        .delete(
          `http://localhost:8080/api/backlog/${projectIdentifier}/${projectSequence}`
        )
        .then(() => {
          let filteredTickets = backlogTickets.filter(
            item => item.projectSequence !== projectSequence
          );
          this.setState({ backlog: filteredTickets });
        });
    }
  };

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
                {/*backlog.map(ticket => (
                  <Ticket
                    key={ticket.id}
                    ticket={ticket}
                    deleteTicket={this.handleTicketDelete}
                  />
                )) */}
              </div>
            </div>
          </section>
        </div>
      </div>
    );
  }
}

export default Backlog;
