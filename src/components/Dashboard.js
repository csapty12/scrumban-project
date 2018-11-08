/*this is where you can access all projects
this is where you can click the form to create a new project.*/
import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";

class Dashboard extends Component {
  render() {
    return (
      <div>
        <h1 className="alert alert-warning">Welcome to your dashboard.</h1>
        <ProjectItem />
      </div>
    );
  }
}
export default Dashboard;
