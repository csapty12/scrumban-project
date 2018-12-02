import React, { Component, Fragment } from "react";
import TextInput from "./TextInput";
import TextArea from "./TextArea";
import axios from "axios";
import SubmitButton from "./SubmitButton";

export default class ProjectForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      projectName: "",
      projectIdentifier: "",
      description: "",
      startDate: "",
      endDate: "",
      errors: {}
    };
  }
  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    const newProject = {
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate
    };

    axios
      .post("http://localhost:8080/api/project", newProject)
      .then(() => alert("thank you, your project has been created"))
      .catch(error => {
        const errorResponse = error.response.data;
        this.setState({
          errors: {
            projectName: errorResponse.projectName,
            projectIdentifier: errorResponse.projectIdentifier,
            description: errorResponse.description,
            startDate: errorResponse.startDate,
            endDate: errorResponse.endDate
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
              <h5 className="display-4 text-center">
                {this.props.type} Project
              </h5>
              <hr />
              <form onSubmit={this.handleSubmit}>
                <TextInput
                  type="text"
                  id="projectName"
                  placeholder="Project Name"
                  name="projectName"
                  value={this.state.projectName}
                  handleChange={this.handleChange}
                  onError={this.state.errors.projectName}
                />
                <TextInput
                  type="text"
                  placeholder="Project Identifier"
                  name="projectIdentifier"
                  value={this.state.projectIdentifier}
                  handleChange={this.handleChange}
                  onError={this.state.errors.projectIdentifier}
                />
                <TextArea
                  className="form-control form-control-sm"
                  placeholder="Project Description"
                  name="description"
                  description={this.state.description}
                  handleChange={this.handleChange}
                  onError={this.state.errors.description}
                />
                <h6>Start Date</h6>
                <TextInput
                  id="startDate"
                  type="date"
                  name="startDate"
                  value={this.state.startDate}
                  handleChange={this.handleChange}
                  onError={this.state.errors.startDate}
                />
                <TextInput
                  id="endDate"
                  type="date"
                  name="endDate"
                  value={this.state.endDate}
                  handleChange={this.handleChange}
                  onError={this.state.errors.endDate}
                />
                <SubmitButton />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
