import React, { Component } from "react";
import TicketForm from "./TicketForm";

class AddTicket extends Component {
  render() {
    const { projectIdentifier } = this.props.match.params;
    return <TicketForm type="Create" projectIdentifier={projectIdentifier} />;
  }
}
export default AddTicket;
