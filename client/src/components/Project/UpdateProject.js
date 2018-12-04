import React, { Component } from "react";
import axios from "axios";
import classnames from "classnames";
import TextInput from "./Forms/TextInput";
import TextArea from "./Forms/TextArea";
import SubmitButton from "./Forms/SubmitButton";
class UpdateProject extends Component {
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

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    const { projectIdentifier } = this.props.match.params;

    axios
      .get(`http://localhost:8080/api/project/${projectIdentifier}`)
      .then(json =>
        this.setState({
          id: json.data.id,
          projectName: json.data.projectName,
          projectIdentifier: json.data.projectIdentifier,
          description: json.data.description,
          startDate: json.data.startDate,
          endDate: json.data.endDate
        })
      )
      .catch(err => {
        this.props.history.push("/dashboard");
      });
  }

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = event => {
    event.preventDefault();
    const updatedProjectState = {
      id: this.state.id,
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate
    };
    console.log("project id: " + JSON.stringify(updatedProjectState));
    axios
      .post("http://localhost:8080/api/project", updatedProjectState)
      .then(() => alert("thank you, your project has been updated"))
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
    const { errors } = this.state;
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Create / Edit Project</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <TextInput
                  type="text"
                  id="projectName"
                  placeholder="Project Name"
                  name="projectName"
                  value={this.state.projectName}
                  handleChange={this.handleChange}
                  onError={errors.projectName}
                />
                <TextInput
                  type="text"
                  placeholder="Project Identifier"
                  name="projectIdentifier"
                  value={this.state.projectIdentifier}
                  handleChange={this.handleChange}
                  onError={errors.projectIdentifier}
                  disabled
                />
                <TextArea
                  className="form-control form-control-sm"
                  placeholder="Project Description"
                  name="description"
                  description={this.state.description}
                  handleChange={this.handleChange}
                  onError={errors.description}
                />
                <h6>Start Date</h6>
                <TextInput
                  id="startDate"
                  type="date"
                  name="startDate"
                  value={this.state.startDate}
                  handleChange={this.handleChange}
                  onError={errors.startDate}
                />
                <TextInput
                  id="endDate"
                  type="date"
                  name="endDate"
                  value={this.state.endDate}
                  handleChange={this.handleChange}
                  onError={errors.endDate}
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

export default UpdateProject;
