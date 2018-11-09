import React, { Component } from "react";
import "./App.css";
import "./projectItem.css";
import Dashboard from "./components/Dashboard";
import Navbar from "./components/Layout/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from "./components/Project/AddProject";

class App extends Component {
  render() {
    return (
      <Router>
        <div className="App">
          <Navbar />
          <Dashboard />
          <Route path="/addProject" Component={AddProject} />
        </div>
      </Router>
    );
  }
}

export default App;
