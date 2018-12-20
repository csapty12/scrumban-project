import React, { Component, Fragment } from "react";
import TextInput from "./TextInput";
import TextArea from "./TextArea";
import axios from "axios";
import SubmitButton from "./SubmitButton";
// import Slug from "react-slug";

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

  componentDidMount = () => {
    const { projectIdentifier } = this.props;
    // console.log("pojectId: " + JSON.stringify(projectIdentifier));
    if (projectIdentifier !== undefined) {
      // console.log("fetching project with ID: " + projectIdentifier);
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
        );
    }
  };

  handleChange = event => {
    // console.log(event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    let slugify = require("slugify");
    const projectIdentifierSlug = slugify(this.state.projectName);
    // const projectId = this.state.projectName.match(/\b(\w)/g).join("");
    // console.log("projectIdentifier: " + projectId);

    const newProject = {
      id: this.state.id,
      projectName: this.state.projectName,
      projectIdentifier: projectIdentifierSlug,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate
    };

    console.log("new project: " + JSON.stringify(newProject));

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
