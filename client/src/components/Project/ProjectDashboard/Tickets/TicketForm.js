import React, { Component } from "react";
import TextInput from "../../Forms/TextInput";
import TextArea from "../../Forms/TextArea";
import axios from "axios";
import SubmitButton from "../../Forms/SubmitButton";

export default class TicketForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      id: "",
      projectSequence: "",
      summary: "",
      acceptanceCriteria: "",
      status: "",
      priority: "",
      errors: {}
    };
  }
  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  // componentDidMount = () => {
  //   const { projectIdentifier, ticketIdentifier } = this.props;
  //   if (projectIdentifier !== undefined) {
  //     if (ticketIdentifier !== undefined) {
  //       axios
  //         .get(
  //           `http://localhost:8080/api/backlog/${projectIdentifier}/${ticketIdentifier}`
  //         )
  //         .then(json => {
  //           // console.log(JSON.stringify(json.data.id));
  //           this.setState({
  //             id: json.data.id,
  //             summary: json.data.summary,
  //             acceptanceCriteria: json.data.acceptanceCriteria,
  //             status: json.data.status,
  //             priority: json.data.priority
  //           });
  //         });
  //     }
  //   }
  // };

  // handleSubmit = event => {
  //   const projectIdentifier = this.props.projectIdentifier;
  //   console.log("project identifier: " + projectIdentifier);
  //   event.preventDefault();
  //   const newTicket = {
  //     id: this.state.id,
  //     summary: this.state.summary,
  //     acceptanceCriteria: this.state.acceptanceCriteria,
  //     status: this.state.status,
  //     priority: this.state.priority
  //   };
  //   console.log(JSON.stringify(newTicket));

  //   axios
  //     .post(`http://localhost:8080/api/backlog/${projectIdentifier}`, newTicket)
  //     .then(() => {
  //       let type = "created";
  //       if (newTicket.id !== "") {
  //         type = "updated";
  //       }
  //       alert("thank you, the ticket has been " + type);
  //     })

  //     .catch(error => {
  //       const errorResponse = error.response.data;
  //       this.setState({
  //         errors: {
  //           summary: errorResponse.summary,
  //           acceptanceCriteria: errorResponse.acceptanceCriteria,
  //           status: errorResponse.status,
  //           priority: errorResponse.priority
  //         }
  //       });
  //     });
  // };

  render() {
    const errors = this.state.errors;

    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">
                {this.props.type} Ticket
              </h5>
              <hr />
              <form onSubmit={this.handleSubmit}>
                <TextInput
                  type="text"
                  id="ticketSummary"
                  placeholder="Ticket Summary"
                  name="summary"
                  value={this.state.summary}
                  handleChange={this.handleChange}
                  onError={errors.summary}
                />
                <TextArea
                  className="form-control form-control-sm"
                  placeholder="Acceptance Criteria"
                  name="acceptanceCriteria"
                  description={this.state.acceptanceCriteria}
                  handleChange={this.handleChange}
                  onError={errors.acceptanceCriteria}
                />

                <div className="form-group">
                  <select
                    className="form-control form-control-sm"
                    name="status"
                    onChange={this.handleChange}
                    value={this.state.status}
                  >
                    <option>Status</option>
                    <option value="BACKLOG">Backlog</option>
                    <option value="TO_DO">To Do</option>
                    {this.state.id !== "" && (
                      <React.Fragment>
                        <option value="IN_PROGRESS">In Progress</option>
                        <option value="TESTING">In Test</option>
                        <option value="DONE">Done</option>
                        <option value="BLOCKED">Blocked</option>
                      </React.Fragment>
                    )}
                  </select>
                </div>
                <div className="form-group">
                  <select
                    id="priorityId"
                    className="form-control form-control-sm"
                    name="priority"
                    value={this.state.priority}
                    onChange={this.handleChange}
                  >
                    <option>Priority</option>
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
