import React, { Component } from "react";
import TicketForm from "./TicketForm";

export default class UpdateTicket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      summary: "",
      acceptanceCriteria: "",
      status: "",
      priority: "",
      errors: {}
    };
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { projectIdentifier, ticketIdentifier } = this.props.match.params;
    return (
      <div>
        <TicketForm
          type="Update"
          projectIdentifier={projectIdentifier}
          ticketIdentifier={ticketIdentifier}
        />
      </div>
    );
  }
}
