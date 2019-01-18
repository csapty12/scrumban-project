import React, { Component } from "react";
import Ticket from "./Tickets/Ticket";

export default class InnerList extends Component {
  render() {
    return this.props.tickets.map((ticket, index) => (
      <Ticket
        key={ticket.id}
        ticket={ticket}
        index={index}
        removeTicket={this.props.removeTicket}
        swimLaneId={this.props.swimLaneId}
      />
    ));
  }
}
