import React, { Component } from "react";
import axios from "axios";
// import classnames from "classnames";

class UpdateProject extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectName: "",
      projectIdentifier: "",
      description: "",
      startDate: "",
      endDate: ""
    };
  }
  componentDidMount() {
    const { projectIdentifier } = this.props.match.params;
    axios
      .get(`http://localhost:8080/api/project/${projectIdentifier}`)
      .then(json =>
        this.setState({
          projectName: json.data.projectName,
          projectIdentifier: json.data.projectIdentifier,
          description: json.data.description,
          startDate: json.data.startDate,
          endDate: json.data.endDate
        })
      );
  }

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = event => {
    event.preventDefault();
    const updatedProjectState = {
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate
    };
    console.log("project id: " + JSON.stringify(updatedProjectState));
  };
  render() {
    // const currentProject = this.state.project;
    // console.log("currenProject: " + JSON.stringify(currentProject));
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
                    className="form-control form-control-sm"
                    type="text"
                    placeholder="Project Name"
                    name="projectName"
                    value={this.state.projectName}
                    onChange={this.onChange}
                  />
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
                    className="form-control form-control-sm"
                    placeholder="Project Description"
                    name="description"
                    value={this.state.description}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-sm"
                    name="startDate"
                    value={this.state.startDate}
                    onChange={this.onChange}
                  />
                </div>
                <h6>End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-sm"
                    name="endDate"
                    value={this.state.endDate}
                    onChange={this.onChange}
                  />
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
