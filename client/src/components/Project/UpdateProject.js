import React, { Component } from "react";
import { getProject } from "../../actions/ProjectActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
// import classnames from "classnames";

class UpdateProject extends Component {
  componentDidMount() {
    const { projectIdentifier } = this.props.match.params;
    console.log("id is: " + projectIdentifier);
    this.props.getProject(projectIdentifier, this.props.history);
  }
  render() {
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
                  />
                </div>
                <div className="form-group">
                  <input
                    className="form-control form-control-sm"
                    type="text"
                    placeholder="Project Identifier"
                    name="projectIdentifier"
                    disabled
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className="form-control form-control-sm"
                    placeholder="Project Description"
                    name="description"
                  />
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-sm"
                    name="startDate"
                  />
                </div>

                <h6>End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-sm"
                    name="endDate"
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
UpdateProject.propTypes = {
  getProject: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  project: state.project.project
});
export default connect(
  mapStateToProps,
  { getProject }
)(UpdateProject);
