import React, { Component } from "react";
import TicketForm from "./TicketForm";

class AddTicket extends Component {
  constructor(props) {
    super(props);

    this.state = {
      projectIdentifier: "",
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
    const { errors } = this.state;
    const { projectIdentifier } = this.props.match.params;
    console.log("here: " + projectIdentifier);
    return (
      <TicketForm
        type="Create"
        errors={errors}
        projectIdentifier={projectIdentifier}
      />
    );
  }
}
export default AddTicket;
