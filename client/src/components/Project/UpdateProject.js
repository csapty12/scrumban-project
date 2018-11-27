import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import axios from "axios";
import classnames from "classnames";

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

  onChange = event => {
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
                <div className="form-group">
                  <input
                    className={classnames("form-control form-control-sm", {
                      "is-invalid": errors.projectName
                    })}
                    type="text"
                    placeholder="Project Name"
                    name="projectName"
                    value={this.state.projectName}
                    onChange={this.onChange}
                  />
                  {errors.projectName && (
                    <div className="invalid-feedback">{errors.projectName}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    className="form-control form-control-sm"
                    type="text"
                    name="projectIdentifier"
                    value={this.state.projectIdentifier}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className={classnames("form-control form-control-sm", {
                      "is-invalid": errors.description
                    })}
                    placeholder="Project Description"
                    name="description"
                    value={this.state.description}
                    onChange={this.onChange}
                  />
                  {errors.description && (
                    <div className="invalid-feedback">{errors.description}</div>
                  )}
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className={classnames("form-control form-control-sm", {
                      "is-invalid": errors.startDate
                    })}
                    name="startDate"
                    value={this.state.startDate}
                    onChange={this.onChange}
                  />
                  {errors.startDate && (
                    <div className="invalid-feedback">{errors.startDate}</div>
                  )}
                </div>
                <h6>End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className={classnames("form-control form-control-sm", {
                      "is-invalid": errors.endDate
                    })}
                    name="endDate"
                    value={this.state.endDate}
                    onChange={this.onChange}
                  />
                  {errors.endDate && (
                    <div className="invalid-feedback">{errors.endDate}</div>
                  )}
                </div>
                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default UpdateProject;
