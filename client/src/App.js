import React, { Component } from "react";
import "./App.css";
import "./projectItem.css";
import Dashboard from "./components/Project/projectView/Dashboard";
import Navbar from "./components/Layout/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Route } from "react-router-dom";
import ProjectDashboard from "./components/Project/ProjectDashboard/ProjectDashboard";
import Landing from "./components/Layout/Landing";
// import { Provider } from "react-redux";
// import store from "./Store";

class App extends Component {
  render() {
    return (
      // <Provider store={store}>
      <Router>
        <div className="App">
          <Navbar />
          <Route exact path="/" component={Landing} />
          <Route exact path="/dashboard" component={Dashboard} />
          <Route
            exact
            path="/dashboard/:projectIdentifier"
            component={ProjectDashboard}
          />
        </div>
      </Router>
      // </Provider>
    );
  }
}

export default App;
