import React, { Component } from "react";
import "./App.css";
import "./projectItem.css";
import Dashboard from "./components/Dashboard";
import Navbar from "./components/Layout/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from "./components/Project/projectView/AddProject";
import UpdateProject from "./components/Project/projectView/UpdateProject";
import ProjectDashboard from "./components/Project/ProjectDashboard/ProjectDashboard";
import { Provider } from "react-redux";
import store from "./Store";
import AddTicket from "./components/Project/ProjectDashboard/Tickets/AddTicket";

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Navbar />
            <Route exact path="/dashboard" component={Dashboard} />
            <Route exact path="/addProject" component={AddProject} />
            <Route
              exact
              path="/updateProject/:projectIdentifier"
              component={UpdateProject}
            />
            <Route
              exact
              path="/projectDashboard/:projectIdentifier"
              component={ProjectDashboard}
            />
            <Route
              exact
              path="/addProjectTask/:projectIdentifier"
              component={AddTicket}
            />
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
