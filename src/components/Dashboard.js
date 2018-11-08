/*this is where you can access all projects
this is where you can click the form to create a new project.*/
import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";

class Dashboard extends Component {
  render() {
    return (
    	<div className="projects">
			<div className="container">
				<div className="row">
					<div className="col-md-12">
					<h1 className="display-4 text-center">All Projects</h1>
					<br />
					<a href="ProjectForm.html" className="btn btn-lg btn-info">
							Create a Project <i className="fa fa-plus-circle"></i>
					</a>
					<br />
					<hr />
					<ProjectItem />
					</div>
				</div>
			</div>
      	</div>
    );
  }
}
export default Dashboard;
