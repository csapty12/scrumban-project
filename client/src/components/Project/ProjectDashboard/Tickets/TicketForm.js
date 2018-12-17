import React, { Component } from "react";
import TextInput from "../../Forms/TextInput";
import TextArea from "../../Forms/TextArea";
import axios from "axios";
import SubmitButton from "../../Forms/SubmitButton";

export default class TicketForm extends Component {
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
  handleChange = event => {
    // console.log(event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  componentDidMount() {
    // axios request to the backend to fetch current data,  for update for ticket.
  }

  handleSubmit = event => {
    const projectIdentifier = this.props.projectIdentifier;
    event.preventDefault();
    const newTicket = {
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      status: this.state.status,
      priority: this.state.priority
    };

    console.log("new ticket: " + JSON.stringify(newTicket));
    axios
      .post(`http://localhost:8080/api/backlog/${projectIdentifier}`, newTicket)
      .then(() => alert("thank you, the ticket has been created"))
      .catch(error => {
        const errorResponse = error.response.data;
        this.setState({
          errors: {
            summary: errorResponse.projectName,
            acceptanceCriteria: errorResponse.projectIdentifier,
            status: errorResponse.description,
            priority: errorResponse.startDate
          }
        });
      });
  };

  render() {
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Create Ticket</h5>
              <hr />
              <form onSubmit={this.handleSubmit}>
                <TextInput
                  type="text"
                  id="ticketSummary"
                  placeholder="Ticket Summary"
                  name="summary"
                  value={this.state.summary}
                  handleChange={this.handleChange}
                />
                <TextArea
                  className="form-control form-control-sm"
                  placeholder="Acceptance Criteria"
                  name="acceptanceCriteria"
                  value={this.state.acceptanceCriteria}
                  handleChange={this.handleChange}
                />
                <div className="form-group">
                  <select
                    className="form-control form-control-sm"
                    name="status"
                    onChange={this.handleChange}
                    value={this.state.status}
                  >
                    <option value="">Select Status</option>
                    <option value="BACKLOG">Backlog</option>
                    <option value="TO_DO">To Do</option>
                  </select>
                </div>
                <div className="form-group">
                  <select
                    className="form-control form-control-sm"
                    name="priority"
                    value={this.state.priority}
                    onChange={this.handleChange}
                  >
                    <option>Select Priority</option>
                    <option value="HIGH">High</option>
                    <option value="MEDIUM">Medium</option>
                    <option value="LOW">Low</option>
                  </select>
                  <SubmitButton />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
