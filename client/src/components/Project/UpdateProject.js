import React, { Component } from "react";
import axios from "axios";
// import classnames from "classnames";

class UpdateProject extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectName: "",
      project: {}
    };
  }
  componentDidMount() {
    const { projectIdentifier } = this.props.match.params;
    const res = axios
      .get(`http://localhost:8080/api/project/${projectIdentifier}`)
      .then(json =>
        this.setState({
          project: json.data
        })
      );
  }
  render() {
    const currentProject = this.state.project;
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Create / Edit Project</h5>
              <hr />
              <form action="">
                <div className="form-group">
                  <input
                    className="form-control form-control-sm"
                    type="text"
                    placeholder="Project Name"
                    name="projectName"
                    value={currentProject.projectName}
                  />
                </div>
                <div className="form-group">
                  <input
                    className="form-control form-control-sm"
                    type="text"
                    placeholder="Project Identifier"
                    name="projectIdentifier"
                    value={currentProject.projectIdentifier}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className="form-control form-control-sm"
                    placeholder="Project Description"
                    name="description"
                    value={currentProject.description}
                  />
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-sm"
                    name="startDate"
                    value={currentProject.startDate}
                  />
                </div>

                <h6>End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-sm"
                    name="endDate"
                    value={currentProject.endDate}
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
