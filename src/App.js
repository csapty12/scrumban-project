import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Navbar from "./components/Layout/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Navbar />
        <Dashboard />
      </div>
    );
  }
}

export default App;
