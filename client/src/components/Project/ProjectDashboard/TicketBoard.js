import React, { Component } from "react";
// import { Link } from "react-router-dom";
import axios from "axios";
import SwimLane from "./SwimLane";

export default class Backlog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectTickets: [],
      swimLanes: [],
      swimLaneOrder: [],
      projectIdentifier: props.projectIdentifier,
      errors: {}
    };
  }

  componentDidMount() {
    axios
      .get(`http://localhost:8080/dashboard/${this.state.projectIdentifier}`)
      .then(json => {
        console.log("json response: " + JSON.stringify(json));
        this.setState({
          projectTickets: json.data.tickets,
          swimLanes: json.data.swimLanes,
          swimLaneOrder: json.data.swimLaneOrder
        });
      });
    // .catch(json => {
    //   this.setState({
    //     errors: {
    //       projectIdentifier: json.response.data.projectIdentifier
    //     }
    //   });
    // });
  }

  // handleTicketDelete = project => {
  //   const backlogTickets = this.state.backlog;
  //   const { projectIdentifier, projectSequence } = project;
  //   if (
  //     window.confirm(
  //       `Are you sure you want to delete ticket: ${projectSequence}`
  //     )
  //   ) {
  //     axios
  //       .delete(
  //         `http://localhost:8080/api/backlog/${projectIdentifier}/${projectSequence}`
  //       )
  //       .then(() => {
  //         let filteredTickets = backlogTickets.filter(
  //           item => item.projectSequence !== projectSequence
  //         );
  //         this.setState({ backlog: filteredTickets });
  //       });
  //   }
  // };

  // persistTicketToNewColumn = ticketAfterMove => {
  //   console.log("udpated ticket: " + JSON.stringify(ticketAfterMove));
  //   console.log("project identifier: " + this.state.projectIdentifier);
  //   axios.post(
  //     `http://localhost:8080/api/backlog/${this.state.projectIdentifier}`,
  //     ticketAfterMove
  //   );
  // };

  render() {
    // console.log("this.allTickets: " + JSON.stringify(this.state.allTickets));
    // console.log("this.columns" + JSON.stringify(this.state.columns));
    // console.log("this.columnOrder" + JSON.stringify(this.state.columnOrder));

    return (
      <div className="container-fluid">
        <section className="card-horizontal-scrollable-container">
          {this.state.swimLaneOrder.map((swimLaneId, index) => {
            const swimLane = this.state.swimLanes[index][swimLaneId];
            console.log("json of swimlane: " + JSON.stringify(swimLane));
            const tickets = swimLane.ticketIds.map(
              (ticketId, index) => this.state.projectTickets[0][ticketId]
            );
            console.log("tasks: " + JSON.stringify(tickets));
            return (
              <SwimLane
                key={swimLane.id}
                swimLane={swimLane}
                tickets={tickets}
              />
            );
          })}
        </section>
      </div>
    );
  }
}
