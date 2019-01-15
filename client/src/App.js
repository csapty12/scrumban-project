import React, { Component } from "react";
import "./App.css";
import "./projectItem.css";
import Dashboard from "./components/Dashboard";
import Navbar from "./components/Layout/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Route } from "react-router-dom";
import UpdateProject from "./components/Project/projectView/UpdateProject";
import ProjectDashboard from "./components/Project/ProjectDashboard/ProjectDashboard";
import { Provider } from "react-redux";
import store from "./Store";
import AddTicket from "./components/Project/ProjectDashboard/Tickets/AddTicket";
import UpdateTicket from "./components/Project/ProjectDashboard/Tickets/UpdateTicket";

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Navbar />
            <Route exact path="/dashboard" component={Dashboard} />
            <Route
              exact
              path="/updateProject/:projectIdentifier"
              component={UpdateProject}
            />
            <Route
              exact
              path="/dashboard/:projectIdentifier"
              component={ProjectDashboard}
            />
            <Route
              exact
              path="/addProjectTask/:projectIdentifier"
              component={AddTicket}
            />
            <Route
              exact
              path="/updateProjectTicket/:projectIdentifier/:ticketIdentifier"
              component={UpdateTicket}
            />
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
