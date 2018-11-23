/*this is where you can access all projects
this is where you can click the form to create a new project.*/
import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import CreateProjectButton from "./Project/CreateProjectButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/ProjectActions";
import PropTypes from "prop-types";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    const allProjects = this.props.project.projects;
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">All Projects</h1>
              <br />
              <CreateProjectButton />
              <br />
              <hr />
              <section className="gallery-block grid-gallery">
                <div className="container">
                  <div className="row">
                    {allProjects.map(project => (
                      <ProjectItem key={project.id} project={project} />
                    ))}
                  </div>
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
Dashboard.propTypes = {
  project: PropTypes.object.isRequired,
  getProjects: PropTypes.func.isRequired
};
const mapStateToProps = state => ({
  project: state.project
});
export default connect(
  mapStateToProps,
  { getProjects }
)(Dashboard);
