import React, { Component } from "react";
import Ticket from "./Tickets/Ticket";

export default class InnerList extends Component {
  componentWillUpdate(nextProps) {
    if (nextProps.tickets === this.props.tickets) {
      return false;
    }
    return true;
  }
  render() {
    // console.log("this . props : " + JSON.stringify(this.props));
    return this.props.tickets.map((ticket, index) => (
      <Ticket
        key={index}
        ticket={ticket}
        index={index}
        removeTicket={this.props.removeTicket}
        swimLaneId={this.props.swimLaneId}
        handleChange={this.props.onChange}
      />
    ));
  }
}
